package com.lambferret.game.setting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.screen.ground.GroundScreen;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.screen.title.TitleScreen;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.util.AssetFinder;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.ScreenManager;
import de.eskalon.commons.screen.transition.ScreenTransition;
import de.eskalon.commons.screen.transition.impl.BlendingTransition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScreenConfig {
    private static final Logger logger = LogManager.getLogger(ScreenConfig.class.getName());
    public static final String BLENDING = "BLENDING";
    private static AddedScreen currentScreen = AddedScreen.TITLE_SCREEN;
    public static AddedScreen changeScreen = currentScreen;
    private static ScreenManager<ManagedScreen, ScreenTransition> screenManager;
    private static TitleScreen titleScreen;
    private static GroundScreen groundScreen;
    private static PhaseScreen phaseScreen;
    private static Sound bgm;
    static long bgmID;

    public static void init(ScreenManager<ManagedScreen, ScreenTransition> screenManager) {
        var startTime = System.currentTimeMillis();

        ScreenConfig.screenManager = screenManager;

        if (!GlobalSettings.isFullscreen) {
            GlobalSettings.currWidth = Setting.DEFAULT_WIDTH;
            GlobalSettings.currHeight = Setting.DEFAULT_HEIGHT;
            Gdx.graphics.setWindowedMode(GlobalSettings.currWidth, GlobalSettings.currHeight);
        } else {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            GlobalSettings.currWidth = Gdx.graphics.getWidth();
            GlobalSettings.currHeight = Gdx.graphics.getHeight();
        }

        addScreen();
        addTransition();

        Gdx.input.setInputProcessor(titleScreen.getStage());
        screenManager.pushScreen(AddedScreen.TITLE_SCREEN.name(), BLENDING);
        setBGM(AddedScreen.TITLE_SCREEN);
        logger.info("screenConfig | " + (System.currentTimeMillis() - startTime) / 1000F + " s");
    }

    /**
     * 스크린을 바꾸는 쪽에서 모든 관련 인자를 넘겨주어야 함
     */
    public static void screenChanger() {
        if (currentScreen == changeScreen) return;
        screenChanger(changeScreen);
    }

    public static void screenChanger(AddedScreen screen) {
        logger.info("screenChanger | change | " + currentScreen + " to " + changeScreen);

        screenManager.pushScreen(screen.name(), BLENDING);
        setBGM(screen);

        // 여기서 생명주기를 관리한다 / 이전 스크린을 정상적으로 종료 후, 다음 스크린의 init 호출
        switch (screen) {
            case TITLE_SCREEN -> {
                Overlay.disposeInstance();
                Gdx.input.setInputProcessor(titleScreen.getStage());
                titleScreen.create();
            }
            case GROUND_SCREEN -> {
                groundScreen.onPlayerReady();
                Overlay.getInstance().onPlayerReady();
                Overlay.setVisibleGroundUI();
            }
            case PHASE_SCREEN -> {
                phaseScreen.onPlayerReady();
                Overlay.getInstance().onPlayerReady();
                Overlay.setVisiblePhaseUI();
            }
        }
        currentScreen = screen;
    }

    private static void setBGM(AddedScreen screen) {
        if (bgm != null) bgm.stop();
        switch (screen) {
            case TITLE_SCREEN -> bgm = AssetFinder.getSound("appassionata");
            case GROUND_SCREEN -> bgm = AssetFinder.getSound("appassionata");
            case PHASE_SCREEN -> bgm = AssetFinder.getSound("appassionata");
        }
        bgmID = bgm.loop(GlobalSettings.bgmVolume);
    }

    public static void setBgmVolume() {
        float value = (GlobalSettings.bgmVolume / 100F) * (GlobalSettings.masterVolume / 100F);
        if (bgm != null) bgm.setVolume(bgmID, value);
    }

    public static void pauseBGM() {
        if (bgm != null) bgm.pause();
    }

    public static void resumeBGM() {
        if (bgm != null) bgm.resume();
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
        screenManager.addScreenTransition(BLENDING, blending);
    }

    public enum AddedScreen {
        TITLE_SCREEN,
        GROUND_SCREEN,
        PHASE_SCREEN,
        ;
    }

}
