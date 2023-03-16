package com.lambferret.game.screen.title;

import com.lambferret.game.screen.AbstractScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TitleScreen extends AbstractScreen {
    private static final Logger logger = LogManager.getLogger(TitleScreen.class.getName());

    public static Screen screen;
    private final TitleMenuScreen titleMenuScreen;
    private final SelectSaveScreen selectSaveScreen;

    public TitleScreen() {
        titleMenuScreen = new TitleMenuScreen();
        selectSaveScreen = new SelectSaveScreen();
    }

    @Override
    public void create() {
        titleMenuScreen.create();
        selectSaveScreen.create();
        screen = Screen.TITLE;
    }

    @Override
    public void render() {
        switch (screen) {
            case TITLE -> {
                titleMenuScreen.render();
            }
            case SELECT_SAVE -> {
                selectSaveScreen.render();
            }
        }
    }

    @Override
    public void update() {
        switch (screen) {
            case TITLE -> {
                titleMenuScreen.update();
            }
            case SELECT_SAVE -> {
                selectSaveScreen.update();
            }
        }
    }

    public enum Screen {
        TITLE,
        SELECT_SAVE,
        ;
    }
}
