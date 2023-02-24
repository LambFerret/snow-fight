package com.lambferret.game.component;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.constant.Direction;
import com.lambferret.game.util.AssetPath;
import com.lambferret.game.util.CustomInputProcessor;
import com.lambferret.game.util.GlobalUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HorizontalScroll {
    private static final Logger logger = LogManager.getLogger(HorizontalScroll.class.getName());
    private Texture pointerTexture;
    private Texture barTexture;
    private Hitbox scrollPointer;
    private float X;
    private float Y;
    private float WIDTH;
    private final float HEIGHT = 25.0F;
    private float POINTER_X;
    private float POINTER_PREV_X;
    private float POINTER_WIDTH;
    private final Direction direction;
    private static final float SCROLL_SPEED = 10.0F;
    private float transparency = 0.0F;

    public HorizontalScroll(Direction direction, ScrollObserver observer) {
        if (!(direction == Direction.UP || direction == Direction.DOWN)) {
            throw new IllegalStateException("Scroll direction isn't valid");
        }
        this.direction = direction;
    }

    public void create(Hitbox plate) {
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
        POINTER_PREV_X = POINTER_X;
        scrollPointer = new Hitbox(X, Y, POINTER_WIDTH, HEIGHT);
    }

    public void render(SpriteBatch batch) {
        if (POINTER_PREV_X == POINTER_X) {
            transparency = GlobalUtil.smallLerp(transparency, 0, 0.3F);
        } else {
            transparency = GlobalUtil.smallLerp(transparency, 3, 30F);
            POINTER_X = POINTER_PREV_X;
        }
        if (scrollPointer.isHovered) {
            transparency = 1;
        }
        batch.setColor(1, 1, 1, transparency);

        batch.draw(barTexture, X, Y, WIDTH, HEIGHT);
        batch.draw(pointerTexture, POINTER_X, Y, POINTER_WIDTH, HEIGHT);
        scrollPointer.render(batch);
    }

    public void update(float delta) {
        POINTER_PREV_X += CustomInputProcessor.getScrolledAmount() * SCROLL_SPEED;

        if (POINTER_PREV_X < X) {
            POINTER_PREV_X = X;
        }
        if (POINTER_PREV_X > X + WIDTH - POINTER_WIDTH) {
            POINTER_PREV_X = X + WIDTH - POINTER_WIDTH;
        }
        if (POINTER_PREV_X != POINTER_X) scrollPointer.move(POINTER_PREV_X, Y);
        scrollPointer.update(delta);
    }
}
