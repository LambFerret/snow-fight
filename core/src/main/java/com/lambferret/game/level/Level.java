package com.lambferret.game.level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.constant.Region;
import com.lambferret.game.constant.Terrain;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.util.GlobalUtil;
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

        try {
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    MAP[i][j] = MapAttribute.builder()
                        .terrain(Terrain.values()[mapTerrain[i][j]])
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
        int size = isPre ? 40 : 100;
        Table map = new Table();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                map.add(makeMapElement(i, j)).width(size).height(size);
            }
            map.row();
        }
        map.setDebug(true, true);
        return map;
    }

    private CustomButton makeMapElement(int i, int j) {
        var attr = MAP[i][j];
        CustomButton element = GlobalUtil.simpleButton("wh12ite");
        float transparency = attr.getCurrentAmount() / (float) attr.getMaxAmount();
        Color color = switch (attr.getTerrain()) {
            case NULL -> Color.BLACK;
            case SEA -> Color.BLUE;
            case LAKE -> Color.YELLOW;
            case TOWN -> Color.GREEN;
            case MOUNTAIN -> Color.RED;
        };
        element.setColor(color);
        if (attr.getTerrain() != Terrain.NULL) {
            element.addAction(Actions.alpha(0));
            element.addAction(Actions.alpha(transparency, 0.5F));
        }
        element.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) {
                    element.setText("Terrain : " + attr.getTerrain() + "\n" + "Amount : " + attr.getCurrentAmount() + " / " + attr.getMaxAmount());
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    element.setText("");
                }
            }
        });
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

