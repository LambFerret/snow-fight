package com.lambferret.game.screen.title;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
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

import java.util.ArrayList;
import java.util.List;

public class SelectSaveScreen extends Window {
    private static final Logger logger = LogManager.getLogger(SelectSaveScreen.class.getName());
    private static final List<Actor> component = new ArrayList<>();
    private BitmapFont font;
    private Skin skin;
    private Stage stage;


    public SelectSaveScreen(Stage stage) {
        super("save", GlobalSettings.skin);
        this.stage = stage;
        this.font = GlobalSettings.font;
        this.skin = GlobalSettings.skin;

        component.add(new Label("select save file", skin));
        component.add(table());
    }

    @Override
    public void setVisible(boolean vis) {
        for (Actor actor : component) {
            actor.setVisible(vis);
        }
    }

    public void create() {
        for (Actor actor : component) {
            stage.addActor(actor);
        }
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
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
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


    public void render() {
        stage.draw();
    }

    public void update() {
        stage.act();
    }
}
