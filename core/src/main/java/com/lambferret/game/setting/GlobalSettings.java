package com.lambferret.game.setting;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
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
import com.lambferret.game.util.SoundUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        var endTime = String.format("%.3f", (System.currentTimeMillis() - startTime) / 1000F);
        logger.info(" ├ GlobalSettings : " + endTime + " s │");
    }

    /**
     * 유저 config.json 을 로드
     * 이미 파일이 존재한다면 로드한 시간을 저장하기위해, 존재하지 않는다면 새 파일 만들기 위해 저장한다
     */
    public static void loadJsonToSetting() {
        try (Reader reader = new FileReader(CONFIG_FILE_PATH)) {
            settings = gson.fromJson(reader, Setting.class);
            settings.setLastPlayedTime(LocalDateTime.now());
        } catch (IOException e) {
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


    private static final List<String> soldierAll = new ArrayList<>();
    private static final List<String> commandAll = new ArrayList<>();
    private static final List<String> manualAll = new ArrayList<>();
    private static final List<String> questAll = new ArrayList<>();
    private static final List<String> soldiersPlayerCanHave = new ArrayList<>();
    private static final List<String> commandsPlayerCanHave = new ArrayList<>();
    private static final List<String> manualsPlayerCanHave = new ArrayList<>();
    private static final List<String> questsPlayerCanHave = new ArrayList<>();

    // TODO 바보같은지 획기적인지 분간이 안감
    public static void loadAllInGameStructure() {
        soldierAll.addAll(LocalizeConfig.soldierText.getID().keySet());
        commandAll.addAll(LocalizeConfig.commandText.getID().keySet());
        manualAll.addAll(LocalizeConfig.manualText.getID().keySet());
        questAll.addAll(LocalizeConfig.questText.getID().keySet());
        soldiersPlayerCanHave.addAll(LocalizeConfig.soldierText.getID().keySet());
        commandsPlayerCanHave.addAll(LocalizeConfig.commandText.getID().keySet());
        manualsPlayerCanHave.addAll(LocalizeConfig.manualText.getID().keySet());
        questsPlayerCanHave.addAll(LocalizeConfig.questText.getID().keySet());

        // TODO : seed randomize
    }

    public static Soldier popSoldier() {
        // 가져올 수 있는 것 중 아무거나 가져옴
        int i = MathUtils.random(soldiersPlayerCanHave.size() - 1);
        String id = soldiersPlayerCanHave.remove(i);
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.soldier." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Soldier) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " Soldier load error");
            throw new RuntimeException("Soldier load error");
        }
    }

    public static Command popCommand() {
        int i = MathUtils.random(commandsPlayerCanHave.size() - 1);
        String id = commandsPlayerCanHave.remove(i);
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.command." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Command) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " Command load error");
            throw new RuntimeException("Command load error");
        }
    }

    public static Manual popManual() {
        int i = MathUtils.random(manualsPlayerCanHave.size() - 1);
        String id = manualsPlayerCanHave.remove(i);
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.manual." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Manual) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " Manual load error");
            throw new RuntimeException("Manual load error");
        }
    }

    public static Quest popQuest() {
        int i = MathUtils.random(questsPlayerCanHave.size() - 1);
        String id = questsPlayerCanHave.remove(i);
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.quest." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Quest) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " Quest load error");
            throw new RuntimeException("Quest load error");
        }
    }

    public static Soldier popSoldier(String id) {
        // 가져올 수 있는것 중 특정해서 가져옴
        soldiersPlayerCanHave.remove(id);
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.soldier." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Soldier) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " Soldier load error");
            throw new RuntimeException("Soldier load error");
        }
    }

    public static Command popCommand(String id) {
        commandsPlayerCanHave.remove(id);
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.command." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Command) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " command load error");
            throw new RuntimeException("command load error");
        }
    }

    public static Manual popManual(String id) {
        manualsPlayerCanHave.remove(id);
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.manual." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Manual) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " manual load error");
            throw new RuntimeException("manual load error");
        }
    }

    public static Quest popQuest(String id) {
        questsPlayerCanHave.remove(id);
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.quest." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Quest) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " quest load error");
            throw new RuntimeException("quest load error");
        }
    }

    public static String peekSoldierID() {
        int i = MathUtils.random(soldiersPlayerCanHave.size() - 1);
        return soldiersPlayerCanHave.get(i);
    }

    public static String peekCommandID() {
        int i = MathUtils.random(commandsPlayerCanHave.size() - 1);
        return commandsPlayerCanHave.get(i);
    }

    public static String peekManualID() {
        int i = MathUtils.random(manualsPlayerCanHave.size() - 1);
        return manualsPlayerCanHave.get(i);
    }

    public static String peekQuestID() {
        int i = MathUtils.random(questsPlayerCanHave.size() - 1);
        return questsPlayerCanHave.get(i);
    }

    public static int getSoldierSize() {
        return soldiersPlayerCanHave.size();
    }

    public static int getCommandSize() {
        return commandsPlayerCanHave.size();
    }

    public static int getManualSize() {
        return manualsPlayerCanHave.size();
    }

    public static int getQuestSize() {
        return questsPlayerCanHave.size();
    }

    public static Soldier getSoldier() {
        // 전부 중 무작위로 가져옴
        try {
            int i = MathUtils.random(soldierAll.size() - 1);
            String id = soldierAll.get(i);
            Class<?> clazz = Class.forName("com.lambferret.game.soldier." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Soldier) constructor.newInstance();
        } catch (Exception e) {
            logger.error("Soldier load error");
            throw new RuntimeException("Soldier load error");
        }
    }

    public static Command getCommand() {
        try {
            int i = MathUtils.random(commandAll.size() - 1);
            String id = commandAll.get(i);
            Class<?> clazz = Class.forName("com.lambferret.game.command." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Command) constructor.newInstance();
        } catch (Exception e) {
            logger.error("command load error");
            throw new RuntimeException("command load error");
        }
    }

    public static Manual getManual() {
        try {
            int i = MathUtils.random(manualAll.size() - 1);
            String id = manualAll.get(i);
            Class<?> clazz = Class.forName("com.lambferret.game.manual." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Manual) constructor.newInstance();
        } catch (Exception e) {
            logger.error("manual load error");
            throw new RuntimeException("manual load error");
        }
    }

    public static Quest getQuest() {
        try {
            int i = MathUtils.random(questAll.size() - 1);
            String id = questAll.get(i);
            Class<?> clazz = Class.forName("com.lambferret.game.quest." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Quest) constructor.newInstance();
        } catch (Exception e) {
            logger.error("Quest load error");
            throw new RuntimeException("Quest load error");
        }
    }

    public static Soldier getSoldier(String id) {
        // 전부 중 특정해서 가져옴
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.soldier." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Soldier) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " Soldier load error");
            throw new RuntimeException("Soldier load error");
        }
    }

    public static Command getCommand(String id) {
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.command." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Command) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " command load error");
            throw new RuntimeException("command load error");
        }
    }

    public static Manual getManual(String id) {
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.manual." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Manual) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " manual load error");
            throw new RuntimeException("manual load error");
        }
    }

    public static Quest getQuest(String id) {
        try {
            Class<?> clazz = Class.forName("com.lambferret.game.quest." + id);
            Constructor<?> constructor = clazz.getConstructor();
            return (Quest) constructor.newInstance();
        } catch (Exception e) {
            logger.error(id + " quest load error");
            throw new RuntimeException("quest load error");
        }
    }

    public static void setBgmVolume(int bgmVolume) {
        GlobalSettings.bgmVolume = bgmVolume;
        SoundUtil.setBgmVolume();
    }

    public static void setEffectVolume(int effectVolume) {
        GlobalSettings.effectVolume = effectVolume;
    }

    public static void setMasterVolume(int masterVolume) {
        GlobalSettings.masterVolume = masterVolume;
        SoundUtil.setBgmVolume();
    }

}
