package com.lambferret.game.screen.ground;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShopScreen   {
    private static final Logger logger = LogManager.getLogger(ShopScreen.class.getName());

    TextButton.TextButtonStyle style;
    TextButton textButton;
    BitmapFont font;
    Stage stage;

    public ShopScreen() {
        stage = new Stage();
        font = new BitmapFont();
        style = new TextButton.TextButtonStyle();
        style.font = font;
        textButton = new TextButton("SHOP", style);
        stage.addActor(textButton);
    }

    public void render(SpriteBatch batch) {
        stage.act();
        stage.draw();
    }

    public void update(float delta) {
    }
}
