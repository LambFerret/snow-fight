package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.constant.Direction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Overlay {
    private static final Logger logger = LogManager.getLogger(Overlay.class.getName());
    private static Overlay instance = null;
    private static AbstractOverlay map;
    private static AbstractOverlay bar;
    private static AbstractOverlay score;
    private static AbstractOverlay ability;
    private static AbstractOverlay execute;
    private static AbstractOverlay soldier;
    private static final List<AbstractOverlay> allOverlay = new ArrayList<>();
    private static final List<AbstractOverlay> groundUIList = new ArrayList<>();
    private static final List<AbstractOverlay> phaseUIList = new ArrayList<>();
    private static List<AbstractOverlay> currentUIList;

    private Overlay() {
        map = new MapOverlay();
        bar = new BarOverlay();
        score = new ScoreOverlay();
        ability = new AbilityOverlay();
        execute = new ExecuteOverlay();
        soldier = new SoldierOverlay();
        groundUIList.add(bar);
        groundUIList.add(map);
        groundUIList.add(score);

        phaseUIList.add(bar);
        phaseUIList.add(ability);
        phaseUIList.add(execute);
        phaseUIList.add(soldier);

        allOverlay.add(bar);
        allOverlay.add(score);
        allOverlay.add(ability);
        allOverlay.add(execute);
        allOverlay.add(soldier);

        currentUIList = groundUIList;
    }

    public static Overlay getInstance() {
        if (instance == null) {
            instance = new Overlay();
            create();
        }
        return instance;
    }

    private static void create() {
        for (AbstractOverlay overlay : allOverlay) {
            overlay.create();
            overlay.hide(Direction.INSTANTLY);
        }
        bar.show(true);
    }

    public void setPhaseUI() {
        ability.show(true);
        execute.show(true);
        soldier.show(true);

        currentUIList = phaseUIList;
    }

    public static void setGroundUI() {
        map.show(true);
        score.show(true);

        currentUIList = groundUIList;

    }

    public static void hideAll() {
        for (AbstractOverlay overlay : allOverlay) {
            overlay.hide(Direction.INSTANTLY);
        }
    }

    public void disposeOne(AbstractOverlay overlay) {
        groundUIList.remove(overlay);
    }

    public void createOne(AbstractOverlay overlay) {
        groundUIList.add(overlay);
        overlay.create();
    }

    public void render() {
        for (AbstractOverlay overlay : currentUIList) {
            overlay.render();
        }
    }

    public void update() {

        for (AbstractOverlay overlay : currentUIList) {
            overlay.update();
        }
    }

}
