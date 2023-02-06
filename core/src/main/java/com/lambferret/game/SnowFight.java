package com.lambferret.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.lambferret.game.screen.ground.RecruitScreen;
import com.lambferret.game.screen.ground.ShopScreen;
import com.lambferret.game.screen.ground.TrainingGroundScreen;
import com.lambferret.game.screen.stage.StageScreen;
import com.lambferret.game.screen.title.TitleMenuScreen;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.CustomInputProcessor;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import de.eskalon.commons.screen.transition.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;


/**
 * 스크린 관리, 스크린 전환 등 게임 전반에 걸친 설정을 하는 곳
 */
public class SnowFight extends ManagedGame<ManagedScreen, ScreenTransition> {

    private static final Logger logger = LogManager.getLogger(SnowFight.class.getName());
    public static OrthographicCamera camera;
    private static AddedScreen currentScreen;
    public static AddedScreen changeScreen;
    public static AssetManager assetManager;
//    private static ScreenManager<ManagedScreen, ScreenTransition> screenManager;

    static {
//        screenManager = getScreen
    }

    @Override
    public void create() {
        super.create();

        // NEVER SCRAMBLE THIS INIT CONFIG'S ORDER !!
        globalGameConfig();
        inputConfig();
        cameraConfig();
        assetConfig();
        LocalizeConfig.init();
        screenConfig();

        //if you wanna test something, in here plz
        if (GlobalSettings.isDev) {
            testPlzMethod();
        }
    }

    public void testPlzMethod() {
        var a = File.listRoots();
        for (File file : a) {
        }

    }

    @Override
    public void render() {
        super.render();
        screenChanger(TransitionEffect.NULL);
    }

    private void globalGameConfig() {
        GlobalSettings.init();
    }

    private void inputConfig() {
        Gdx.input.setInputProcessor(new CustomInputProcessor());
    }

    private void cameraConfig() {
        camera = new OrthographicCamera();
        Matrix4 matrix = new Matrix4();
        matrix.setToOrtho2D(0, 0, GlobalSettings.WIDTH, GlobalSettings.HEIGHT);
        matrix.scale(
            (float) GlobalSettings.WIDTH / GlobalSettings.scale,
            (float) GlobalSettings.WIDTH / GlobalSettings.scale,
            1
        );

        camera.setToOrtho(false, GlobalSettings.WIDTH, GlobalSettings.HEIGHT);
    }


    private void screenConfig() {
        var startTime = System.currentTimeMillis();

        Gdx.graphics.setWindowedMode(GlobalSettings.WIDTH, GlobalSettings.HEIGHT);

        // add screens
        screenManager.addScreen(AddedScreen.TITLE_SCREEN.name(), new TitleMenuScreen());
        screenManager.addScreen(AddedScreen.STAGE_SCREEN.name(), new StageScreen());
        screenManager.addScreen(AddedScreen.TRAINING_GROUND_SCREEN.name(), new TrainingGroundScreen());
        screenManager.addScreen(AddedScreen.RECRUIT_SCREEN.name(), new RecruitScreen());
        screenManager.addScreen(AddedScreen.SHOP_SCREEN.name(), new ShopScreen());

        // config transition 자세한 설정은 나중에 할것 투두
        // 근데 blending 아니고서야 진짜 개구리다 ㅋㅋ
        SpriteBatch batch = new SpriteBatch();
        var blending = new BlendingTransition(batch, 0.5F);
        var slidingIn = new SlidingInTransition(batch, SlidingDirection.DOWN, 1.5F);
        var slidingOut = new SlidingOutTransition(batch, SlidingDirection.DOWN, 1.5F);
        var push = new PushTransition(batch, SlidingDirection.DOWN, 1.5F);
        var horizontalSlicing = new HorizontalSlicingTransition(batch, 19, 1.5F);
        var verticalSlicing = new VerticalSlicingTransition(batch, 19, 1.5F);
//        var shader = new ShaderTransition();

        // add transition
        screenManager.addScreenTransition(TransitionEffect.BLENDING.name(), blending);
        screenManager.addScreenTransition(TransitionEffect.SLIDING_IN.name(), slidingIn);
        screenManager.addScreenTransition(TransitionEffect.SLIDING_OUT.name(), slidingOut);
        screenManager.addScreenTransition(TransitionEffect.PUSH.name(), push);
        screenManager.addScreenTransition(TransitionEffect.HORIZONTAL_SLICING.name(), horizontalSlicing);
        screenManager.addScreenTransition(TransitionEffect.VERTICAL_SLICING.name(), verticalSlicing);
//        screenManager.addScreenTransition(TransitionEffect.SHADER.name(), shader);

        // show first screen
        screenManager.pushScreen(AddedScreen.TITLE_SCREEN.name(), TransitionEffect.BLENDING.name());


        logger.info("screenConfig | time : " + (System.currentTimeMillis() - startTime));
    }

    private void assetConfig() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager = new AssetManager();
        AssetFinder assetFinder = new AssetFinder(assetManager, resolver);
        assetFinder.load();
        assetManager.finishLoading();
    }


    private void screenChanger(TransitionEffect effect) {
        if (currentScreen == changeScreen) return;
        logger.info("screenChanger | change | " + currentScreen + " to " + changeScreen);
        String te = effect.name();
        if (effect == TransitionEffect.NULL) te = null;
        screenManager.pushScreen(changeScreen.name(), te);
        currentScreen = changeScreen;
    }

    public enum AddedScreen {
        TITLE_SCREEN,
        STAGE_SCREEN,
        TRAINING_GROUND_SCREEN,
        RECRUIT_SCREEN,
        SHOP_SCREEN,

        ;
    }

    public enum TransitionEffect {
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
