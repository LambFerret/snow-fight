package com.lambferret.game.level.rural;

import com.lambferret.game.constant.Region;
import com.lambferret.game.level.Level;

public class LevelR2 extends Level {

    public static final float SCALED_MULTIPLY = 0.3F;
    private static final short[][] map = {
        {1, 1, 0, 0},
        {1, 1, 0, 0},
        {0, 0, 0, 0},
        {0, 0, 0, 0},
    };
    private static final int[][] maxAmountMap = {
        {40, 50, 10, 10},
        {50, 40, 10, 10},
        {10, 10, 10, 10},
        {10, 10, 10, 10},
    };
    private static final int minSnowForClear = 100;
    private static final int assignedSnow = 300;
    public static final int MAX_SOLDIER_CAPACITY = 2;

    @Override
    protected int[][] originAmountInMap() {
        return null;
    }

    public LevelR2() {
        super(Region.RURAL, map, maxAmountMap, minSnowForClear, assignedSnow, MAX_SOLDIER_CAPACITY);
    }

}
