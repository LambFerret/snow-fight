package com.lambferret.game.screen.phase;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionPhaseScreen implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(ActionPhaseScreen.class.getName());
    TextButton.TextButtonStyle style;
    TextButton textButton;
    BitmapFont font;
    Stage stage;

    public ActionPhaseScreen() {
        this.stage = new Stage();
        font = new BitmapFont();
        style = new TextButton.TextButtonStyle();
        style.font = font;
        textButton = new TextButton("ActionPhaseScreen", style);
        stage.addActor(textButton);
    }

    public void create() {
        //    stage.addActor(this);
        setProperty();
    }

    @Override
    public void init(Player player) {
    }

    private void setProperty() {
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
