package com.lambferret.game.screen.ground;

import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.GroundText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class GroundScreen extends AbstractScreen {
    private static final Logger logger = LogManager.getLogger(GroundScreen.class.getName());
    private static Overlay overlay;
    public static Screen currentScreen;
    private static final RecruitScreen recruitScreen;
    private static final ShopScreen shopScreen;
    private static final TrainingGroundScreen trainingGroundScreen;
    GroundText text;

    static {
        recruitScreen = new RecruitScreen();
        shopScreen = new ShopScreen();
        trainingGroundScreen = new TrainingGroundScreen();
    }

    public GroundScreen() {
        text = LocalizeConfig.uiText.getGroundText();
        overlay = Overlay.getInstance();
        changeScreen(Screen.TRAINING_GROUND);
    }

    @Override
    public void create() {
        Overlay.setGroundUI();
        recruitScreen.create();
        shopScreen.create();
        trainingGroundScreen.create();
    }

    public static void changeScreen(Screen screen) {
        if (currentScreen != screen) {
            Overlay.currentMainStage = switch (screen) {
                case RECRUIT -> recruitScreen.getStage();
                case SHOP -> shopScreen.getStage();
                case TRAINING_GROUND -> trainingGroundScreen.getStage();
            };
            Overlay.initInput();
            currentScreen = screen;
        }
    }

    @Override
    public void render() {
        overlay.render();
        switch (currentScreen) {
            case RECRUIT -> recruitScreen.render();
            case SHOP -> shopScreen.render();
            case TRAINING_GROUND -> trainingGroundScreen.render();
        }
    }

    @Override
    public void update() {
        overlay.update();
        switch (currentScreen) {
            case RECRUIT -> recruitScreen.update();
            case SHOP -> shopScreen.update();
            case TRAINING_GROUND -> trainingGroundScreen.update();
        }
    }

    public enum Screen {
        RECRUIT,
        SHOP,
        TRAINING_GROUND,
        ;
    }
}
