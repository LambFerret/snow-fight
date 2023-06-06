package com.lambferret.game.screen.title;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.save.SaveLoader;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.setting.FontConfig;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.setting.ScreenConfig;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.TitleMenuText;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TitleScreen extends AbstractScreen {
    private static final Logger logger = LogManager.getLogger(TitleScreen.class.getName());
    private static final TitleMenuText text;
    public static final int TITLE_BUTTON_WIDTH = 100;
    public static final int TITLE_BUTTON_HEIGHT = 35;
    public static final int TITLE_BUTTON_PAD = 10;
    public static final int TITLE_X = 300;
    public static final int TITLE_Y = 300;

    private final SelectSaveWindow selectSaveWindow;
    private final SelectLoadWindow selectLoadWindow;
    private final OptionWindow optionWindow;
    private final Stage stage;
    BitmapFont font;

    public TitleScreen() {
        stage = new Stage();
        stage.addActor(backGroundImage());

        selectSaveWindow = new SelectSaveWindow(stage);
        selectLoadWindow = new SelectLoadWindow(stage);
        optionWindow = new OptionWindow(stage, true);

        stage.addActor(selectSaveWindow);
        stage.addActor(selectLoadWindow);

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
        optionWindow.setVisible(false);
    }

    private Image backGroundImage() {
        Image backgroundImage = new Image(AssetFinder.getTexture("black"));
        backgroundImage.setSize(stage.getWidth(), stage.getHeight());
        backgroundImage.toBack();
        backgroundImage.addListener(Input.click(this::initDisplay));
        return backgroundImage;
    }

    private void setTable() {
        Table table = new Table() {
            @Override
            public Cell add(Actor actor) {
                Cell<Actor> cell = super.add(actor)
                    .width(((CustomButton) actor).getLabel().getWidth())
                    .height(TITLE_BUTTON_HEIGHT)
                    .pad(TITLE_BUTTON_PAD);
                cell.row();
                return cell;
            }
        };
        table.add(button(TitleAction.NEW));
        if (SaveLoader.getRecentSave() != -1) {
            table.add(button(TitleAction.CONTINUE));
            table.add(button(TitleAction.LOAD));
        }
        table.add(button(TitleAction.OPTION));
        table.add(button(TitleAction.EXIT));
        table.setPosition(TITLE_X, TITLE_Y);
        stage.addActor(table);
    }

    private CustomButton button(TitleAction action) {
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.font = FontConfig.titleButtonFont;
        style.up = new TextureRegionDrawable(AssetFinder.transparentTexture());
//        style.over = new TextureRegionDrawable(AssetFinder.getTexture("spotlight"));
        String label = switch (action) {
            case NEW -> text.getNewGame();
            case CONTINUE -> text.getContinueGame();
            case LOAD -> text.getLoadGame();
            case OPTION -> text.getOption();
            case EXIT -> text.getExit();
        };
        CustomButton button = new CustomButton(label, style);
        button.addListener(Input.click(() -> setAction(action)));
        button.addListener(Input.hover(() -> {
                AssetFinder.getSound("button_click").play(GlobalSettings.effectVolume);
                button.getLabel().setColor(Color.GOLD);
            },
            () -> button.getLabel().setColor(Color.WHITE)
        ));
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
                optionWindow.setVisible(true);
                optionWindow.toFront();
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
        EXIT
    }

    static {
        text = LocalizeConfig.uiText.getTitleMenuText();
    }

}
