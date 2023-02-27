package com.lambferret.game.screen.ground;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
        super.create();
        Overlay.setGroundUI();
    }

    @Override
    public void render(SpriteBatch batch) {
        overlay.render(batch);
        switch (screen) {
            case RECRUIT -> {
                recruitScreen.render(batch);
            }
            case SHOP -> {
                shopScreen.render(batch);
            }
            case TRAINING_GROUND -> {
                trainingGroundScreen.render(batch);
            }
        }
    }

    @Override
    public void update(float delta) {
        overlay.update(delta);
        switch (screen) {
            case RECRUIT -> {
                recruitScreen.update(delta);
            }
            case SHOP -> {
                shopScreen.update(delta);

            }
            case TRAINING_GROUND -> {
                trainingGroundScreen.update(delta);

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
