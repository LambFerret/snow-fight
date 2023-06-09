package com.lambferret.game.player;

import com.lambferret.game.command.Command;
import com.lambferret.game.command.ThreeShift;
import com.lambferret.game.constant.MainEvent;
import com.lambferret.game.constant.StoryType;
import com.lambferret.game.manual.ColdWeatherTraining;
import com.lambferret.game.manual.Manual;
import com.lambferret.game.quest.Quest;
import com.lambferret.game.save.Item;
import com.lambferret.game.save.Save;
import com.lambferret.game.save.SaveLoader;
import com.lambferret.game.screen.event.EventWindow;
import com.lambferret.game.screen.event.main.First;
import com.lambferret.game.screen.event.main.StoryWindow;
import com.lambferret.game.screen.event.main.Tutorial;
import com.lambferret.game.screen.ui.*;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.Choco;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.soldier.Vanilla;
import lombok.Getter;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.*;

/*
 프로퍼티 추가시 여기 필드, 여기 Constructor, SaveLoader, Save 총 네군데 추가
 */
@Getter
@ToString
public class Player {
    private static final Logger logger = LogManager.getLogger(Player.class.getName());
    private String name;
    private List<Soldier> soldiers;
    private List<Command> commands;
    private List<Manual> manuals;
    private List<Quest> quests;
    private Set<PlayerObserver> listeners;
    private int clearedMainQuestNumber;
    private int day;
    private int money;
    private Map<Affinity, Integer> affinity;
    private int maxCost;
    private int currentCost;
    private int difficulty;
    private int snowAmount;
    private int bossAffinity;
    private int downAffinity;
    private int upperAffinity;
    private int maxManualCapacity;
    private List<String> eventList;
    List<Item> lateInitItems = new ArrayList<>();

    public Player() {
        GlobalSettings.loadAllInGameStructure();
        listeners = new HashSet<>();
        if (SaveLoader.currentSave.isInitialized()) {
            loadSaveIntoPlayer();
            return;
        }

        this.name = "p1";
        this.soldiers = new ArrayList<>();
        this.commands = new ArrayList<>();
        this.manuals = new ArrayList<>();
        this.quests = new ArrayList<>();

        this.day = 0;
        this.money = 1000;

        this.maxCost = 15;
        this.currentCost = maxCost;

        this.difficulty = 0;
        this.clearedMainQuestNumber = 0;

        this.downAffinity = 10;
        this.bossAffinity = 50;
        this.upperAffinity = 10;
        this.maxManualCapacity = 3;
        this.eventList = new ArrayList<>();

        //=-=-=-=-=-=--=-=//
        soldiers.add(new Vanilla());
        soldiers.add(new Choco());
        commands.add(new ThreeShift());
        manuals.add(new ColdWeatherTraining());
        //=-=-=-=-=-=--=-=//

    }

    public void loadSaveIntoPlayer() {
        Save save = SaveLoader.currentSave;

        this.soldiers = new ArrayList<>();
        this.commands = new ArrayList<>();
        this.manuals = new ArrayList<>();
        this.eventList = new ArrayList<>();
        this.quests = new ArrayList<>();

        this.day = save.getDay();
        this.money = save.getMoney();
        this.maxCost = save.getMaxCost();
        this.currentCost = save.getCurrentCost();
        this.difficulty = save.getDifficulty();
        this.downAffinity = save.getDownAffinity();
        this.bossAffinity = save.getBossAffinity();
        this.upperAffinity = save.getUpperAffinity();
        this.eventList = save.getEventList();
        this.maxManualCapacity = save.getMaxManualCapacity();
        Iterator<Item> iterator = save.getAllItems().iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            switch (item.getType()) {
                case SOLDIER -> soldiers.add(GlobalSettings.popSoldier(item.getID()));
                case COMMAND -> commands.add(GlobalSettings.popCommand(item.getID()));
                case MANUAL -> manuals.add(GlobalSettings.popManual(item.getID()));
            }
            iterator.remove();
        }
        lateInitItems = save.getAllItems();
    }

    public void lateInit() {
        for (Item item : lateInitItems) {
            switch (item.getType()) {
                case QUEST -> quests.add(GlobalSettings.getQuest(item.getID()));
                case EVENT -> {
                }
            }
        }
    }

    public int getAffinity(Affinity affinity) {
        return switch (affinity) {
            case UPPER -> upperAffinity;
            case BOSS -> bossAffinity;
            case DOWN -> downAffinity;
        };
    }

    private void playerUpdate(Item.Type item) {
        for (PlayerObserver listener : listeners) {
            if (listener instanceof Quest) {
                listener.onPlayerUpdate(item);
            }
        }
        switch (item) {
            case SOLDIER -> {
                for (PlayerObserver listener : listeners) {
                    if (listener instanceof InventoryOverlay) {
                        listener.onPlayerUpdate(Item.Type.SOLDIER);
                    }
                }
            }
            case COMMAND -> {
                for (PlayerObserver listener : listeners) {
                    if (listener instanceof CommandOverlay || listener instanceof InventoryOverlay) {
                        listener.onPlayerUpdate(Item.Type.COMMAND);
                    }
                }
            }
            case MANUAL -> {
                for (PlayerObserver listener : listeners) {
                    if (listener instanceof ManualBookshelfOverlay || listener instanceof InventoryOverlay) {
                        listener.onPlayerUpdate(Item.Type.MANUAL);
                    }
                }
            }
            case QUEST -> {
                for (PlayerObserver listener : listeners) {
                    if (listener instanceof QuestOverlay) {
                        listener.onPlayerUpdate(Item.Type.QUEST);
                    }
                }
            }
            case EVENT -> {
                for (PlayerObserver listener : listeners) {
                    if (listener instanceof CostOverlay) {
                        listener.onPlayerUpdate(Item.Type.EVENT);
                    }
                }
            }
            case MAP, BUFF -> {

            }
        }
    }

    public void addSoldier(Soldier soldier) {
        soldiers.add(soldier);
        playerUpdate(Item.Type.SOLDIER);
    }

    public void addCommand(Command command) {
        commands.add(command);
        playerUpdate(Item.Type.COMMAND);
    }

    public boolean checkManualSize() {
        return manuals.size() < maxManualCapacity;
    }

    public void addManual(Manual manual) {
        if (checkManualSize()) {
            manuals.add(manual);
            playerUpdate(Item.Type.MANUAL);
        }
    }

    public void deleteSoldier(Soldier soldier) {
        soldiers.remove(soldier);
        playerUpdate(Item.Type.SOLDIER);
    }

    public void deleteCommand(Command command) {
        commands.remove(command);
        playerUpdate(Item.Type.COMMAND);
    }

    public void deleteManual(Manual manual) {
        manuals.remove(manual);
        playerUpdate(Item.Type.MANUAL);
    }

    public void addQuest(Quest quest) {
        quests.add(quest);
        playerUpdate(Item.Type.QUEST);
    }

    public void deleteQuest(Quest quest) {
        quests.remove(quest);
        removePlayerObserver(quest);
        playerUpdate(Item.Type.QUEST);
    }

    public void setBossAffinityBy(int amount) {
        bossAffinity += amount;
        if (bossAffinity < 0) bossAffinity = 0;
        if (bossAffinity > 100) bossAffinity = 100;
    }

    public void setUpperAffinityBy(int amount) {
        upperAffinity += amount;
        if (upperAffinity < 0) upperAffinity = 0;
        if (upperAffinity > 100) upperAffinity = 100;
    }

    public void setDownAffinityBy(int amount) {
        downAffinity += amount;
        if (downAffinity < 0) downAffinity = 0;
        if (downAffinity > 100) downAffinity = 100;
    }

    public void setBossAffinityTo(int amount) {
        bossAffinity = amount;
        if (bossAffinity < 0) bossAffinity = 0;
        if (bossAffinity > 100) bossAffinity = 100;
    }

    public void setUpperAffinityTo(int amount) {
        upperAffinity = amount;
        if (upperAffinity < 0) upperAffinity = 0;
        if (upperAffinity > 100) upperAffinity = 100;
    }

    public void setDownAffinityTo(int amount) {
        downAffinity = amount;
        if (downAffinity < 0) downAffinity = 0;
        if (downAffinity > 100) downAffinity = 100;
    }

    public void setSnowAmount(int snowAmount) {
        this.snowAmount = snowAmount;
        playerUpdate(Item.Type.MAP);
    }

    public void buffChanged() {
        for (PlayerObserver listener : listeners) {
            if (listener instanceof BuffTableOverlay) {
                listener.onPlayerUpdate(Item.Type.BUFF);
            }
        }
    }

    public void setMoneyBy(int amount) {
        this.money += amount;
    }

    public void useCost(int cost) {
        currentCost -= cost;
        playerUpdate(Item.Type.EVENT);
    }

    public StoryWindow getPlayerMainEvent() {
        if (clearedMainQuestNumber >= MainEvent.values().length) {
            logger.info(" 🐳 this shows up when there is no more main event " + clearedMainQuestNumber);
            return new First();
        }
        return switch (MainEvent.values()[clearedMainQuestNumber++]) {
            case TUTORIAL -> new Tutorial();
            case FIRST -> new First();
        };
    }

    public EventWindow getEvent(StoryType type, String event) {
        String eventString = type.toString().toLowerCase() + "." + event;
        try {
            if (type == StoryType.MAIN && eventList.contains(eventString)) {
                return null;
            }
            Class<?> clazz = Class.forName("com.lambferret.game.screen.event." + eventString);
            Constructor<?> constructor = clazz.getConstructor();
            EventWindow storyWindow = (EventWindow) constructor.newInstance();
            eventList.add(event);
            return storyWindow;
        } catch (Exception e) {
            logger.error(event + " event load error", e);
            throw new RuntimeException("event load error");
        }
    }

    public boolean hasEvent(StoryType type, String event) {
        String eventString = type.toString().toLowerCase() + "." + event;
        if (eventList.contains(eventString)) {
            return true;
        } else {
            eventList.add(eventString);
            return false;
        }
    }

    public void addPlayerObserver(PlayerObserver observer) {
        listeners.add(observer);
    }

    public void removePlayerObserver(PlayerObserver observer) {
        listeners.remove(observer);
    }

    public void clearAllDeadQuest() {
        // to avoid ConcurrentModificationException
        List<Quest> a = new ArrayList<>();
        for (Quest quest : quests) {
            quest.setPhase(false);
            if (quest.isDeadQuest()) {
                a.add(quest);
            }
        }
        for (Quest quest : a) {
            deleteQuest(quest);
        }
    }

    public void initAllQuest() {
        for (Quest quest : quests) {
            quest.setPhase(true);
            quest.reset();
        }
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setCurrentCost(int currentCost) {
        playerUpdate(Item.Type.EVENT);
        this.currentCost = currentCost;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public enum Affinity {
        UPPER, BOSS, DOWN
    }

}
