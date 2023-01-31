package com.lambferret.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambferret.game.screen.setting.Setting;
import com.lambferret.game.util.GsonDateFormatAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;

public class GlobalSettings {

    private static final Logger logger = LogManager.getLogger(GlobalSettings.class.getName());

    public static final String CONFIG_FILE_PATH = "./config.json";
    public static boolean isDev = true;
    public static int SAVED_WIDTH;
    public static int SAVED_HEIGHT;
    public static int WIDTH;
    public static int HEIGHT;
    public static boolean IS_FULLSCREEN;
    public static int FPS;
    public static boolean IS_VSYNC;
    public static float MASTER_VOLUME;
    public static float BGM_VOLUME;
    public static float EFFECT_VOLUME;

    public static Setting.Language language;


    public static void init() {
        var startTime = System.currentTimeMillis();
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new GsonDateFormatAdapter())
            .create();

        Setting settings;
        try (Reader reader = new FileReader(CONFIG_FILE_PATH)) {
            settings = gson.fromJson(reader, Setting.class);
            logger.info("load config file! last INIT time : " + settings.getLastPlayedTime());
            settings.setLastPlayedTime(LocalDateTime.now());
        } catch (IOException e) {
            logger.info("make new Config file");
            settings = new Setting();

        }
        try (FileWriter file = new FileWriter(CONFIG_FILE_PATH)) {
            file.append(gson.toJson(settings));
        } catch (IOException ex) {
            logger.info("init | config file IOException!!");
        }

        WIDTH = settings.getDisplay().getWidth();
        HEIGHT = settings.getDisplay().getHeight();
        FPS = settings.getDisplay().getFps();
        IS_FULLSCREEN = settings.getDisplay().isFullScreen();
        IS_VSYNC = settings.getDisplay().isVsync();
        MASTER_VOLUME = settings.getVolume().getMasterVolume();
        BGM_VOLUME = settings.getVolume().getBgmVolume();
        EFFECT_VOLUME = settings.getVolume().getEffectVolume();
        language = settings.getGameplay().getLanguage();

        logger.info("GlobalSettings init time | " + (System.currentTimeMillis() - startTime));
    }


}
