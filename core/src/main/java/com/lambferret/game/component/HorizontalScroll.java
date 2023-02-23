package com.lambferret.game.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.util.AssetPath;
import com.lambferret.game.util.CustomInputProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HorizontalScroll {
    private static final Logger logger = LogManager.getLogger(HorizontalScroll.class.getName());
    private Texture pointerTexture;
    private Texture barTexture;
    private Hitbox scrollPointer;
    private Hitbox plate;
    private float X;
    private float POINTER_X;
    private float Y;
    private float WIDTH;
    private final float POINTER_WIDTH = 100.0F;
    private final float HEIGHT = 50.0F;

    public HorizontalScroll() {

    }

    public void create(Hitbox plate) {
        this.plate = plate;
        pointerTexture = AssetPath.getTexture("scrollPointer");
        barTexture = AssetPath.getTexture("scrollBar");
        X = plate.getX();
        Y = plate.getY();
        POINTER_X = X;
        WIDTH = plate.getWidth();
        scrollPointer = new Hitbox(X, Y, POINTER_WIDTH, HEIGHT);

    }

    public void render(SpriteBatch batch) {
        batch.draw(barTexture, X, Y, WIDTH, HEIGHT);
        batch.draw(pointerTexture, POINTER_X, Y, POINTER_WIDTH, HEIGHT);
        scrollPointer.render(batch);
    }

    public void update(float delta) {
        scrollPointer.update(delta);
        POINTER_X += CustomInputProcessor.getScrolledAmount() * 10;
        if (POINTER_X < X) {
            POINTER_X = 0;
        }
        if (POINTER_X > WIDTH-POINTER_WIDTH) {
            POINTER_X = WIDTH-POINTER_WIDTH;
        }

    }

}
