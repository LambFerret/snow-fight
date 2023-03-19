package com.lambferret.game.player;

import com.lambferret.game.book.Book;
import com.lambferret.game.constant.Region;
import com.lambferret.game.save.SaveLoader;
import com.lambferret.game.soldier.SilvanusPark;
import com.lambferret.game.soldier.Soldier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player {
    private static final Logger logger = LogManager.getLogger(Player.class.getName());
    private String name;
    private List<Soldier> soldiers;
    private List<Book> books;
    private int money;
    private Map<AFFINITY, Integer> affinity;
    private int maxCost;
    private int currentCost;
    private int difficulty;
    private int snowAmount;
    private int humanAffinity;
    private int hellAffinity;
    private Region currentRegion;
    private int levelNumber;

    public Player() {
        // TODO : save 와 연동

        name = SaveLoader.currentSave.getName();

        this.hellAffinity = 10;
        this.humanAffinity = 50;
        this.soldiers = new ArrayList<>();
        this.books = new ArrayList<>();

        //=-=-=-=-=-=--=-=//
        for (int i = 0; i < 11; i++) {
            soldiers.add(new SilvanusPark());
        }
        //=-=-=-=-=-=--=-=//

        this.currentRegion = Region.URBAN;
        this.levelNumber = 1;

    }

    public static void init() {

    }

    public String getName() {
        return name;
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

    public List<Soldier> getSoldiers() {
        return soldiers;
    }

    public void addSoldier(Soldier soldier) {
        soldiers.add(soldier);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public Region getCurrentRegion() {
        return currentRegion;
    }

    public void setCurrentRegion(Region currentRegion) {
        this.currentRegion = currentRegion;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public enum AFFINITY {
        HUMAN, HELL
    }

}
