package com.lambferret.game.buff;

import com.lambferret.game.nonbuff.CommandNonBuff;
import com.lambferret.game.screen.phase.PhaseScreen;

public class CommandBuff extends Buff {

    boolean isSetTo;
    int count;

    public CommandBuff(String from, float value, boolean isSetTo, int count, int turn) {
        super(from, Target.COMMAND, Operation.ADD, value, turn);
        this.isSetTo = isSetTo;
        this.count = count;
    }

    @Override
    protected void useEffect() {
        CommandNonBuff commandNonBuff = new CommandNonBuff(
            CommandNonBuff.Figure.COST, count, value, isSetTo
        );
        PhaseScreen.tempBuffList.add(commandNonBuff);
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
}
