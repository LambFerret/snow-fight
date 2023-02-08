package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Overlay {

    void update(float delta);

    void render(SpriteBatch batch);

    void hide();

    void destroy();
}
