package com.lambferret.game.setting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.screen.ground.GroundScreen;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.screen.title.TitleScreen;
import com.lambferret.game.screen.ui.Overlay;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.ScreenManager;
import de.eskalon.commons.screen.transition.ScreenTransition;
import de.eskalon.commons.screen.transition.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScreenConfig {
    private static final Logger logger = LogManager.getLogger(ScreenConfig.class.getName());
    private static AddedScreen currentScreen;
    public static AddedScreen changeScreen;
    private static ScreenManager<ManagedScreen, ScreenTransition> screenManager;
    private static InputProcessor currentInputProcessor;
    private static TitleScreen titleScreen;
    private static GroundScreen groundScreen;
    private static PhaseScreen phaseScreen;

    public static void init(ScreenManager<ManagedScreen, ScreenTransition> screenManager) {
        var startTime = System.currentTimeMillis();

        ScreenConfig.screenManager = screenManager;

        if (GlobalSettings.isFullscreen) {
            Gdx.graphics.setWindowedMode(GlobalSettings.currWidth, GlobalSettings.currHeight);
        } else {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }

        addScreen();
        addTransition();

        screenManager.pushScreen(AddedScreen.TITLE_SCREEN.name(), TransitionEffect.BLENDING.name());

        logger.info("screenConfig | " + (System.currentTimeMillis() - startTime) / 1000F + " s");
    }

    /**
     * 스크린을 바꾸는 쪽에서 모든 관련 인자를 넘겨주어야 함
     */
    public static void screenChanger() {
        if (changeScreen == null) {
            currentScreen = AddedScreen.TITLE_SCREEN;
            Gdx.input.setInputProcessor(titleScreen.getStage());
            return;
        }
        if (currentScreen == changeScreen) return;
        logger.info("screenChanger | change | " + currentScreen + " to " + changeScreen);
        String te = null; // effect.name(); TODO: 각각 원하는 changer effect 사용
//        if (effect == TransitionEffect.NULL) te = null;
        screenManager.pushScreen(changeScreen.name(), te);
        switch (changeScreen) {
            case TITLE_SCREEN -> {
                currentInputProcessor = titleScreen.getStage();
                Gdx.input.setInputProcessor(currentInputProcessor);
            }
            case GROUND_SCREEN -> {
                Overlay.setGroundUI();
                GroundScreen.changeScreen(GroundScreen.Screen.TRAINING_GROUND);
                Overlay.getInstance().onPlayerReady();
                groundScreen.onPlayerReady();
            }
            case PHASE_SCREEN -> {
                Overlay.setPhaseUI();
                PhaseScreen.changeScreen(PhaseScreen.Screen.PRE);
                Overlay.getInstance().onPlayerReady();
                phaseScreen.onPlayerReady();
            }
        }
        currentScreen = changeScreen;
    }

    /**
     * Register screens
     */
    private static void addScreen() {
        titleScreen = new TitleScreen();
        groundScreen = new GroundScreen();
        phaseScreen = new PhaseScreen();

        screenManager.addScreen(AddedScreen.TITLE_SCREEN.name(), titleScreen);
        screenManager.addScreen(AddedScreen.GROUND_SCREEN.name(), groundScreen);
        screenManager.addScreen(AddedScreen.PHASE_SCREEN.name(), phaseScreen);
    }

    /**
     * Register transition
     * config transition 자세한 설정은 나중에 할것 투두
     * 근데 blending 아니고서야 진짜 개구리다 ㅋㅋ
     */
    private static void addTransition() {
        SpriteBatch batch = new SpriteBatch();
        var blending = new BlendingTransition(batch, 0.5F);
        var slidingIn = new SlidingInTransition(batch, SlidingDirection.DOWN, 1.5F);
        var slidingOut = new SlidingOutTransition(batch, SlidingDirection.DOWN, 1.5F);
        var push = new PushTransition(batch, SlidingDirection.DOWN, 1.5F);
        var horizontalSlicing = new HorizontalSlicingTransition(batch, 19, 1.5F);
        var verticalSlicing = new VerticalSlicingTransition(batch, 19, 1.5F);

        screenManager.addScreenTransition(TransitionEffect.BLENDING.name(), blending);
        screenManager.addScreenTransition(TransitionEffect.SLIDING_IN.name(), slidingIn);
        screenManager.addScreenTransition(TransitionEffect.SLIDING_OUT.name(), slidingOut);
        screenManager.addScreenTransition(TransitionEffect.PUSH.name(), push);
        screenManager.addScreenTransition(TransitionEffect.HORIZONTAL_SLICING.name(), horizontalSlicing);
        screenManager.addScreenTransition(TransitionEffect.VERTICAL_SLICING.name(), verticalSlicing);
    }

    public enum AddedScreen {
        TITLE_SCREEN,
        GROUND_SCREEN,
        PHASE_SCREEN,
        ;
    }

    private enum TransitionEffect {
        BLENDING,
        SLIDING_IN,
        SLIDING_OUT,
        PUSH,
        HORIZONTAL_SLICING,
        VERTICAL_SLICING,
        SHADER,
        NULL
    }
}
