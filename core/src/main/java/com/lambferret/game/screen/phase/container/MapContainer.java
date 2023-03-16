package com.lambferret.game.screen.phase.container;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.component.constant.Terrain;
import com.lambferret.game.level.Level;
import com.lambferret.game.screen.ui.AbstractOverlay;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.CustomInputProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MapContainer {
    private static final Logger logger = LogManager.getLogger(MapContainer.class.getName());

    float xOffset;
    float yOffset;
    private float boxHeight;
    private float boxWidth;
    private final int COLUMNS;
    private final int ROWS;
    private final short[][] map;
    private BitmapFont font = new BitmapFont();
    private Level level;

    private final List<Hitbox> hitboxList = new ArrayList<>();
    Matrix4 rotationMatrix;
    ShapeRenderer shapeRenderer;
    public MapContainer(Level currentLevel) {
        rotationMatrix = new Matrix4();
        shapeRenderer = new ShapeRenderer();
        this.boxHeight = 50.0F;
        this.boxWidth = 50.0F;
        this.level = currentLevel;
        this.COLUMNS = level.COLUMNS;
        this.ROWS = level.ROWS;
        this.map = level.getMap();
//        this.xOffset = (GlobalSettings.currHeight - ;
//        this.yOffset = 300.0F;
        setOffset();
        drawMap();
    }

    private void setOffset() {
        float planeWidth = GlobalSettings.currWidth - AbstractOverlay.OVERLAY_WIDTH;
        float planeHeight = GlobalSettings.currHeight - AbstractOverlay.OVERLAY_HEIGHT - AbstractOverlay.BAR_HEIGHT;
        float rectangleWidth = COLUMNS * boxWidth;
        float rectangleHeight = ROWS * boxHeight;

// Step 1: Check if the rectangle is larger than the plane
        if (rectangleWidth > planeWidth || rectangleHeight > planeHeight) {
            // Step 2: Calculate scaling factors and choose the smallest one
            float widthScaleFactor = planeWidth / rectangleWidth;
            float heightScaleFactor = planeHeight / rectangleHeight;
            float scaleFactor = Math.min(widthScaleFactor, heightScaleFactor);

            // Step 3: Scale the rectangle using the chosen scaling factor
            rectangleWidth *= widthScaleFactor;
            rectangleHeight *= heightScaleFactor;
            boxWidth *= widthScaleFactor;
            boxHeight *= heightScaleFactor;
        }

// Step 4: Calculate the top-left corner coordinates (x, y)
        float x = (planeWidth - rectangleWidth) / 2;
        float y = (planeHeight - rectangleHeight) / 2;

        System.out.println("Top-left corner coordinates: (" + planeHeight + ", " + y + ")");
        System.out.println("Scaled rectangle dimensions: (" + rectangleWidth + ", " + rectangleHeight + ")");

        this.xOffset = x;
        this.yOffset = AbstractOverlay.OVERLAY_HEIGHT;

    }

    private void drawMap() {
        for (int i = ROWS; i > 0; i--) {
            for (int j = 0; j < COLUMNS; j++) {
                Hitbox box = new Hitbox();
                box.resize(boxWidth, boxHeight);
                box.move(xOffset + boxWidth * j, yOffset + boxHeight * i);
                hitboxList.add(box);
            }
        }
    }

    public void updateMap() {
        int index = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                hitboxList.get(index++).update();
            }
        }
    }

    private void renderHover(int i, int j) {
        float x = CustomInputProcessor.getMouseLocationX();
        float y = CustomInputProcessor.getMouseLocationY();
        String informationString = level.getCurrentAmount()[i][j] + " / " + level.getMaxAmountMap()[i][j];
//        font.draw(informationString, x + 5.0F, y + 20.0F);
    }

    public void renderMap() {
        int index = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Color color = switch (map[i][j]) {
                    case Terrain.NULL -> Color.BLACK;
                    case Terrain.LAKE -> Color.ORANGE;
                    case Terrain.MOUNTAIN -> Color.YELLOW;
                    case Terrain.SEA -> Color.GREEN;
                    case Terrain.TOWN -> Color.CHARTREUSE;
                    default -> Color.VIOLET;
                };
                Hitbox box = hitboxList.get(index++);

                box.setColor(color);
                box.render();

            }
        }
        index = 0;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (hitboxList.get(index++).isHovered) {
//                    renderHover(batch, i, j);
                }
            }
        }

    }
}
