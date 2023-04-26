package com.lambferret.game.screen.ui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.component.SnowBarStyle;
import com.lambferret.game.player.Player;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.UIText;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SnowBarOverlay extends ProgressBar implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(SnowBarOverlay.class.getName());
    public static final float ANIMATION_DURATION = 1.0f;
    public static final int THRESHOLD_LABEL_WIDTH = 50;
    public static final int THRESHOLD_LABEL_HEIGHT = 30;
    public static final UIText text;
    private final CustomButton clearThresholdXLabel;
    private final CustomButton labelDescription;
    private final Label barLabel;
    private boolean isOverlayOn = false;
    Player player;
    private int assignedSnow;
    private int snowAmountToClear;
    private int playerCurrentSnow;
    private float animationTime = 0.0f;

    public SnowBarOverlay(Stage stage) {
        super(0, 100, 1, false, new SnowBarStyle());

        ImageTextButton.ImageTextButtonStyle xLabel = new ImageTextButton.ImageTextButtonStyle();
        ImageTextButton.ImageTextButtonStyle descriptionStyle = new ImageTextButton.ImageTextButtonStyle();
        descriptionStyle.up = new TextureRegionDrawable(AssetFinder.getTexture("silvanusParkDogT123ag"));
        descriptionStyle.font = GlobalSettings.font;
        xLabel.up = new TextureRegionDrawable(AssetFinder.getTexture("silvanusParkDogTag"));
        xLabel.font = GlobalSettings.font;
        clearThresholdXLabel = new CustomButton("", xLabel);
        labelDescription = new CustomButton("UI String TODO", descriptionStyle);
        barLabel = new Label("", GlobalSettings.skin);

        stage.addActor(this);
        stage.addActor(barLabel);
        stage.addActor(clearThresholdXLabel);
        stage.addActor(labelDescription);
    }

    public void create() {
        labelDescription.setVisible(false);

        this.setSize(GlobalSettings.currWidth - OVERLAY_BORDERLINE_WIDTH, SNOW_BAR_HEIGHT);
        this.setPosition(0, 0);

        clearThresholdXLabel.setSize(THRESHOLD_LABEL_WIDTH, THRESHOLD_LABEL_HEIGHT);
        clearThresholdXLabel.setPosition(-999, this.getY() + this.getHeight());

        barLabel.setSize(20, 20);
        barLabel.setPosition(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2);
    }

    @Override
    public void init(Player player) {
        this.player = player;
        setLabelProperty();
    }

    private void setLabelX() {
        float thresholdX = ((this.getWidth() * (assignedSnow - snowAmountToClear)) / assignedSnow);
        clearThresholdXLabel.setX(thresholdX - clearThresholdXLabel.getWidth() / 2);
        clearThresholdXLabel.setText(snowAmountToClear + "");

        labelDescription.setSize(30, 15);
        labelDescription.setPosition(clearThresholdXLabel.getX(), clearThresholdXLabel.getY() + clearThresholdXLabel.getHeight());
        clearThresholdXLabel.clearListeners();

        clearThresholdXLabel.addListener(new InputListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                if (pointer == -1) {
                    labelDescription.setVisible(false);
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (pointer == -1) {
                    labelDescription.setVisible(true);
                }
            }
        });
    }

    private void setLabelProperty() {
        setLabelX();
        this.addListener(new InputListener() {
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                if (pointer == -1) {
                    barLabel.setText("");
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (pointer == -1) {
                    barLabel.setText((assignedSnow - player.getSnowAmount()) + " / " + assignedSnow);
                }
            }
        });
    }

    private void setAnimateValue(float deltaTime) {
        if (playerCurrentSnow == player.getSnowAmount() || !isOverlayOn) return;

        animationTime += deltaTime;
        animationTime = MathUtils.clamp(animationTime, 0, ANIMATION_DURATION);
        float interpolatedAlpha = Interpolation.linear.apply(animationTime / ANIMATION_DURATION);
        playerCurrentSnow = (int) MathUtils.lerp(playerCurrentSnow, player.getSnowAmount(), interpolatedAlpha);
        int oldValue = (playerCurrentSnow * 100) / assignedSnow;
        setValue(100 - oldValue);
        ((SnowBarStyle) getStyle()).updateAnimationFrame(oldValue);
    }

    @Override
    public void setVisible(boolean visible) {
        isOverlayOn = visible;
        clearThresholdXLabel.setVisible(visible);
        if (visible) {
            start();
        } else {
            stop();
        }
        super.setVisible(visible);
    }

    private void start() {
        assignedSnow = PhaseScreen.level.getAssignedSnow();
        snowAmountToClear = PhaseScreen.level.getMinSnowForClear();
        playerCurrentSnow = assignedSnow;
        isOverlayOn = true;
        setLabelX();
    }

    private void stop() {
        isOverlayOn = false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setAnimateValue(delta);
    }

    @Override
    public boolean setValue(float value) {
        animationTime = 0.0F;
        return super.setValue(value);
    }

    static {
        text = LocalizeConfig.uiText;
    }

}
