package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefeatScreen implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(DefeatScreen.class.getName());
    TextButton textButton;
    public static final Stage stage = new Stage();

    public DefeatScreen() {
        textButton = new TextButton("DefeatScreen", GlobalSettings.skin);
        textButton.setPosition(300, 300);
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
