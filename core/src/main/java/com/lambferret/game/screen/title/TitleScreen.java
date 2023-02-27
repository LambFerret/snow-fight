package com.lambferret.game.screen.title;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
        screen = Screen.TITLE;
    }

    @Override
    public void create() {
        super.create();
    }

    @Override
    public void render(SpriteBatch batch) {
        switch (screen) {
            case TITLE -> {
                titleMenuScreen.render(batch);
            }
            case SELECT_SAVE -> {
                selectSaveScreen.render(batch);
            }
        }
    }

    @Override
    public void update(float delta) {
        switch (screen) {
            case TITLE -> {
                titleMenuScreen.update(delta);
            }
            case SELECT_SAVE -> {
                selectSaveScreen.update(delta);
            }
        }
    }
    public enum Screen {
        TITLE,
        SELECT_SAVE,
        ;
    }
}
