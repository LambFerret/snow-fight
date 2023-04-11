package com.lambferret.game.quest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Quest implements Comparable<Quest> {
    private static final Logger logger = LogManager.getLogger(Quest.class.getName());

    private final String ID;
    private final String name;
    private final String description;
    private boolean isCompleted;

    public Quest(String ID, String name, String description) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        isCompleted = false;
    }

    public void complete() {
        isCompleted = true;
    }

    public String getDescription() {
        return description;
    }

    public String getID() {
        return ID;
    }

    @Override
    public int compareTo(Quest o) {
        return this.ID.compareTo(o.ID);
    }

}
