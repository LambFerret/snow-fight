package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.SnowFight;
import com.lambferret.game.buff.Buff;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadyPhaseScreen implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(ReadyPhaseScreen.class.getName());
    public static final Stage stage = new Stage();
    static Container<Table> mapContainer;
    Player player;

    public ReadyPhaseScreen() {
        mapContainer = new Container<>();
        mapContainer.setPosition(300, 300);

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
        mapContainer.setActor(PhaseScreen.level.makeTable(false));
        mapContainer.setBackground(new TextureRegionDrawable(AssetFinder.getTexture("yellow")));
        mapContainer.addListener(new DragListener() {
            boolean isEntered = false;

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                mapContainer.moveBy(x - getDragStartX(), y - getDragStartY());
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) {
                    isEntered = true;
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    isEntered = false;
                }
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
                if (isEntered) {
                    logger.info("scrolled |  🐳 ??? | " + amountY);
                    mapContainer.setOrigin(x, y);
                    mapContainer.setScale(mapContainer.getScaleX() + amountY * 3);
                    return true;
                } else {
                    logger.info("scrolled |  🐳 you aren't entered | ");
                    return false;
                }
            }
        });
        stage.addActor(mapContainer);
        //각종 플레이어의 덱이나 능력을 확인하거나 일단 작동시킴 즉 transaction 이 일어나기 전 모든 행동들
        PhaseScreen.buffList.forEach(Buff::effect);
    }

    @Override
    public void executePhase() {
        // irreversible execute transaction
        for (Soldier soldier : player.getSoldiers()) {
            soldier.initValue();
        }
    }

    public static float getTableX() {
        return mapContainer.getX();
    }

    public static float getTableY() {
        return mapContainer.getY();
    }

    @Override
    public void render() {
        stage.draw();
        stage.act();
    }

}
