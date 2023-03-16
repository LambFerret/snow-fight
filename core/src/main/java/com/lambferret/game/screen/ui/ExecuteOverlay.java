package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.Texture;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.component.constant.Direction;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.AssetFinder;

public class ExecuteOverlay extends AbstractOverlay {
    private Hitbox box;
    private boolean isHidden = true;
    private Texture texture;


    @Override
    public void create() {
        this.box = new Hitbox(GlobalSettings.currWidth - OVERLAY_WIDTH, 0.0F, OVERLAY_WIDTH, OVERLAY_HEIGHT);
        texture = AssetFinder.getTexture("execute");
    }

    @Override
    public void update() {
        this.box.update();
    }

    @Override
    public void render() {
        this.box.render();
//        batch.draw(texture, GlobalSettings.currWidth - OVERLAY_WIDTH, 0.0F, OVERLAY_WIDTH, OVERLAY_HEIGHT);
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
