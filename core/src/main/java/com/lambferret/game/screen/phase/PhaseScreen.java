package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.lambferret.game.SnowFight;
import com.lambferret.game.buff.Buff;
import com.lambferret.game.command.Command;
import com.lambferret.game.level.Level;
import com.lambferret.game.level.LevelFinder;
import com.lambferret.game.manual.Manual;
import com.lambferret.game.player.Player;
import com.lambferret.game.player.PlayerObserver;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.soldier.Soldier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class PhaseScreen extends AbstractScreen implements PlayerObserver {
    private static final Logger logger = LogManager.getLogger(PhaseScreen.class.getName());
    public static final float MAP_X = 50.0F;
    public static final float MAP_Y = 50.0F;
    public static final float MAP_WIDTH = 500.0F;
    public static final float MAP_HEIGHT = 500.0F;
    public static final Container<Level> mapContainer = new Container<>();
    private static Overlay overlay;
    private static Screen currentScreen;
    public static Player player;
    public static Level level;
    public static Map<Command, List<Soldier>> commands = new HashMap<>();
    public static List<Buff> buffList = new ArrayList<>();
    private static List<Manual> manualList = new ArrayList<>();
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
        Overlay.setVisiblePhaseUI();
        for (AbstractPhase phase : phaseListener) {
            phase.create();
        }
        mapContainer.fill();
        mapContainer.setPosition(MAP_X, MAP_Y);
        mapContainer.setSize(MAP_WIDTH, MAP_HEIGHT);
    }

    public void onPlayerReady() {
        player = SnowFight.player;
        SnowFight.player.addSoldierObserver(this);
        level = LevelFinder.get(player.getDay());
        mapContainer.setActor(level);
        mapContainer.fill();
        for (AbstractPhase phase : phaseListener) {
            phase.init(player);
        }
        commands = new LinkedHashMap<>();
        buffList = new ArrayList<>();
        manualList = player.getManuals();
    }

    @Override
    public void onPlayerUpdate() {

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
    public void render() {
        switch (currentScreen) {
            case PRE -> prePhaseScreen.render();
            case READY -> readyPhaseScreen.render();
            case ACTION -> actionPhaseScreen.render();
            case VICTORY -> victoryScreen.render();
            case DEFEAT -> defeatScreen.render();
        }
        overlay.render();
    }

    @Override
    public void update() {
        switch (currentScreen) {
            case PRE -> prePhaseScreen.update();
            case READY -> readyPhaseScreen.update();
            case ACTION -> actionPhaseScreen.update();
            case VICTORY -> victoryScreen.update();
            case DEFEAT -> defeatScreen.update();
        }
        overlay.update();
    }

    public enum Screen {
        PRE,
        READY,
        ACTION,
        VICTORY,
        DEFEAT,

    }
}
