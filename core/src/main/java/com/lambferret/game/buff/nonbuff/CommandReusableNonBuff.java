package com.lambferret.game.buff.nonbuff;

import com.lambferret.game.command.Command;

import java.util.List;

public class CommandReusableNonBuff extends NonBuff {
    private final boolean setReusable;
    private final List<Command.Type> conditions;


    public CommandReusableNonBuff(int count, List<Command.Type> conditions, boolean setReusable) {
        super(Target.COMMAND, count);
        this.setReusable = setReusable;
        this.conditions = conditions;
    }

    public void resultBoolean(Command command) {
        if (conditions == null) {
            command.setReusable(setReusable);
        } else {
            for (Command.Type type : conditions) {
                if (command.getType() == type) {
                    command.setReusable(setReusable);
                }
            }
        }
    }

    public List<Command.Type> getConditions() {
        return conditions;
    }

}
