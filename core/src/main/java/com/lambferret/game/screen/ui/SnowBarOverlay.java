package com.lambferret.game.screen.ui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.component.SnowBarStyle;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.Chili;
import com.lambferret.game.soldier.Vanilla;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.OverlayText;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SnowBarOverlay extends ProgressBar implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(SnowBarOverlay.class.getName());
    public static final OverlayText text;
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
        super(0, 100, 1, false, new SnowBarStyle(List.of(new Chili(), new Vanilla()), SNOW_BAR_WIDTH, SNOW_BAR_HEIGHT));
        clearThresholdXLabel = GlobalUtil.simpleButton("silvanusParkDogT123ag");
        labelDescription = GlobalUtil.simpleButton("silvanusParkDogTag", text.getSnowOverlayDescription());
        barLabel = new Label("", GlobalSettings.skin);
        stage.addActor(this);
        stage.addActor(barLabel);
        stage.addActor(clearThresholdXLabel);
        stage.addActor(labelDescription);
        labelDescription.setVisible(false);

        this.pack();
        this.setSize(SNOW_BAR_WIDTH, SNOW_BAR_HEIGHT);
        this.setPosition(SNOW_BAR_X, SNOW_BAR_Y);

        clearThresholdXLabel.setSize(SNOW_BAR_THRESHOLD_LABEL_WIDTH, SNOW_BAR_THRESHOLD_LABEL_HEIGHT);
        clearThresholdXLabel.setPosition(-999, this.getY() + this.getHeight());

        barLabel.setSize(20, 20);
        barLabel.setPosition(this.getX() + this.getWidth() / 2, this.getY() + this.getHeight() / 2);
    }

    @Override
    public void onPlayerReady() {
        this.player = SnowFight.player;
        setLabelProperty();
        reset();
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {

    }

    private void setLabelX() {
        float thresholdX = ((this.getWidth() * (assignedSnow - snowAmountToClear)) / assignedSnow);
        clearThresholdXLabel.setX((thresholdX - clearThresholdXLabel.getWidth()) / 2);
        clearThresholdXLabel.setText(snowAmountToClear + "");

        labelDescription.setSize(30, 15);
        labelDescription.setPosition(clearThresholdXLabel.getX(), clearThresholdXLabel.getY() + clearThresholdXLabel.getHeight());
        clearThresholdXLabel.clearListeners();

        clearThresholdXLabel.addListener(Input.revealWhenHover(labelDescription));
    }

    private void setLabelProperty() {
        setLabelX();
        this.addListener(Input.setTextWhenHover(barLabel, (assignedSnow - player.getSnowAmount()) + " / " + assignedSnow));
    }

    private void setAnimateValue(float deltaTime) {
        if (!isOverlayOn || playerCurrentSnow == player.getSnowAmount()) return;

        animationTime += deltaTime;
        animationTime = MathUtils.clamp(animationTime, 0, SNOW_BAR_ANIMATION_DURATION);
        float interpolatedAlpha = Interpolation.linear.apply(animationTime / SNOW_BAR_ANIMATION_DURATION);
        playerCurrentSnow = (int) MathUtils.lerp(playerCurrentSnow, player.getSnowAmount(), interpolatedAlpha);
        int oldValue = (playerCurrentSnow * 100) / assignedSnow;
        setValue(100 - oldValue);
        ((SnowBarStyle) getStyle()).updateAnimationFrame(oldValue);
    }

    private void start() {
        assignedSnow = PhaseScreen.level.getAssignedSnow();
        snowAmountToClear = PhaseScreen.level.getMinSnowForClear();
        playerCurrentSnow = assignedSnow;
        isOverlayOn = true;
        setLabelX();
    }

    private void reset() {
        setValue(0);
        ((SnowBarStyle) getStyle()).reset();
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

    @Override
    public boolean setValue(float value) {
        animationTime = 0.0F;
        return super.setValue(value);
    }

    static {
        text = LocalizeConfig.uiText.getOverlayText();
    }

}
