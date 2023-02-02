package com.lambferret.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambferret.game.setting.Setting;
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
    public static float scale;

    public static Setting settings;
    private static boolean isLetterbox;
    private static boolean is4v3;
    private static boolean is16v10;
    private static float xScale;
    private static float renderScale;
    private static float yScale;
    private static float SCROLL_SPEED;
    public static Setting.Language language;


    public static void init() {
        var startTime = System.currentTimeMillis();

        settingJsonConfig();
        loadDisplayConfig();
        loadSoundConfig();
        loadGamePlayConfig();

        logger.info("GlobalSettings init time | " + (System.currentTimeMillis() - startTime));

    }

    public static void settingJsonConfig() {
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new GsonDateFormatAdapter())
            .create();

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


    }

    public static void loadDisplayConfig() {

        WIDTH = settings.getDisplay().getWidth();
        HEIGHT = settings.getDisplay().getHeight();
        FPS = settings.getDisplay().getFps();
        IS_FULLSCREEN = settings.getDisplay().isFullScreen();
        IS_VSYNC = settings.getDisplay().isVsync();

        float ratio = (float) WIDTH / (float) HEIGHT;
        boolean isUltrawide = false;
//        isLetterbox = ratio > 2.34F || ratio < 1.3332F;
//        if (ratio > 1.32F && ratio < 1.34F) {
//            is4v3 = true;
//        } else if (ratio > 1.59F && ratio < 1.61F) {
//            is16v10 = true;
//        } else if (ratio > 1.78F) {
//            isUltrawide = true;
//        }
//
//        if (isLetterbox) {
//            if (ratio < 1.333F) {
//                HEIGHT = MathUtils.round((float) WIDTH * 0.75F);
//                scale = (float) WIDTH / Setting.DEFAULT_WIDTH;
//                xScale = scale;
//                renderScale = scale;
//                yScale = (float) HEIGHT / Setting.DEFAULT_HEIGHT;
//                is4v3 = true;
//
//            } else if (ratio > 2.34F) {
//                WIDTH = MathUtils.round((float) HEIGHT * 2.3333F);
//                scale = (float) ((int) ((float) HEIGHT * 1.77778F)) / Setting.DEFAULT_WIDTH;
//                xScale = (float) WIDTH / Setting.DEFAULT_WIDTH;
//                renderScale = xScale;
//                yScale = scale;
//                setXOffset();
//            }
//        } else if (is4v3) {
//            scale = (float) WIDTH / Setting.DEFAULT_WIDTH;
//            xScale = scale;
//            yScale = (float) HEIGHT / Setting.DEFAULT_WIDTH;
//            renderScale = yScale;
//
//        } else if (isUltrawide) {
//            scale = (float) ((int) ((float) HEIGHT * 1.77778F)) / Setting.DEFAULT_WIDTH;
//            xScale = (float) WIDTH / Setting.DEFAULT_WIDTH;
//            renderScale = xScale;
//            yScale = scale;
//            setXOffset();
//            isLetterbox = true;
//        } else {
//            scale = (float) WIDTH / Setting.DEFAULT_WIDTH;
//            xScale = scale;
//            yScale = scale;
//            renderScale = scale;
//        }

        scale = ratio;
        SCROLL_SPEED = 75.0F * scale;


    }

    private static void setXOffset() {
        if (scale == 1.0F) {

        }
    }

    public static void loadSoundConfig() {
        MASTER_VOLUME = settings.getVolume().getMasterVolume();
        BGM_VOLUME = settings.getVolume().getBgmVolume();
        EFFECT_VOLUME = settings.getVolume().getEffectVolume();
    }

    public static void loadGamePlayConfig() {
        language = settings.getGameplay().getLanguage();
    }


}
