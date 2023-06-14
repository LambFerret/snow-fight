package com.lambferret.game.buff;

import com.lambferret.game.level.Level;

public class LevelBuff extends Buff {

    Level level;
    Figure which;

    public LevelBuff(String from, Figure which, Level level, Operation operation, float value, int turn) {
        super(from, Target.LEVEL, operation, value, turn);
        this.level = level;
        this.which = which;
    }

    @Override
    protected void useEffect() {
        switch (operation) {
            case ADD -> {
                switch (which) {
                    case CAPACITY -> level.setMaxSoldierCapacity((byte) (level.getMaxSoldierCapacity() + value));
                }
            }
            case MUL -> {
                switch (which) {
                    case CAPACITY -> level.setMaxSoldierCapacity((byte) (level.getMaxSoldierCapacity() * value));
                }
            }
        }

    }

    @Override
    protected Result isIncreased() {
        if (operation == Operation.ADD) {
            if (value > 0) {
                return Result.INCREASE;
            } else if (value < 0) {
                return Result.DECREASE;
            } else {
                return Result.NEUTRAL;
            }
        } else if (operation == Operation.MUL) {
            if (value > 1) {
                return Result.INCREASE;
            } else if (value < 1) {
                return Result.DECREASE;
            } else {
                return Result.NEUTRAL;
            }
        } else {
            return Result.NEUTRAL;
        }
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }

    public enum Figure {
        CAPACITY,
    }

}
