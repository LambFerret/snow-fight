package com.lambferret.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    public SpriteBatch batch;
    OrthographicCamera camera;
    int width;
    int height;
    public SnowFight(final int SCREEN_WIDTH, final int SCREEN_HEIGHT) {
        this.width = SCREEN_WIDTH;
        this.height = SCREEN_HEIGHT;
    }
    @Override
    public void create() {
        super.create();

        logger.info("create | IN_THE");
        cameraConfig();
        screenConfig();

    }

    @Override
    public void render() {
        super.render();
        debug();
    }

    private void cameraConfig() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
    }

    private void debug() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.F12)) {
            if (Gdx.app.getLogLevel() == Application.LOG_NONE) {
                Gdx.app.setLogLevel(Application.LOG_DEBUG);
            } else {
                Gdx.app.setLogLevel(Application.LOG_NONE);
            }
        }
    }

    private void screenConfig() {

        batch = new SpriteBatch();

        // add screens
        screenManager.addScreen(MAIN_SCREEN, new FirstScreen(this));

        // config transition
        var shader = new ShaderTransition(0.1f);

        // add transition
        screenManager.addScreenTransition(ShaderTransition.class.getName(), shader);

        // show first screen
        screenManager.pushScreen(MAIN_SCREEN, null);

    }

}
