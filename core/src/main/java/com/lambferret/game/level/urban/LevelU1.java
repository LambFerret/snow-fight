package com.lambferret.game.level.urban;

import com.lambferret.game.constant.Region;
import com.lambferret.game.level.Level;

public class LevelU1 extends Level {

    public static final float SCALED_MULTIPLY = 0.3F;
    private static final short[][] map = {
        {1, 2, 1, 1, 0, 1},
        {1, 2, 1, 0, 0, 0},
        {3, 2, 1, 0, 0, 0},
        {3, 2, 1, 0, 0, 0},
        {3, 2, 1, 0, 0, 0},
        {1, 2, 1, 1, 0, 1},
    };
    private static final int[][] maxAmountMap = {
        {10, 20, 10, 10, 0, 10},
        {10, 20, 10, 0, 0, 0},
        {30, 20, 10, 0, 0, 0},
        {30, 20, 10, 0, 0, 0},
        {30, 20, 10, 0, 0, 0},
        {10, 20, 10, 10, 0, 10},
    };
    private static final int minSnowForClear = 250;
    private static final int assignedSnow = 500;
    public static final int MAX_SOLDIER_CAPACITY = 5;

    @Override
    protected int[][] originAmountInMap() {
        return null;
    }

    public LevelU1() {
        super(Region.RURAL, map, maxAmountMap, minSnowForClear, assignedSnow, MAX_SOLDIER_CAPACITY);
    }

}
