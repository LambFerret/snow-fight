package com.lambferret.game.screen.phase;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.SnowFight;
import com.lambferret.game.buff.Buff;
import com.lambferret.game.command.Command;
import com.lambferret.game.level.Level;
import com.lambferret.game.level.LevelFinder;
import com.lambferret.game.manual.Manual;
import com.lambferret.game.player.Player;
import com.lambferret.game.player.PlayerObserver;
import com.lambferret.game.save.Item;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class PhaseScreen extends AbstractScreen implements PlayerObserver {
    private static final Logger logger = LogManager.getLogger(PhaseScreen.class.getName());
    public static final float MAP_X = 50.0F;
    public static final float MAP_Y = 50.0F;
    public static final float MAP_WIDTH = 500.0F;
    public static final float MAP_HEIGHT = 500.0F;
    public static final Container<Table> mapContainer = new Container<>();
    private static final Overlay overlay = Overlay.getInstance();

    private static Screen currentScreen;
    public static Player player;
    public static Level level;
    public static Map<Command, List<Soldier>> commands = new HashMap<>();
    public static List<Buff> buffList = new ArrayList<>();
    private static List<Manual> manualList = new ArrayList<>();
    private static final List<AbstractPhase> phaseScreenList;
    private static final AbstractPhase actionPhaseScreen;
    private static final AbstractPhase readyPhaseScreen;
    private static final AbstractPhase defeatScreen;
    private static final AbstractPhase prePhaseScreen;
    private static final AbstractPhase victoryScreen;
    //    PhaseText text;
    Table mapTable;

    static {
        prePhaseScreen = new PrePhaseScreen(mapContainer);
        actionPhaseScreen = new ActionPhaseScreen(mapContainer);
        readyPhaseScreen = new ReadyPhaseScreen(mapContainer);
        defeatScreen = new DefeatScreen();
        victoryScreen = new VictoryScreen();
        phaseScreenList = List.of(
            actionPhaseScreen,
            readyPhaseScreen,
            defeatScreen,
            prePhaseScreen,
            victoryScreen
        );
    }

    public PhaseScreen() {
        mapContainer.fill();
        mapContainer.setPosition(MAP_X, MAP_Y);
        mapContainer.setSize(MAP_WIDTH, MAP_HEIGHT);
        commands = new LinkedHashMap<>();
        buffList = new ArrayList<>();
    }

    public void onPlayerReady() {
        player = SnowFight.player;
        level = LevelFinder.get(player.getDay());
        Overlay.setVisiblePhaseUI();
        updateMap(level);
        for (AbstractPhase phase : phaseScreenList) {
            phase.onPlayerReady();
            player.addPlayerObserver(phase);
        }
        manualList = player.getManuals();
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {

    }

    private Table makeMap(Level level) {
        Table map = new Table();
        map.setFillParent(true);
        for (int i = 0; i < level.ROWS; i++) {
            for (int j = 0; j < level.COLUMNS; j++) {
                map.add(makeMapElement(level.getTerrainMaxCurrentInfo(i, j)));
            }
            map.row();
        }
        map.setSize(mapContainer.getWidth(), mapContainer.getHeight());
        map.setDebug(true, true);
        return map;
    }

    private ImageButton makeMapElement(int[] terrainMaxCurrent) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(AssetFinder.getTexture("mapElement"));
        ImageButton button = new ImageButton(style);
        float transparency = (float) terrainMaxCurrent[2] / terrainMaxCurrent[1];
        Color color = switch (terrainMaxCurrent[0]) {
            case 0 -> Color.BLACK;
            case 1 -> new Color(255, 69, 0, transparency); //Color.ORANGE.;
            case 2 -> new Color(255, 192, 203, transparency); //Color.YELLOW;
            case 3 -> new Color(127, 255, 0, transparency); //Color.GREEN;
            case 4 -> new Color(0, 128, 128, transparency); //Color.CHARTREUSE;
            default -> Color.VIOLET;
        };
        button.setColor(color);
        button.setSize(MAP_WIDTH / level.COLUMNS, MAP_HEIGHT / level.ROWS);
        return button;
    }

    private void updateMap(Level level) {
        mapContainer.setActor(makeMap(level));
    }

    public static Map<Command, List<Soldier>> getCommands() {
        return commands;
    }

    public static void addBuff(Buff... buff) {
        buffList.addAll(Arrays.asList(buff));
    }

    public static Screen getCurrentScreen() {
        return currentScreen;
    }

    public static void screenInitToP() {
        commands.clear();
        buffList.clear();
        manualList.forEach(Manual::effect);
        player.setSnowAmount(level.getAssignedSnow());

        prePhaseScreen.startPhase();

        currentScreen = Screen.PRE;
    }

    // 이하 순서 조심할 것. 아직 섞인 상태 노확신
    public static void screenPtoR() {
        prePhaseScreen.executePhase();

        level.initCurrentIteration();

        readyPhaseScreen.startPhase();
        currentScreen = Screen.READY;
    }

    public static void screenRtoA() {
        readyPhaseScreen.executePhase();

        actionPhaseScreen.startPhase();
        currentScreen = Screen.ACTION;
    }

    public static void screenAtoR() {
        actionPhaseScreen.executePhase();

        player.setCurrentCost(player.getMaxCost());
        level.toNextIteration();
        commands.clear();
        buffList.forEach(Buff::setEnable);
        buffList.removeIf(Buff::isExpired);

        readyPhaseScreen.startPhase();
        currentScreen = Screen.READY;
    }

    public static void screenAtoD() {
        actionPhaseScreen.executePhase();

        defeatScreen.startPhase();
        currentScreen = Screen.DEFEAT;
    }

    public static void screenAtoV() {
        actionPhaseScreen.executePhase();

        victoryScreen.startPhase();
        currentScreen = Screen.VICTORY;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        switch (currentScreen) {
            case PRE -> prePhaseScreen.render();
            case READY -> readyPhaseScreen.render();
            case ACTION -> actionPhaseScreen.render();
            case VICTORY -> victoryScreen.render();
            case DEFEAT -> defeatScreen.render();
        }
        overlay.render();
    }

    public enum Screen {
        PRE,
        READY,
        ACTION,
        VICTORY,
        DEFEAT,
    }

}
