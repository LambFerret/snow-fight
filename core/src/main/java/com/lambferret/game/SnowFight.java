package com.lambferret.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.screen.main.MainMenuScreen;
import com.lambferret.game.screen.main.SubScreen;
import com.lambferret.game.util.CustomInputProcessor;
import de.eskalon.commons.core.ManagedGame;
import de.eskalon.commons.screen.ManagedScreen;
import de.eskalon.commons.screen.transition.ScreenTransition;
import de.eskalon.commons.screen.transition.impl.ShaderTransition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 스크린 관리, 스크린 전환 등 게임 전반에 걸친 설정을 하는 곳
 */
public class SnowFight extends ManagedGame<ManagedScreen, ScreenTransition> {

    private static final Logger logger = LogManager.getLogger(SnowFight.class.getName());
    public static final String MAIN_SCREEN = "First";
    public static final String SUB_SCREEN = "sub";
    public SpriteBatch batch;
    OrthographicCamera camera;

    @Override
    public void create() {
        super.create();

        globalGameConfig();
        inputConfig();
        cameraConfig();
        screenConfig();

    }

    @Override
    public void render() {
        super.render();
    }

    private void globalGameConfig() {
        GlobalSettings.init();
    }

    private void inputConfig() {
        Gdx.input.setInputProcessor(new CustomInputProcessor());

    }

    private void cameraConfig() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GlobalSettings.WIDTH, GlobalSettings.HEIGHT);
    }


    private void screenConfig() {
        var startTime = System.currentTimeMillis();

        batch = new SpriteBatch();
        // add screens
        screenManager.addScreen(MAIN_SCREEN, new MainMenuScreen(this));
        screenManager.addScreen(SUB_SCREEN, new SubScreen(this));


        // config transition
        var shader = new ShaderTransition(0.1f);

        // add transition
        screenManager.addScreenTransition(ShaderTransition.class.getName(), shader);

        // show first screen
        screenManager.pushScreen(MAIN_SCREEN, null);

        logger.info("screenConfig | time : " + (System.currentTimeMillis() - startTime));
    }

}
