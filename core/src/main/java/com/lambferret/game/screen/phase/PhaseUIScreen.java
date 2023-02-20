package com.lambferret.game.screen.phase;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.SnowFight;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.screen.ui.BarOverlay;
import com.lambferret.game.util.CustomInputProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class PhaseUIScreen extends AbstractScreen {
    private static final Logger logger = LogManager.getLogger(PhaseUIScreen.class.getName());

    private BarOverlay bar = new BarOverlay();
    protected static short phaseNumber = 0;
    protected static short lastPhaseNumber = 6;

    @Override
    public void render(SpriteBatch batch) {
        bar.render(batch);

    }

    @Override
    public void update(float delta) {
        bar.update(delta);
        switchScreen();
    }

    private void switchScreen() {
        if (phaseNumber % 2 == 0 && CustomInputProcessor.getPressedKeyUp() == Input.Keys.NUM_0) {
            SnowFight.changeScreen = SnowFight.AddedScreen.ACTION_SCREEN;
            logger.info("phaseNumber : " + phaseNumber);
            phaseNumber += 1;
//            isReadyPhase = false;
        } else if (phaseNumber % 2 == 1 && CustomInputProcessor.getPressedKeyUp() == Input.Keys.NUM_9) {
            SnowFight.changeScreen = SnowFight.AddedScreen.READY_SCREEN;
//            isReadyPhase = true;
            phaseNumber += 1;
            logger.info("phaseNumber : " + phaseNumber);

        }
    }

}

