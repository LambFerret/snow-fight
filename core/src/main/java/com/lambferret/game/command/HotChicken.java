package com.lambferret.game.command;

import com.lambferret.game.buff.SoldierEmpowerBuff;
import com.lambferret.game.constant.EmpowerLevel;
import com.lambferret.game.constant.Rarity;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class HotChicken extends Command {

    public static final String ID;

    static {
        cost = 3;
        price = 10;
        affectToUp = 0;
        affectToMiddle = 0;
        affectToDown = 0;
    }

    public HotChicken() {
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
    }

    @Override
    protected void execute(List<Soldier> soldiers) {
        SoldierEmpowerBuff buff = new SoldierEmpowerBuff(name, soldiers, EmpowerLevel.NEUTRAL, 1);
        buff.permanently();
        buff.hasCondition(EmpowerLevel.WEAKEN);
        PhaseScreen.addBuff(buff);
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = HotChicken.class.getSimpleName();
    }

}
