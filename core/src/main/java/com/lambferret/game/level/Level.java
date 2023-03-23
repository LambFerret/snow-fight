package com.lambferret.game.level;

import com.lambferret.game.constant.Region;
import com.lambferret.game.constant.Terrain;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 맵에 대한 정보만 저장할 것!
 */
@Setter
@Getter
public class Level {
    /**
     * 전체 지도의 좌표
     */
    private final short[][] map;
    /**
     * 지도에서 최대 쌓을 수 있는 눈의 양
     */
    private final int[][] maxAmountMap;
    /**
     * 지역
     */
    private final Region region;
    /**
     * 제설이 쉬워지거나 어려워지는 지형과 그 배수
     */
    private Map<Terrain, Float> scaleByMapFeature;
    /**
     * 할당된 제설량
     */
    private int snowMax;
    /**
     * 클리어 최소량
     */
    private int snowMin;
    /**
     * 행 (가로)
     */
    public final int ROWS;
    /**
     * 열 (세로)
     */
    public final int COLUMNS;
    /**
     * 현재 지도에 적설된 량
     */
    private int[][] currentAmount;
    private short maxIteration;
    private short currentIteration = 0;

    public Level(Region region, short[][] map, int[][] maxAmountMap, int snowMin, int snowMax) {
        checkMap(map, maxAmountMap);
        this.region = region;
        this.map = map;
        this.maxAmountMap = maxAmountMap;
        this.snowMin = snowMin;
        this.snowMax = snowMax;
        this.ROWS = map.length;
        this.COLUMNS = map[0].length;
        this.currentAmount = new int[ROWS][COLUMNS];
        this.maxIteration = setMaxIteration(region);
    }

    private void checkMap(short[][] map, int[][] maxAmountMap) {
        // 유효성 체크
        boolean isError = false;
        if (map.length != maxAmountMap.length) {
            isError = true;
        } else {
            for (int i = 0; i < map.length; i++) {
                if (map[i].length != maxAmountMap[i].length) {
                    isError = true;
                }
            }
        }
        if (isError) {
            throw new RuntimeException("this Map " + getClass().getSimpleName() + " is INCORRECT");
        }
    }

    public short setMaxIteration(Region region) {
        return switch (region) {
            case NATION -> (short) 3;
            case RURAL -> (short) 4;
            case URBAN -> (short) 5;
        };
    }

    public short[][] getMap() {
        return map;
    }

    public int[][] getMaxAmountMap() {
        return maxAmountMap;
    }

    public Region getRegion() {
        return region;
    }

    public int[][] getCurrentAmount() {
        return currentAmount;
    }

    public void initCurrentIteration() {
        this.currentIteration = 0;
    }

    public void toNextIteration() {
        ++this.currentIteration;
    }

    public int getSnowAmountInMap() {
        int result = 0;
        for (int i = 0; i < currentAmount.length; i++) {
            for (int j = 0; j < currentAmount[i].length; j++) {
                result += currentAmount[i][j];
            }
        }
        return result;
    }

}
