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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SelectSaveScreen extends Window {
    private static final Logger logger = LogManager.getLogger(SelectSaveScreen.class.getName());
    private BitmapFont font;
    private final Skin skin;
    private final Stage stage;
    public static final int SAVE_WIDTH = 800;
    public static final int SAVE_HEIGHT = 400;

    public SelectSaveScreen(Stage stage) {
        super("save", GlobalSettings.skin);
        this.stage = stage;
        this.font = GlobalSettings.font;
        this.skin = GlobalSettings.skin;

        setWindowProperty(this);
    }

    private void setWindowProperty(Window window) {
        TextureRegionDrawable texture = GlobalSettings.debugTexture;
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
            table.add(makeButton(i)).pad(10);
            table.row();
        }
        table.setPosition(GlobalSettings.currWidth / 2.0F, GlobalSettings.currHeight / 2.0F);
        return table;
    }

    private ImageTextButtonStyle setButtonStyle() {
        TextureRegionDrawable texture = GlobalSettings.debugTexture;
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = texture;
        style.font = GlobalSettings.font;
        return style;
    }

    private ImageTextButton makeButton(int index) {
        boolean isExist = SaveLoader.isSaveExist(index);
        String label = isExist ? "load " + index : "new " + index;
        ImageTextButton button = new ImageTextButton(label, setButtonStyle());
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isExist) {
                    showDialog(index);
                } else {
                    logger.info("cannot select empty slot");
                }
            }

        });
        return button;
    }

    private void showDialog(int index) {
        Dialog dialog = new Dialog("Confirmation", skin) {
            {
                text("Are you sure?");
                button("Yes", "YES");
                button("No", "NO");
            }

            @Override
            protected void result(Object object) {
                if ("YES".equals(object)) {
                    SaveLoader.makeNewSave(index);
                    SaveLoader.load(index);
                    SnowFight.setPlayer();
                    ScreenConfig.changeScreen = ScreenConfig.AddedScreen.GROUND_SCREEN;
                }
            }
        };
        dialog.show(stage);
    }
}
