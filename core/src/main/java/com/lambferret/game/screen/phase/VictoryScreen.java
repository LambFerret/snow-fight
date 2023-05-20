package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.lambferret.game.save.Item;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VictoryScreen extends Window implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(VictoryScreen.class.getName());
    TextButton textButton;
    Stage stage;

    public VictoryScreen() {
        super("VictoryScreen", GlobalSettings.skin);
        textButton = new TextButton("VictoryScreen", GlobalSettings.skin);
        stage = ActionPhaseScreen.stage;
        stage.addActor(textButton);
    }

    @Override
    public void onPlayerReady() {

    }

    @Override
    public void onPlayerUpdate(Item.Type type) {

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
