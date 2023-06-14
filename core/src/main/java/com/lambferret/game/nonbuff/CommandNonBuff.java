package com.lambferret.game.nonbuff;

import com.lambferret.game.command.Command;

public class CommandNonBuff extends NonBuff {
    private final Figure figure;
    private final boolean isSetTo;
    private final float value;


    public CommandNonBuff(Figure figure, int count, float value, boolean setInToValue) {
        super(Target.COMMAND, count);
        this.figure = figure;
        this.value = value;
        this.isSetTo = setInToValue;
    }

    public float resultInt(Command command) {
        return switch (figure) {
            case COST -> {
                if (isSetTo) {
                    yield value;
                } else {
                    yield command.getCost() + value;
                }
            }
            case PRICE -> {
                yield 0;
            }
        };
    }

    public enum Figure {
        COST, PRICE,
        ;
    }
}
