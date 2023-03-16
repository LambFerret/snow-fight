package com.lambferret.game.screen.title;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.setting.ScreenConfig;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.TitleMenuText;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TitleMenuScreen {
    private static final Logger logger = LogManager.getLogger(TitleMenuScreen.class.getName());

    Table table;
    Stage stage;
    TitleMenuText text;
    BitmapFont font;


    public TitleMenuScreen() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        text = LocalizeConfig.uiText.getTitleMenuText();

    }

    public void create() {
        font = GlobalSettings.font;
        table = createTable();
        stage.addActor(backGroundImage());
        stage.addActor(table);
    }

    private Image backGroundImage() {
        Image backgroundImage = new Image(AssetFinder.getTexture("titleBackground"));
        backgroundImage.setSize(stage.getWidth(), stage.getHeight());
        backgroundImage.setZIndex(0);
        return backgroundImage;
    }

    private Table createTable() {
        Table table = new Table();
        table.clear();
        table.add(button(TitleAction.NEW)).pad(10);
        table.row();
        table.add(button(TitleAction.CONTINUE)).pad(10);
        table.row();
        table.add(button(TitleAction.LOAD)).pad(10);
        table.row();
        table.add(button(TitleAction.OPTION)).pad(10);
        table.row();
        table.add(button(TitleAction.CREDIT)).pad(10);
        table.row();
        table.add(button(TitleAction.EXIT)).pad(10);
        table.setPosition(300, 300);
        return table;
    }

    private ImageTextButtonStyle getButtonStyle() {
        TextureRegionDrawable texture = new TextureRegionDrawable(AssetFinder.getTexture("yellow"));
        ImageTextButtonStyle style = new ImageTextButtonStyle();
        style.up = texture;
        style.font = font;
        return style;
    }

    private ImageTextButton button(TitleAction action) {
        String label = switch (action) {
            case NEW -> text.getNewGame();
            case CONTINUE -> text.getContinueGame();
            case LOAD -> text.getLoadGame();
            case OPTION -> text.getOption();
            case CREDIT -> text.getCredit();
            case EXIT -> text.getExit();
        };

        logger.info("button |  🐳 here label | " + label);

        var a = new ImageTextButton(label, getButtonStyle());
        a.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setAction(action);
            }
        });
        return a;

    }

    private void setAction(TitleAction action) {
        switch (action) {
            case NEW -> {
                SelectSaveScreen.isLoad = false;
                TitleScreen.screen = TitleScreen.Screen.SELECT_SAVE;
            }
            case CONTINUE -> {
                SelectSaveScreen.isLoad = true;
                TitleScreen.screen = TitleScreen.Screen.SELECT_SAVE;
            }
            case LOAD -> {
                ScreenConfig.changeScreen = ScreenConfig.AddedScreen.GROUND_SCREEN;
            }
            case OPTION -> {
                ScreenConfig.changeScreen = ScreenConfig.AddedScreen.PHASE_SCREEN;
            }
            case CREDIT -> {
                logger.info("update | CREDIT");
            }
            case EXIT -> {
                Gdx.app.exit();
            }
        }
    }


    public void render() {
        stage.draw();
    }

    public void update() {
        stage.act();
    }

    enum TitleAction {
        NEW,
        CONTINUE,
        LOAD,
        OPTION,
        CREDIT,
        EXIT
    }
}
