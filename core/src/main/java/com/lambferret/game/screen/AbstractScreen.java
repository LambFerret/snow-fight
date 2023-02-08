package com.lambferret.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
        batch.begin();
        this.render(batch);
        batch.end();
        this.update(delta);
    }

    public abstract void render(SpriteBatch batch);

    public abstract void update(float delta);
}
