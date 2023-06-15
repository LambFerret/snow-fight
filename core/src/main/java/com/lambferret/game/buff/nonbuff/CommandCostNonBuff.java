package com.lambferret.game.buff.nonbuff;

import com.lambferret.game.command.Command;

import java.util.List;

public class CommandCostNonBuff extends NonBuff {
    private final boolean isSetTo;
    private final float value;
    private List<Command.Type> condition;


    public CommandCostNonBuff(int count, float value, boolean setInToValue) {
        super(Target.COMMAND, count);
        this.value = value;
        this.isSetTo = setInToValue;
    }

    public float resultInt(Command command) {
        float result;
        if (isSetTo) {
            result = value;
        } else {
            result = command.getCost() + value;
        }
        return result;

    }

    public void hasCondition(List<Command.Type> condition) {
        this.condition = condition;
    }

    public List<Command.Type> getCondition() {
        return condition;
    }
}
