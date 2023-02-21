package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.save.SaveLoader;
import com.lambferret.game.setting.GlobalSettings;

public class BarOverlay extends Overlay {
    private Hitbox box;
    public static long time = 0;

    public BarOverlay() {
        this.box = new Hitbox(0.0F, (float) GlobalSettings.currHeight - 50.0F, GlobalSettings.currWidth, 50.0F);
        hide();
    }


    @Override
    public void create() {
        time = SaveLoader.currentSave.getTime();
    }

    @Override
    public void update(float delta) {
        time++;
    }

    @Override
    public void render(SpriteBatch batch) {
        this.box.render(batch);
    }

    @Override
    public void hide() {
        box.hide(Hitbox.Direction.UP);
    }

    @Override
    public void show() {
        box.show();
    }
}
