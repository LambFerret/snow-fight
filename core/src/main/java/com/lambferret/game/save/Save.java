package com.lambferret.game.save;

import com.lambferret.game.player.Player;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
public class Save {
    private String name;
    private long time;
    private boolean isInitialized;
    private List<Item> allItems;
    private int day;
    private int money;
    private Map<Player.Affinity, Integer> affinity;
    private int maxCost;
    private int currentCost;
    private int difficulty;
    private int snowAmount;
    private int downAffinity;
    private int middleAffinity;
    private int upperAffinity;
    private int maxManualCapacity;
    private int maxCommandInHand;
    private List<String> eventList;
    private List<Item> shopList;
}
