package com.lambferret.game.command;

import com.lambferret.game.buff.Buff;
import com.lambferret.game.constant.Rarity;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class AvoidSurveillance extends Command {

    public static final String ID;

    static {
        cost = 3;
        price = 20000;
        affectToUp = -20;
        affectToMiddle = +20;
        affectToDown = +20;
    }

    public AvoidSurveillance() {
        super(
            ID,
            Type.OPERATION,
            cost,
            Target.SOLDIER,
            Rarity.COMMON,
            price,
            affectToUp,
            affectToMiddle,
            affectToDown,
            false,
            false
        );
    }

    @Override
    public void execute(List<Soldier> soldiers) {
        PhaseScreen.addBuff(
            Buff.figure(Buff.Figure.CAPACITY).turn(1).operation(Buff.Operation.MUL)
                .to(PhaseScreen.level).value(2),
            Buff.figure(Buff.Figure.CAPACITY).turnAfter(1).operation(Buff.Operation.DIV).permanently()
                .to(PhaseScreen.level).value(2));
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = AvoidSurveillance.class.getSimpleName();
    }

}
