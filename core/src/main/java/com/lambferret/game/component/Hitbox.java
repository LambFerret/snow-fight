package com.lambferret.game.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.AssetPath;
import com.lambferret.game.util.CustomInputProcessor;
import com.lambferret.game.util.GlobalUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hitbox {

    private static final Logger logger = LogManager.getLogger(Hitbox.class.getName());

    private static final float SPEED = 3.0F;

    private float x;
    private float y;
    private float width;
    private float height;
    private float destX;
    private float destY;
    private float destWidth;
    private float destHeight;
    private float initX;
    private float initY;
    private float initWidth;
    private float initHeight;
    public boolean isHovered;
    public boolean isClicked;
    private boolean isActive = true;

    public Hitbox() {
        this(1, 1);
    }

    public Hitbox(float width, float height) {
        this(-10000.0F, -10000.0F, width, height);
    }

    public Hitbox(float x, float y, float width, float height) {
        this.initX = x;
        this.initY = y;
        this.initWidth = width;
        this.initHeight = height;
        this.destX = x;
        this.destY = y;
        this.destWidth = width;
        this.destHeight = height;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isHovered = false;
        this.isClicked = false;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void move(float x, float y) {
        this.x = x;
        this.destX = x;
        this.y = y;
        this.destY = y;
    }

    public void resize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void hide(Direction hidingDirection) {
        switch (hidingDirection){
            case UP -> {
                this.destY = this.y + GlobalSettings.HEIGHT;
            }
            case DOWN -> {
                this.destY = this.y - GlobalSettings.HEIGHT;
            }
            case LEFT -> {
                this.destX = this.x - GlobalSettings.WIDTH;
            }
            case RIGHT -> {
                this.destX = this.x + GlobalSettings.WIDTH;
            }
        }
        this.isActive = false;
    }
    public void show() {
        this.destX = this.initX;
        this.destY = this.initY;
        this.isActive = true;

    }




    public void render(SpriteBatch debugBatch) {
        if (GlobalSettings.isDev) {
            if (this.isHovered) {
                debugBatch.setColor(0, 255, 0, 0.5F);
            } else if (this.isClicked) {
                debugBatch.setColor(0, 0, 255, 0.5F);
            } else {
                debugBatch.setColor(255, 0, 0, 0.5F);
            }
            Texture tex = AssetPath.getTexture("yellow");
            debugBatch.draw(tex, this.x, this.y, this.width, this.height);
        }
    }


    public void update(float delta) {
        if (isActive) {
            isHovered();
            isClicked();
        }
        this.x = GlobalUtil.lerp(x, destX, SPEED, delta);
        this.y = GlobalUtil.lerp(y, destY, SPEED, delta);
    }

    private void isHovered() {
        this.isHovered = CustomInputProcessor.x > this.x && CustomInputProcessor.x < this.x + this.width && CustomInputProcessor.y > this.y && CustomInputProcessor.y < this.y + this.height;
    }

    private void isClicked() {
        this.isClicked = this.isHovered && CustomInputProcessor.isTouched;
    }
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

}
