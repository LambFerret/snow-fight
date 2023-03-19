package com.lambferret.game.screen.title;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.SnowFight;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.SaveLoader;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.setting.ScreenConfig;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.TitleMenuText;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TitleScreen extends AbstractScreen {
    private static final Logger logger = LogManager.getLogger(TitleScreen.class.getName());

    public static Screen screen;
    private final SelectSaveScreen selectSaveScreen;
    private final SelectLoadScreen selectLoadScreen;
    private final Stage stage;
    TitleMenuText text;
    BitmapFont font;

    public TitleScreen() {
        stage = new Stage();
        text = LocalizeConfig.uiText.getTitleMenuText();

        selectSaveScreen = new SelectSaveScreen(stage);
        selectLoadScreen = new SelectLoadScreen(stage);

        stage.addActor(selectSaveScreen);
        stage.addActor(selectLoadScreen);

        screen = Screen.TITLE;
    }

    public Stage getStage() {
        return stage;
    }

    private void initDisplay() {
        selectLoadScreen.setVisible(false);
        selectSaveScreen.setVisible(false);
    }

    @Override
    public void create() {
        font = GlobalSettings.font;
        stage.addActor(backGroundImage());
        stage.addActor(createTable());
        selectSaveScreen.create();
        selectLoadScreen.create();

        initDisplay();
    }

    private Image backGroundImage() {
        Image backgroundImage = new Image(AssetFinder.getTexture("titleBackground"));
        backgroundImage.setSize(stage.getWidth(), stage.getHeight());
        backgroundImage.toBack();
        backgroundImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                initDisplay();
            }
        });
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

    private ImageTextButton.ImageTextButtonStyle getButtonStyle() {
        TextureRegionDrawable texture = GlobalSettings.debugTexture;
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
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
        var button = new ImageTextButton(label, getButtonStyle());
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setAction(action);
            }
        });
        return button;

    }

    private void setAction(TitleAction action) {
        initDisplay();
        switch (action) {
            case NEW -> {
                selectSaveScreen.setVisible(true);
                selectSaveScreen.toFront();
            }
            case CONTINUE -> {
                selectLoadScreen.setVisible(true);
                selectLoadScreen.toFront();
            }
            case LOAD -> {
                ScreenConfig.changeScreen = ScreenConfig.AddedScreen.GROUND_SCREEN;
            }
            case OPTION -> {
                SaveLoader.load(0);
                SnowFight.player = new Player();
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

    public enum Screen {
        TITLE,
        SELECT_SAVE,
        SELECT_LOAD,
        ;
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
