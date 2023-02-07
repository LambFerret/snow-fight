package com.lambferret.game.screen.ground;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.screen.ui.BarOverlay;
import com.lambferret.game.screen.ui.MapOverlay;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.screen.ui.ScoreOverlay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public abstract class GroundAbstractScreen extends AbstractScreen {
    private static final Logger logger = LogManager.getLogger(GroundAbstractScreen.class.getName());

    public static Overlay map = new MapOverlay();
    public static Overlay bar = new BarOverlay();
    public static Overlay score = new ScoreOverlay();
    public static List<Overlay> lists = new ArrayList<>();

    static {
        lists.add(map);
        lists.add(bar);
        lists.add(score);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        var batch = new SpriteBatch();
        batch.begin();

        for (Overlay overlay : lists) {
            overlay.render(batch);
        }

        batch.end();
    }

    @Override
    public void update() {
        for (Overlay overlay : lists) {
            overlay.update();
        }
    }


}
