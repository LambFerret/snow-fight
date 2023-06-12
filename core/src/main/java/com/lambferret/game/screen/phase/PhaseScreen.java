package com.lambferret.game.screen.phase;

import com.badlogic.gdx.math.MathUtils;
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
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class PhaseScreen extends AbstractScreen implements PlayerObserver {
    private static final Logger logger = LogManager.getLogger(PhaseScreen.class.getName());
    public static final int MAP_X = 50;
    public static final int MAP_Y = 50;
    public static final int MAP_WIDTH = 500;
    public static final int MAP_HEIGHT = 500;
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
    public static long mapRandomSeed;
    public static Random handRandom;

    static {
        prePhaseScreen = new PrePhaseScreen();
        actionPhaseScreen = new ActionPhaseScreen();
        readyPhaseScreen = new ReadyPhaseScreen();
        defeatScreen = new DefeatScreen();
        victoryScreen = new VictoryScreen();
        phaseScreenList = List.of(
            prePhaseScreen,
            readyPhaseScreen,
            actionPhaseScreen,
            defeatScreen,
            victoryScreen
        );
    }

    public PhaseScreen() {
        commands = new LinkedHashMap<>();
        buffList = new ArrayList<>();
    }

    public void onPlayerReady() {
        Overlay.isPhaseUI = true;
        screenInitToP();
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {

    }


    public static Map<Command, List<Soldier>> getCommands() {
        return commands;
    }

    public static void addBuff(Buff... buff) {
        buffList.addAll(Arrays.asList(buff));
        player.buffChanged();
    }

    public static Screen getCurrentScreen() {
        return currentScreen;
    }

    public static void screenInitToP() {
        player = SnowFight.player;
        level = LevelFinder.get(player.getDay());
        mapRandomSeed = MathUtils.random(999L);
        handRandom = new Random();
        for (AbstractPhase phase : phaseScreenList) {
            phase.onPlayerReady();
            player.addPlayerObserver(phase);
        }
        player.initAllQuest();
        commands.clear();
        buffList.clear();
        manualList = player.getManuals();
        logger.info(" Phase : Activated Manual - " + GlobalUtil.listToString(manualList));
        manualList.forEach(Manual::effect);
        player.setSnowAmount(level.getAssignedSnow());
        // warning
        Overlay.getInstance().onPlayerReady();
        prePhaseScreen.startPhase();
        logger.info(" Phase : Init -> Pre");
        currentScreen = Screen.PRE;
    }

    // 이하 순서 조심할 것. 아직 섞인 상태 노확신
    public static void screenPtoR() {
        prePhaseScreen.executePhase();

        Overlay.getInstance().nextPhase();
        level.initCurrentIteration();

        readyPhaseScreen.startPhase();
        logger.info(" Phase : Pre -> Ready");
        currentScreen = Screen.READY;
    }

    public static void screenRtoA() {
        readyPhaseScreen.executePhase();

        Overlay.getInstance().nextPhase();

        actionPhaseScreen.startPhase();
        logger.info(" Phase : Ready -> Action");
        currentScreen = Screen.ACTION;
    }

    public static void screenAtoR() {
        player.setCurrentCost(player.getMaxCost());
        Overlay.getInstance().nextPhase();
        level.toNextIteration();
        commands.clear();
        buffList.forEach(Buff::setEnable);
        buffList.removeIf(Buff::isExpired);
        player.buffChanged();

        readyPhaseScreen.startPhase();
        logger.info(" Phase : Action -> Ready");
        currentScreen = Screen.READY;
    }

    public static void executeAction() {
    }

    public static void screenAtoD() {
        defeatScreen.startPhase();
        logger.info(" Phase : Action -> Defeat DEPRECATED");
        currentScreen = Screen.DEFEAT;
    }

    public static void screenAtoV() {
        victoryScreen.startPhase();
        logger.info(" Phase : Action -> Victory DEPRECATED");
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
