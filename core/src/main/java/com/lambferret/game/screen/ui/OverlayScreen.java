package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class OverlayScreen {
    private static final Logger logger = LogManager.getLogger(OverlayScreen.class.getName());

    private SpriteBatch batch;
    private MenuBar menuBar;
    private List<OverlayButton> buttons;

    public void create(MenuBar menuBar) {
        this.menuBar = menuBar;


    }

    public enum MenuBar {
        IN_GAME,
        ;
    }
}
