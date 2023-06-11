package com.lambferret.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.SaveLoader;
import com.lambferret.game.setting.FontConfig;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.setting.ScreenConfig;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.util.AssetLoader;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 각종 Config 를 모아두는 곳
 */
public class SnowFight extends ManagedGame<ManagedScreen, ScreenTransition> {

    private static final Logger logger = LogManager.getLogger(SnowFight.class.getName());
    public static OrthographicCamera camera;
    public static Viewport viewport;
    public static AssetManager assetManager;
    public static Player player;

    @Override
    public void create() {
        super.create();
        var startTime = System.currentTimeMillis();
        logger.info(" ┌──────────────────────────┐");

        // NEVER SCRAMBLE THIS INIT CONFIG'S ORDER !!
        GlobalSettings.init();
        cameraConfig();
        assetConfig();
        LocalizeConfig.init();
        FontConfig.init();
        ScreenConfig.init(screenManager);

        var endTime = String.format("%.3f", (System.currentTimeMillis() - startTime) / 1000F);
        logger.info(" ├ TOTAL LOAD     : " + endTime + " s │");
        logger.info(" └──────────────────────────┘");
    }

    public static void setPlayer() {
        player = new Player();
        player.lateInit();
    }

    private void cameraConfig() {
        var startTime = System.currentTimeMillis();
        camera = new OrthographicCamera();
        viewport = new FitViewport(GlobalSettings.currWidth, GlobalSettings.currHeight, camera);
        camera.setToOrtho(false, GlobalSettings.currWidth, GlobalSettings.currHeight);
        var endTime = String.format("%.3f", (System.currentTimeMillis() - startTime) / 1000F);
        logger.info(" ├ cameraConfig   : " + endTime + " s │");
    }

    private void assetConfig() {
        var startTime = System.currentTimeMillis();
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager = new AssetManager();
        AssetLoader assetLoader = new AssetLoader(assetManager, resolver);
        assetLoader.load();
        assetManager.finishLoading();
        var endTime = String.format("%.3f", (System.currentTimeMillis() - startTime) / 1000F);
        logger.info(" ├ assetConfig    : " + endTime + " s │");
    }

    @Override
    public void render() {
        super.render();
        ScreenConfig.screenChanger();
    }

    /**
     * 게임이 꺼질때 해야하는 것들 ex) 저장
     */
    @Override
    public void dispose() {
        if (player != null) SaveLoader.save();
        super.dispose();
        logger.info(" BYE! ");
    }

}
