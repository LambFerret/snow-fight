package com.lambferret.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.lambferret.game.SnowFight;
import com.lambferret.game.setting.ScreenConfig;
import de.eskalon.commons.screen.ManagedScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractScreen extends ManagedScreen {
    private static final Logger logger = LogManager.getLogger(AbstractScreen.class.getName());

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    protected void create() {
    }

    @Override
    public void resize(int width, int height) {
        SnowFight.viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
        ScreenConfig.pauseBGM();
    }

    @Override
    public void resume() {
        ScreenConfig.resumeBGM();
    }

}
