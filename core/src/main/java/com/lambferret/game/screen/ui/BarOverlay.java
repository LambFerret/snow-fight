package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.component.constant.Direction;
import com.lambferret.game.setting.GlobalSettings;

public class BarOverlay extends AbstractOverlay {
    private Hitbox box;
    private boolean isHidden = false;

    @Override
    public void create() {
        this.box = new Hitbox(0.0F, (float) GlobalSettings.currHeight - BAR_HEIGHT, GlobalSettings.currWidth, BAR_HEIGHT);

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
    public void hide(Direction direction) {
        if (isHidden) return;
        this.box.hide(direction);
        isHidden = true;
    }

    @Override
    public void show(boolean instantly) {
        if (!isHidden) return;
        if (instantly) {
            this.box.showInstantly();
        } else {
            this.box.show();
        }
        isHidden = false;
    }
}
