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
    private final CustomButton borderline;
    private final CustomButton thresholdDescription;
    private final Label barLabel;
    Player player;
    private int assignedSnow;
    private int snowAmountToClear;
    private int playerCurrentSnow;
    private float animationTime = 0.0f;

    public SnowBarOverlay(Stage stage) {
        super(0, 100, 1, false, new SnowBarStyle(List.of(new Chili(), new Vanilla()), SNOW_BAR_WIDTH, SNOW_BAR_HEIGHT));
        borderline = GlobalUtil.simpleButton("borderline");
        thresholdDescription = GlobalUtil.simpleButton("silvanusParkDogTag", text.getSnowOverlayDescription());
        barLabel = new Label("", GlobalSettings.skin);
        stage.addActor(this);
        stage.addActor(barLabel);
        stage.addActor(borderline);
        stage.addActor(thresholdDescription);
        thresholdDescription.setVisible(false);

        this.pack();
        setSize();
    }

    private void setSize() {
        this.setSize(SNOW_BAR_WIDTH, SNOW_BAR_HEIGHT);
        this.setPosition(SNOW_BAR_X, SNOW_BAR_Y);

        borderline.setSize(SNOW_BAR_BORDERLINE_WIDTH, SNOW_BAR_BORDERLINE_HEIGHT);
        borderline.setPosition(-999, this.getY() + this.getHeight());
        borderline.padBottom(SNOW_BAR_BORDER_BOTTOM_PAD);

        barLabel.setSize(SNOW_BAR_LABEL_WIDTH, SNOW_BAR_LABEL_HEIGHT);
        barLabel.setPosition(
            this.getX() + (this.getWidth() - barLabel.getWidth()) / 2,
            this.getY() + (this.getHeight() - barLabel.getHeight()) / 2
        );
    }

    @Override
    public void onPlayerReady() {
        this.player = SnowFight.player;
        assignedSnow = PhaseScreen.level.getAssignedSnow();
        snowAmountToClear = PhaseScreen.level.getMinSnowForClear();
        playerCurrentSnow = assignedSnow;

        setLabelX();
        reset();
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {

    }

    private void setLabelX() {
        float thresholdX = ((this.getWidth() * (assignedSnow - snowAmountToClear)) / assignedSnow);

        borderline.setX(this.getX() + thresholdX - borderline.getWidth() / 2);
        borderline.setText(String.valueOf(snowAmountToClear));

        thresholdDescription.setSize(SNOW_BAR_DESCRIPTION_WIDTH, SNOW_BAR_DESCRIPTION_HEIGHT);
        thresholdDescription.setPosition(
            borderline.getX() + (borderline.getWidth() - thresholdDescription.getWidth()) / 2,
            borderline.getY() + borderline.getHeight()
        );
        borderline.addListener(Input.visibleWhenHover(thresholdDescription));
        this.addListener(Input.hover(
            () -> barLabel.setText((assignedSnow - player.getSnowAmount()) + " / " + assignedSnow),
            () -> barLabel.setText("")
        ));
    }

    private void setAnimateValue(float deltaTime) {
        if (player == null || playerCurrentSnow == player.getSnowAmount()) return;

        animationTime += deltaTime;
        animationTime = MathUtils.clamp(animationTime, 0, SNOW_BAR_ANIMATION_DURATION);
        float interpolatedAlpha = Interpolation.linear.apply(animationTime / SNOW_BAR_ANIMATION_DURATION);
        playerCurrentSnow = (int) MathUtils.lerp(playerCurrentSnow, player.getSnowAmount(), interpolatedAlpha);
        int oldValue = (playerCurrentSnow * 100) / assignedSnow;
        setValue(100 - oldValue);
        ((SnowBarStyle) getStyle()).updateAnimationFrame(oldValue);
    }

    private void reset() {
        setValue(0);
        ((SnowBarStyle) getStyle()).reset();
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        setAnimateValue(delta);
    }

    @Override
    public void setVisible(boolean visible) {
        borderline.setVisible(visible);
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
