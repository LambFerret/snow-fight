package com.lambferret.game.setting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambferret.game.save.SaveLoader;
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
    public static final int MAXIMUM_SAVE = 3;
    public static boolean isDev = true;
    public static int prevWidth;
    public static int prevHeight;
    public static int currWidth;
    public static int currHeight;
    public static boolean isFullscreen;
    public static int FPS;
    public static boolean isVsync;
    public static float masterVolume;
    public static float bgmVolume;
    public static float effectVolume;
    public static float scale;
    public static Skin skin;
    public static final Color debugColorGreen;
    public static final TextureRegionDrawable debugTexture;


    public static Setting settings;
    public static Setting.Language language;
    public static BitmapFont font = null;

    static {
        debugColorGreen = new Color(0, 255, 0, 0.1F);
        debugTexture = new TextureRegionDrawable(
            new TextureRegionDrawable(new Texture(Gdx.files.internal("./texture/yellow.png")))
        );
    }

    public static void init() {
        var startTime = System.currentTimeMillis();

        settingJsonConfig();
        loadDisplayConfig();
        loadSoundConfig();
        loadGamePlayConfig();
        SaveLoader.init();

        logger.info("GlobalSettings | " + (System.currentTimeMillis() - startTime) / 1000F + " s");

    }

    public static void settingJsonConfig() {
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new GsonDateFormatAdapter())
            .create();

        try (Reader reader = new FileReader(CONFIG_FILE_PATH)) {
            settings = gson.fromJson(reader, Setting.class);
            logger.info("load config file | last INIT time : " + settings.getLastPlayedTime());
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

        var display = settings.getDisplay();

        currWidth = display.getWidth();
        currHeight = display.getHeight();
        prevWidth = currWidth;
        prevHeight = currHeight;
        FPS = display.getFps();
        isFullscreen = display.isFullScreen();
        isVsync = display.isVsync();

        float ratio = (float) currWidth / (float) currHeight;
        if (1.3F < ratio && ratio < 1.4F) {
            //xScale, yScale of 4:3
        } else if (1.77777F < ratio && ratio < 1.8F) {
            scale = (float) Setting.DEFAULT_WIDTH / (float) currWidth;
        } else {
            // have LetterBox
        }
    }

    public static void loadSoundConfig() {
        masterVolume = settings.getVolume().getMasterVolume();
        bgmVolume = settings.getVolume().getBgmVolume();
        effectVolume = settings.getVolume().getEffectVolume();
    }

    public static void loadGamePlayConfig() {
        language = settings.getGameplay().getLanguage();
        font = switch (language) {
            case KR -> getFont("KR_nanumBold");
            case EN -> getFont("EN_Archivo_Condensed-Light");
            case JP -> getFont("JP_ShipporiSans");
            case RU -> getFont("RU_kremlin");
        };
        skin = new Skin(Gdx.files.internal("./data/uiskin.json"));

    }

    private static BitmapFont getFont(String name) {
        String FONT = "font/";
        String fileName = FONT + name + ".ttf";
        if (!Gdx.files.absolute(fileName).exists()) {
            fileName = FONT + name + ".otf";
        }

        FileHandle file = Gdx.files.internal(fileName);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(file);
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 12;
        params.color = Color.BLACK;

        BitmapFont font = generator.generateFont(params);
        generator.dispose();
        return font;
    }

}
