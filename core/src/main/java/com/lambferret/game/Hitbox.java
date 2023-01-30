package com.lambferret.game;

public class Hitbox {
    float x;
    float y;
    float width;
    float height;
    boolean isHovered;
    boolean isClicked;

    public Hitbox(float width, float height) {
        this(-10000F, -10000F, width, height);
    }

    public Hitbox(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width =width;
        this.height = height;
        this.isHovered = false;
        this.isClicked = false;
    }

    public void update() {
        this.update(this.x, this.y);

    }

    public void update(float x, float y) {
        this.x = x;
        this.y = y;


    }
}
