package com.lambferret.game.level.nation;

import com.lambferret.game.component.constant.Region;
import com.lambferret.game.level.Level;

public class LevelN1 extends Level {

    public static final float SCALED_MULTIPLY = 0.3F;
    private static final short[][] map = {
        {1, 2, 1, 1, -1},
        {1, 2, 1, -1, -1},
        {3, 2, 1, -1, -1},
        {3, 2, 1, -1, -1},
        {3, 2, 1, -1, -1},
    };
    private static final int[][] maxAmountMap = {
        {10, 20, 10, 10, 0},
        {10, 20, 10, 0, 0},
        {30, 20, 10, 0, 0},
        {30, 20, 10, 0, 0},
        {30, 20, 10, 0, 0},
    };
    private static final int snowMin = 400;
    private static final int snowMax = 400;

    public LevelN1() {
        super(Region.RURAL, map, maxAmountMap, snowMin, snowMax);
    }

}
