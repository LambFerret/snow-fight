package com.lambferret.game.level;

import com.lambferret.game.constant.Region;
import com.lambferret.game.constant.Terrain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * 맵에 대한 정보만 저장할 것!
 */
public class Level {
    private static final Logger logger = LogManager.getLogger(Level.class.getName());

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
    private int assignedSnow;
    /**
     * 클리어 최소량
     */
    private int minSnowForClear;
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
    /**
     * 페이즈 최대 반복 횟수
     */
    private short maxIteration;
    /**
     * 현재 페이즈
     */
    private short currentIteration = 0;
    /**
     * 작업할 수 있는 최대 군인 수
     */
    private int maxSoldierCapacity;

    public Level(Region region, short[][] map, int[][] maxAmountMap, int minSnowForClear, int assignedSnow, int maxSoldierCapacity) {
        checkMap(map, maxAmountMap);
        this.region = region;
        this.map = map;
        this.maxAmountMap = maxAmountMap;
        this.minSnowForClear = minSnowForClear;
        this.assignedSnow = assignedSnow;
        this.ROWS = map.length;
        this.COLUMNS = map[0].length;
        this.currentAmount = new int[ROWS][COLUMNS];
        this.maxIteration = setMaxIteration(region);
        this.maxSoldierCapacity = maxSoldierCapacity;
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
                    break;
                }
            }
        }
        if (isError) {
            throw new RuntimeException("this Map " + getClass().getSimpleName() + " is INCORRECT");
        }
    }

    public void createTiledMap() {

//        for (int i = 0; i < ROWS; i++) {
//            for (int j = 0; j < COLUMNS; j++) {
//                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
//                var tile = LevelFinder.tiledMapTileSet.getTile(map[i][j]);
//                MapProperties properties = tile.getProperties();
//                try {
//                    properties.put(TileType.MAX_AMOUNT.toString(), maxAmountMap[i][j]);
//                    properties.put(TileType.CURRENT_AMOUNT.toString(), currentAmount[i][j]);
//                    properties.put(TileType.WORKER_LIST.toString(), new ArrayList<>());
//                } catch (Exception e) {
//                    throw new RuntimeException("this Map " + getClass().getSimpleName() + " has INCORRECT tile in " + i + ", " + j);
//                }
//                cell.setTile(tile);
//            }
//        }
    }

    public short setMaxIteration(Region region) {
        return switch (region) {
            case NATION -> (short) 2;
            case RURAL -> (short) 3;
            case URBAN -> (short) 4;
        };
    }

    public void initCurrentIteration() {
        this.currentIteration = 0;
    }

    public void toNextIteration() {
        ++this.currentIteration;
    }

    public int getSnowAmountInMap() {
        int result = 0;
        for (int[] ints : currentAmount) {
            for (int anInt : ints) {
                result += anInt;
            }
        }
        return result;
    }

    public int[] getTerrainMaxCurrentInfo(int i, int j) {
        return new int[]{map[i][j], maxAmountMap[i][j], currentAmount[i][j]};
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

    public int getAssignedSnow() {
        return assignedSnow;
    }

    public void setAssignedSnow(int assignedSnow) {
        this.assignedSnow = assignedSnow;
    }

    public int getMinSnowForClear() {
        return minSnowForClear;
    }

    public void setMinSnowForClear(int minSnowForClear) {
        this.minSnowForClear = minSnowForClear;
    }

    public int[][] getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int[][] currentAmount) {
        this.currentAmount = currentAmount;
    }

    public short getMaxIteration() {
        return maxIteration;
    }

    public void setMaxIteration(short maxIteration) {
        this.maxIteration = maxIteration;
    }

    public short getCurrentIteration() {
        return currentIteration;
    }

    public int getMaxSoldierCapacity() {
        return maxSoldierCapacity;
    }

    public void setMaxSoldierCapacity(int maxSoldierCapacity) {
        this.maxSoldierCapacity = maxSoldierCapacity;
    }

}
