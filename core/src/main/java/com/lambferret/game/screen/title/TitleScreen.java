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
import com.lambferret.game.component.CustomButton;
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
    private static final TitleMenuText text;

    private final SelectSaveWindow selectSaveWindow;
    private final SelectLoadWindow selectLoadWindow;
    private final Stage stage;
    Table table;
    BitmapFont font;

    public TitleScreen() {
        stage = new Stage();

        selectSaveWindow = new SelectSaveWindow(stage);
        selectLoadWindow = new SelectLoadWindow(stage);
        table = new Table();

        stage.addActor(backGroundImage());
        stage.addActor(selectSaveWindow);
        stage.addActor(selectLoadWindow);
        stage.addActor(table);

        font = GlobalSettings.font;

        create();
    }

    @Override
    public void create() {
        initDisplay();
        setTable();
    }

    public void initDisplay() {
        selectLoadWindow.setVisible(false);
        selectSaveWindow.setVisible(false);
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

    private void setTable() {
        table.clear();
        table.add(button(TitleAction.NEW)).pad(10);
        table.row();
        if (SaveLoader.getRecentSave() != -1) {
            table.add(button(TitleAction.CONTINUE)).pad(10);
            table.row();
            table.add(button(TitleAction.LOAD)).pad(10);
            table.row();
        }
        table.add(button(TitleAction.OPTION)).pad(10);
        table.row();
        table.add(button(TitleAction.CREDIT)).pad(10);
        table.row();
        table.add(button(TitleAction.EXIT)).pad(10);
        table.setPosition(300, 300);
    }

    private ImageTextButton.ImageTextButtonStyle getButtonStyle() {
        TextureRegionDrawable texture = new TextureRegionDrawable(AssetFinder.getTexture("buttonTitle"));
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = texture;
        style.font = font;
        return style;
    }

    private CustomButton button(TitleAction action) {
        String label = switch (action) {
            case NEW -> text.getNewGame();
            case CONTINUE -> text.getContinueGame();
            case LOAD -> text.getLoadGame();
            case OPTION -> text.getOption();
            case CREDIT -> text.getCredit();
            case EXIT -> text.getExit();
        };
        var button = new CustomButton(label, getButtonStyle());
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
                selectSaveWindow.setVisible(true);
                selectSaveWindow.toFront();
                selectSaveWindow.create();
            }
            case CONTINUE -> {
                int recentSave = SaveLoader.getRecentSave();
                SaveLoader.load(recentSave);
                SnowFight.setPlayer();
                ScreenConfig.changeScreen = ScreenConfig.AddedScreen.GROUND_SCREEN;
            }
            case LOAD -> {
                selectLoadWindow.setVisible(true);
                selectLoadWindow.toFront();
                selectLoadWindow.create();
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

    public Stage getStage() {
        return stage;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.draw();
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

    static {
        text = LocalizeConfig.uiText.getTitleMenuText();
    }

}
