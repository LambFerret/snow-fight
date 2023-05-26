package com.lambferret.game.setting;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class Setting {
    private static final Logger logger = LogManager.getLogger(Setting.class.getName());

    public static final int DEFAULT_WIDTH = 1280;
    public static final int DEFAULT_HEIGHT = 720;
    public static final int DEFAULT_FPS = 60;
    public static final boolean DEFAULT_VSYNC = true;
    private static final boolean FULLSCREEN = false;
    public static final int DEFAULT_MASTER_VOLUME = 50;
    public static final int DEFAULT_BGM_VOLUME = 50;
    public static final int DEFAULT_EFFECT_VOLUME = 50;

    private Display display;
    private Volume volume;
    private Gameplay gameplay;
    public LocalDateTime lastPlayedTime;

    public Setting() {
        this.lastPlayedTime = LocalDateTime.now();
        this.display = new Display();
        this.volume = new Volume();
        this.gameplay = new Gameplay();
    }

    /**
     * the ratio by resolution that common in monitor
     * <p>
     * 4:3 Aspect Ratio:
     * 800 x 600
     * 1024 x 768
     * 1152 x 864
     * <p>
     * 16:9 Aspect Ratio:
     * 1280 x 720 (720p) -> default of this game
     * 1920 x 1080 (1080p)
     * 2560 x 1440 (1440p)
     * <p>
     * additional ratio for mobile -> it will gonna have Letterbox
     * 2160 x 1080
     * 3120 x 1440
     * 2340 x 1080
     */
    @Getter
    @Setter
    public static class Display {
        private int width;
        private int height;
        private int fps;
        private boolean isFullScreen;
        private boolean isVsync;

        public Display() {
            this.width = DEFAULT_WIDTH;
            this.height = DEFAULT_HEIGHT;
            this.fps = DEFAULT_FPS;
            this.isFullScreen = DEFAULT_VSYNC;
            this.isVsync = FULLSCREEN;
        }
    }

    @Getter
    @Setter
    public static class Volume {
        private int masterVolume;
        private int bgmVolume;
        private int effectVolume;

        public Volume() {
            this.masterVolume = DEFAULT_MASTER_VOLUME;
            this.bgmVolume = DEFAULT_MASTER_VOLUME;
            this.effectVolume = DEFAULT_MASTER_VOLUME;
        }
    }

    @Getter
    @Setter
    public static class Gameplay {
        private Difficulty difficulty;
        private Language language;

        public Gameplay() {
            this.difficulty = Difficulty.NORMAL;
            this.language = Language.EN;
        }

    }

    public enum Difficulty {
        EASY, NORMAL, HARD
    }

    public enum Language {
        KR("한국어"),
        EN("English"),
        JP("日本語"),
        RU("Русский");
        final String locale;
        Language(String locale) {
            this.locale = locale;
        }
        public String getLocale() {
            return locale;
        }
        public static Language fromLocale(String locale) {
            for (Language language : Language.values()) {
                if (language.getLocale().equals(locale)) {
                    return language;
                }
            }
            throw new IllegalArgumentException("No enum constant for locale: " + locale);
        }
    }
}

