package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.SnowFight;
import com.lambferret.game.buff.Buff;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.soldier.Soldier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadyPhaseScreen implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(ReadyPhaseScreen.class.getName());
    public static final Stage stage = new Stage();
    Container<Table> mapContainer;
    Player player;
    Level level;

    public ReadyPhaseScreen(Container<Table> mapContainer) {
        this.mapContainer = mapContainer;
    }

    @Override
    public void onPlayerReady() {
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {

    }

    @Override
    public void startPhase() {
        this.player = SnowFight.player;
        this.mapContainer.setBackground(null);
        stage.addActor(this.mapContainer);
        //각종 플레이어의 덱이나 능력을 확인하거나 일단 작동시킴 즉 transaction 이 일어나기 전 모든 행동들
        PhaseScreen.buffList.forEach(Buff::effect);
        logger.info("startPhase |  🐳 buff 목록 | " + PhaseScreen.buffList);
    }

    @Override
    public void executePhase() {
        // irreversible execute transaction
        for (Soldier soldier : player.getSoldiers()) {
            soldier.initValue();
        }
    }

    @Override
    public void render() {
        stage.draw();
        stage.act();
    }

}
