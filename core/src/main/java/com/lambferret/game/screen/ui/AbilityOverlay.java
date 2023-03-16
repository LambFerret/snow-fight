package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.component.VerticalScroll;
import com.lambferret.game.component.constant.Direction;
import com.lambferret.game.setting.GlobalSettings;

public class AbilityOverlay extends AbstractOverlay {
    private Hitbox plate;
    private boolean isHidden = true;
    private final VerticalScroll scroll = new VerticalScroll(Direction.LEFT);


    @Override
    public void create() {
        this.plate = new Hitbox(GlobalSettings.currWidth - OVERLAY_WIDTH, OVERLAY_HEIGHT, OVERLAY_WIDTH, GlobalSettings.currHeight - OVERLAY_HEIGHT);
        scroll.create(this.plate);

    }

    @Override
    public void update() {
        if (this.plate.isHovered) {
            this.scroll.update();
        }
        this.plate.update();
    }

    @Override
    public void render() {
        this.scroll.render();
        this.plate.render();
    }

    @Override
    public void hide(Direction direction) {
        if (isHidden) return;
        this.plate.hide(direction);
        isHidden = true;
    }

    @Override
    public void show(boolean instantly) {
        if (!isHidden) return;
        if (instantly) {
            this.plate.showInstantly();
        } else {
            this.plate.show();
        }
        isHidden = true;
    }


}
