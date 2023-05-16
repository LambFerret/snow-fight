package com.lambferret.game.level;

import com.lambferret.game.constant.Region;
import com.lambferret.game.constant.Terrain;
import com.lambferret.game.soldier.Soldier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 맵에 대한 정보만 저장할 것!
 */
public class Level {
    private static final Logger logger = LogManager.getLogger(Level.class.getName());

    /**
     * 전체 지도의 좌표
     */
    private final short[][] mapTerrain;
    /**
     * 지도에서 최대 쌓을 수 있는 눈의 양
     */
    private final int[][] mapMaxAmount;
    private final MapAttribute[][] MAP;
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

    public Level(Region region, short[][] mapTerrain, int[][] maxAmountMap, int minSnowForClear, int assignedSnow, int maxSoldierCapacity) {
        initMap();
        this.region = region;
        this.mapTerrain = mapTerrain;
        this.mapMaxAmount = maxAmountMap;
        this.minSnowForClear = minSnowForClear;
        this.assignedSnow = assignedSnow;
        this.ROWS = mapTerrain.length;
        this.COLUMNS = mapTerrain[0].length;
        this.currentAmount = new int[ROWS][COLUMNS];
        this.maxIteration = setMaxIteration(region);
        this.maxSoldierCapacity = maxSoldierCapacity;
        this.MAP = new MapAttribute[ROWS][COLUMNS];
    }

    // TODO i , j order
    private void initMap() {
        try {
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    MAP[i][j] = MapAttribute.builder()
                        .terrain(Terrain.values()[mapTerrain[i][j]])
                        .maxAmount(mapMaxAmount[i][j])
                        .currentAmount(currentAmount[i][j])
                        .currentlyWorkingList(new ArrayList<>())
                        .build();
                }
            }
        } catch (IndexOutOfBoundsException e) {
            logger.fatal("this map has malfunction : " + getClass().getSimpleName());
            throw new RuntimeException();
        }
    }

    private void modifyMapTerrain(int i, int j, Terrain terrain) {
        MAP[i][j].setTerrain(terrain);
    }

    private void modifyMapMaxAmount(int i, int j, int amount) {
        MAP[i][j].setCurrentAmount(amount);
    }

    private void modifyMapCurrentAmount(int i, int j, int amount) {
        MAP[i][j].setCurrentAmount(amount);
    }

    private void modifyMapCurrentAmountBy(int i, int j, int amount) {
        MAP[i][j].setCurrentAmount(MAP[i][j].getCurrentAmount() + amount);
    }

    private void addWorker(int i, int j, Soldier soldier) {
        MAP[i][j].getCurrentlyWorkingList().add(soldier);
    }

    private List<Soldier> getWorker(int i, int j) {
        return MAP[i][j].getCurrentlyWorkingList();
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
        return new int[]{mapTerrain[i][j], mapMaxAmount[i][j], currentAmount[i][j]};
    }

    public short[][] getMapTerrain() {
        return mapTerrain;
    }

    public int[][] getMaxAmountMap() {
        return mapMaxAmount;
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

