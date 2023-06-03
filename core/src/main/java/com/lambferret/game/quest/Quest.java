package com.lambferret.game.quest;

import com.badlogic.gdx.graphics.Color;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.player.Player;
import com.lambferret.game.player.PlayerObserver;
import com.lambferret.game.save.Item;
import com.lambferret.game.util.GlobalUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Quest implements Comparable<Quest>, PlayerObserver {
    private static final Logger logger = LogManager.getLogger(Quest.class.getName());

    private final String ID;
    private final String name;
    private final String description;
    private int timeLimit;
    protected Player player;
    private final CustomButton questItem;
    private boolean isThisLifeIsGonnaDead = false;
    private boolean isPhase = false;

    public Quest(String ID, String name, String description, int timeLimit) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.timeLimit = timeLimit;
        player = SnowFight.player;
        player.addPlayerObserver(this);
        this.questItem = GlobalUtil.simpleButton("quest", description);
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {
        if (isThisLifeIsGonnaDead || !isPhase) return;
        boolean isClear = checkSuccessCondition();
        boolean isFailed = checkFailCondition();
        if (timeLimit <= 0) {
            fail();
            return;
        }
        if (isFailed) {
            fail();
            return;
        }
        if (isClear) {
            success();
        }
    }

    private void fail() {
        isThisLifeIsGonnaDead = true;
        getPenalty();
        questItem.setDisabled(true);
        questItem.setColor(Color.RED);
    }

    private void success() {
        isThisLifeIsGonnaDead = true;
        getReword();
        questItem.setDisabled(true);
        questItem.setColor(Color.GREEN);
    }

    public void timeFlow() {
        timeLimit -= 1;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public boolean isDeadQuest() {
        return isThisLifeIsGonnaDead;
    }

    public CustomButton getQuestItem() {
        return questItem;
    }

    public void setPhase(boolean isPhase) {
        this.isPhase = isPhase;
    }

    public void reset() {
        isThisLifeIsGonnaDead = false;
        questItem.setDisabled(false);
        questItem.setColor(Color.WHITE);
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
