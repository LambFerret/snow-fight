package com.lambferret.game.screen.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.setting.ScreenConfig;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExecuteOverlay extends ImageTextButton implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(ExecuteOverlay.class.getName());
    private final Stage stage;
    private static final ImageTextButtonStyle imageButtonStyle;
    private final Image pen;

    static {
        imageButtonStyle = GlobalSettings.imageButtonStyle;
        imageButtonStyle.font = GlobalSettings.font;
    }

    public ExecuteOverlay(Stage stage) {
        super("execute", imageButtonStyle);
        imageButtonStyle.up = new TextureRegionDrawable(AssetFinder.getTexture("execute"));
        this.stage = stage;
        pen = new Image(AssetFinder.getTexture("pen"));
        pen.setSize(50, 60);
        pen.setVisible(false);

    }

    public void create() {
        stage.addActor(this);
        stage.addActor(pen);
        setProperty();
    }

    private void setProperty() {
        this.clear();
        this.setPosition(GlobalSettings.currWidth - OVERLAY_WIDTH, 0);
        this.setSize(OVERLAY_WIDTH, OVERLAY_HEIGHT);
        this.setColor(Color.RED);
        var xOff = this.getX();
        var yOff = this.getY();

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
                    pen.setVisible(false);
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                pen.setVisible(true);

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

                logger.info("keyDown |  🐳  | " + event);
                logger.info("keyDown |  🐳 keycode | " + keycode);
                if (keycode == Input.Keys.SPACE) {
                    screenChanger();
                }
                return super.keyDown(event, keycode);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {

                logger.info("keyUp |  🐳  | " + event);
                logger.info("keyUp |  🐳 keycode | " + keycode);
                return super.keyUp(event, keycode);
            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {

                pen.setPosition(xOff + x + pen.getWidth(), yOff + y + getHeight());
                return super.mouseMoved(event, x, y);

            }
        });

        this.setBackground(GlobalSettings.debugTexture);
        this.setColor(GlobalSettings.debugColorGreen);
    }

    private void screenChanger() {
        if (PhaseScreen.currentScreen == PhaseScreen.Screen.PRE) {
            // set PRE phase to ready phase
            PhaseScreen.currentScreen = PhaseScreen.Screen.READY;
        } else if (PhaseScreen.currentScreen == PhaseScreen.Screen.DEFEAT) {
            logger.info("game over");
            ScreenConfig.changeScreen = ScreenConfig.AddedScreen.TITLE_SCREEN;
        } else if (PhaseScreen.currentScreen == PhaseScreen.Screen.VICTORY) {
            logger.info("beat this level");
            ScreenConfig.changeScreen = ScreenConfig.AddedScreen.GROUND_SCREEN;
        } else {
            if (PhaseScreen.currentScreen == PhaseScreen.Screen.READY) {
                // execute some stuff when READY
                PhaseScreen.currentScreen = PhaseScreen.Screen.ACTION;
            } else if (PhaseScreen.currentScreen == PhaseScreen.Screen.ACTION) {

                if (Math.random() > 0.5d) {
                    // execute some stuff when ACTION
                    PhaseScreen.currentScreen = PhaseScreen.Screen.READY;

                    // if this level get maximum phase iteration ...
                } else {

                    // victory or defeat
                    if (Math.random() > 0.5d) {
                        PhaseScreen.currentScreen = PhaseScreen.Screen.VICTORY;
                    } else {
                        PhaseScreen.currentScreen = PhaseScreen.Screen.DEFEAT;
                    }
                }
            }
        }
    }
    @Override
    public void init() {

    }
}
