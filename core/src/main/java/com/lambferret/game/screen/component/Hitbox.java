package com.lambferret.game.screen.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.setting.GlobalSettings;
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

    public Hitbox(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isHovered = false;
        this.isClicked = false;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void render(SpriteBatch batch) {
        if (GlobalSettings.isDev) {
            if (this.isHovered) {
                batch.setColor(Color.GREEN);
            } else if (this.isClicked) {
                batch.setColor(Color.BLUE);
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
        this.isHovered = CustomInputProcessor.x > this.x && CustomInputProcessor.x < this.x + this.width
            && CustomInputProcessor.y > this.y && CustomInputProcessor.y < this.y + this.height;
    }

    private void isClicked() {
        this.isClicked = this.isHovered && CustomInputProcessor.isTouched;
    }

}
