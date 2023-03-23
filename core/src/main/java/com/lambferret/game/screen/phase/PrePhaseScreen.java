package com.lambferret.game.screen.phase;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrePhaseScreen implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(PrePhaseScreen.class.getName());
    TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
    Container<Table> mapContainer;
    TextButton textButton;
    BitmapFont font = new BitmapFont();
    Stage stage;
    Player player;

    public PrePhaseScreen(Container<Table> mapContainer) {
        this.stage = new Stage();
        this.mapContainer = mapContainer;
        style.font = font;
        textButton = new TextButton("PrePhaseScreen", style);
        stage.addActor(textButton);
        stage.addActor(mapContainer);
    }


    public void create() {

    }

    public void init(Player player) {
        this.player = player;
    }

    @Override
    public void startPhase() {

    }

    @Override
    public void executePhase() {

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
