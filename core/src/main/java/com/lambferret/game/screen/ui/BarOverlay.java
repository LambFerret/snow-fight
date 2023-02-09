package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.setting.GlobalSettings;

public class BarOverlay extends Overlay {
    private Hitbox box;

    public BarOverlay() {
        this.box = new Hitbox(0.0F, (float) GlobalSettings.HEIGHT - 50.0F, GlobalSettings.WIDTH, 50.0F);
        hide();
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(SpriteBatch batch) {
        this.box.render(batch);
    }

    @Override
    public void hide() {
    }

    @Override
    public void show() {
    }
}
