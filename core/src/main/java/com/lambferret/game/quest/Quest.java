package com.lambferret.game.quest;

import com.lambferret.game.SnowFight;
import com.lambferret.game.player.Player;
import com.lambferret.game.player.PlayerObserver;
import com.lambferret.game.save.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Quest implements Comparable<Quest>, PlayerObserver {
    private static final Logger logger = LogManager.getLogger(Quest.class.getName());

    private final String ID;
    private final String name;
    private final String description;
    private int timeLimit;
    protected Player player;

    public Quest(String ID, String name, String description, int timeLimit) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.timeLimit = timeLimit;
        player = SnowFight.player;
        player.addPlayerObserver(this);
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {
        boolean isClear = checkSuccessCondition();
        boolean isFailed = checkFailCondition();
        if (timeLimit <= 0) {
            fail();
        } else if (isFailed) {
            fail();
        } else if (isClear) {
            success();
        }
    }

    private void fail() {
        getPenalty();
        player.removePlayerObserver(this);
    }

    private void success() {
        getReword();
        player.removePlayerObserver(this);
    }

    public void timeFlow() {
        timeLimit -= 1;
    }

    protected abstract boolean checkSuccessCondition();

    protected abstract boolean checkFailCondition();

    protected abstract void getReword();

    protected abstract void getPenalty();

    @Override
    public void onPlayerReady() {
    }

    @Override
    public int compareTo(Quest o) {
        return this.ID.compareTo(o.ID);
    }

}
