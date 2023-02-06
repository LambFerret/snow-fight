package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.screen.ground.GroundButton;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Overlay {
    private static final Logger logger = LogManager.getLogger(Overlay.class.getName());

    private OverlayComponent overlayComponent;
    private Hitbox box;
    private List<GroundButton> buttons;

    public Overlay(OverlayComponent overlayComponent) {
        this.buttons = new ArrayList<>();
        this.overlayComponent = overlayComponent;
        switch (this.overlayComponent) {
            case SCORE -> makeScoreOverlay();
            case BAR -> makeBarOverlay();
        }
    }

    public void update() {

        /*
         * TODO : 바보같음. BAR를 따로 만들고 차라리 OVERLAY를 abstract나 interface로 고려하는게
         *  render에도 있으니 확인
         */
        if (this.overlayComponent == OverlayComponent.BAR) {
            for (GroundButton button : buttons) {
                button.update();
            }
        }
    }

    public void render(SpriteBatch batch) {
        if (this.overlayComponent == OverlayComponent.BAR) {
            for (GroundButton button : buttons) {
                button.render(batch);
            }
        }
        this.box.render(batch);
    }

    private void addGroundButtonsToUI() {
        buttons.add(new GroundButton(GroundButton.GroundButtonAction.RECRUIT));
        buttons.add(new GroundButton(GroundButton.GroundButtonAction.SHOP));
        buttons.add(new GroundButton(GroundButton.GroundButtonAction.TRAINING_GROUND));
    }


    private void makeScoreOverlay() {
        this.box = new Hitbox(0.0F, (float) GlobalSettings.HEIGHT - 100.0F, 50.0F, 50.0F);
    }

    private void makeBarOverlay() {
        addGroundButtonsToUI();
        this.box = new Hitbox(0.0F, (float) GlobalSettings.HEIGHT - 50.0F, GlobalSettings.WIDTH, 50.0F);

    }


    public enum OverlayComponent {
        SCORE,
        BAR,
        ;
    }
}
