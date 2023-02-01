package com.lambferret.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    public void render(SpriteBatch batch) {
        if (GlobalSettings.isDev) {
            if (this.isClicked) {
                batch.setColor(Color.GREEN);
            } else {
                batch.setColor(Color.RED);
            }
            Texture texture = new Texture("./sprite/yellow.png");
            batch.draw(texture, this.x, this.y, this.width, this.height);

        }
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
