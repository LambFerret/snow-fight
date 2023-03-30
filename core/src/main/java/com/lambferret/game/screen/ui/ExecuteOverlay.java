package com.lambferret.game.screen.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.setting.ScreenConfig;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExecuteOverlay extends Container<ImageTextButton> implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(ExecuteOverlay.class.getName());

    private final ImageTextButton executeButton;
    private final Stage stage;
    private Image pen;
    private boolean isHide = false;
    Player player;

    public ExecuteOverlay(Stage stage) {
        if (GlobalSettings.isDev) this.setDebug(true, true);
        this.stage = stage;
        stage.addActor(this);
        this.setPosition(GlobalSettings.currWidth - OVERLAY_WIDTH, 0);
        this.setSize(OVERLAY_WIDTH, OVERLAY_HEIGHT);
        this.fill();
        stage.setKeyboardFocus(this);

        ImageTextButton.ImageTextButtonStyle imageButtonStyle = GlobalSettings.imageButtonStyle;
        imageButtonStyle.font = GlobalSettings.font;
        imageButtonStyle.up = new TextureRegionDrawable(AssetFinder.getTexture("execute"));
        executeButton = new ImageTextButton("Execute", imageButtonStyle);
        this.executeButton.setPosition(0, 0);
        this.setActor(executeButton);
    }

    public void create() {
        pen = new Image(AssetFinder.getTexture("pen"));
        pen.setSize(50, 60);
        pen.setPosition(-100, -100);
        pen.setVisible(false);
        stage.addActor(pen);
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
                    pen.setVisible(false);
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (pointer == -1) {
                    pen.setVisible(true);
                    show();
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                screenChanger();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    screenChanger();
                }
                return super.keyDown(event, keycode);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                return super.keyUp(event, keycode);
            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                pen.setPosition(x + getX() + PADDING, y + getY() + PADDING);
                return super.mouseMoved(event, x, y);
            }
        });
    }

    private void hide() {
        if (isHide) return;
        this.executeButton.addAction(
            Actions.moveBy(this.getWidth() * 2 / 3.0F + PADDING, 0, ANIMATION_DURATION)
        );
        isHide = true;
    }

    private void show() {
        if (!isHide) return;
        this.executeButton.addAction(
            Actions.moveBy(-(this.getWidth() * 2 / 3.0F + PADDING), 0, ANIMATION_DURATION)
        );
        isHide = false;
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
                    if (player.getSnowAmount() > level.getSnowMin()) {
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
