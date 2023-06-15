package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.lambferret.game.SnowFight;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.screen.phase.PhaseScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CostOverlay extends ImageTextButton implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(CostOverlay.class.getName());

    public static final int COST_SIZE = 100;
    public static final int COST_X = 200;
    public static final int COST_Y = 200;
    Stage stage;
    Player player;

    public CostOverlay(Stage stage, ImageTextButtonStyle style) {
        super("", style);
        this.stage = stage;
        stage.addActor(this);
        resize();
//        style.font = GlobalSettings.font;
//        style.up = new TextureRegionDrawable( AssetFinder.getTexture("123"));
//        addListener(Input.hover(()-> {}))
    }

    public void resize() {
        this.setSize(COST_SIZE, COST_SIZE);
        this.setPosition(COST_X, COST_Y);
    }

    @Override
    public void onPlayerReady() {
        player = SnowFight.player;
        setText(player.getCurrentCost() + " / " + player.getMaxCost() + " / " + PhaseScreen.level.getMaxSoldierCapacity());
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {
        setText(player.getCurrentCost() + " / " + player.getMaxCost());
    }


}
