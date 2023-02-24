package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Direction;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.component.VerticalScroll;
import com.lambferret.game.setting.GlobalSettings;

public class AbilityOverlay extends AbstractOverlay {
    private Hitbox plate;
    private boolean isHidden = true;
    private static final float OVERLAY_WIDTH = GlobalSettings.currWidth / 3.0F;
    private static final float OVERLAY_HEIGHT = 200.0F;
    private final VerticalScroll scroll = new VerticalScroll(Direction.LEFT);


    @Override
    public void create() {
        this.plate = new Hitbox(GlobalSettings.currWidth - OVERLAY_WIDTH, OVERLAY_HEIGHT, OVERLAY_WIDTH, GlobalSettings.currHeight - OVERLAY_HEIGHT);
        logger.info("create |  🐳 plate 0 | " + OVERLAY_HEIGHT);
        scroll.create(this.plate);

    }

    @Override
    public void update(float delta) {
        if (this.plate.isHovered) {
            this.scroll.update(delta);
        }
        this.plate.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        this.scroll.render(batch);
        this.plate.render(batch);
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
