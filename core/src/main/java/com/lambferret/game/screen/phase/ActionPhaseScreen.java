package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.player.Player;
import com.lambferret.game.soldier.Soldier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ActionPhaseScreen implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(ActionPhaseScreen.class.getName());
    Stage stage;
    Table map;
    Container<Table> mapContainer;
    Player player;
    List<Soldier> actionMember;


    public ActionPhaseScreen(Container<Table> mapContainer) {
        this.stage = new Stage();
        this.mapContainer = mapContainer;
        this.stage.addActor(this.mapContainer);
        this.map = mapContainer.getActor();
    }

    public void create() {
    }

    @Override
    public void init(Player player) {
        this.player = player;
    }

    @Override
    public void startPhase() {

    }

    @Override
    public void executePhase() {

    }

    public void executeAction() {

    }

    public Stage getStage() {
        return this.stage;
    }

    public void render() {
        stage.draw();
    }

    public void update() {
        stage.act();
    }

}
