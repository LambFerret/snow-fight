package com.lambferret.game.screen.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadyPhaseScreen extends PhaseUIScreen {
    private static final Logger logger = LogManager.getLogger(ReadyPhaseScreen.class.getName());
    Hitbox box;

    public ReadyPhaseScreen() {
        box = new Hitbox(50, 50, 50, 50);
    }


    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        batch.setColor(Color.CHARTREUSE);
        box.render(batch);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

    }
}
