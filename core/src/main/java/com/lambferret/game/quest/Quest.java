package com.lambferret.game.quest;

import com.lambferret.game.SnowFight;
import com.lambferret.game.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Quest implements Comparable<Quest> {
    private static final Logger logger = LogManager.getLogger(Quest.class.getName());

    private final String ID;
    private final String name;
    private final String description;
    private boolean isCompleted;
    protected Player player;

    public Quest(String ID, String name, String description) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        player = SnowFight.player;
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

    public abstract void getReword();

    @Override
    public int compareTo(Quest o) {
        return this.ID.compareTo(o.ID);
    }

}
