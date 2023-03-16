package com.lambferret.game.screen.phase;

import com.badlogic.gdx.Input;
import com.lambferret.game.SnowFight;
import com.lambferret.game.level.Level;
import com.lambferret.game.level.LevelFinder;
import com.lambferret.game.player.Player;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.screen.phase.container.MapContainer;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.util.CustomInputProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PhaseScreen extends AbstractScreen {
    private static final Logger logger = LogManager.getLogger(PhaseScreen.class.getName());
    private static Overlay overlay;
    public static Screen screen;
    public static Player player;
    private ActionPhaseScreen actionPhaseScreen;
    private ReadyPhaseScreen readyPhaseScreen;
    private DefeatScreen defeatScreen;
    private PrePhaseScreen prePhaseScreen;
    private VictoryScreen victoryScreen;
    public static Level currentLevel;

    public PhaseScreen() {

    }

    @Override
    public void create() {
        overlay = Overlay.getInstance();
        overlay.setPhaseUI();
        player = SnowFight.player;
        currentLevel = LevelFinder.get(player.getCurrentRegion(), player.getLevelNumber());
        MapContainer mapContainer = new MapContainer(currentLevel);
        prePhaseScreen = new PrePhaseScreen(mapContainer);
        readyPhaseScreen = new ReadyPhaseScreen(mapContainer);
        actionPhaseScreen = new ActionPhaseScreen(mapContainer);
        victoryScreen = new VictoryScreen();
        defeatScreen = new DefeatScreen();
        screen = Screen.PRE;

    }

    @Override
    public void render() {
        overlay.render();
        switch (screen) {
            case PRE -> {
                prePhaseScreen.render();
            }
            case READY -> {
                readyPhaseScreen.render();
            }
            case ACTION -> {
                actionPhaseScreen.render();
            }
            case VICTORY -> {
                victoryScreen.render();
            }
            case DEFEAT -> {
                defeatScreen.render();

            }
        }
    }

    @Override
    public void update() {
        overlay.update();
        switch (screen) {
            case PRE -> {
                prePhaseScreen.update();
            }
            case READY -> {
                readyPhaseScreen.update();
            }
            case ACTION -> {
                actionPhaseScreen.update();
            }
            case VICTORY -> {
                victoryScreen.update();
            }
            case DEFEAT -> {
                defeatScreen.update();
            }
        }
        if (CustomInputProcessor.pressedKey(Input.Keys.NUM_6)) {
            screen = Screen.PRE;
        } else if (CustomInputProcessor.pressedKey(Input.Keys.NUM_7)) {
            screen = Screen.READY;
        } else if (CustomInputProcessor.pressedKey(Input.Keys.NUM_8)) {
            screen = Screen.ACTION;
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
