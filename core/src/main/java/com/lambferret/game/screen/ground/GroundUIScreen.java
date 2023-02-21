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


public abstract class GroundUIScreen extends AbstractScreen {
    private static final Logger logger = LogManager.getLogger(GroundUIScreen.class.getName());

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
    public void create() {
        for (Overlay overlay : lists) {
            overlay.create();
        }
    }

    public void disposeOne(Overlay overlay) {
        lists.remove(overlay);
    }

    public void createOne(Overlay overlay) {
        lists.add(overlay);
        overlay.create();
    }

    @Override
    public void render(SpriteBatch batch) {
        for (Overlay overlay : lists) {
            overlay.render(batch);
        }
    }

    @Override
    public void update(float delta) {
        for (Overlay overlay : lists) {
            overlay.update(delta);
        }
    }
}
