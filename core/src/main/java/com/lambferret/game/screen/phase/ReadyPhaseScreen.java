package com.lambferret.game.screen.phase;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lambferret.game.constant.Terrain;
import com.lambferret.game.level.Level;
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
    Level level;

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
        stage.addActor(a);
    }

    protected Table makeMap() {
        Table map = new Table();
        map.setFillParent(true);
        map.setDebug(true, true);

        map.setSkin(GlobalSettings.skin);
        for (int i = 0; i < level.ROWS; i++) {
            for (int j = 0; j < level.COLUMNS; j++) {
                map.add(makeMapElement(level.getTerrainMaxCurrentInfo(i, j)));
            }
            map.row();
        }
        map.setSize(PhaseScreen.MAP_WIDTH, PhaseScreen.MAP_HEIGHT);
        return map;
    }

    private ImageButton makeMapElement(int[] terrainMaxCurrent) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = GlobalSettings.debugTexture;
        ImageButton button = new ImageButton(style);
        float transparency = (float) terrainMaxCurrent[2] / terrainMaxCurrent[1];
        Color color = switch (terrainMaxCurrent[0]) {
            case Terrain.NULL -> Color.BLACK;
            case Terrain.LAKE -> new Color(255, 69, 0, transparency); //Color.ORANGE.;
            case Terrain.MOUNTAIN -> new Color(255, 192, 203, transparency); //Color.YELLOW;
            case Terrain.SEA -> new Color(127, 255, 0, transparency); //Color.GREEN;
            case Terrain.TOWN -> new Color(0, 128, 128, transparency); //Color.CHARTREUSE;
            default -> Color.VIOLET;
        };
        if (transparency == 1) {
            color = Color.BROWN;
        }
        button.setColor(color);
        button.setSize(PhaseScreen.MAP_WIDTH / level.COLUMNS, PhaseScreen.MAP_HEIGHT / level.ROWS);

        return button;
    }

    public void create() {
    }

    @Override
    public void init(Player player) {
        this.player = player;
        this.level = PhaseScreen.level;
    }

    @Override
    public void startPhase() {
        mapContainer.setActor(makeMap());
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
