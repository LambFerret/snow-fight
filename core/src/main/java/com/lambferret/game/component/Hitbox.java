package com.lambferret.game.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.AssetPath;
import com.lambferret.game.util.CustomInputProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hitbox {

    private static final Logger logger = LogManager.getLogger(Hitbox.class.getName());

    private float x;
    private float y;
    private float width;
    private float height;
    public boolean isHovered;
    public boolean isClicked;

    public Hitbox() {
        this(1, 1);
    }

    public Hitbox(float width, float height) {
        this(-10000.0F, -10000.0F, width, height);
    }

    public Hitbox(float x, float y, float width, float height) {
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
        this.y = y;
    }

    public void resize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void render(SpriteBatch debugBatch) {
        if (GlobalSettings.isDev) {
            if (this.isHovered) {
                debugBatch.setColor(0, 255, 0, 0.5F);
            } else if (this.isClicked) {
                debugBatch.setColor(0, 0, 255, 0.5F);
            } else {
                debugBatch.setColor(255, 0, 0, 0.1F);
            }
            Texture tex = AssetPath.getTexture("yellow");
            debugBatch.draw(tex, this.x, this.y, this.width, this.height);
        }
    }

    public void update(float delta) {
        isHovered();
        isClicked();
    }

    private void isHovered() {
        this.isHovered = CustomInputProcessor.x > this.x && CustomInputProcessor.x < this.x + this.width
            && CustomInputProcessor.y > this.y && CustomInputProcessor.y < this.y + this.height;
    }

    private void isClicked() {
        this.isClicked = this.isHovered && CustomInputProcessor.isTouched;
    }

}
