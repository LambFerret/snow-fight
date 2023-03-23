package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadyPhaseScreen implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(ReadyPhaseScreen.class.getName());
    Stage stage;
    Container<Table> mapContainer;
    Player player;

    public ReadyPhaseScreen(Container<Table> mapContainer) {
        this.stage = new Stage();
        this.mapContainer = mapContainer;
        this.stage.addActor(this.mapContainer);
    }

    public void create() {
    }

    @Override
    public void init(Player player) {
        this.player = player;
    }

    @Override
    public void startPhase() {
        //각종 플레이어의 덱이나 능력을 확인하거나 일단 작동시킴 즉 transaction 이 일어나기 전 모든 행동들

    }

    @Override
    public void executePhase() {
        // irreversible execute transaction

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
