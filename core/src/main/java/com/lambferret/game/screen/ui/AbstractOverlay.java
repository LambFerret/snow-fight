package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.constant.Direction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractOverlay {
    public static final Logger logger = LogManager.getLogger(AbstractOverlay.class.getName());

    public abstract void create();

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch);

    public abstract void hide(Direction direction);

    public abstract void show(boolean instantly);
}
