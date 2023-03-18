package com.lambferret.game.screen.phase;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrePhaseScreen {
    private static final Logger logger = LogManager.getLogger(PrePhaseScreen.class.getName());
    TextButton.TextButtonStyle style;
    TextButton textButton;
    BitmapFont font;
    Stage stage;

    public PrePhaseScreen() {
        this.stage = new Stage();
        font = new BitmapFont();
        style = new TextButton.TextButtonStyle();
        style.font = font;
        textButton = new TextButton("PrePhaseScreen", style);
        stage.addActor(textButton);
    }

    public Stage getStage() {
        return this.stage;
    }

    public void create() {
//    stage.addActor(this);
        setProperty();
    }

    private void setProperty() {
    }

    public void render() {
        stage.draw();
    }

    public void update() {
        stage.act();
    }
}
