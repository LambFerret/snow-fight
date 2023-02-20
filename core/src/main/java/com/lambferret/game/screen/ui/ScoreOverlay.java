package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.setting.GlobalSettings;

public class ScoreOverlay extends Overlay {
    private Hitbox box;

    public ScoreOverlay() {
        this.box = new Hitbox(0.0F, (float) GlobalSettings.currHeight - 100.0F, 50.0F, 50.0F);
        hide();
    }

    @Override
    public void update(float delta) {
        this.box.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        this.box.render(batch);
    }

    @Override
    public void hide() {
        this.box.hide(Hitbox.Direction.LEFT);
    }

    @Override
    public void show() {
        this.box.show();
    }
}
