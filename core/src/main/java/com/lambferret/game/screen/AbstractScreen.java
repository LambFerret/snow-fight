package com.lambferret.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.SnowFight;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.CustomInputProcessor;
import de.eskalon.commons.screen.ManagedScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractScreen extends ManagedScreen {
    private static final Logger logger = LogManager.getLogger(AbstractScreen.class.getName());
    private static final SpriteBatch batch = new SpriteBatch();


    @Override
    public void create() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void resize(int width, int height) {
        SnowFight.cameraConfig(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(SnowFight.camera.combined);

        TEST();

        batch.begin();
        this.render(batch);
        batch.end();
        this.update(delta);
    }

    public abstract void render(SpriteBatch batch);

    public abstract void update(float delta);

    private void TEST() {
        if (CustomInputProcessor.pressedKeyUp(Input.Keys.NUMPAD_9)) {
            switchFullScreen();
        }
    }

    private void switchFullScreen() {
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setWindowedMode(GlobalSettings.prevWidth, GlobalSettings.prevHeight);
        } else {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }
    }
}
