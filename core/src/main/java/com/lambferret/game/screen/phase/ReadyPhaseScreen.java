package com.lambferret.game.screen.phase;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.lambferret.game.SnowFight;
import com.lambferret.game.buff.Buff;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.soldier.Soldier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.lambferret.game.level.Level.LEVEL_EACH_SIZE_BIG;

public class ReadyPhaseScreen implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(ReadyPhaseScreen.class.getName());
    public static final Stage stage = new Stage();
    static Container<Table> mapContainer;
    Player player;
    Level level;

    public ReadyPhaseScreen() {
        mapContainer = new Container<>();
    }

    @Override
    public void onPlayerReady() {
        this.player = SnowFight.player;
        this.level = PhaseScreen.level;
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {

    }

    @Override
    public void startPhase() {
        mapContainer.setDebug(true, true);
        mapContainer.setActor(level.makeTable(false));
//        mapContainer.setBackground(new TextureRegionDrawable(AssetFinder.getTexture("yellow")));
        mapContainer.setSize(LEVEL_EACH_SIZE_BIG * level.COLUMNS, LEVEL_EACH_SIZE_BIG * level.ROWS);

        mapContainer.addListener(new DragListener() {

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                mapContainer.moveBy(x - getDragStartX(), y - getDragStartY());
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) {
                    stage.setScrollFocus(mapContainer);
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    stage.setScrollFocus(null);
                }
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
                OrthographicCamera camera = (OrthographicCamera) stage.getCamera();
                camera.zoom += amountY * 0.05;// adjust the 0.1 value to zoom faster or slower
                if (camera.zoom < 0.5f) camera.zoom = 0.5f;
                if (camera.zoom > 3f) camera.zoom = 3f;
                camera.update();
                return true;
            }

        });
        stage.addActor(mapContainer);
        //각종 플레이어의 덱이나 능력을 확인하거나 일단 작동시킴 즉 transaction 이 일어나기 전 모든 행동들
        PhaseScreen.buffList.forEach(Buff::effect);
    }

    @Override
    public void executePhase() {
        for (Soldier soldier : player.getSoldiers()) {
            soldier.initValue();
        }
        mapContainer.clear();
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
