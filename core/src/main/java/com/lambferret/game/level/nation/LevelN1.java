package com.lambferret.game.level.nation;

import com.lambferret.game.component.constant.Region;
import com.lambferret.game.component.constant.Terrain;
import com.lambferret.game.level.Level;

public class LevelN1 extends Level {

    public static final float SCALED_MULTIPLY = 0.3F;
    private final short[][] currentMap = {
        {1, 2, 1, 1, 0},
        {1, 2, 1, 0, 0},
        {3, 2, 1, 0, 0},
        {3, 2, 1, 0, 0},
        {3, 2, 1, 0, 0},
    };
    private final int[][] maxAmount = {
        {10, 20, 10, 10, -1},
        {10, 20, 10, -1, -1},
        {30, 20, 10, -1, -1},
        {30, 20, 10, -1, -1},
        {30, 20, 10, -1, -1},
    };
    private final int ROWS = currentMap.length;
    private final int COLUMNS = currentMap[0].length;

    private final int[][] currentAmount = new int[ROWS][COLUMNS];



    public LevelN1() {
        super(Region.RURAL, Terrain.LAKE, SCALED_MULTIPLY);
        this.setMap(currentMap, maxAmount);
    }

}
