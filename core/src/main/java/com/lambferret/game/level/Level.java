package com.lambferret.game.level;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.constant.Region;
import com.lambferret.game.constant.Terrain;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;
import com.lambferret.game.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 맵에 대한 정보만 저장할 것!
 */
public abstract class Level {
    private static final Logger logger = LogManager.getLogger(Level.class.getName());
    private static final TextureAtlas tileAtlas = AssetFinder.getAtlas("tile");
    public static final int LEVEL_EACH_SIZE_SMALL = 40;
    public static final int LEVEL_EACH_SIZE_BIG = 100;
    public static final int MAP_ITERATION_CONSTANT = 1;
    Random random;

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
     * 현재 지도에 적설된 량 TODO : 이미 깔린 눈이 있을 경우 오버라이드해서 사용
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
        this.ROWS = mapTerrain.length;
        this.COLUMNS = mapTerrain[0].length;
        this.MAP = initMap(mapTerrain, maxAmountMap);
        this.region = region;
        this.minSnowForClear = minSnowForClear;
        this.assignedSnow = assignedSnow;
        this.currentAmount = new int[ROWS][COLUMNS];
        this.maxIteration = setMaxIteration(region);
        this.maxSoldierCapacity = maxSoldierCapacity;
    }

    // TODO i , j order
    private MapAttribute[][] initMap(short[][] mapTerrain, int[][] maxAmountMap) {
        MapAttribute[][] MAP = new MapAttribute[ROWS][COLUMNS];
        var a = originAmountInMap();
        var b = a == null;
        var c = new short[ROWS][COLUMNS];

        try {
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    // -=-=-=-=-=--=-=-=-
                    if (mapTerrain[i][j] == 0) {
                        int left = j - 1 >= 0 ? mapTerrain[i][j - 1] : 1;
                        int right = j + 1 < COLUMNS ? mapTerrain[i][j + 1] : 1;
                        int top = i - 1 >= 0 ? mapTerrain[i - 1][j] : 1;
                        int bottom = i + 1 < ROWS ? mapTerrain[i + 1][j] : 1;

                        if (top != 0 && bottom != 0 && left != 0 && right != 0) {
                            c[i][j] = 5;  // "#"
                        } else if (left != 0 && right == 0) {
                            c[i][j] = 6;  // "["
                        } else if (left == 0 && right != 0) {
                            c[i][j] = 7;  // "]"
                        } else if (top != 0 && bottom == 0) {
                            c[i][j] = 8;  // "ㅜ"
                        } else if (top == 0 && bottom != 0) {
                            c[i][j] = 9;  // "ㅗ"
                        } else if (top == 0 && left == 0) {
                            c[i][j] = 0;  // "*"
                        }
                    } else {
                        c[i][j] = mapTerrain[i][j];
                    }
                    // -=-=-=-=-=--=-=-=-

                    MAP[i][j] = MapAttribute.builder()
                        .terrain(setTerrain(c[i][j]))
                        .maxAmount(maxAmountMap[i][j])
                        .currentAmount(b ? 0 : a[i][j])
                        .currentlyWorkingList(new ArrayList<>())
                        .build();
                }
            }
        } catch (IndexOutOfBoundsException e) {
            logger.fatal("this map has malfunction : " + getClass().getSimpleName());
            throw new RuntimeException();
        }
        return MAP;
    }

    public Table makeTable(boolean isPre) {
        int size = isPre ? LEVEL_EACH_SIZE_SMALL : LEVEL_EACH_SIZE_BIG;
        Table map = new Table();
        random = new Random(PhaseScreen.mapRandomSeed);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                map.add(makeMapElement(i, j)).width(size).height(size);
            }
            map.row();
        }
        return map;
    }

    private CustomButton makeMapElement(int i, int j) {
        MapAttribute attr = MAP[i][j];
        CustomButton element;
        switch (attr.getTerrain()) {
            case SEA -> element = GlobalUtil.simpleButton("sea");
            case FOREST0 -> element = GlobalUtil.simpleButton(tileAtlas.findRegions("forest").get(0), "");
            case FOREST_LEFT -> element = GlobalUtil.simpleButton(tileAtlas.findRegions("forest").get(1), "");
            case FOREST_RIGHT -> element = GlobalUtil.simpleButton(tileAtlas.findRegions("forest").get(3), "");
            case FOREST_UP -> element = GlobalUtil.simpleButton(tileAtlas.findRegions("forest").get(2), "");
            case FOREST_DOWN -> element = GlobalUtil.simpleButton(tileAtlas.findRegions("forest").get(4), "");
            case FOREST_ALONE -> element = GlobalUtil.simpleButton(tileAtlas.findRegions("forest").get(5), "");
            default -> {
                var regions = tileAtlas.findRegions(attr.getTerrain().name().toLowerCase());
                if (regions.size == 0) {
                    element = GlobalUtil.simpleButton("");
                } else {
                    TextureAtlas.AtlasRegion region = regions.get(random.random(regions.size));
                    element = GlobalUtil.simpleButton(region);
                }
            }
        }

        String description = "Terrain : " + attr.getTerrain() + "\n" + "Amount : " + attr.getCurrentAmount() + " / " + attr.getMaxAmount();
        element.addListener(Input.hover(() -> element.setText(description), () -> element.setText("")));
        return element;
    }

    protected abstract int[][] originAmountInMap();

    public void modifyMapTerrain(int i, int j, Terrain terrain) {
        MAP[i][j].setTerrain(terrain);
    }

    public void modifyMapMaxAmount(int i, int j, int amount) {
        MAP[i][j].setCurrentAmount(amount);
    }

    public float getSnowRatio(int i, int j) {
        return MAP[i][j].getCurrentAmount() / (float) MAP[i][j].getMaxAmount();
    }

    public int getCurrentAmount(int i, int j) {
        return MAP[i][j].getCurrentAmount();
    }

    public int getMaxAmount(int i, int j) {
        return MAP[i][j].getMaxAmount();
    }

    public Terrain getTerrain(int i, int j) {
        return MAP[i][j].getTerrain();
    }

    public int modifyMapCurrentAmountBy(int i, int j, int amount) {
        int init = MAP[i][j].getCurrentAmount();
        MAP[i][j].setCurrentAmount(Math.min(MAP[i][j].getMaxAmount(), init + amount));
        return MAP[i][j].getCurrentAmount() - init;
    }

    public void modifyMapCurrentAmount(int i, int j, int amount) {
        MAP[i][j].setCurrentAmount(amount);
    }

    public void addWorker(int i, int j, Soldier soldier) {
        MAP[i][j].getCurrentlyWorkingList().add(soldier);
    }

    public List<Soldier> getWorker(int i, int j) {
        return MAP[i][j].getCurrentlyWorkingList();
    }

    public short setMaxIteration(Region region) {
        return (short) (region.ordinal() + MAP_ITERATION_CONSTANT);
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
        if (maxSoldierCapacity < 0) {
            maxSoldierCapacity = 0;
        }
        this.maxSoldierCapacity = maxSoldierCapacity;
    }


    private Terrain setTerrain(short i) {
        return switch (i) {
            case 0 -> Terrain.FOREST0;
            case 1 -> Terrain.SEA;
            case 2 -> Terrain.LAKE;
            case 3 -> Terrain.TOWN;
            case 4 -> Terrain.MOUNTAIN;
            case 5 -> Terrain.FOREST_ALONE;
            case 6 -> Terrain.FOREST_LEFT;
            case 7 -> Terrain.FOREST_RIGHT;
            case 8 -> Terrain.FOREST_UP;
            case 9 -> Terrain.FOREST_DOWN;
            default -> throw new IllegalStateException("Unexpected value: " + i);
        };
    }
}

