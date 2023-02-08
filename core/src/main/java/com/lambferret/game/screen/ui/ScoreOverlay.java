package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.setting.GlobalSettings;

public class ScoreOverlay implements Overlay {
    private Hitbox box;

    public ScoreOverlay() {
        this.box = new Hitbox(0.0F, (float) GlobalSettings.HEIGHT - 100.0F, 50.0F, 50.0F);
    }
    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
        this.box.render();
    }

    @Override
    public void hide() {

    }

    @Override
    public void destroy() {

    }
}
