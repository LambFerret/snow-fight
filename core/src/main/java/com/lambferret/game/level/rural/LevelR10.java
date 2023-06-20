package com.lambferret.game.level.rural;

import com.lambferret.game.constant.Region;
import com.lambferret.game.level.Level;

public class LevelR10 extends Level {

    public static final float SCALED_MULTIPLY = 0.3F;
    private static final short[][] map = {
        {1, 2, 1, 5, 5, 1},
        {1, 2, 1, 5, 5, 5},
        {3, 2, 1, 5, 5, 5},
        {3, 2, 1, 5, 5, 5},
        {3, 2, 1, 0, 5, 5},
        {1, 2, 1, 1, 5, 1},
    };
    private static final int[][] maxAmountMap = {
        {60, 100, 60, 50, 50, 60},
        {60, 100, 60, 50, 50, 50},
        {30, 100, 60, 50, 50, 50},
        {30, 100, 60, 50, 50, 50},
        {30, 100, 60, 0, 50, 50},
        {60, 100, 60, 60, 50, 60},
    };
    private static final int minSnowForClear = 1500;
    private static final int assignedSnow = 2000;
    public static final int MAX_SOLDIER_CAPACITY = 5;

    @Override
    protected int[][] originAmountInMap() {
        return null;
    }

    public LevelR10() {
        super(Region.RURAL, map, maxAmountMap, minSnowForClear, assignedSnow, MAX_SOLDIER_CAPACITY);
    }

}
