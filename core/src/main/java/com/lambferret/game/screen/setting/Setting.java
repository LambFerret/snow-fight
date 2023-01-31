package com.lambferret.game.screen.setting;

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

    private static final int DEFAULT_WIDTH = 1280;
    private static final int DEFAULT_HEIGHT = 720;
    private static final int DEFAULT_FPS = 60;
    private static final boolean DEFAULT_VSYNC = true;
    private static final boolean FULLSCREEN = false;
    public static final float DEFAULT_MASTER_VOLUME = 0.5F;
    public static final float DEFAULT_BGM_VOLUME = 0.5F;
    public static final float DEFAULT_EFFECT_VOLUME = 0.5F;

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
        private float masterVolume;
        private float bgmVolume;
        private float effectVolume;
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
        KO,
        EN,
        JP,
        RU
    }
}

