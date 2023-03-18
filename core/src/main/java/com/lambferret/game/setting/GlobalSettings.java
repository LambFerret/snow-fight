package com.lambferret.game.setting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    public static float masterVolume;
    public static float bgmVolume;
    public static float effectVolume;
    public static float scale;
    public static Setting.Language language;

    public static Skin skin;
    public static final Color debugColorGreen;
    public static final TextureRegionDrawable debugTexture;
    public static BitmapFont font;

    public static final Gson gson;

    static {
        gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new GsonDateFormatAdapter())
            .create();
        debugColorGreen = new Color(0, 255, 0, 0.1F);
        debugTexture = new TextureRegionDrawable(
            new TextureRegionDrawable(new Texture(Gdx.files.internal("./texture/yellow.png")))
        );
    }

    public static void init() {
        var startTime = System.currentTimeMillis();

        loadJsonConfig();
        loadDisplayConfig();
        loadSoundConfig();
        loadGamePlayConfig();
        SaveLoader.init();

        logger.info("GlobalSettings | " + (System.currentTimeMillis() - startTime) / 1000F + " s");
    }

    /**
     * 유저 config.json 을 로드
     * 이미 파일이 존재한다면 로드한 시간을 저장하기위해, 존재하지 않는다면 새 파일 만들기 위해 저장한다
     */
    public static void loadJsonConfig() {
        try (Reader reader = new FileReader(CONFIG_FILE_PATH)) {
            settings = gson.fromJson(reader, Setting.class);
            logger.info("load config file | last INIT time : " + settings.getLastPlayedTime());
            settings.setLastPlayedTime(LocalDateTime.now());
        } catch (IOException e) {
            logger.info("make new Config file");
            settings = new Setting();
        }
        saveConfigJson();
    }

    /**
     * config save.
     * 세팅을 바꾸고 싶을땐 settings를 조작한 후 메소드 호출하면 된다.
     */
    public static void saveConfigJson() {
        try (FileWriter file = new FileWriter(CONFIG_FILE_PATH)) {
            file.append(gson.toJson(settings));
        } catch (IOException ex) {
            logger.fatal("Config file saving error");
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
    }

    public static void loadSoundConfig() {
        var sound = settings.getVolume();
        masterVolume = sound.getMasterVolume();
        bgmVolume = sound.getBgmVolume();
        effectVolume = sound.getEffectVolume();
    }

    public static void loadGamePlayConfig() {
        var gameplay = settings.getGameplay();
        language = gameplay.getLanguage();
    }

}
