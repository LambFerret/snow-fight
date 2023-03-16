package com.lambferret.game.screen.ground;

import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.screen.ui.Overlay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class GroundScreen extends AbstractScreen {
    private static final Logger logger = LogManager.getLogger(GroundScreen.class.getName());
    private static Overlay overlay;
    public static Screen screen;
    private RecruitScreen recruitScreen;
    private ShopScreen shopScreen;
    private TrainingGroundScreen trainingGroundScreen;

    public GroundScreen() {
        recruitScreen = new RecruitScreen();
        shopScreen = new ShopScreen();
        trainingGroundScreen = new TrainingGroundScreen();
        screen = Screen.TRAINING_GROUND;
        overlay = Overlay.getInstance();
    }

    @Override
    public void create() {
        Overlay.setGroundUI();
    }

    @Override
    public void render() {
        overlay.render();
        switch (screen) {
            case RECRUIT -> {
                recruitScreen.render();
            }
            case SHOP -> {
                shopScreen.render();
            }
            case TRAINING_GROUND -> {
                trainingGroundScreen.render();
            }
        }
    }

    @Override
    public void update() {
        overlay.update();
        switch (screen) {
            case RECRUIT -> {
                recruitScreen.update();
            }
            case SHOP -> {
                shopScreen.update();

            }
            case TRAINING_GROUND -> {
                trainingGroundScreen.update();

            }
        }
    }

    enum Screen {
        RECRUIT,
        SHOP,
        TRAINING_GROUND,
        ;
    }
}
