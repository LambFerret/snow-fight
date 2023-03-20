package com.lambferret.game.screen.ground;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShopScreen implements AbstractGround {
    private static final Logger logger = LogManager.getLogger(ShopScreen.class.getName());

    TextButton.TextButtonStyle style;
    TextButton textButton;
    BitmapFont font;
    Stage stage;

    public ShopScreen() {
        this.stage = new Stage();
        font = new BitmapFont();
        style = new TextButton.TextButtonStyle();
        style.font = font;
        textButton = new TextButton("SHOP", style);
        stage.addActor(textButton);
    }

    public Stage getStage() {
        return this.stage;
    }

    public void create() {
//        stage.addActor(makeButton());
    }

    private void setProperty() {
    }

    @Override
    public void init() {

    }

    public void render() {
        stage.draw();
    }

    public void update() {
        stage.act();
    }

}
