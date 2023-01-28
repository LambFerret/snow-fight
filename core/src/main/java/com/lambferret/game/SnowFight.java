package com.lambferret.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class SnowFight extends Game {

    public SpriteBatch batch;
    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new FirstScreen(this));
    }

}
