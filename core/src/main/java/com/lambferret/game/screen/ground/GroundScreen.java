package com.lambferret.game.screen.ground;

import com.lambferret.game.SnowFight;
import com.lambferret.game.player.Player;
import com.lambferret.game.player.PlayerObserver;
import com.lambferret.game.save.Item;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.screen.ui.Overlay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GroundScreen extends AbstractScreen implements PlayerObserver {
    private static final Logger logger = LogManager.getLogger(GroundScreen.class.getName());

    private static final List<AbstractGround> groundScreenList;
    private static final RecruitScreen recruitScreen;
    private static final ShopScreen shopScreen;
    private static final TrainingGroundScreen trainingGroundScreen;

    private static Screen currentScreen = Screen.TRAINING_GROUND;
    private static Player player;
    private static final Overlay overlay = Overlay.getInstance();

    static {
        recruitScreen = new RecruitScreen();
        shopScreen = new ShopScreen();
        trainingGroundScreen = new TrainingGroundScreen();
        groundScreenList = List.of(
            recruitScreen,
            shopScreen,
            trainingGroundScreen
        );
    }

    public GroundScreen() {

    }

    @Override
    public void onPlayerReady() {
        player = SnowFight.player;
        Overlay.setVisibleGroundUI();
        for (AbstractGround ground : groundScreenList) {
            ground.onPlayerReady();
            player.addPlayerObserver(ground);
        }
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {

    }

    public static void changeScreen(Screen screen) {
        currentScreen = screen;
        Overlay.changeGroundInputProcessor();
        switch (screen) {
            case RECRUIT -> recruitScreen.init();
            case SHOP -> shopScreen.init();
            case TRAINING_GROUND -> trainingGroundScreen.init();
        }
    }

    public static Screen getCurrentScreen() {
        return currentScreen;
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        switch (currentScreen) {
            case RECRUIT -> recruitScreen.render();
            case SHOP -> shopScreen.render();
            case TRAINING_GROUND -> trainingGroundScreen.render();
        }
        overlay.render();
    }

    public enum Screen {
        RECRUIT,
        SHOP,
        TRAINING_GROUND,
        ;
    }
}
