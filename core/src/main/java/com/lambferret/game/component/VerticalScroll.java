package com.lambferret.game.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.util.AssetPath;
import com.lambferret.game.util.CustomInputProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VerticalScroll {
    private static final Logger logger = LogManager.getLogger(HorizontalScroll.class.getName());
    private Texture pointerTexture;
    private Texture barTexture;
    private Hitbox scrollPointer;
    private Hitbox plate;
    private float X;
    private float Y;
    private final float WIDTH = 50.0F;
    private float HEIGHT;
    private float POINTER_Y;
    private float POINTER_HEIGHT;
    private final Direction direction;

    public VerticalScroll(Direction direction) {
        if (!(direction == Direction.LEFT || direction == Direction.RIGHT)) {
            throw new IllegalStateException("Scroll direction isn't valid");
        }
        this.direction = direction;

    }

    public void create(Hitbox plate) {
        this.plate = plate;
        pointerTexture = AssetPath.getTexture("scrollPointer_V");
        barTexture = AssetPath.getTexture("scrollBar_V");

        if (direction == Direction.RIGHT) {
            X = plate.getX() + plate.getWidth() - WIDTH;
        } else if (direction == Direction.LEFT) {
            X = plate.getX();
        }
        Y = plate.getY();
        HEIGHT = plate.getHeight();
        POINTER_HEIGHT = HEIGHT / 10.0F;
        POINTER_Y = Y;

        scrollPointer = new Hitbox(X, Y, WIDTH, POINTER_HEIGHT);

    }

    public void render(SpriteBatch batch) {
        batch.draw(barTexture, X, Y, WIDTH, HEIGHT);
        batch.draw(pointerTexture, X, POINTER_Y, WIDTH, POINTER_HEIGHT);
    }

    public void update(float delta) {
        scrollPointer.update(delta);
        POINTER_Y -= CustomInputProcessor.getScrolledAmount() * 10;
        if (POINTER_Y < Y) {
            POINTER_Y = Y;
        }
        if (POINTER_Y > Y + HEIGHT - POINTER_HEIGHT) {
            logger.info("update |  🐳 weofij | " + POINTER_Y);
            logger.info("update |  🐳 weofij | " + POINTER_HEIGHT);
            POINTER_Y = Y + HEIGHT - POINTER_HEIGHT;
        }

    }


}
