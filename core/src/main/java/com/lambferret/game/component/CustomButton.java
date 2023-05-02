package com.lambferret.game.component;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class CustomButton extends ImageTextButton {
    Label label;

    public CustomButton(String text, ImageTextButtonStyle style) {
        super(text, style);
        label = getLabel();
        label.setTouchable(Touchable.disabled);
        this.setTransform(true);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        this.setOrigin(Align.center);
    }

}
