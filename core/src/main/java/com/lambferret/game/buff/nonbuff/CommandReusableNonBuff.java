package com.lambferret.game.buff.nonbuff;

import com.lambferret.game.command.Command;

import java.util.List;

public class CommandReusableNonBuff extends NonBuff {
    private final boolean isReusable;
    private List<Command.Type> condition;


    public CommandReusableNonBuff(int count, boolean isReusable) {
        super(Target.COMMAND, count);
        this.isReusable = isReusable;
    }

    public void resultBoolean(Command command) {
        if (condition == null) {
            command.setReusable(isReusable);
        } else {
            for (Command.Type type : condition) {
                if (command.getType() == type) {
                    command.setReusable(isReusable);
                }
            }
        }
    }

    public void hasCondition(List<Command.Type> condition) {
        this.condition = condition;
    }


}
