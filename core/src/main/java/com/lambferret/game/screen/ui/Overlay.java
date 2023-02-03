package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.screen.component.Hitbox;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Overlay {
    private static final Logger logger = LogManager.getLogger(Overlay.class.getName());

    private OverlayComponent overlayComponent;
    private Hitbox box;
    public Overlay(OverlayComponent overlayComponent) {

        this.overlayComponent = overlayComponent;
        switch (this.overlayComponent) {
            case SCORE -> makeScoreOverlay();
            case BAR -> makeBarOverlay();
        }
    }

    public void update() {

    }

    public void render(SpriteBatch batch) {
        this.box.render(batch);

    }


    private void makeScoreOverlay() {
        this.box = new Hitbox(0.0F, (float) GlobalSettings.HEIGHT - 100.0F, 50.0F, 50.0F);
    }

    private void makeBarOverlay() {
        this.box = new Hitbox(0.0F, (float) GlobalSettings.HEIGHT - 50.0F, GlobalSettings.WIDTH, 50.0F);

    }


    public enum OverlayComponent {
        SCORE,
        BAR,
        ;
    }
}
