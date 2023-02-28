package com.lambferret.game.level;

import com.lambferret.game.component.constant.Region;
import com.lambferret.game.component.constant.Terrain;

public class Level {
    private short[][] map;
    private int[][] maxAmount;
    private Region region;
    private Terrain scaledTerrain;
    private float scaledMultiply;

    public Level(Region region, Terrain scaledTerrain, float scaledMultiply) {
        this.region = region;
        this.scaledMultiply = scaledMultiply;
        this.scaledTerrain = scaledTerrain;

    }

    public void setMap(short[][] map, int[][] maxAmount) {
        this.map = map;
        this.maxAmount = maxAmount;
    }

    public float getScaledMultiply() {
        return scaledMultiply;
    }
}
