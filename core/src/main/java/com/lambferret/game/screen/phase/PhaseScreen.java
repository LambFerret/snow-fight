package com.lambferret.game.screen.phase;

import com.badlogic.gdx.math.MathUtils;
import com.lambferret.game.SnowFight;
import com.lambferret.game.buff.Buff;
import com.lambferret.game.buff.nonbuff.NonBuff;
import com.lambferret.game.command.Command;
import com.lambferret.game.level.Level;
import com.lambferret.game.level.LevelFinder;
import com.lambferret.game.manual.Manual;
import com.lambferret.game.player.Player;
import com.lambferret.game.player.PlayerObserver;
import com.lambferret.game.save.Item;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

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
    public static List<Command> deck = new ArrayList<>();
    public static List<Buff> buffList = new ArrayList<>();
    public static List<NonBuff> tempBuffList = new ArrayList<>();
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
        buffList = new ArrayList<>();
        tempBuffList = new ArrayList<>();
    }

    public void onPlayerReady() {
        Overlay.isPhaseUI = true;
        screenInitToP();
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {

    }

    public static void screenInitToP() {
        buffList.clear();
        tempBuffList.clear();

        setPlayer();
        setRandom();
        activateManual(Manual.ManualTiming.START_STAGE);

        Overlay.getInstance().onPlayerReady();
        prePhaseScreen.startPhase();
        logger.info(" Phase : Init -> Pre");
        currentScreen = Screen.PRE;
    }

    private static void setPlayer() {
        player = SnowFight.player;
        setLevel();
        for (AbstractPhase phase : phaseScreenList) {
            phase.onPlayerReady();
            player.addPlayerObserver(phase);
        }
        player.setCurrentCost(player.getMaxCost());
        player.initAllQuest();
        deck.addAll(player.getCommands());
    }

    private static void setLevel() {
        level = LevelFinder.get(player.getDay());
        player.setSnowAmount(level.getAssignedSnow());
    }

    private static void setRandom() {
        mapRandomSeed = MathUtils.random(999L);
        handRandom = new Random();
    }

    protected static void activateManual(Manual.ManualTiming timing) {
        var manualList = player.getManuals();
        logger.info(" Phase : Activated Manual - " + GlobalUtil.listToString(manualList));
        for (Manual manual : manualList) {
            manual.effect(timing);
        }
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
    public void end() {
        player.setShopItems(new ArrayList<>());
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

    public static void addBuff(Buff... buff) {
        for (Buff b : buff) {
            buffList.add(b);
            b.effect();
        }
        player.buffChanged();
    }

    public static Screen getCurrentScreen() {
        return currentScreen;
    }

    public enum Screen {
        PRE,
        READY,
        ACTION,
        VICTORY,
        DEFEAT,
    }

}
