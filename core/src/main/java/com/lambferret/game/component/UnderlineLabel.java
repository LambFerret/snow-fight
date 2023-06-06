package com.lambferret.game.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.lambferret.game.util.AssetFinder;

public class UnderlineLabel extends Label {
    private boolean underline = false;
    private final Texture underlineTexture;

    public UnderlineLabel(CharSequence text, BitmapFont font) {
        super(text, new LabelStyle(font, null));
        underlineTexture = AssetFinder.getTexture("underline");
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        // Draw underline if needed
        if (underline) {
            float lineY = getY(); // Adjust this value to position the underline correctly
            float underlineHeight = 1f;
            batch.draw(underlineTexture, getX(), lineY, getWidth(), underlineHeight);
        }
    }
}
