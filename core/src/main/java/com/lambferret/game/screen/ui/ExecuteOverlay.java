package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.constant.Direction;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.AssetPath;

public class ExecuteOverlay extends AbstractOverlay {
    private Hitbox box;
    private boolean isHidden = true;
    private static final float OVERLAY_WIDTH = GlobalSettings.currWidth / 3.0F;
    private static final float OVERLAY_HEIGHT = 200.0F;
    private Texture texture;


    @Override
    public void create() {
        this.box = new Hitbox(GlobalSettings.currWidth - OVERLAY_WIDTH, 0.0F, OVERLAY_WIDTH, OVERLAY_HEIGHT);
        texture = AssetPath.getTexture("execute");
    }

    @Override
    public void update(float delta) {
        this.box.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        this.box.render(batch);
        batch.draw(texture, GlobalSettings.currWidth - OVERLAY_WIDTH, 0.0F, OVERLAY_WIDTH, OVERLAY_HEIGHT);
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
        isHidden = true;
    }


}
