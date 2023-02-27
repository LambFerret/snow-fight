package com.lambferret.game.player;

import com.lambferret.game.book.Book;
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
    private static int humanAffinity;
    private static int hellAffinity;

    public Player() {
        name = SaveLoader.currentSave.getName();
        hellAffinity = 10;
        humanAffinity = 50;
        soldiers = new ArrayList<>();
        books = new ArrayList<>();
        soldiers.add(new SilvanusPark());
    }

    public static void init() {
//        name = SaveLoader.currentSave.getName();
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

    public enum AFFINITY {
        HUMAN, HELL
    }

}
