package com.lambferret.game.screen.ground;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ShopScreen extends GroundUIScreen {
    TextButton.TextButtonStyle style;
    TextButton textButton;
    BitmapFont font;
    Stage stage;

    @Override
    public void show() {
        stage = new Stage();
        font = new BitmapFont();
        style = new TextButton.TextButtonStyle();
        style.font = font;
        textButton = new TextButton("SHOP", style);
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
