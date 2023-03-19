package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.SnowFight;
import com.lambferret.game.level.Level;
import com.lambferret.game.level.LevelFinder;
import com.lambferret.game.player.Player;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.lambferret.game.screen.ui.Overlay.changeCurrentInputProcessor;

public class PhaseScreen extends AbstractScreen {
    private static final Logger logger = LogManager.getLogger(PhaseScreen.class.getName());
    private static Overlay overlay;
    public static Screen currentScreen;
    public static Player player;
    private static final ActionPhaseScreen actionPhaseScreen;
    private static final ReadyPhaseScreen readyPhaseScreen;
    private static final DefeatScreen defeatScreen;
    private static final PrePhaseScreen prePhaseScreen;
    private static final VictoryScreen victoryScreen;
    public static Level currentLevel;
    public static Table map;
//    PhaseText text;

    static {
        actionPhaseScreen = new ActionPhaseScreen();
        readyPhaseScreen = new ReadyPhaseScreen();
        defeatScreen = new DefeatScreen();
        prePhaseScreen = new PrePhaseScreen();
        victoryScreen = new VictoryScreen();
    }

    public PhaseScreen() {
//        text = LocalizeConfig.uiText.getGroundText();
        overlay = Overlay.getInstance();
    }

    @Override
    public void create() {
        Overlay.setPhaseUI();
        actionPhaseScreen.create();
        readyPhaseScreen.create();
        defeatScreen.create();
        prePhaseScreen.create();
        victoryScreen.create();
    }

    /**
     * constructor, create, init
     * constructor : 화면이 등록될 때 이므로 게임 시작과 거의 동시에 호출된다
     * create : 보통은 constructor 와 비슷한 시간에 호출되나 constructor 보다 늦는다
     * init : 화면이 띄워질 때, 즉 changeScreen 에서 주로 사용한다
     */
    public void init() {
        player = SnowFight.player;
        currentLevel = LevelFinder.get(player.getCurrentRegion(), player.getLevelNumber());

        map = makeMap();
        // interface로 바꿀것. 이거보고 결심해요
        prePhaseScreen.init();
//        readyPhaseScreen.init();
//        actionPhaseScreen.init();
//        defeatScreen.init();
//        victoryScreen.init();
    }

    public static Table makeMap() {
        Table map = new Table();
        map.setSkin(GlobalSettings.skin);
        for (int i = 0; i < currentLevel.ROWS; i++) {
            for (int j = 0; j < currentLevel.COLUMNS; j++) {
                var a = new TextButton.TextButtonStyle();
                a.font = GlobalSettings.font;
                TextButton button = new TextButton(String.valueOf(currentLevel.getMap()[i][j]), a);
                map.add(button).pad(3);
            }
            map.row().pad(3);
        }
        return map;
    }

    public static void changeScreen(Screen screen) {
        if (currentScreen != screen) {
            Stage currentMainStage = switch (screen) {
                case PRE -> prePhaseScreen.getStage();
                case READY -> readyPhaseScreen.getStage();
                case ACTION -> actionPhaseScreen.getStage();
                case VICTORY -> victoryScreen.getStage();
                case DEFEAT -> defeatScreen.getStage();
            };
            changeCurrentInputProcessor(currentMainStage);
            currentScreen = screen;
        }
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
        ;
    }
}
