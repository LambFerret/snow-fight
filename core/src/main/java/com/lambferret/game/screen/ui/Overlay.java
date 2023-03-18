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
    public static Stage stage;
    public static Stage currentStage;
    public static InputMultiplexer inputManager;

    private Overlay() {
        initInput();

        map = new MapOverlay(stage);
        bar = new BarOverlay(stage);
        score = new ScoreOverlay(stage);
        ability = new AbilityOverlay(stage);
        execute = new ExecuteOverlay(stage);
        soldier = new SoldierOverlay(stage);

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
        inputManager.addProcessor(stage);
        inputManager.addProcessor(currentStage);
    }

    public static InputProcessor getInput() {
        return inputManager;
    }

    public static Overlay getInstance() {
        if (instance == null) {
            inputManager = new InputMultiplexer();
            stage = new Stage();
            currentStage = new Stage();
            instance = new Overlay();
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
        for (AbstractOverlay overlay : allOverlay) {
            overlay.render();
        }
    }

    public void update() {
        for (AbstractOverlay overlay : allOverlay) {
            overlay.update();
        }
    }

}
