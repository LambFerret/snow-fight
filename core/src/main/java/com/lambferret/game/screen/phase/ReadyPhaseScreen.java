package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.Soldier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadyPhaseScreen implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(ReadyPhaseScreen.class.getName());
    public static final Stage stage = new Stage();
    Container<Table> mapContainer;
    Player player;

    public ReadyPhaseScreen(Container<Table> mapContainer) {
        this.mapContainer = mapContainer;
        stage.addActor(this.mapContainer);
        var a = new TextButton("aaaaaa", GlobalSettings.skin);
        a.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                logger.info("aaaaaa");
            }
        });
        a.setPosition(50, 50);
        a.setSize(300, 300);
        stage.addActor(a);
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
        for (Soldier soldier : player.getSoldiers()) {
            soldier.initValue();
        }
    }

    @Override
    public void render() {
        stage.draw();
    }

    @Override
    public void update() {
        stage.act();
    }

}
