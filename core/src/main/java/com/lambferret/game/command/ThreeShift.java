package com.lambferret.game.command;

import com.lambferret.game.buff.Buff;
import com.lambferret.game.buff.LevelBuff;
import com.lambferret.game.constant.Rarity;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class ThreeShift extends Command {

    public static final String ID;

    static {
        cost = 3;
        price = 20000;
        affectToUp = -20;
        affectToMiddle = +20;
        affectToDown = +20;
    }

    public ThreeShift() {
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
        Buff buff1 = new LevelBuff(this.name, LevelBuff.Figure.CAPACITY, PhaseScreen.level, Buff.Operation.MUL, 2, 1);
        Buff buff2 = new LevelBuff(this.name, LevelBuff.Figure.CAPACITY, PhaseScreen.level, Buff.Operation.MUL, 0.5F, 1);
        buff2.permanently();
        buff2.turnAfter(1);
        PhaseScreen.addBuff(buff1, buff2);
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = ThreeShift.class.getSimpleName();
    }

}
