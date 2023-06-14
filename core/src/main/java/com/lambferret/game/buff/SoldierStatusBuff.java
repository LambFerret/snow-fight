package com.lambferret.game.buff;

import com.lambferret.game.soldier.Soldier;

import java.util.List;
import java.util.stream.Collectors;

public class SoldierStatusBuff extends Buff {
    private final List<Soldier> soldiers;
    private final Figure which;

    public SoldierStatusBuff(String from, List<Soldier> to, Figure which, Operation operation, float value, int turn) {
        super(from, Target.SOLDIER, operation, value, turn);
        this.soldiers = to;
        this.which = which;
    }

    @Override
    protected void useEffect() {
        for (Soldier s : soldiers) {
            switch (operation) {
                case ADD -> {
                    switch (which) {
                        case X -> s.setRangeX((byte) (s.getRangeX() + value));
                        case Y -> s.setRangeY((byte) (s.getRangeY() + value));
                        case SPEED -> s.setSpeed((short) (s.getSpeed() + value));
                        case RUNAWAY -> s.setRunAwayProbability((byte) (s.getRunAwayProbability() + value));
                    }
                }
                case MUL -> {
                    switch (which) {
                        case X -> s.setRangeX((byte) (s.getRangeX() * value));
                        case Y -> s.setRangeY((byte) (s.getRangeY() * value));
                        case SPEED -> s.setSpeed((short) (s.getSpeed() * value));
                        case RUNAWAY -> s.setRunAwayProbability((byte) (s.getRunAwayProbability() * value));
                    }
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
    public String toString() {
        return null;
    }

    @Override
    public String getDescription() {
        // TODO description
        String description = this.which.description.replace("{isIncreased}", "어케됨");
        if (this.soldiers.size() > 0) {
            description = description.replace(
                "{soldier}", soldiers.stream().map(Soldier::getName).collect(Collectors.joining(", "))
            );
        }
        if (this.turn < 1000) description += text.getTurn().replace("{turn}", String.valueOf(this.turn));
        return description;
    }

    public enum Figure {
        X(text.getBuffSoldierX()),
        Y(text.getBuffSoldierY()),
        SPEED(text.getBuffSoldierSpeed()),
        RUNAWAY(text.getBuffSoldierSpeed()),
        ;

        final String description;

        Figure(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
