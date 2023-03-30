package com.lambferret.game.screen.phase;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.player.Player;
import com.lambferret.game.screen.ground.GroundScreen;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VictoryScreen implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(VictoryScreen.class.getName());
    TextButton textButton;
    Stage stage;

    public VictoryScreen() {
        this.stage = new Stage();
        textButton = new TextButton("VictoryScreen", GlobalSettings.skin);
        stage.addActor(textButton);
    }

    public void create() {
//    stage.addActor(this);
    }

    @Override
    public void init(Player player) {
    }

    @Override
    public void startPhase() {

    }

    @Override
    public void executePhase() {
        GroundScreen.initiation();
    }

    public Stage getStage() {
        return this.stage;
    }

    public void render() {
        stage.draw();
    }

    public void update() {
        stage.act();
    }
}
