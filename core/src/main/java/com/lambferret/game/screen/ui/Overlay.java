package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Overlay {

    void update();

    void render(SpriteBatch batch);

    void hide();

    void destroy();
}
