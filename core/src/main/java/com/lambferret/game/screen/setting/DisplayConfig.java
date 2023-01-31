package com.lambferret.game.screen.setting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DisplayConfig {
    private static final Logger logger = LogManager.getLogger(DisplayConfig.class.getName());
    private static final int DEFAULT_WIDTH = 1280;
    private static final int DEFAULT_HEIGHT = 720;
    private static final int DEFAULT_FPS = 60;
    private static final boolean DEFAULT_VSYNC = true;

    private static final boolean FULLSCREEN = false;


    private int width;
    private int height;
    private int fps;
    private boolean isFullscreen;
    private boolean vsync;

    private DisplayConfig(int width, int height, int fps, boolean isFullscreen, boolean vsync) {
        this.width = width;
        this.height = height;
        this.fps = fps;
        this.isFullscreen = isFullscreen;
        this.vsync = vsync;
    }

    public static void saveDisplayConfig() {

    }

    public static DisplayConfig loadDisplayConfig() {
        return new DisplayConfig(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_FPS, false, DEFAULT_VSYNC);
    }


}
