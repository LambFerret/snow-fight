package com.lambferret.game.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractDrawable {
    public int z;
    public abstract void render(SpriteBatch sprite);
}
