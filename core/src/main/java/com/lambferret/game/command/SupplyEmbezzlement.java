package com.lambferret.game.command;

import com.lambferret.game.buff.CommandBuff;
import com.lambferret.game.constant.Rarity;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class SupplyEmbezzlement extends Command {

    public static final String ID;

    static {
        cost = 3;
        price = 10;
        affectToUp = 0;
        affectToMiddle = 0;
        affectToDown = 0;
    }

    public SupplyEmbezzlement() {
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
        CommandBuff buff = new CommandBuff(name, CommandBuff.Figure.COST, 0, true, 1, 1);
        buff.permanently();
        buff.hasCondition(List.of(Type.OPERATION));
        PhaseScreen.addBuff(buff);
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = SupplyEmbezzlement.class.getSimpleName();
    }

}
