package com.lambferret.game.screen.phase;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.SnowFight;
import com.lambferret.game.buff.Buff;
import com.lambferret.game.command.Command;
import com.lambferret.game.constant.Terrain;
import com.lambferret.game.level.Level;
import com.lambferret.game.level.LevelFinder;
import com.lambferret.game.player.Player;
import com.lambferret.game.player.PlayerObserver;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.Soldier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static com.lambferret.game.screen.ui.Overlay.changeCurrentInputProcessor;

public class PhaseScreen extends AbstractScreen implements PlayerObserver {
    private static final Logger logger = LogManager.getLogger(PhaseScreen.class.getName());
    public static final float MAP_X = 50.0F;
    public static final float MAP_Y = 50.0F;
    public static final float MAP_WIDTH = 500.0F;
    public static final float MAP_HEIGHT = 500.0F;
    public static final Container<Table> mapContainer = new Container<>();
    private static Overlay overlay;
    private static Screen currentScreen;
    public static Player player;
    public static Level level;
    public static Map<Command, List<Soldier>> commands = new HashMap<>();
    private static List<Buff> buffList = new ArrayList<>();
    private static final List<AbstractPhase> phaseListener;
    private static final AbstractPhase actionPhaseScreen;
    private static final AbstractPhase readyPhaseScreen;
    private static final AbstractPhase defeatScreen;
    private static final AbstractPhase prePhaseScreen;
    private static final AbstractPhase victoryScreen;
//    PhaseText text;

    static {
        prePhaseScreen = new PrePhaseScreen(mapContainer);
        actionPhaseScreen = new ActionPhaseScreen(mapContainer);
        readyPhaseScreen = new ReadyPhaseScreen(mapContainer);
        defeatScreen = new DefeatScreen();
        victoryScreen = new VictoryScreen();
        phaseListener = new ArrayList<>();
        phaseListener.add(actionPhaseScreen);
        phaseListener.add(readyPhaseScreen);
        phaseListener.add(defeatScreen);
        phaseListener.add(prePhaseScreen);
        phaseListener.add(victoryScreen);
    }

    public PhaseScreen() {
//        text = LocalizeConfig.uiText.getGroundText();
        overlay = Overlay.getInstance();
    }

    @Override
    public void create() {
        Overlay.setPhaseUI();
        for (AbstractPhase phase : phaseListener) {
            phase.create();
        }
        mapContainer.fill();
        mapContainer.setPosition(MAP_X, MAP_Y);
        mapContainer.setSize(MAP_WIDTH, MAP_HEIGHT);

        mapContainer.setDebug(true, true);
    }

    public void onPlayerReady() {
        player = SnowFight.player;
        level = LevelFinder.get(player.getCurrentRegion(), player.getLevelNumber());
        mapContainer.setActor(makeMap());
        for (AbstractPhase phase : phaseListener) {
            phase.init(player);
        }
        commands = new LinkedHashMap<>();
        buffList = new ArrayList<>();

    }

    private Table makeMap() {
        Table map = new Table();
        map.setFillParent(true);

        map.setSkin(GlobalSettings.skin);
        for (int i = 0; i < level.ROWS; i++) {
            for (int j = 0; j < level.COLUMNS; j++) {
                map.add(makeMapElement(level.getMap()[i][j]));
            }
            map.row();
        }
        map.setSize(MAP_WIDTH, MAP_HEIGHT);
        return map;
    }

    private ImageButton makeMapElement(int terrain) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = GlobalSettings.debugTexture;
        ImageButton button = new ImageButton(style);
        Color color = switch (terrain) {
            case Terrain.NULL -> Color.BLACK;
            case Terrain.LAKE -> Color.ORANGE;
            case Terrain.MOUNTAIN -> Color.YELLOW;
            case Terrain.SEA -> Color.GREEN;
            case Terrain.TOWN -> Color.CHARTREUSE;
            default -> Color.VIOLET;
        };
        button.setColor(color);
        button.setSize(MAP_WIDTH / level.COLUMNS, MAP_HEIGHT / level.ROWS);

        return button;
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

        prePhaseScreen.startPhase();

        changeCurrentInputProcessor(prePhaseScreen.getStage());
        currentScreen = Screen.PRE;
    }

    // 이하 순서 조심할 것. 아직 섞인 상태 노확신
    public static void screenPtoR() {
        prePhaseScreen.executePhase();

        player.setSnowAmount(level.getSnowMax());
        level.initCurrentIteration();

        readyPhaseScreen.startPhase();
        changeCurrentInputProcessor(readyPhaseScreen.getStage());
        currentScreen = Screen.READY;
    }

    public static void screenRtoA() {
        readyPhaseScreen.executePhase();

        actionPhaseScreen.startPhase();
        changeCurrentInputProcessor(actionPhaseScreen.getStage());
        currentScreen = Screen.ACTION;
    }

    public static void screenAtoR() {
        actionPhaseScreen.executePhase();

        player.setCurrentCost(player.getMaxCost());
        level.toNextIteration();
        commands.clear();
        buffList.removeIf(Buff::isExpired);

        readyPhaseScreen.startPhase();
        changeCurrentInputProcessor(readyPhaseScreen.getStage());
        currentScreen = Screen.READY;
    }

    public static void screenAtoD() {
        actionPhaseScreen.executePhase();

        defeatScreen.startPhase();
        changeCurrentInputProcessor(defeatScreen.getStage());
        currentScreen = Screen.DEFEAT;
    }

    public static void screenAtoV() {
        actionPhaseScreen.executePhase();

        victoryScreen.startPhase();
        changeCurrentInputProcessor(victoryScreen.getStage());
        currentScreen = Screen.VICTORY;
    }

    @Override
    public void render() {
        overlay.render();
        switch (currentScreen) {
            case PRE -> prePhaseScreen.render();
            case READY -> readyPhaseScreen.render();
            case ACTION -> actionPhaseScreen.render();
            case VICTORY -> victoryScreen.render();
            case DEFEAT -> defeatScreen.render();
        }
    }

    @Override
    public void update() {
        overlay.update();
        switch (currentScreen) {
            case PRE -> prePhaseScreen.update();
            case READY -> readyPhaseScreen.update();
            case ACTION -> actionPhaseScreen.update();
            case VICTORY -> victoryScreen.update();
            case DEFEAT -> defeatScreen.update();
        }
    }

    public enum Screen {
        PRE,
        READY,
        ACTION,
        VICTORY,
        DEFEAT,

    }
}
