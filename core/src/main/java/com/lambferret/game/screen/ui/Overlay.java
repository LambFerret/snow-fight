package com.lambferret.game.screen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.lambferret.game.SnowFight;
import com.lambferret.game.player.PlayerObserver;
import com.lambferret.game.save.Item;
import com.lambferret.game.screen.ground.GroundScreen;
import com.lambferret.game.screen.ground.RecruitScreen;
import com.lambferret.game.screen.ground.ShopScreen;
import com.lambferret.game.screen.ground.TrainingGroundScreen;
import com.lambferret.game.screen.phase.ActionPhaseScreen;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.screen.phase.PrePhaseScreen;
import com.lambferret.game.screen.phase.ReadyPhaseScreen;
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
    public static boolean isPhaseUI = false;
    AbstractOverlay map;
    AbstractOverlay bar;
    AbstractOverlay quest;
    AbstractOverlay command;
    AbstractOverlay execute;
    AbstractOverlay inventory;
    AbstractOverlay manual;
    AbstractOverlay buffTable;
    AbstractOverlay phaseOrder;

    private Overlay() {
        allOverlay.clear();
        groundUIList.clear();
        phaseUIList.clear();

        uiSpriteBatch = new Stage();

        map = new MapOverlay(uiSpriteBatch);
        bar = new SnowBarOverlay(uiSpriteBatch);
        quest = new QuestOverlay(uiSpriteBatch);
        command = new CommandOverlay(uiSpriteBatch);
        execute = new ExecuteOverlay(uiSpriteBatch);
        manual = new ManualBookshelfOverlay(uiSpriteBatch);
        buffTable = new BuffTableOverlay(uiSpriteBatch);
        phaseOrder = new PhaseOrderOverlay(uiSpriteBatch);
        inventory = new InventoryOverlay(uiSpriteBatch);

        allOverlay.add(map);
        allOverlay.add(bar);
        allOverlay.add(quest);
        allOverlay.add(command);
        allOverlay.add(execute);
        allOverlay.add(manual);
        allOverlay.add(buffTable);
        allOverlay.add(phaseOrder);
        allOverlay.add(inventory);

        groundUIList.add(map);
        groundUIList.add(manual);
        groundUIList.add(inventory);

        phaseUIList.add(quest);
        phaseUIList.add(bar);
        phaseUIList.add(command);
        phaseUIList.add(execute);
        phaseUIList.add(manual);
        phaseUIList.add(buffTable);
        phaseUIList.add(phaseOrder);
        phaseUIList.add(inventory);
    }

    public static Overlay getInstance() {
        if (instance == null) {
            instance = new Overlay();
        }
        return instance;
    }

    public static void disposeInstance() {
        instance = null;
    }


    @Override
    public void onPlayerReady() {
        for (AbstractOverlay overlay : allOverlay) {
            SnowFight.player.addPlayerObserver(overlay);
        }
        if (isPhaseUI) {
            for (AbstractOverlay overlay : phaseUIList) {
                overlay.onPlayerReady();
            }
        } else {
            for (AbstractOverlay overlay : groundUIList) {
                overlay.onPlayerReady();
            }
        }
        changeGroundInputProcessor();
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {

    }

    public static void changePhaseInputProcessor() {
        inputManager.clear();
        inputManager.addProcessor(uiSpriteBatch);
        switch (PhaseScreen.getCurrentScreen()) {
            case PRE -> inputManager.addProcessor(PrePhaseScreen.stage);
            case READY -> inputManager.addProcessor(ReadyPhaseScreen.stage);
            case ACTION, VICTORY, DEFEAT -> inputManager.addProcessor(ActionPhaseScreen.stage);
        }
        Gdx.input.setInputProcessor(inputManager);
    }

    public static void changeGroundInputProcessor() {
        inputManager.clear();
        inputManager.addProcessor(uiSpriteBatch);
        switch (GroundScreen.getCurrentScreen()) {
            case RECRUIT -> inputManager.addProcessor(RecruitScreen.stage);
            case SHOP -> inputManager.addProcessor(ShopScreen.stage);
            case TRAINING_GROUND -> inputManager.addProcessor(TrainingGroundScreen.stage);
        }
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

    public static void hideAll() {
        for (AbstractOverlay overlay : allOverlay) {
            overlay.setVisible(false);
        }
    }

    public void nextPhase() {
        ((PhaseOrderOverlay) phaseOrder).next();
    }

    public static void reset() {
        if (isPhaseUI) {
            setVisiblePhaseUI();
        } else {
            setVisibleGroundUI();
        }
    }

    public void render() {
        uiSpriteBatch.draw();
        uiSpriteBatch.act();
    }

}
