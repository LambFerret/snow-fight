package com.lambferret.game.screen.main;

import com.badlogic.gdx.Gdx;
import com.lambferret.game.Hitbox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainMenuButton {
    private static final Logger logger = LogManager.getLogger(MainMenuButton.class.getName());

    public Hitbox box;
    private static final String NAME;
    public MenuButton action;

    static {
        NAME = "sdf";
    }
    public MainMenuButton(MenuButton action) {
        this.action = action;
//        this.box = new Hitbox();
    }

    public void update() {
        switch (this.action) {
            case NEW -> {
            }
            case LOAD -> {
            }
            case OPTION -> {
            }
            case SETTINGS -> {
            }
            case EXIT -> {
                logger.info("update | exit game!");
                Gdx.app.exit();
            }
        }
    }

    enum MenuButton {
        NEW,
        LOAD,
        OPTION,
        SETTINGS,
        EXIT;
//        private MenuButton() {}
    }
}
