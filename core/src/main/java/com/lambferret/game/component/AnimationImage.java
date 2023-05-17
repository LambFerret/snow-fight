package com.lambferret.game.component;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class AnimationImage extends Image {
    private float stateTime = 0;
    private final Animation<TextureRegion> animation;

    public AnimationImage(Animation<TextureRegion> animation) {
        super(animation.getKeyFrame(0));
        this.animation = animation;
    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setDrawable(new TextureRegionDrawable(animation.getKeyFrame(stateTime, true)));
        super.draw(batch, parentAlpha);
    }
}
