package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Overlay {
    public static final Logger logger = LogManager.getLogger(Overlay.class.getName());

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch);

    public abstract void hide();

    public abstract void show();
}
