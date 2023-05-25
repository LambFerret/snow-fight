package com.lambferret.game.screen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.OverlayText;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.GlobalUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExecuteOverlay extends Group implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(ExecuteOverlay.class.getName());
    private static final OverlayText text = LocalizeConfig.uiText.getOverlayText();

    private final Stage stage;
    private final CustomButton executeButton;
    Cursor cursor;
    Player player;
    boolean spaceKeyPressed = false;
    Animation<TextureRegion> animation;
    float elapsedTime = 0;
    TextureRegion currentFrame;
    private boolean isHide = true;

    public ExecuteOverlay(Stage stage) {
        this.stage = stage;
        executeButton = GlobalUtil.simpleButton("execute", text.getExecuteOverlayName());
        this.setDebug(true, true);

        setCursor();
        setSignatureAnimation();

        this.addActor(executeButton);
        this.stage.setKeyboardFocus(this.executeButton);
        this.stage.addActor(this);
        this.setPosition(EXECUTE_X, EXECUTE_Y);
        this.setSize(EXECUTE_WIDTH, EXECUTE_HEIGHT);
        executeButton.setSize(EXECUTE_WIDTH, EXECUTE_HEIGHT);

        if (isHide) {
            this.executeButton.setX(EXECUTE_HIDE_BUTTON_RELATIVE_X);
        }
    }

    @Override
    public void onPlayerReady() {
        this.player = SnowFight.player;

        executeButton.addListener(new InputListener() {

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
                } else if (pointer == 0) {
                    spaceKeyPressed = false;
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
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (pointer == 0) {
                    elapsedTime = 0;
                    spaceKeyPressed = true;
                }
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.SPACE && !isHide) {
                    elapsedTime = 0;
                    spaceKeyPressed = true;
                }
                return super.keyDown(event, keycode);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    spaceKeyPressed = false;
                }
                return super.keyUp(event, keycode);
            }
        });
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {

    }

    private void setCursor() {
        int penWidth = 128, penHeight = 256;
        Pixmap srcPix = GlobalUtil.readyPixmap(AssetFinder.getTexture("pen"));
        Pixmap penPix = new Pixmap(penWidth, penHeight, Pixmap.Format.RGBA8888);

        penPix.drawPixmap(srcPix, 0, 0, srcPix.getWidth(), srcPix.getHeight(), 0, 0, penWidth, penHeight);
        cursor = Gdx.graphics.newCursor(penPix, 0, penHeight - 1);

        srcPix.dispose();
        penPix.dispose();
    }

    private void setSignatureAnimation() {
        TextureAtlas atlas = AssetFinder.getAtlas("signature");
        float frameDuration = EXECUTE_PRESS_TIME / atlas.getRegions().size;

        Array<TextureRegion> animationFrames = new Array<>();
        for (var region : atlas.getRegions()) {
            animationFrames.add(region);
        }
        animation = new Animation<>(frameDuration, animationFrames, Animation.PlayMode.NORMAL);
    }

    private void hide() {
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        spaceKeyPressed = false;
        elapsedTime = 0;
        this.executeButton.addAction(
            Actions.moveTo(EXECUTE_HIDE_BUTTON_RELATIVE_X, 0, INVENTORY_HIDE_ANIMATION_DURATION)
        );
        executeButton.addAction(Actions.color(Color.WHITE, 0.5F));
        isHide = true;
    }

    private void show() {
        Gdx.graphics.setCursor(cursor);
        this.executeButton.addAction(
            Actions.moveTo(0, 0, EXECUTE_HIDE_ANIMATION_DURATION)
        );
        isHide = false;
    }

    private void screenChanger() {
        if (PhaseScreen.getCurrentScreen() != PhaseScreen.Screen.ACTION) hide();
        Level level = PhaseScreen.level;
        switch (PhaseScreen.getCurrentScreen()) {
            case PRE -> {
                PhaseScreen.screenPtoR();
            }
            case READY -> {
                PhaseScreen.screenRtoA();
            }
            case ACTION -> {
                PhaseScreen.executeAction();
                if (level.getMaxIteration() > level.getCurrentIteration()) {
                    PhaseScreen.screenAtoR();
                } else if (level.getMaxIteration() == level.getCurrentIteration()) {
                    if (player.getSnowAmount() >= level.getMinSnowForClear()) {
                        PhaseScreen.screenAtoD();
                    } else {
                        PhaseScreen.screenAtoV();
                    }
                } else {
                    throw new RuntimeException("current iter is bigger than max iter");
                }
            }
            case VICTORY -> {
                logger.warn("DEPRECATED VICTORY");
            }
            case DEFEAT -> {
                logger.warn("DEPRECATED DEFEAT");
            }
        }
        Overlay.changePhaseInputProcessor();
    }

    float actionPhaseTimer = 0;

    @Override
    public void act(float delta) {
        super.act(delta);
        if (PhaseScreen.getCurrentScreen() == PhaseScreen.Screen.ACTION) {
            actionPhaseTimer += delta;
            executeButton.setText(String.format("%.2f", 1 - actionPhaseTimer));
            if (actionPhaseTimer >= EXECUTE_TIMER_TO_NEXT_PHASE) {
                actionPhaseTimer = 0;
                screenChanger();
            }
        }
        if (spaceKeyPressed) {
            elapsedTime += delta;
            if (elapsedTime >= EXECUTE_PRESS_TIME) {
                screenChanger();
            }
        } else {
            elapsedTime = 0;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (spaceKeyPressed) {
            currentFrame = animation.getKeyFrame(elapsedTime);
            if (currentFrame != null) {
                batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
            }
        }
    }

}
