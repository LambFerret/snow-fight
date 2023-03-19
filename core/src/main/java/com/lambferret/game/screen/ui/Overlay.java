package com.lambferret.game.screen.ui;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    public static Stage uiStage = new Stage();
    public static Stage currentMainStage = new Stage();
    public static InputMultiplexer inputManager = new InputMultiplexer();

    private Overlay() {
        initInput();

        map = new MapOverlay(uiStage);
        bar = new BarOverlay(uiStage);
        score = new ScoreOverlay(uiStage);
        ability = new AbilityOverlay(uiStage);
        execute = new ExecuteOverlay(uiStage);
        soldier = new SoldierOverlay(uiStage);

        allOverlay.add(map);
        allOverlay.add(bar);
        allOverlay.add(score);
        allOverlay.add(ability);
        allOverlay.add(execute);
        allOverlay.add(soldier);

        groundUIList.add(map);
        groundUIList.add(bar);
        groundUIList.add(score);

        phaseUIList.add(bar);
        phaseUIList.add(ability);
        phaseUIList.add(execute);
        phaseUIList.add(soldier);

    }

    public static void initInput() {
//        inputManager.addProcessor(currentMainStage);
    }

    public static InputProcessor getInput() {
        return inputManager;
    }

    public static Overlay getInstance() {
        if (instance == null) {
            instance = new Overlay();
            inputManager.addProcessor(uiStage);
            create();
        }
        return instance;
    }

    private static void create() {
        for (AbstractOverlay overlay : allOverlay) {
            overlay.create();
        }
    }

    public static void setPhaseUI() {
        for (AbstractOverlay overlay : groundUIList) {
            overlay.setVisible(false);
        }
        for (AbstractOverlay overlay : phaseUIList) {
            overlay.setVisible(true);
        }
    }

    public static void setGroundUI() {
        for (AbstractOverlay overlay : phaseUIList) {
            overlay.setVisible(false);
        }
        for (AbstractOverlay overlay : groundUIList) {
            overlay.setVisible(true);
        }
    }

    public void render() {
        uiStage.draw();

    }

    public void update() {
        uiStage.act();
    }

}
