package com.lambferret.game.command;

import com.lambferret.game.buff.Buff;
import com.lambferret.game.constant.Rarity;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.CommandInfo;

import java.util.List;

public class ThreeShift extends Command {

    public static final String ID;
    public static final CommandInfo INFO;

    static {
        cost = 3;
        price = 20;
        affectToUp = -20;
        affectToMiddle = +20;
        affectToDown = +20;
    }

    public ThreeShift() {
        super(
            ID,
            INFO,
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

        var level = PhaseScreen.level;
        level.setMaxSoldierCapacity(level.getMaxSoldierCapacity() * 2);
        Buff first = Buff
            .figure(Buff.Figure.CAPACITY)
            .turn(1)
            .operation(Buff.Operation.MUL)
            .to(PhaseScreen.level)
            .value(2);
        Buff second = Buff
            .figure(Buff.Figure.CAPACITY)
            .turnAfter(1).turn(99)
            .operation(Buff.Operation.DIV)
            .to(PhaseScreen.level)
            .value(2);

        PhaseScreen.buffList.add(first);
        PhaseScreen.buffList.add(second);
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = ThreeShift.class.getSimpleName();
        INFO = LocalizeConfig.commandText.getID().get(ID);
    }

}
