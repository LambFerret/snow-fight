package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.lambferret.game.player.Player;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SnowBarOverlay extends ProgressBar implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(SnowBarOverlay.class.getName());
    private final Stage stage;
    Player player;

    private int assignedSnow;
    private int snowAmountToClear;


    public SnowBarOverlay(Stage stage) {
        super(0, 100, 1, false, new SnowBarStyle());
        this.stage = stage;
        this.stage.addActor(this);
    }

    public void create() {
        this.setPosition(0, 0);
        this.setSize(GlobalSettings.currWidth - OVERLAY_WIDTH, BAR_HEIGHT);

    }

    @Override
    public void init(Player player) {
        this.player = player;
        assignedSnow = PhaseScreen.level.getAssignedSnow();
        snowAmountToClear = PhaseScreen.level.getMinSnowForClear();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setValue();
    }

    private void setValue() {
        int playerCurrentSnow = player.getSnowAmount();
        int oldValue = (playerCurrentSnow * 100) / assignedSnow;
        logger.info("setValue |  🐳  | " + oldValue);
        setValue(100 - oldValue);

        var style = (SnowBarStyle) getStyle();
        style.updateAnimationFrame(oldValue);
    }

}
