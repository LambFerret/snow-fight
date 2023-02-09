package com.lambferret.game.screen.stage;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.screen.ui.BarOverlay;
import com.lambferret.game.util.CustomInputProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class PhaseUIScreen extends AbstractScreen {
    private static final Logger logger = LogManager.getLogger(PhaseUIScreen.class.getName());

    private BarOverlay bar = new BarOverlay();
    protected short phaseNumber;
    protected short lastPhaseNumber = 3;
    private static boolean isSwitch = false;



    @Override
    public void render(SpriteBatch batch) {
        bar.render(batch);

    }

    @Override
    public void update(float delta) {
        bar.update(delta);
        if (CustomInputProcessor.pressedKey == Input.Keys.NUM_0) {
            CustomInputProcessor.pressedKey = Input.Keys.NUM_9;
            isSwitch = true;
        }
        if (isSwitch) {
            logger.info("update |  🐳 clickerd | ");
            isSwitch = false;
            switchScreen();

        }
    }

    protected abstract void switchScreen();
}
