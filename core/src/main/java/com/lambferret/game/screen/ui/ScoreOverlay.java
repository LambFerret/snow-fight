package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScoreOverlay extends Table implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(ScoreOverlay.class.getName());
    private final Stage stage;

    public ScoreOverlay(Stage stage) {
        this.stage = stage;
        stage.addActor(this);
    }

    public void create() {
        this.setPosition(50, GlobalSettings.currHeight - BAR_HEIGHT - 50);
        this.setSize(100, 100);
        this.setBackground(GlobalSettings.debugTexture);
        this.setColor(GlobalSettings.debugColorGreen);
    }

    @Override
    public void init(Player player) {
    }

}
