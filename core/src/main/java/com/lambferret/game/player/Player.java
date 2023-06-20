package com.lambferret.game.player;

import com.lambferret.game.command.Command;
import com.lambferret.game.constant.MainEvent;
import com.lambferret.game.constant.StoryType;
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
import com.lambferret.game.soldier.Chili;
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
    private final Set<PlayerObserver> listeners;
    private int clearedMainQuestNumber;
    private int day;
    private int money;
    private Map<Affinity, Integer> affinity;
    private int maxCost;
    private int currentCost;
    private int difficulty;
    private int snowAmount;
    private int middleAffinity;
    private int downAffinity;
    private int upperAffinity;
    private int maxManualCapacity;
    private int maxCommandInHand;
    private List<String> eventList;
    private List<Item> shopItems;
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
        this.middleAffinity = 50;
        this.upperAffinity = 10;
        this.maxManualCapacity = 3;
        this.maxCommandInHand = 5;
        this.eventList = new ArrayList<>();
        this.shopItems = new ArrayList<>();

        //=-=-=-=-=-=--=-=//
        soldiers.add(new Vanilla());
        soldiers.add(new Choco());
        soldiers.add(new Chili());
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
        this.middleAffinity = save.getMiddleAffinity();
        this.upperAffinity = save.getUpperAffinity();
        this.eventList = save.getEventList();
        this.maxManualCapacity = save.getMaxManualCapacity();
        this.maxCommandInHand = save.getMaxCommandInHand();
        this.shopItems = save.getShopList();
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
        logger.info(" Player Add Soldier : " + soldier.getName());
        soldiers.add(soldier);
        playerUpdate(Item.Type.SOLDIER);
    }

    public void addCommand(Command command) {
        logger.info(" Player Add Command : " + command.getName());
        commands.add(command);
        playerUpdate(Item.Type.COMMAND);
    }

    public void addManual(Manual manual) {
        if (checkManualSize()) {
            logger.info(" Player Add Manual : " + manual.getName());
            manuals.add(manual);
            playerUpdate(Item.Type.MANUAL);
        } else {
            logger.info(" Player Add Manual : " + manual.getName() + " failed");
        }
    }

    public void addQuest(Quest quest) {
        logger.info(" Player Add Quest : " + quest.getName());
        quests.add(quest);
        playerUpdate(Item.Type.QUEST);
    }

    public void deleteSoldier(Soldier soldier) {
        logger.info(" Player Delete Soldier : " + soldier.getName());
        soldiers.remove(soldier);
        playerUpdate(Item.Type.SOLDIER);
    }

    public void deleteCommand(Command command) {
        logger.info(" Player Delete Command : " + command.getName());
        commands.remove(command);
        playerUpdate(Item.Type.COMMAND);
    }

    public void deleteManual(Manual manual) {
        logger.info(" Player Delete Manual : " + manual.getName());
        manuals.remove(manual);
        playerUpdate(Item.Type.MANUAL);
    }

    public void deleteQuest(Quest quest) {
        logger.info(" Player Delete Quest : " + quest.getName());
        quests.remove(quest);
        removePlayerObserver(quest);
        playerUpdate(Item.Type.QUEST);
    }

    public boolean checkManualSize() {
        return manuals.size() < maxManualCapacity;
    }

    public void setShopItems(List<Item> shopItems) {
        this.shopItems = shopItems;
    }

    public void setMiddleAffinityBy(int amount) {
        middleAffinity += amount;
        if (middleAffinity < 0) middleAffinity = 0;
        if (middleAffinity > 100) middleAffinity = 100;
        logger.info(" Player Middle Affinity : value " + amount + ", result " + middleAffinity);
    }

    public void setUpperAffinityBy(int amount) {
        upperAffinity += amount;
        if (upperAffinity < 0) upperAffinity = 0;
        if (upperAffinity > 100) upperAffinity = 100;
        logger.info(" Player Upper Affinity : value " + amount + ", result " + upperAffinity);
    }

    public void setDownAffinityBy(int amount) {
        downAffinity += amount;
        if (downAffinity < 0) downAffinity = 0;
        if (downAffinity > 100) downAffinity = 100;
        logger.info(" Player Down Affinity : value " + amount + ", result " + downAffinity);
    }

    public void setMiddleAffinityTo(int amount) {
        middleAffinity = amount;
        if (middleAffinity < 0) middleAffinity = 0;
        if (middleAffinity > 100) middleAffinity = 100;
        logger.info(" Player Middle Affinity : " + middleAffinity);
    }

    public void setUpperAffinityTo(int amount) {
        upperAffinity = amount;
        if (upperAffinity < 0) upperAffinity = 0;
        if (upperAffinity > 100) upperAffinity = 100;
        logger.info(" Player Upper Affinity : " + upperAffinity);
    }

    public void setDownAffinityTo(int amount) {
        downAffinity = amount;
        if (downAffinity < 0) downAffinity = 0;
        if (downAffinity > 100) downAffinity = 100;
        logger.info(" Player Down Affinity : " + downAffinity);
    }

    public void setSnowAmount(int snowAmount) {
        this.snowAmount = snowAmount;
        logger.info(" Player Set Snow Amount : " + snowAmount);
        playerUpdate(Item.Type.MAP);
    }

    public void setMoneyBy(int amount) {
        this.money += amount;
        logger.info(" Player Money : value " + amount + ", result " + money);
    }

    public void setMoney(int money) {
        this.money = money;
        logger.info(" Player Money : " + money);
    }

    public void setCurrentCost(int currentCost) {
        this.currentCost = currentCost;
        playerUpdate(Item.Type.EVENT);
        logger.info(" Player Current Cost : " + currentCost);
    }

    public void setDay(int day) {
        this.day = day;
        logger.info(" Player Day : " + day);
    }

    public void buffChanged() {
        for (PlayerObserver listener : listeners) {
            if (listener instanceof BuffTableOverlay) {
                listener.onPlayerUpdate(Item.Type.BUFF);
            }
        }
    }

    public void useCost(int cost) {
        currentCost -= cost;
        logger.info(" Player Use Cost : " + cost + ", result " + currentCost);
        playerUpdate(Item.Type.EVENT);
    }

    public boolean canAfford(Command command) {
        int parsedCost = command.parsingCost();
        return currentCost >= parsedCost;
    }

    public void setDeck(List<Command> commands) {
        this.commands = commands;
    }

    public StoryWindow getPlayerMainEvent() {
        if (clearedMainQuestNumber >= MainEvent.values().length) {
            logger.info(" Player : no more main event " + clearedMainQuestNumber);
            return new First();
        }
        logger.info(" Player : main event switch into " + MainEvent.values()[clearedMainQuestNumber++]);
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

    public int getAffinity(Affinity affinity) {
        return switch (affinity) {
            case UPPER -> upperAffinity;
            case MIDDLE -> middleAffinity;
            case DOWN -> downAffinity;
        };
    }

    public void addPlayerObserver(PlayerObserver observer) {
        listeners.add(observer);
    }

    public void removePlayerObserver(PlayerObserver observer) {
        listeners.remove(observer);
    }

    public enum Affinity {
        UPPER, MIDDLE, DOWN
    }

}
