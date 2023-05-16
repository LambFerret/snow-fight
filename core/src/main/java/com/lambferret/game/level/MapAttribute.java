package com.lambferret.game.level;

import com.lambferret.game.constant.Terrain;
import com.lambferret.game.soldier.Soldier;
import lombok.Builder;
import lombok.ToString;

import java.util.List;

@ToString
@Builder
public class MapAttribute {
    private Terrain terrain;
    private int maxAmount;
    private int currentAmount;
    private List<Soldier> currentlyWorkingList;

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public List<Soldier> getCurrentlyWorkingList() {
        return currentlyWorkingList;
    }

    public void setCurrentlyWorkingList(List<Soldier> currentlyWorkingList) {
        this.currentlyWorkingList = currentlyWorkingList;
    }
}
