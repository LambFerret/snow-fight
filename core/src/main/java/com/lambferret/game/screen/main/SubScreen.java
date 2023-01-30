package com.lambferret.game.screen.main;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.SnowFight;
import com.lambferret.game.screen.AbstractScreen;

public class SubScreen extends AbstractScreen {

    Skin skin;
    TextButton.TextButtonStyle style;
    TextButton textButton;
    SnowFight game;
    BitmapFont font;
    Stage stage;
    public SubScreen(SnowFight game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();
        font = new BitmapFont();
        style = new TextButton.TextButtonStyle();
        style.font = font;
        textButton = new TextButton("SUB", style);
        stage.addActor(textButton);

    }

    @Override
    public void render(float delta) {

        stage.act();
        stage.draw();
    }


    protected void update(float delta) {

    }
}
