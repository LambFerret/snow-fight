package com.lambferret.game.screen.event;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameEvent {
    abstract void render(SpriteBatch batch);

    abstract void update(float delta);

}
