package com.lambferret.game.screen.title;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.SnowFight;
import com.lambferret.game.save.SaveLoader;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.setting.ScreenConfig;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SelectLoadScreen extends Window{
    private static final Logger logger = LogManager.getLogger(SelectLoadScreen.class.getName());
    private BitmapFont font;
    private Skin skin;
    private Stage stage;
    public static final int SAVE_WIDTH = 800;
    public static final int SAVE_HEIGHT = 400;

    public SelectLoadScreen(Stage stage) {
        super("load", GlobalSettings.skin);
        this.stage = stage;
        this.font = GlobalSettings.font;
        this.skin = GlobalSettings.skin;

        setWindowProperty(this);
    }

    private void setWindowProperty(Window window) {
        TextureRegionDrawable texture = new TextureRegionDrawable(AssetFinder.getTexture("yellow"));
        window.setSize(SAVE_WIDTH, SAVE_HEIGHT);
        float windowX = (Gdx.graphics.getWidth() - this.getWidth()) / 2;
        float windowY = (Gdx.graphics.getHeight() - this.getHeight()) / 2;
        window.setPosition(windowX, windowY);
        window.setColor(Color.GREEN);
        window.setBackground(texture);
        window.add(table());
        window.add(new Label("select save file", skin));
    }

    public void create() {
     stage.addActor(this);
    }

    private Table table() {
        Table table = new Table();
        for (int i = 0; i < 3; i++) {
            table.add(button(i)).pad(10);
            table.row();
        }
        table.setPosition(GlobalSettings.currWidth / 2.0F, GlobalSettings.currHeight / 2.0F);
        return table;
    }

    private ImageTextButtonStyle getButtonStyle() {
        TextureRegionDrawable texture = new TextureRegionDrawable(AssetFinder.getTexture("yellow"));
        ImageTextButtonStyle style = new ImageTextButtonStyle();
        style.up = texture;
        style.font = GlobalSettings.font;
        return style;
    }

    private ImageTextButton button(int index) {
        boolean isExist = SaveLoader.isSaveExist(index);
        String label = isExist ? "load " + index : "new " + index;
        ImageTextButton button = new ImageTextButton(label, getButtonStyle());

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isExist) {
                    SaveLoader.load(index);
                    SnowFight.setPlayer();
                    ScreenConfig.changeScreen = ScreenConfig.AddedScreen.GROUND_SCREEN;
                } else {
                    logger.info("cannot select empty slot" );
                }
            }

        });


        return button;
    }


    public void render() {
        stage.draw();
    }

    public void update() {
        stage.act();
    }
}
