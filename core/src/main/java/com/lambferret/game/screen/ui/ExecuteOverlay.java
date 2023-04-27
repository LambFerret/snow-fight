package com.lambferret.game.screen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.setting.ScreenConfig;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.OverlayText;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExecuteOverlay extends Container<CustomButton> implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(ExecuteOverlay.class.getName());
    private static final OverlayText text = LocalizeConfig.uiText.getOverlayText();
    public static final float EXECUTE_X = GlobalSettings.currWidth - OVERLAY_BORDERLINE_WIDTH;
    public static final int EXECUTE_Y = 0;
    public static final float EXECUTE_WIDTH = OVERLAY_BORDERLINE_WIDTH;
    public static final float EXECUTE_HEIGHT = OVERLAY_BORDERLINE_HEIGHT;
    public static final float EXECUTE_HIDE_BUTTON_RELATIVE_X = EXECUTE_WIDTH * 2 / 3;
    public static final float EXECUTE_HIDE_X = EXECUTE_X + EXECUTE_HIDE_BUTTON_RELATIVE_X;
    public static final float EXECUTE_HIDE_ANIMATION_DURATION = 0.1F;

    private final Stage stage;
    private final CustomButton executeButton;

    Cursor cursor;
    Player player;
    private boolean isHide = true;

    public ExecuteOverlay(Stage stage) {
        this.stage = stage;
        executeButton = new CustomButton(text.getExecuteOverlayName(), executeButtonStyle());

        this.setActor(executeButton);
        this.stage.setKeyboardFocus(this);
        this.stage.addActor(this);
    }

    private void setCursor() {
        Texture pen = AssetFinder.getTexture("pen");
        pen.getTextureData().prepare();
        Pixmap pixmapSrc = pen.getTextureData().consumePixmap();
        Pixmap pixmapRGBA = new Pixmap(256, 256, Pixmap.Format.RGBA8888);
        pixmapRGBA.drawPixmap(pixmapSrc, 0, 0);

        cursor = Gdx.graphics.newCursor(pixmapRGBA, 0, 0);
    }

    public void create() {
        this.setPosition(EXECUTE_X, EXECUTE_Y);
        this.setSize(EXECUTE_WIDTH, EXECUTE_HEIGHT);
        this.fill();

        this.setDebug(true, true);

        setCursor();
        this.executeButton.setPosition(0, 0);
        if (isHide) {
            this.executeButton.setX(EXECUTE_HIDE_BUTTON_RELATIVE_X);
        }
    }

    @Override
    public void init(Player player) {
        this.player = player;

        this.addListener(new InputListener() {

            /*
            욕나오는 부분
            Hover -> entered with pointer -1
            Click down -> entered with pointer 0
            Release click -> exited with pointer 0
            Move cursor away -> exited with pointer -1
             */
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                if (pointer == -1) {
                    hide();
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (pointer == -1) {
                    show();
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                screenChanger();
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    screenChanger();
                }
                return super.keyDown(event, keycode);
            }
        });
    }

    private void hide() {
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        this.executeButton.addAction(
            Actions.moveTo(EXECUTE_HIDE_BUTTON_RELATIVE_X, 0, SOLDIER_HIDE_ANIMATION_DURATION)
        );
        isHide = true;
    }

    private void show() {
        Gdx.graphics.setCursor(cursor);
        this.executeButton.addAction(
            Actions.moveTo(0, 0, EXECUTE_HIDE_ANIMATION_DURATION)
        );
        isHide = false;
    }

    private ImageTextButton.ImageTextButtonStyle executeButtonStyle() {
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = new TextureRegionDrawable(AssetFinder.getTexture("execute"));
        style.font = GlobalSettings.font;
        return style;
    }

    private void screenChanger() {
        Level level = PhaseScreen.level;
        switch (PhaseScreen.getCurrentScreen()) {
            case PRE -> {
                PhaseScreen.screenPtoR();
            }
            case READY -> {
                PhaseScreen.screenRtoA();
            }
            case ACTION -> {
                if (level.getMaxIteration() > level.getCurrentIteration()) {
                    PhaseScreen.screenAtoR();
                } else if (level.getMaxIteration() == level.getCurrentIteration()) {
                    if (player.getSnowAmount() > level.getMinSnowForClear()) {
                        PhaseScreen.screenAtoD();
                    } else {
                        PhaseScreen.screenAtoV();
                    }
                } else {
                    throw new RuntimeException("current iter is bigger than max iter");
                }
            }
            case VICTORY -> {
                logger.info("beat this level");
                ScreenConfig.changeScreen = ScreenConfig.AddedScreen.GROUND_SCREEN;
            }
            case DEFEAT -> {
                logger.info("game over");
                ScreenConfig.changeScreen = ScreenConfig.AddedScreen.TITLE_SCREEN;
            }
        }
        Overlay.changePhaseInputProcessor();
    }

}
