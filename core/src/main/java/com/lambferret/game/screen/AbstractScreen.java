package com.lambferret.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.lambferret.game.setting.GlobalSettings;
import de.eskalon.commons.screen.ManagedScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractScreen extends ManagedScreen {
    private static final Logger logger = LogManager.getLogger(AbstractScreen.class.getName());
    private static final Stage stage = new Stage();

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.render();
        this.update();
    }

    public abstract void create();

    public abstract void render();

    public abstract void update();

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
    }


    private void switchFullScreen() {
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setWindowedMode(GlobalSettings.prevWidth, GlobalSettings.prevHeight);
        } else {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
