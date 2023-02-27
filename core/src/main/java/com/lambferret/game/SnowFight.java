package com.lambferret.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lambferret.game.save.SaveLoader;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.setting.ScreenConfig;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.util.AssetLoader;
import com.lambferret.game.util.CustomInputProcessor;
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

    @Override
    public void create() {
        super.create();

        // NEVER SCRAMBLE THIS INIT CONFIG'S ORDER !!
        globalGameConfig();
        inputConfig();
        cameraConfig(GlobalSettings.currWidth, GlobalSettings.currHeight);
        assetConfig();
        LocalizeConfig.init();
        ScreenConfig.init(screenManager);

    }

    @Override
    public void render() {
        super.render();
        ScreenConfig.screenChanger();
    }

    @Override
    public void dispose() {
        SaveLoader.save();
        super.dispose();
    }

    private void globalGameConfig() {
        GlobalSettings.init();
    }

    private void inputConfig() {
        Gdx.input.setInputProcessor(new CustomInputProcessor());
    }

    public static void cameraConfig(int width, int height) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(width, height, camera);
        camera.setToOrtho(false, width, height);
    }

    private void assetConfig() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager = new AssetManager();
        AssetLoader assetLoader = new AssetLoader(assetManager, resolver);
        assetLoader.load();
        assetManager.finishLoading();
    }
}
