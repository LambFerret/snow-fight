package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VictoryScreen implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(VictoryScreen.class.getName());
    TextButton textButton;
    public static final Stage stage = new Stage();

    public VictoryScreen() {
        textButton = new TextButton("VictoryScreen", GlobalSettings.skin);
        stage.addActor(textButton);
    }

    @Override
    public void onPlayerReady() {
    }

    @Override
    public void onPlayerUpdate() {

    }

    @Override
    public void startPhase() {

    }

    @Override
    public void executePhase() {
    }

    @Override
    public void render() {
        stage.draw();
        stage.act();

    }

}
