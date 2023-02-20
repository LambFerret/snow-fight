package com.lambferret.game.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambferret.game.util.GsonDateFormatAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;

import static com.lambferret.game.setting.GlobalSettings.MAXIMUM_SAVE;

public class SaveLoader {
    private static final Logger logger = LogManager.getLogger(SaveLoader.class.getName());
    private static final String SAVE_FILE_PATH = "save/save";
    private static Save save;


    public static void init(int saveFileNumber) {
        if (saveFileNumber>MAXIMUM_SAVE) {
            logger.info("max save file is " + MAXIMUM_SAVE + " but number is " + saveFileNumber);
        }
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new GsonDateFormatAdapter())
            .create();

        try (Reader reader = new FileReader(SAVE_FILE_PATH + saveFileNumber)) {
            save = gson.fromJson(reader, Save.class);
        } catch (IOException e) {
            logger.info("make new Config file");
            save = new Save();
        }
        try (FileWriter file = new FileWriter(SAVE_FILE_PATH + saveFileNumber)) {
            file.append(gson.toJson(save));
        } catch (IOException ex) {
            logger.info("save file IOException!!");
        }
    }
}
