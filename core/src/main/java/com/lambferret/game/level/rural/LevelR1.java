package com.lambferret.game.level.rural;

import com.lambferret.game.constant.Region;
import com.lambferret.game.level.Level;

public class LevelR1 extends Level {

    public static final float SCALED_MULTIPLY = 0.3F;
    private static final short[][] map = {
        {0, 0, 0},
        {0, 0, 0},
        {0, 0, 0},
    };
    private static final int[][] maxAmountMap = {
        {10, 10, 20},
        {10, 10, 20},
        {10, 10, 20},
    };
    private static final int minSnowForClear = 50;
    private static final int assignedSnow = 100;
    public static final int MAX_SOLDIER_CAPACITY = 2;

    @Override
    protected int[][] originAmountInMap() {
        return null;
    }

    public LevelR1() {
        super(Region.RURAL, map, maxAmountMap, minSnowForClear, assignedSnow, MAX_SOLDIER_CAPACITY);
    }

}
