package com.lambferret.game.buff;

import com.badlogic.gdx.graphics.Texture;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.PhaseText;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Buff {
    protected static final Logger logger = LogManager.getLogger(Buff.class.getName());
    protected static final PhaseText text;

    protected int turn;
    private int turnAfter = 0;
    protected float value;
    protected Operation operation;
    protected Target to;
    protected String from;
    protected Result isIncreased;
    private boolean isDisable = false;

    protected Buff(String from, Target to, Operation operation, float value, int turn) {
        this.from = from;
        this.to = to;
        this.operation = operation;
        this.value = value;
        this.turn = turn;
        this.isIncreased = isIncreased();
        logger.info("BUFF created : " + this.getClass().getSimpleName());
    }

    protected abstract void useEffect();

    public void effect() {
        if (isDisable) {
            return;
        }

        if (turnAfter > 0) {
            this.turnAfter--;
            return;
        }

        logger.info(" BUFF effect : " + this);
        useEffect();

        this.turn--;
        isDisable = true;
    }

    public void turnAfter(int turnAfter) {
        this.turnAfter = turnAfter;
    }

    public void permanently() {
        this.turn = 9999;
    }

    public boolean isExpired() {
        return this.turn <= 0;
    }

    public void setEnable() {
        isDisable = false;
    }

    public Texture getTexture() {
        return switch (isIncreased) {
            case INCREASE -> AssetFinder.getTexture("sun");
            case DECREASE -> AssetFinder.getTexture("moon");
            case NEUTRAL -> AssetFinder.getTexture("star");
        };
    }

    protected abstract Result isIncreased();

    public abstract String getDescription();


    public abstract String toString();
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder("\n BUFF | ");
//        if (turnAfter > 0) {
//            sb.append("after ").append(turnAfter).append(" turn, ");
//        }
//        sb.append(figure).append(" Buff ").append(turn).append(" turn left ")
//            .append("operation ").append(operation).append(" by value ").append(value);
//        if (!soldiers.isEmpty()) {
//            sb.append(" to SOLDIERS : ").append(soldiers);
//        }
//        if (level != null) {
//            sb.append(" to current LEVEL ").append(level.getClass().getSimpleName());
//        }
//        if (player != null) {
//            sb.append(" to current PLAYER ");
//        }
//        return sb.toString();
//    }

    static {
        text = LocalizeConfig.uiText.getPhaseText();
    }

    public enum Operation {
        ADD,
        MUL,
        ;
    }

    public enum Result {
        INCREASE, DECREASE, NEUTRAL,
        ;
    }

    public enum Target {
        PLAYER,
        SOLDIER,
        LEVEL,
        COMMAND,
        MANUAL,
        SYSTEM,
        ;
    }

}
