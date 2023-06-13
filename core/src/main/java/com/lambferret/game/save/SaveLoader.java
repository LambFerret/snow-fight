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
import lombok.Builder;
import lombok.Getter;
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

    private static Save read(int saveFileNumber) {
        String fileName = SAVE_FILE_PATH + FILE_PREFIX + saveFileNumber + SAVE_SUFFIX;
        Save saveFile = null;
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
        return saveFile;
    }

    public static SaveFileInfo info(int saveFileNumber) {
        FileHandle file = Gdx.files.internal(SAVE_FILE_PATH + FILE_PREFIX + saveFileNumber + SAVE_SUFFIX);
        Save save = read(saveFileNumber);
        return SaveFileInfo.builder()
            .day(save.getDay())
            .lastModified(file.lastModified())
            .build();
    }

    public static void load(int saveFileNumber) {
        currentSaveSlot = saveFileNumber;
        currentSave = read(saveFileNumber);
        logger.info(" SYSTEM : Successfully loaded save file " + saveFileNumber);
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
            items.add(new Item(Item.Type.SOLDIER, soldier.getID()));
        }
        for (Manual manual : player.getManuals()) {
            items.add(new Item(Item.Type.MANUAL, manual.getID()));
        }
        for (Command command : player.getCommands()) {
            items.add(new Item(Item.Type.COMMAND, command.getID()));
        }
        for (Quest quest : player.getQuests()) {
            items.add(new Item(Item.Type.QUEST, quest.getID()));
        }
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new GsonDateFormatAdapter())
            .create();
        Save save = Save.builder()
//            .time(BarOverlay.time)
            .isInitialized(true)
            .name(player.getName())
            .allItems(items)
            .shopList(player.getShopItems())
            .day(player.getDay())
            .money(player.getMoney())
            .affinity(player.getAffinity())
            .maxCost(player.getMaxCost())
            .currentCost(player.getCurrentCost())
            .difficulty(player.getDifficulty())
            .snowAmount(player.getSnowAmount())
            .middleAffinity(player.getMiddleAffinity())
            .downAffinity(player.getDownAffinity())
            .upperAffinity(player.getUpperAffinity())
            .maxManualCapacity(player.getMaxManualCapacity())
            .eventList(player.getEventList())
            .build();
        try (FileWriter file = new FileWriter(fileName)) {
            file.append(GlobalUtil.encrypt(gson.toJson(save)));
        } catch (IOException ex) {
            logger.fatal("old save file IOException");
            Gdx.app.exit();
        }
        logger.info(" SYSTEM : Successfully saved save file " + saveFileNumber);
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
        logger.info(" SYSTEM : make new save file " + saveFileNumber);
    }

    public static void deleteSave(int saveFileNumber) {
        String fileName = SAVE_FILE_PATH + FILE_PREFIX + saveFileNumber + SAVE_SUFFIX;
        FileHandle f = Gdx.files.local(fileName);
        f.delete();
        logger.info(" SYSTEM : delete save file " + saveFileNumber);
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

    @Builder
    @Getter
    public static class SaveFileInfo {
        long lastModified;
        int day;
    }

}
