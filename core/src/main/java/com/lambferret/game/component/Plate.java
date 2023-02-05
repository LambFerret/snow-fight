package com.lambferret.game.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.util.AssetPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Plate {
    private static final Logger logger = LogManager.getLogger(Plate.class.getName());


    private float x;
    private float y;
    private float width;
    private float height;
    private Color color = Color.SKY;

    public Plate(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

    public void setColor(Color color){
        this.color = color;
    }

    public void render(SpriteBatch batch) {
        batch.draw(AssetPath.getTexture("load"), x, y, width, height);
        batch.setColor(color);
    }
}
