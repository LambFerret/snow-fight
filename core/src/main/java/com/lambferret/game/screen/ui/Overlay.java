package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.screen.ground.MapButton;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Overlay {
    private static final Logger logger = LogManager.getLogger(Overlay.class.getName());

    private OverlayComponent overlayComponent;
    private Hitbox box;
    private List<MapButton> buttons;

    public Overlay(OverlayComponent overlayComponent) {
        this.buttons = new ArrayList<>();
        this.overlayComponent = overlayComponent;
        switch (this.overlayComponent) {
            case SCORE -> {
                this.box = new Hitbox(0.0F, (float) GlobalSettings.HEIGHT - 100.0F, 50.0F, 50.0F);
            }
            case BAR -> {
                this.box = new Hitbox(0.0F, (float) GlobalSettings.HEIGHT - 50.0F, GlobalSettings.WIDTH, 50.0F);


            }
        }
    }

    public void update() {

    }

    public void render(SpriteBatch batch) {

    }



    public enum OverlayComponent {
        SCORE,
        BAR,
        ;
    }
}
