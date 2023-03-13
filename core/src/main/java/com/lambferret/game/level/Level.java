package com.lambferret.game.level;

import com.lambferret.game.component.constant.Region;
import com.lambferret.game.component.constant.Terrain;

import java.util.Map;

public class Level {
    /**
     * 전체 지도의 좌표
     */
    protected short[][] map;
    /**
     * 지도에서 최대 쌓을 수 있는 눈의 양
     */
    protected int[][] maxAmountMap;
    /**
     * 지역
     */
    protected Region region;
    /**
     * 제설이 쉬워지거나 어려워지는 지형과 그 배수
     */
    protected Map<Terrain, Float> scaleByMapFeature;

    public Level(Region region) {
        this.region = region;
    }


}
