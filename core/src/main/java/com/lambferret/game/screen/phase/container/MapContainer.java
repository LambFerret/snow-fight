package com.lambferret.game.screen.phase.container;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.component.constant.Terrain;
import com.lambferret.game.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MapContainer {
    private static final Logger logger = LogManager.getLogger(MapContainer.class.getName());

    float xOffset;
    float yOffset;
    float boxSize;
    private final int COLUMNS;
    private final int ROWS;
    private final short[][] map;

    private List<Hitbox> hitboxList = new ArrayList<>();

    public MapContainer(Level currentLevel) {
        this.xOffset = 300.0F;
        this.yOffset = 500.0F;
        this.boxSize = 50.0F;
        this.COLUMNS = currentLevel.COLUMNS;
        this.ROWS = currentLevel.ROWS;
        this.map = currentLevel.getMap();
        drawMap();
    }

    private void drawMap() {
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                Hitbox box = new Hitbox();
                box.resize(boxSize, boxSize);
                box.move(xOffset + boxSize * j, yOffset - boxSize * i);
                hitboxList.add(box);
            }
        }
    }

    public void updateMap(float delta) {
        int index = 0;
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                hitboxList.get(index++).update(delta);
            }
        }
    }

    public void renderMap(SpriteBatch batch) {
        int index = 0;
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                Color color = switch (map[i][j]) {
                    case Terrain.NULL -> Color.BLACK;
                    case Terrain.LAKE -> Color.ORANGE;
                    case Terrain.MOUNTAIN -> Color.YELLOW;
                    case Terrain.SEA -> Color.GREEN;
                    case Terrain.TOWN -> Color.CHARTREUSE;
                    default -> Color.VIOLET;
                };

                hitboxList.get(index).setColor(color);
                hitboxList.get(index++).render(batch);
            }
        }
    }
}
