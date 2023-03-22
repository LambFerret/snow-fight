package com.lambferret.game.screen.phase;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.SnowFight;
import com.lambferret.game.constant.Terrain;
import com.lambferret.game.level.Level;
import com.lambferret.game.level.LevelFinder;
import com.lambferret.game.player.Player;
import com.lambferret.game.player.PlayerObserver;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.lambferret.game.screen.ui.Overlay.changeCurrentInputProcessor;

public class PhaseScreen extends AbstractScreen implements PlayerObserver {
    private static final Logger logger = LogManager.getLogger(PhaseScreen.class.getName());
    public static final float MAP_X = 50.0F;
    public static final float MAP_Y = 50.0F;
    public static final float MAP_WIDTH = 500.0F;
    public static final float MAP_HEIGHT = 500.0F;
    public static final Container<Table> mapContainer = new Container<>();
    private static Overlay overlay;
    public static Screen currentScreen;
    public static Player player;
    public static Level currentLevel;

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
        currentLevel = LevelFinder.get(player.getCurrentRegion(), player.getLevelNumber());
        mapContainer.setActor(makeMap());
        for (AbstractPhase phase : phaseListener) {
            phase.init(player);
        }
    }

    private Table makeMap() {
        Table map = new Table();
        map.setFillParent(true);

        map.setSkin(GlobalSettings.skin);
        for (int i = 0; i < currentLevel.ROWS; i++) {
            for (int j = 0; j < currentLevel.COLUMNS; j++) {
                map.add(makeMapElement(currentLevel.getMap()[i][j]));
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
        button.setSize(MAP_WIDTH / currentLevel.COLUMNS, MAP_HEIGHT / currentLevel.ROWS);

        return button;
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

    }
}
