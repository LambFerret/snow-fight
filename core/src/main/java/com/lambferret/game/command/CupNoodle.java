package com.lambferret.game.command;

import com.lambferret.game.buff.Buff;
import com.lambferret.game.buff.SoldierStatusBuff;
import com.lambferret.game.constant.Rarity;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class CupNoodle extends Command {
    public static final String ID;

    static {
        cost = 3;
        price = 10;
        affectToUp = 0;
        affectToMiddle = 0;
        affectToDown = 0;
    }

    public CupNoodle() {
        super(
            ID,
            Type.REWARD,
            cost,
            Target.SOLDIER,
            Rarity.COMMON,
            price,
            affectToUp,
            affectToMiddle,
            affectToDown
        );
        setTargetCount(2);
    }

    @Override
    public void execute(List<Soldier> soldiers) {
        PhaseScreen.addBuff(
            new SoldierStatusBuff(name, soldiers, SoldierStatusBuff.Figure.X, Buff.Operation.ADD, 1, 1)
        );
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = CupNoodle.class.getSimpleName();
    }

}
