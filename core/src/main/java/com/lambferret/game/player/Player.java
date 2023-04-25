package com.lambferret.game.player;

import com.lambferret.game.command.Command;
import com.lambferret.game.command.ThreeShift;
import com.lambferret.game.constant.MainEvent;
import com.lambferret.game.constant.StoryType;
import com.lambferret.game.manual.Manual;
import com.lambferret.game.quest.Quest;
import com.lambferret.game.quest.TutorialQuest;
import com.lambferret.game.save.Item;
import com.lambferret.game.save.Save;
import com.lambferret.game.save.SaveLoader;
import com.lambferret.game.screen.event.EventWindow;
import com.lambferret.game.screen.event.main.StoryWindow;
import com.lambferret.game.screen.event.main.Tutorial;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.Choco;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.soldier.Vanilla;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class Player {
    private static final Logger logger = LogManager.getLogger(Player.class.getName());
    private String name;
    private List<Soldier> soldiers;
    private List<Command> commands;
    private List<Manual> manuals;
    private List<Quest> quests;
    private int clearedMainQuestNumber;
    private int day;
    private int money;
    private Map<AFFINITY, Integer> affinity;
    private int maxCost;
    private int currentCost;
    private int difficulty;
    private int snowAmount;
    private int humanAffinity;
    private int hellAffinity;
    private List<String> eventList;

    public Player() {
        GlobalSettings.loadAllInGameStructure();

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

        this.maxCost = 3;
        this.currentCost = maxCost;

        this.difficulty = 0;
        this.clearedMainQuestNumber = 0;

        this.hellAffinity = 10;
        this.humanAffinity = 50;
        this.eventList = new ArrayList<>();

        //=-=-=-=-=-=--=-=//
//        soldiers.add(GlobalSettings.popSoldier());
//        soldiers.add(GlobalSettings.popSoldier());
//        commands.add(GlobalSettings.popCommand());
//        manuals.add(GlobalSettings.popManual());
        //=-=-=-=-=-=--=-=//

        //=-=-=-=-=-=--=-=//
        soldiers.add(new Vanilla());
        soldiers.add(new Choco());
        commands.add(new ThreeShift());
        quests.add(new TutorialQuest());
//        manuals.add(new ColdWeatherTraining());
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
        this.hellAffinity = save.getHellAffinity();
        this.humanAffinity = save.getHumanAffinity();
        this.eventList = save.getEventList();
        for (Item item : save.getAllItems()) {
            switch (item.getType()) {
                case SOLDIER -> {
                    soldiers.add(GlobalSettings.popSoldier(item.getID()));
                }
                case COMMAND -> {
                    commands.add(GlobalSettings.popCommand(item.getID()));
                }
                case MANUAL -> {
                    manuals.add(GlobalSettings.popManual(item.getID()));
                }
                case EVENT -> {
                }
                case QUEST -> {
                    quests.add(GlobalSettings.popQuest(item.getID()));
                }
            }
        }

    }

    public int getAffinity(AFFINITY affinity) {
        switch (affinity) {
            case HUMAN -> {
                return humanAffinity;
            }
            case HELL -> {
                return hellAffinity;
            }
            default -> {
                logger.error("getAffinity | doesn't return anything ");
                return -1;
            }
        }
    }

    public void setAffinity(AFFINITY affinity, int amount) {
        switch (affinity) {
            case HUMAN -> {
                humanAffinity += amount;
                if (humanAffinity < 0) humanAffinity = 0;
                if (humanAffinity > 100) humanAffinity = 100;
            }
            case HELL -> {
                hellAffinity += amount;
                if (hellAffinity < 0) hellAffinity = 0;
                if (hellAffinity > 100) hellAffinity = 100;
            }
        }
    }

    public void addSoldier(Soldier soldier) {
        soldiers.add(soldier);
    }

    public void addManual(Command command) {
        this.commands.add(command);
    }

    public void setMoneyBy(int amount) {
        this.money += amount;
    }

    public boolean useCost(int cost) {
        if (currentCost < cost) {
            return false;
        } else {
            currentCost -= cost;
            return true;
        }
    }

    public StoryWindow getPlayerMainEvent() {
        if (clearedMainQuestNumber >= MainEvent.values().length) {
            logger.info(" 🐳 this shows up when there is no more main event " + clearedMainQuestNumber);
            return new Tutorial();
        }
        return switch (MainEvent.values()[clearedMainQuestNumber++]) {
            case TUTORIAL -> new Tutorial();
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

    public enum AFFINITY {
        HUMAN, HELL
    }

}
