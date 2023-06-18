package com.lambferret.game.command;

import com.lambferret.game.buff.CommandBuff;
import com.lambferret.game.constant.Rarity;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class TraffickingInClassifiedInformation extends Command {

    public static final String ID;

    static {
        cost = 3;
        price = 10;
        affectToUp = 0;
        affectToMiddle = 0;
        affectToDown = 0;
    }

    public TraffickingInClassifiedInformation() {
        super(
            ID,
            Type.BETRAYAL,
            cost,
            Target.SOLDIER,
            Rarity.COMMON,
            price,
            affectToUp,
            affectToMiddle,
            affectToDown
        );
    }

    @Override
    protected void execute(List<Soldier> soldiers) {
        CommandBuff commandBuff = new CommandBuff(name, CommandBuff.Figure.REUSABLE, 0, true, 1, 1);
        commandBuff.permanently();
        commandBuff.hasCondition(List.of(Type.REWARD));

    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = TraffickingInClassifiedInformation.class.getSimpleName();
    }

}
