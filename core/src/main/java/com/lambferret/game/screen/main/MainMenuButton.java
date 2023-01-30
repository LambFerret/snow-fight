package com.lambferret.game.screen.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainMenuButton {
    private static final Logger logger = LogManager.getLogger(MainMenuButton.class.getName());

    public MainMenuButton() {
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
