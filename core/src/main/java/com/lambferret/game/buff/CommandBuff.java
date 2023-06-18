package com.lambferret.game.buff;

import com.lambferret.game.buff.nonbuff.CommandCostNonBuff;
import com.lambferret.game.buff.nonbuff.CommandReusableNonBuff;
import com.lambferret.game.command.Command;
import com.lambferret.game.screen.phase.PhaseScreen;

import java.util.List;

public class CommandBuff extends Buff {

    boolean isSetTo;
    int count;
    private List<Command.Type> condition;
    private final Figure which;

    public CommandBuff(String from, Figure which, float value, boolean isSetTo, int count, int turn) {
        super(from, Target.COMMAND, Operation.ADD, value, turn);
        this.which = which;
        this.isSetTo = isSetTo;
        this.count = count;
    }

    @Override
    protected void useEffect() {
        switch (which) {
            case COST -> {
                CommandCostNonBuff commandCostNonBuff = new CommandCostNonBuff(count, value, isSetTo);
                if (this.condition != null) {
                    commandCostNonBuff.hasCondition(this.condition);
                }
                PhaseScreen.tempBuffList.add(commandCostNonBuff);
            }
            case REUSABLE -> {
                CommandReusableNonBuff commandReusableNonBuff = new CommandReusableNonBuff(count, this.condition, isSetTo);
                PhaseScreen.tempBuffList.add(commandReusableNonBuff);
            }
            case PRICE -> {

            }
        }
    }

    public void hasCondition(List<Command.Type> condition) {
        this.condition = condition;
    }

    @Override
    protected Result isIncreased() {
        return Result.INCREASE;
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
        COST, PRICE, REUSABLE,

    }

}
