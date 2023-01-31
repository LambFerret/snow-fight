package com.lambferret.game;

import com.lambferret.game.util.CustomInputProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hitbox {
    private static final Logger logger = LogManager.getLogger(Hitbox.class.getName());

    public float x;
    public float y;
    public float width;
    public float height;
    public boolean isHovered;
    public boolean isClicked;

    public Hitbox(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isHovered = false;
        this.isClicked = false;
    }

    public void update() {
        isHovered();
        isClicked();
    }

    private void isHovered() {
        this.isHovered = CustomInputProcessor.x > x && CustomInputProcessor.x < x + width
            && CustomInputProcessor.y > y && CustomInputProcessor.y < y + height;
    }

    private void isClicked() {
        this.isClicked = this.isHovered && CustomInputProcessor.isTouched;
    }

}
