package com.lambferret.game.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambferret.game.SnowFight;
import com.lambferret.game.command.Command;
import com.lambferret.game.manual.Manual;
import com.lambferret.game.player.Player;
import com.lambferret.game.quest.Quest;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.GsonDateFormatAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.lambferret.game.setting.GlobalSettings.MAXIMUM_SAVE;

public class SaveLoader {
    private static final Logger logger = LogManager.getLogger(SaveLoader.class.getName());
    private static final String SAVE_FILE_PATH = "saves/";
    private static final String FILE_PREFIX = "save";
    private static final String AUTO_SAVE_SUFFIX = ".autosave";
    private static final String SAVE_SUFFIX = ".save";
    public static Save currentSave;
    public static int currentSaveSlot;


    public static void load(int saveFileNumber) {
        currentSaveSlot = saveFileNumber;
        String fileName = SAVE_FILE_PATH + FILE_PREFIX + saveFileNumber + SAVE_SUFFIX;
        Save saveFile = null;
        if (saveFileNumber > MAXIMUM_SAVE) {
            logger.info("max save file is " + MAXIMUM_SAVE + " but number is " + saveFileNumber);
        }
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new GsonDateFormatAdapter())
            .create();

        try (Reader reader = new FileReader(fileName)) {
            BufferedReader br = new BufferedReader(reader);
            String s;
            StringBuilder sb = new StringBuilder();
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            br.close();
            var ac = GlobalUtil.decrypt(String.valueOf(sb));
            saveFile = gson.fromJson(ac, Save.class);
        } catch (IOException e) {
        }

        currentSave = saveFile;
    }

    public static void init() {
        FileHandle dir = Gdx.files.local(SAVE_FILE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static void save() {
        Player player = SnowFight.player;
        int saveFileNumber = currentSaveSlot;
        String fileName = SAVE_FILE_PATH + FILE_PREFIX + saveFileNumber + SAVE_SUFFIX;
        List<Item> items = new ArrayList<>();
        for (Soldier soldier : player.getSoldiers()) {
            items.add(new Item(Item.TYPE.SOLDIER, soldier.getID()));
        }
        for (Manual manual : player.getManuals()) {
            items.add(new Item(Item.TYPE.MANUAL, manual.getID()));
        }
        for (Command command : player.getCommands()) {
            items.add(new Item(Item.TYPE.COMMAND, command.getID()));
        }
        for (Quest quest : player.getQuests()) {
            items.add(new Item(Item.TYPE.QUEST, quest.getID()));
        }
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new GsonDateFormatAdapter())
            .create();
        Save save = Save.builder()
//            .time(BarOverlay.time)
            .isInitialized(true)
            .name(player.getName())
            .allItems(items)
            .day(player.getDay())
            .money(player.getMoney())
            .affinity(player.getAffinity())
            .maxCost(player.getMaxCost())
            .currentCost(player.getCurrentCost())
            .difficulty(player.getDifficulty())
            .snowAmount(player.getSnowAmount())
            .humanAffinity(player.getHumanAffinity())
            .hellAffinity(player.getHellAffinity())
            .eventList(player.getEventList())
            .build();
        try (FileWriter file = new FileWriter(fileName)) {
            file.append(GlobalUtil.encrypt(gson.toJson(save)));
        } catch (IOException ex) {
            logger.fatal("old save file IOException");
            Gdx.app.exit();
        }
    }

    public static boolean isSaveExist(int saveFileNumber) {
        String fileName = SAVE_FILE_PATH + FILE_PREFIX + saveFileNumber + SAVE_SUFFIX;
        FileHandle f = Gdx.files.local(fileName);
        return f.exists();
    }

    public static void makeNewSave(int saveFileNumber) {
        String fileName = SAVE_FILE_PATH + FILE_PREFIX + saveFileNumber + SAVE_SUFFIX;
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new GsonDateFormatAdapter())
            .create();
        Save newSave = Save.builder().isInitialized(false).time(0).build();
        try (FileWriter file = new FileWriter(fileName)) {
            file.append(GlobalUtil.encrypt(gson.toJson(newSave)));
        } catch (IOException ex) {
            logger.fatal("new save file IOException");
            Gdx.app.exit();
        }
    }

    public static void deleteSave(int saveFileNumber) {
        String fileName = SAVE_FILE_PATH + FILE_PREFIX + saveFileNumber + SAVE_SUFFIX;
        FileHandle f = Gdx.files.local(fileName);
        f.delete();
    }

    public static int getRecentSave() {
        int saveFileNumber = -1;
        long mostRecentTime = -1;

        for (int i = 0; i < MAXIMUM_SAVE; i++) {
            String fileName = SAVE_FILE_PATH + FILE_PREFIX + i + SAVE_SUFFIX;
            FileHandle file = Gdx.files.internal(fileName);
            if (!file.exists()) continue;
            long lastModified = file.lastModified();
            if (lastModified > mostRecentTime) {
                mostRecentTime = lastModified;
                saveFileNumber = i;
            }
        }
        return saveFileNumber;
    }

}
