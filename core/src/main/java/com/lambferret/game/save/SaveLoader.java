package com.lambferret.game.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambferret.game.util.GsonDateFormatAdapter;
import com.lambferret.game.util.crypt.CryptoUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.time.LocalDateTime;

import static com.lambferret.game.setting.GlobalSettings.MAXIMUM_SAVE;

public class SaveLoader {
    private static final Logger logger = LogManager.getLogger(SaveLoader.class.getName());
    private static final String SAVE_FILE_PATH = "saves/";
    private static final String FILE_PREFIX = "save";
    private static final String AUTO_SAVE_SUFFIX = ".autosave";
    private static final String SAVE_SUFFIX = ".save";
    public static Save currentSave;


    public static void load(int saveFileNumber) {
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
            var ac = CryptoUtil.decrypt(String.valueOf(sb));
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

    public static void save(int saveFileNumber) {
        String fileName = SAVE_FILE_PATH + FILE_PREFIX + saveFileNumber + SAVE_SUFFIX;
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new GsonDateFormatAdapter())
            .create();
        Save save = Save.builder()
            // some saving stuff
//            .time(BarOverlay.time)
            .init(true)
            .build();
        try (FileWriter file = new FileWriter(fileName)) {
            file.append(CryptoUtil.encrypt(gson.toJson(save)));
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
        Save newSave = Save.builder().time(0).build();
        try (FileWriter file = new FileWriter(fileName)) {
            file.append(CryptoUtil.encrypt(gson.toJson(newSave)));
        } catch (IOException ex) {
            logger.fatal("new save file IOException");
            Gdx.app.exit();
        }
    }
}
