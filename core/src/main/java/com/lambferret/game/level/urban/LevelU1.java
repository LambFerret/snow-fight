package com.lambferret.game.level.urban;

import com.lambferret.game.component.constant.Region;
import com.lambferret.game.component.constant.Terrain;
import com.lambferret.game.level.Level;

public class LevelU1 extends Level {

    public static final float SCALED_MULTIPLY = 0.30303F;
    private final short[][] currentMap = {
        {1, 2, 1, 1, 0,2},
        {1, 2, 1, 0, 0,2},
        {3, 2, 1, 0, 0,2},
        {3, 2, 1, 0, 0,2},
        {3, 2, 1, 0, 0,2},
    };
    private final int[][] maxAmount = {
        {10, 20, 10, 10, -1, 20},
        {10, 20, 10, -1, -1, 20},
        {30, 20, 10, -1, -1, 20},
        {30, 20, 10, -1, -1, 20},
        {30, 20, 10, -1, -1, 20},
    };
    private final int ROWS = currentMap.length;
    private final int COLUMNS = currentMap[0].length;

    private final int[][] currentAmount = new int[ROWS][COLUMNS];



    public LevelU1() {
        super(Region.RURAL, Terrain.LAKE, SCALED_MULTIPLY);
        this.setMap(currentMap, maxAmount);
    }

}
