package com.lambferret.game.level.rural;

import com.lambferret.game.constant.Region;
import com.lambferret.game.level.Level;

public class LevelR3 extends Level {

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
    private static final int assignedSnow = 200;
    public static final int MAX_SOLDIER_CAPACITY = 3;

    @Override
    protected int[][] originAmountInMap() {
        return new int[][]{
            {0, 0, 5, 10},
            {0, 0, 5, 5},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
        };
    }

    public LevelR3() {
        super(Region.RURAL, map, maxAmountMap, minSnowForClear, assignedSnow, MAX_SOLDIER_CAPACITY);
    }

}
