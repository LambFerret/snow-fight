package com.lambferret.game.component;

/**
 * UI backGround Plate
 * width, height 는 설정하지 않아도 알 수 있게 못하나
 */
public class Plate {

    private float x;
    private float y;
    private float width;
    private float height;
    private boolean isAttachedOnMouse;

    public Plate(float width, float height, float x, float y) {
        isAttachedOnMouse = false;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Plate(float width, float height) {
        isAttachedOnMouse = true;
        this.height = height;
        this.width = width;
    }



}
