package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.setting.GlobalSettings;

public class SoldierOverlay extends AbstractOverlay {
    private Hitbox box;
    private boolean isHidden = true;

    // TODO : 이부분은 좀 고정 사이즈를 가질 필요를 느낀다
    private static final float OVERLAY_HEIGHT = 200.0F;

    @Override
    public void create() {
        this.box = new Hitbox(0.0F, 0.0F, GlobalSettings.currWidth * 2 / 3.0F, OVERLAY_HEIGHT);
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
    public void hide(Hitbox.Direction direction) {
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
        isHidden = true;
    }


}
