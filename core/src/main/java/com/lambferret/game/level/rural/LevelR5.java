package com.lambferret.game.level.rural;

import com.lambferret.game.constant.Region;
import com.lambferret.game.level.Level;

public class LevelR5 extends Level {

    public static final float SCALED_MULTIPLY = 0.3F;
    private static final short[][] map = {
        {1, 1, 5, 5, 5, 0},
        {1, 1, 5, 5, 5, 0},
        {1, 5, 5, 5, 5, 0},
        {1, 5, 5, 5, 5, 0},
        {1, 5, 5, 0, 0, 0},
    };
    private static final int[][] maxAmountMap = {
        {10, 20, 10, 10, 0, 10},
        {10, 20, 10, 0, 0, 0},
        {30, 20, 10, 0, 0, 0},
        {30, 20, 10, 0, 0, 0},
        {30, 20, 10, 0, 0, 0},
    };
    private static final int minSnowForClear = 250;
    private static final int assignedSnow = 500;
    public static final int MAX_SOLDIER_CAPACITY = 5;

    @Override
    protected int[][] originAmountInMap() {
        return null;
    }

    public LevelR5() {
        super(Region.RURAL, map, maxAmountMap, minSnowForClear, assignedSnow, MAX_SOLDIER_CAPACITY);
    }

}
