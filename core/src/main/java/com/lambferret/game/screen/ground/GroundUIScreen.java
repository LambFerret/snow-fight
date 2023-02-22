package com.lambferret.game.screen.ground;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.screen.ui.Overlay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class GroundUIScreen extends AbstractScreen {
    private static final Logger logger = LogManager.getLogger(GroundUIScreen.class.getName());
    private static Overlay overlay;

    public GroundUIScreen() {
        overlay = Overlay.getInstance();
    }

    @Override
    public void create() {
        super.create();
        Overlay.setGroundUI();
    }

    @Override
    public void render(SpriteBatch batch) {
        overlay.render(batch);
    }

    @Override
    public void update(float delta) {
        overlay.update(delta);
    }
}
