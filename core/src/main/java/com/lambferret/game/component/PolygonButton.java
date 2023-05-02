package com.lambferret.game.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PolygonButton extends CustomButton {
    private static final Logger logger = LogManager.getLogger(PolygonButton.class.getName());
    Polygon polygon;

    public PolygonButton(String text, ImageTextButtonStyle style, float[] vertices) {
        super(text, style);
        polygon = new Polygon(vertices);
        polygon.setOrigin(0, 0);
        setBounds(polygon.getBoundingRectangle().x, polygon.getBoundingRectangle().y,
            polygon.getBoundingRectangle().width, polygon.getBoundingRectangle().height);

        this.setDebug(true, true);

    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        polygon.setPosition(x, y);
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        polygon.setPosition(x, polygon.getY());
    }

    @Override
    public void moveBy(float x, float y) {
        polygon.setPosition(polygon.getX() + x, polygon.getY() + y);
        super.moveBy(x, y);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        if (polygon == null) return;
        polygon.setScale(
            width / polygon.getBoundingRectangle().width,
            height / polygon.getBoundingRectangle().height
        );
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        if (touchable && getTouchable() != Touchable.enabled) return null;
        var stageCoords = localToStageCoordinates(new Vector2(x, y));
        if (polygon.contains(stageCoords.x, stageCoords.y)) {
            return this;
        }
        return null;
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        shapes.set(ShapeRenderer.ShapeType.Line);
        shapes.setColor(Color.RED);
        shapes.polygon(polygon.getTransformedVertices());
    }

}
