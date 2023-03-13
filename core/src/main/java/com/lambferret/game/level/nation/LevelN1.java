package com.lambferret.game.level.nation;

import com.lambferret.game.component.constant.Region;
import com.lambferret.game.level.Level;

public class LevelN1 extends Level {

    public static final float SCALED_MULTIPLY = 0.3F;
    private final short[][] currentMap = {
        {1, 2, 1, 1, -1},
        {1, 2, 1, -1, -1},
        {3, 2, 1, -1, -1},
        {3, 2, 1, -1, -1},
        {3, 2, 1, -1, -1},
    };
    private final int[][] maxAmount = {
        {10, 20, 10, 10, 0},
        {10, 20, 10, 0, 0},
        {30, 20, 10, 0, 0},
        {30, 20, 10, 0, 0},
        {30, 20, 10, 0, 0},
    };
    private final int ROWS = currentMap.length;
    private final int COLUMNS = currentMap[0].length;

    private final int[][] currentAmount = new int[ROWS][COLUMNS];


    public LevelN1() {
        super(Region.RURAL);
        this.map = currentMap;
        this.maxAmountMap = maxAmount;
    }

}
