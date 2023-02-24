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
    private float Y;
    private float WIDTH;
    private final float HEIGHT = 25.0F;
    private float POINTER_X;
    private float POINTER_WIDTH;
    private Direction direction;
    private boolean isVisible;

    public HorizontalScroll(Direction direction) {
        if (!(direction == Direction.UP || direction == Direction.DOWN)) {
            throw new IllegalStateException("Scroll direction isn't valid");
        }
        this.direction = direction;


    }

    public void create(Hitbox plate) {
        this.plate = plate;
        pointerTexture = AssetPath.getTexture("scrollPointer_H");
        barTexture = AssetPath.getTexture("scrollBar_H");
        if (direction == Direction.UP) {
            Y = plate.getY() + plate.getHeight() - HEIGHT;
        } else if (direction == Direction.DOWN) {
            Y = plate.getY();
        }
        X = plate.getX();
        WIDTH = plate.getWidth();
        POINTER_WIDTH = WIDTH / 10.0F;
        POINTER_X = X;
        scrollPointer = new Hitbox(X, Y, POINTER_WIDTH, HEIGHT);

    }

    public void render(SpriteBatch batch) {
        batch.draw(barTexture, X, Y, WIDTH, HEIGHT);
        batch.draw(pointerTexture, POINTER_X, Y, POINTER_WIDTH, HEIGHT);
    }

    public void update(float delta) {
        scrollPointer.update(delta);

        POINTER_X += CustomInputProcessor.getScrolledAmount() * 10;

        if (POINTER_X < X) {
            POINTER_X = X;
        }
        if (POINTER_X > X + WIDTH - POINTER_WIDTH) {
            POINTER_X = X + WIDTH - POINTER_WIDTH;
        }

    }

}
