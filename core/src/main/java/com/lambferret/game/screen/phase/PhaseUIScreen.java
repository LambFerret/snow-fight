package com.lambferret.game.screen.phase;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.screen.ui.Overlay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class PhaseUIScreen extends AbstractScreen {
    private static final Logger logger = LogManager.getLogger(PhaseUIScreen.class.getName());
    private static Overlay overlay;

    public PhaseUIScreen() {
        overlay = Overlay.getInstance();
    }

    @Override
    public void create() {
        super.create();
        overlay.setPhaseUI();
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
