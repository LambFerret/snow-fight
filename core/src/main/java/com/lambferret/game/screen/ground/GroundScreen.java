package com.lambferret.game.screen.ground;

import com.lambferret.game.SnowFight;
import com.lambferret.game.player.Player;
import com.lambferret.game.player.PlayerObserver;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.GroundText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GroundScreen extends AbstractScreen implements PlayerObserver {
    private static final Logger logger = LogManager.getLogger(GroundScreen.class.getName());

    private static final List<AbstractGround> groundListener;
    private static final RecruitScreen recruitScreen;
    private static final ShopScreen shopScreen;
    private static final TrainingGroundScreen trainingGroundScreen;

    private static Screen currentScreen = Screen.TRAINING_GROUND;
    private static Player player;
    private static Overlay overlay;
    GroundText text;

    static {
        recruitScreen = new RecruitScreen();
        shopScreen = new ShopScreen();
        trainingGroundScreen = new TrainingGroundScreen();
        groundListener = List.of(
            recruitScreen,
            shopScreen,
            trainingGroundScreen
        );
    }

    public GroundScreen() {
        text = LocalizeConfig.uiText.getGroundText();
        overlay = Overlay.getInstance();
    }

    @Override
    public void create() {
        Overlay.setVisibleGroundUI();
        for (AbstractGround ground : groundListener) {
            ground.create();
        }
    }

    @Override
    public void onPlayerReady() {
        player = SnowFight.player;
        for (AbstractGround ground : groundListener) {
            ground.init(player);
        }
    }


    public static void changeScreen(Screen screen) {
        currentScreen = screen;
        Overlay.changeGroundInputProcessor();
        switch (currentScreen) {
            case RECRUIT -> recruitScreen.show();
            case SHOP -> shopScreen.show();
            case TRAINING_GROUND -> trainingGroundScreen.show();
        }
    }

    public static Screen getCurrentScreen() {
        return currentScreen;
    }

    @Override
    public void render() {
        switch (currentScreen) {
            case RECRUIT -> recruitScreen.render();
            case SHOP -> shopScreen.render();
            case TRAINING_GROUND -> trainingGroundScreen.render();
        }
        overlay.render();
    }

    @Override
    public void update() {
        switch (currentScreen) {
            case RECRUIT -> recruitScreen.update();
            case SHOP -> shopScreen.update();
            case TRAINING_GROUND -> trainingGroundScreen.update();
        }
        overlay.update();
    }

    public enum Screen {
        RECRUIT,
        SHOP,
        TRAINING_GROUND,
        ;
    }
}
