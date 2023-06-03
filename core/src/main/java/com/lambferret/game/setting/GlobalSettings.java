package com.lambferret.game.setting;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambferret.game.command.Command;
import com.lambferret.game.manual.Manual;
import com.lambferret.game.quest.Quest;
import com.lambferret.game.save.SaveLoader;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.util.GsonDateFormatAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GlobalSettings {
    private static final Logger logger = LogManager.getLogger(GlobalSettings.class.getName());

    public static final String CONFIG_FILE_PATH = "./config.json";
    public static final int WIDTH_PIXEL = 2048;
    public static final int HEIGHT_PIXEL = 1152;
    public static Setting settings;
    public static final int MAXIMUM_SAVE = 3;
    public static boolean isDev = true;
    public static int prevWidth;
    public static int prevHeight;
    public static int currWidth;
    public static int currHeight;
    public static boolean isFullscreen;
    public static int FPS;
    public static boolean isVsync;
    public static int masterVolume;
    public static int bgmVolume;
    public static int effectVolume;
    public static float scale;
    public static Setting.Language language;
    public static Setting.Difficulty difficulty;

    public static Skin skin;
    public static BitmapFont font;

    public static final Gson gson;

    static {
        gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new GsonDateFormatAdapter())
            .create();
    }

    public static void init() {
        var startTime = System.currentTimeMillis();

        loadJsonToSetting();
        loadSettingToGlobal();
        SaveLoader.init();
        saveConfigJson(); // to save last played time


        logger.info("GlobalSettings | " + (System.currentTimeMillis() - startTime) / 1000F + " s");
    }

    /**
     * 유저 config.json 을 로드
     * 이미 파일이 존재한다면 로드한 시간을 저장하기위해, 존재하지 않는다면 새 파일 만들기 위해 저장한다
     */
    public static void loadJsonToSetting() {
        try (Reader reader = new FileReader(CONFIG_FILE_PATH)) {
            settings = gson.fromJson(reader, Setting.class);
            logger.info("load config file | last INIT time : " + settings.getLastPlayedTime());
            settings.setLastPlayedTime(LocalDateTime.now());
        } catch (IOException e) {
            logger.info("make new Config file");
            settings = new Setting();
        }
    }

    /**
     * config save.
     */
    public static void saveConfigJson() {
        Setting.Display display = settings.getDisplay();
        Setting.Gameplay gameplay = settings.getGameplay();
        Setting.Volume volume = settings.getVolume();

        display.setWidth(currWidth);
        display.setHeight(currHeight);
        display.setFullScreen(isFullscreen);
        display.setFps(FPS);
        display.setVsync(isVsync);
        gameplay.setLanguage(language);
        gameplay.setDifficulty(Setting.Difficulty.NORMAL);
        volume.setMasterVolume(masterVolume);
        volume.setBgmVolume(bgmVolume);
        volume.setEffectVolume(effectVolume);

        try (FileWriter file = new FileWriter(CONFIG_FILE_PATH)) {
            file.append(gson.toJson(settings));
        } catch (IOException ex) {
            logger.fatal("Config file saving error");
        }
    }

    private static void loadSettingToGlobal() {
        loadDisplayConfig();
        loadSoundConfig();
        loadGamePlayConfig();
    }

    public static void loadDisplayConfig() {
        var display = settings.getDisplay();

        currWidth = display.getWidth();
        currHeight = display.getHeight();
        prevWidth = currWidth;
        prevHeight = currHeight;
        FPS = display.getFps();
        isFullscreen = display.isFullScreen();
        isVsync = display.isVsync();
    }

    public static void loadSoundConfig() {
        var sound = settings.getVolume();
        masterVolume = sound.getMasterVolume();
        setBgmVolume(sound.getBgmVolume());
        effectVolume = sound.getEffectVolume();
    }

    public static void loadGamePlayConfig() {
        var gameplay = settings.getGameplay();
        language = gameplay.getLanguage();
        difficulty = gameplay.getDifficulty();
    }


    private static final List<String> soldiers = new ArrayList<>();
    private static final List<String> commands = new ArrayList<>();
    private static final List<String> manuals = new ArrayList<>();
    private static final List<String> quests = new ArrayList<>();

    // TODO 바보같은지 획기적인지 분간이 안감
    public static void loadAllInGameStructure() {
        soldiers.addAll(LocalizeConfig.soldierText.getID().keySet());
        commands.addAll(LocalizeConfig.commandText.getID().keySet());
        manuals.addAll(LocalizeConfig.manualText.getID().keySet());
        quests.addAll(LocalizeConfig.questText.getID().keySet());

        // TODO : seed randomize
        Collections.shuffle(soldiers);
        Collections.shuffle(commands);
        Collections.shuffle(manuals);
        Collections.shuffle(quests);
    }

    public static Soldier popSoldier() {
        String id;
        try {
            id = soldiers.remove(0);
        } catch (IndexOutOfBoundsException e) {
            logger.error("Soldier pop error", e);
            return null;
        }
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.soldier." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Soldier) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " Soldier load error", e);
            throw new RuntimeException("Soldier load error");
        }
    }

    public static Command popCommand() {
        String id;
        try {
            id = commands.remove(0);
        } catch (IndexOutOfBoundsException e) {
            logger.error("commands pop error", e);
            return null;
        }
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.command." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Command) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " command load error", e);
            throw new RuntimeException("command load error");
        }
    }

    public static Manual popManual() {
        String id;
        try {
            id = manuals.remove(0);
        } catch (IndexOutOfBoundsException e) {
            logger.error("manuals pop error", e);
            return null;
        }
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.manual." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Manual) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " manual load error", e);
            throw new RuntimeException("manual load error");
        }
    }

    public static Quest popQuest() {
        String id;
        try {
            id = quests.remove(0);
        } catch (IndexOutOfBoundsException e) {
            logger.error("quests pop error", e);
            return null;
        }
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.quest." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Quest) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " quest load error", e);
            throw new RuntimeException("quest load error");
        }
    }

    public static Soldier popSoldier(String id) {
        try {
            soldiers.remove(id);
        } catch (IndexOutOfBoundsException e) {
            logger.error("Soldier pop error", e);
            logger.error("popSoldier id" + id);
            return null;
        }
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.soldier." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Soldier) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " Soldier load error", e);
            throw new RuntimeException("Soldier load error");
        }
    }

    public static Command popCommand(String id) {
        try {
            commands.remove(id);
        } catch (IndexOutOfBoundsException e) {
            logger.error("commands pop error", e);
            return null;
        }
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.command." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Command) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " command load error", e);
            throw new RuntimeException("command load error");
        }
    }

    public static Manual popManual(String id) {
        try {
            manuals.remove(id);
        } catch (IndexOutOfBoundsException e) {
            logger.error("manuals pop error", e);
            return null;
        }
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.manual." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Manual) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " manual load error", e);
            throw new RuntimeException("manual load error");
        }
    }

    public static Quest popQuest(String id) {
        try {
            quests.remove(id);
        } catch (IndexOutOfBoundsException e) {
            logger.error("quests pop error", e);
            return null;
        }
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.quest." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Quest) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " quest load error", e);
            throw new RuntimeException("quest load error");
        }
    }

    public static Soldier getSoldier(String id) {
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.soldier." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Soldier) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " Soldier load error", e);
            throw new RuntimeException("Soldier load error");
        }
    }

    public static Command getCommand(String id) {
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.command." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Command) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " command load error", e);
            throw new RuntimeException("command load error");
        }
    }

    public static Manual getManual(String id) {
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.manual." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Manual) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " manual load error", e);
            throw new RuntimeException("manual load error");
        }
    }

    public static Quest getQuest(String id) {
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.quest." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Quest) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " quest load error", e);
            throw new RuntimeException("quest load error");
        }
    }

    public static void setBgmVolume(int bgmVolume) {
        GlobalSettings.bgmVolume = bgmVolume;
        ScreenConfig.setBgmVolume();
    }

    public static void setEffectVolume(int effectVolume) {
        GlobalSettings.effectVolume = effectVolume;
    }

    public static void setMasterVolume(int masterVolume) {
        GlobalSettings.masterVolume = masterVolume;
        ScreenConfig.setBgmVolume();
    }

}
