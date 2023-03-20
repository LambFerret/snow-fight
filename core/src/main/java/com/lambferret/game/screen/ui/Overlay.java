package com.lambferret.game.screen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.lambferret.game.SnowFight;
import com.lambferret.game.player.PlayerObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Overlay implements PlayerObserver {
    private static final Logger logger = LogManager.getLogger(Overlay.class.getName());
    private static Overlay instance = null;
    private static final List<AbstractOverlay> allOverlay = new ArrayList<>();
    private static final List<AbstractOverlay> groundUIList = new ArrayList<>();
    private static final List<AbstractOverlay> phaseUIList = new ArrayList<>();
    public static Stage uiSpriteBatch = new Stage();
    public static Stage currentSpriteBatch = new Stage();
    private static final InputMultiplexer inputManager = new InputMultiplexer();

    private Overlay() {
        AbstractOverlay map = new MapOverlay(uiSpriteBatch);
        AbstractOverlay bar = new BarOverlay(uiSpriteBatch);
        AbstractOverlay score = new ScoreOverlay(uiSpriteBatch);
        AbstractOverlay ability = new AbilityOverlay(uiSpriteBatch);
        AbstractOverlay execute = new ExecuteOverlay(uiSpriteBatch);
        AbstractOverlay soldier = new SoldierOverlay(uiSpriteBatch);

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

    public static Overlay getInstance() {
        if (instance == null) {
            changeCurrentInputProcessor(currentSpriteBatch);
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

    @Override
    public void onPlayerReady() {
        for (AbstractOverlay overlay : allOverlay) {
            overlay.init(SnowFight.player);
        }
    }

    public static void changeCurrentInputProcessor(InputProcessor processor) {
        inputManager.clear();
        inputManager.addProcessor(uiSpriteBatch);
        inputManager.addProcessor(processor);
        Gdx.input.setInputProcessor(inputManager);
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

    public static InputProcessor getInput() {
        return inputManager;
    }

    public void render() {
        uiSpriteBatch.draw();
    }

    public void update() {
        uiSpriteBatch.act();
    }

}
