package com.lambferret.game.screen.title;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TitleScreen2 extends AbstractScreen {
    private static final Logger logger = LogManager.getLogger(TitleScreen2.class.getName());

    Stage stage;
    BitmapFont font;
    Viewport viewport;
    ImageButton button;
    ImageButton.ImageButtonStyle buttonStyle;
    Skin skin;
    Texture texture;

    public TitleScreen2() {
        stage = new Stage();
        font = new BitmapFont();
        viewport = new ExtendViewport(64, 48);
        buttonStyle = new ImageButton.ImageButtonStyle();
        skin = new Skin();
        texture = AssetFinder.getTexture("yellow");
        TextureRegionDrawable textureRegion = new TextureRegionDrawable(texture);
        button = new ImageButton(textureRegion);
        button.setPosition(500, 500);
        button.setSize(100, 100);
//        button.setRotation(45);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("clekcklckejo");
            }
        });

        Gdx.input.setInputProcessor(stage);
        stage.addActor(button);
    }

    @Override
    public void create() {
    }

    @Override
    public void render() {
        stage.draw();
    }

    @Override
    public void update() {
    }

}
