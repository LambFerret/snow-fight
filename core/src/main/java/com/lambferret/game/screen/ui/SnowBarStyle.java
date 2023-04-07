package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.util.AssetFinder;

public class SnowBarStyle extends ProgressBar.ProgressBarStyle {
    public TextureRegionDrawable frame1;
    public TextureRegionDrawable frame2;

    public SnowBarStyle() {
        super();
        this.knobBefore = new TextureRegionDrawable(AssetFinder.getTexture("back2"));
//        this.knob =new TextureRegionDrawable(AssetFinder.getTexture("start"));
        this.knobAfter = new TextureRegionDrawable(AssetFinder.getTexture("23"));
        this.frame1 = new TextureRegionDrawable(AssetFinder.getTexture("start"));
        this.frame2 = new TextureRegionDrawable(AssetFinder.getTexture("execute"));
    }

    public void updateAnimationFrame(float value) {
        if (value % 2 == 0) {
            knobBefore = frame1;
        } else {
            knobBefore = frame2;
        }
    }

}
