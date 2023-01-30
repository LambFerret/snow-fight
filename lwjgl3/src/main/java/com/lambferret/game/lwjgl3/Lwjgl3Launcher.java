package com.lambferret.game.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.lambferret.game.SnowFight;

/**
 * 윈도우 크기나 Vsync 등 데스크탑 설정 하는 곳
 */
public class Lwjgl3Launcher {

    public static int SCREEN_WIDTH = 800;
    public static int SCREEN_HEIGHT = 600;

    public static void main(String[] args) {
        createApplication();
    }

    private static void createApplication() {
        new Lwjgl3Application(new SnowFight(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("snow-fight");
        configuration.useVsync(true);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
        configuration.setWindowedMode(SCREEN_WIDTH, SCREEN_HEIGHT);
        configuration.setResizable(false);
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}
