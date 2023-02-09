package com.lambferret.game.screen.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.Hitbox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionPhaseScreen extends PhaseUIScreen {
    private static final Logger logger = LogManager.getLogger(ActionPhaseScreen.class.getName());

    Hitbox box;

    public ActionPhaseScreen() {
        box = new Hitbox(30, 30, 30, 30);
    }

    @Override
    protected void switchScreen() {
        phaseNumber += 1;
        logger.info("switchScreen |  🐳 phasenumber | " + phaseNumber);
        SnowFight.changeScreen = SnowFight.AddedScreen.READY_SCREEN;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        batch.setColor(Color.CORAL);
        box.render(batch);

    }

    @Override
    public void update(float delta) {
        super.update(delta);


        if (lastPhaseNumber == phaseNumber) {
            logger.info("update |  🐳 2 |end ");
            endStage();
        }
    }

    private void endStage() {
        SnowFight.changeScreen = SnowFight.AddedScreen.TRAINING_GROUND_SCREEN;
    }

}
