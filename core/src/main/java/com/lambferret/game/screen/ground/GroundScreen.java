package com.lambferret.game.screen.ground;

import com.badlogic.gdx.scenes.scene2d.Stage;
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

import static com.lambferret.game.screen.ui.Overlay.changeCurrentInputProcessor;

public class GroundScreen extends AbstractScreen implements PlayerObserver {
    private static final Logger logger = LogManager.getLogger(GroundScreen.class.getName());
    private static final List<AbstractGround> groundListener;
    private static Overlay overlay;
    public static Screen currentScreen;
    private static final RecruitScreen recruitScreen;
    private static final ShopScreen shopScreen;
    private static final TrainingGroundScreen trainingGroundScreen;
    GroundText text;
    private static Player player;

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
        Overlay.setGroundUI();
        for (AbstractGround ground : groundListener) {
            ground.create();
        }
    }

    @Override
    public void onPlayerReady() {
        player = SnowFight.player;
        initiation();
    }

    public static void initiation() {
        for (AbstractGround ground : groundListener) {
            ground.init(player);
        }
    }

    public static void changeScreen(Screen screen) {
        if (currentScreen != screen) {
            Stage currentStage = switch (screen) {
                case RECRUIT -> recruitScreen.getStage();
                case SHOP -> shopScreen.getStage();
                case TRAINING_GROUND -> trainingGroundScreen.getStage();
            };
            changeCurrentInputProcessor(currentStage);
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
