package com.lambferret.game.screen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.lambferret.game.SnowFight;
import com.lambferret.game.player.PlayerObserver;
import com.lambferret.game.screen.ground.GroundScreen;
import com.lambferret.game.screen.ground.RecruitScreen;
import com.lambferret.game.screen.ground.ShopScreen;
import com.lambferret.game.screen.ground.TrainingGroundScreen;
import com.lambferret.game.screen.phase.*;
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
    private static final InputMultiplexer inputManager = new InputMultiplexer();

    private Overlay() {
        allOverlay.clear();
        groundUIList.clear();
        phaseUIList.clear();
        uiSpriteBatch = new Stage();
        AbstractOverlay map = new MapOverlay(uiSpriteBatch);
        AbstractOverlay bar = new SnowBarOverlay(uiSpriteBatch);
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
        groundUIList.add(score);

        phaseUIList.add(bar);
        phaseUIList.add(ability);
        phaseUIList.add(execute);
        phaseUIList.add(soldier);
    }

    public static Overlay getInstance() {
        if (instance == null) {
            instance = new Overlay();
            create();
        }
        return instance;
    }

    public static void disposeInstance() {
        instance = null;
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

    public static void changePhaseInputProcessor() {
        inputManager.clear();
        switch (PhaseScreen.getCurrentScreen()) {
            case PRE -> inputManager.addProcessor(PrePhaseScreen.stage);
            case READY -> inputManager.addProcessor(ReadyPhaseScreen.stage);
            case ACTION -> inputManager.addProcessor(ActionPhaseScreen.stage);
            case VICTORY -> inputManager.addProcessor(VictoryScreen.stage);
            case DEFEAT -> inputManager.addProcessor(DefeatScreen.stage);
        }
        inputManager.addProcessor(uiSpriteBatch);
        Gdx.input.setInputProcessor(inputManager);
    }

    public static void changeGroundInputProcessor() {
        inputManager.clear();
        switch (GroundScreen.getCurrentScreen()) {
            case RECRUIT -> inputManager.addProcessor(RecruitScreen.stage);
            case SHOP -> inputManager.addProcessor(ShopScreen.stage);
            case TRAINING_GROUND -> inputManager.addProcessor(TrainingGroundScreen.stage);
        }
        inputManager.addProcessor(uiSpriteBatch);
        Gdx.input.setInputProcessor(inputManager);
    }

    public static void setVisiblePhaseUI() {
        for (AbstractOverlay overlay : groundUIList) {
            overlay.setVisible(false);
        }
        for (AbstractOverlay overlay : phaseUIList) {
            overlay.setVisible(true);
        }
    }

    public static void setVisibleGroundUI() {
        for (AbstractOverlay overlay : phaseUIList) {
            overlay.setVisible(false);
        }
        for (AbstractOverlay overlay : groundUIList) {
            overlay.setVisible(true);
        }
    }

    public void render() {
        uiSpriteBatch.draw();
    }

    public void update() {
        uiSpriteBatch.act();
    }

}
