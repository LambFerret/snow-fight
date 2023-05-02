package com.lambferret.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lambferret.game.level.LevelFinder;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.SaveLoader;
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

        // NEVER SCRAMBLE THIS INIT CONFIG'S ORDER !!
        GlobalSettings.init();
        cameraConfig();
        assetConfig();
        LocalizeConfig.init();
        ScreenConfig.init(screenManager);

    }

    public static void setPlayer() {
        player = new Player();
        LevelFinder.createTiledMapTileSet();
    }

    private static void cameraConfig() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GlobalSettings.currWidth, GlobalSettings.currHeight, camera);
        camera.setToOrtho(false, GlobalSettings.currWidth, GlobalSettings.currHeight);
    }

    private void assetConfig() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager = new AssetManager();
        AssetLoader assetLoader = new AssetLoader(assetManager, resolver);
        assetLoader.load();
        assetManager.finishLoading();
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
    }
}
