package com.lambferret.game.screen.phase;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadyPhaseScreen extends PhaseUIScreen {
    private static final Logger logger = LogManager.getLogger(ReadyPhaseScreen.class.getName());

    TextButton.TextButtonStyle style;
    TextButton textButton;
    BitmapFont font;
    Stage stage;

    public ReadyPhaseScreen() {
        stage = new Stage();
        font = new BitmapFont();
        style = new TextButton.TextButtonStyle();
        style.font = font;
        textButton = new TextButton("READY", style);
        stage.addActor(textButton);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        stage.act();
        stage.draw();
    }

    public void update(float delta) {
        super.update(delta);

    }
}
