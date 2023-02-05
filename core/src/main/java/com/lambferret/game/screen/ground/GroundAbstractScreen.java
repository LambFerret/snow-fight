package com.lambferret.game.screen.ground;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.SnowFight;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.screen.ui.MapOverlay;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.util.CustomInputProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.lambferret.game.screen.ui.Overlay.OverlayComponent;

public abstract class GroundAbstractScreen extends AbstractScreen {
    private static final Logger logger = LogManager.getLogger(GroundAbstractScreen.class.getName());

    public static Overlay bar;
    public static Overlay score;
    public static MapOverlay map;

    @Override
    protected void create() {
        super.create();
        map.create();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        var batch = new SpriteBatch();
        batch.begin();
        bar.render(batch);
        map.render(batch);
        score.render(batch);
        batch.end();
        switch (CustomInputProcessor.pressedKey) {
            case Input.Keys.NUM_1 -> {
                SnowFight.changeScreen = SnowFight.AddedScreen.RECRUIT_SCREEN;
            }
            case Input.Keys.NUM_2 -> {
                SnowFight.changeScreen = SnowFight.AddedScreen.TRAINING_GROUND_SCREEN;
            }
            case Input.Keys.NUM_3 -> {
                SnowFight.changeScreen = SnowFight.AddedScreen.SHOP_SCREEN;
            }
        }
    }

    public void update() {
        bar.update();
        score.update();
    }


    static {
        bar = new Overlay(OverlayComponent.BAR);
        score = new Overlay(OverlayComponent.SCORE);
        map = new MapOverlay();
    }
}
