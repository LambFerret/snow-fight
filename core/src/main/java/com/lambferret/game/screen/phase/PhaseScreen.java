package com.lambferret.game.screen.phase;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.SnowFight;
import com.lambferret.game.level.Level;
import com.lambferret.game.level.LevelFinder;
import com.lambferret.game.player.Player;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.screen.ui.Overlay;
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
        super.create();
        overlay = Overlay.getInstance();
        overlay.setPhaseUI();
        player = SnowFight.player;
        currentLevel = LevelFinder.get(player.getCurrentRegion(), player.getLevelNumber());
        prePhaseScreen = new PrePhaseScreen();
        readyPhaseScreen = new ReadyPhaseScreen();
        actionPhaseScreen = new ActionPhaseScreen();
        victoryScreen = new VictoryScreen();
        defeatScreen = new DefeatScreen();
        screen = Screen.PRE;

    }

    @Override
    public void render(SpriteBatch batch) {
        overlay.render(batch);
        switch (screen) {
            case PRE -> {
                prePhaseScreen.render(batch);
            }
            case READY -> {
                readyPhaseScreen.render(batch);
            }
            case ACTION -> {
                actionPhaseScreen.render(batch);
            }
            case VICTORY -> {
                victoryScreen.render(batch);
            }
            case DEFEAT -> {
                defeatScreen.render(batch);

            }
        }
    }

    @Override
    public void update(float delta) {
        overlay.update(delta);
        switch (screen) {
            case PRE -> {
                prePhaseScreen.update(delta);
            }
            case READY -> {
                readyPhaseScreen.update(delta);
            }
            case ACTION -> {
                actionPhaseScreen.update(delta);
            }
            case VICTORY -> {
                victoryScreen.update(delta);
            }
            case DEFEAT -> {
                defeatScreen.update(delta);
            }
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
