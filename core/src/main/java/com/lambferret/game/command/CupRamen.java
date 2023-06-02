package com.lambferret.game.command;

import com.lambferret.game.constant.EmpowerLevel;
import com.lambferret.game.constant.Rarity;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.CommandInfo;

import java.util.List;

public class CupRamen extends Command {
    public static final String ID;

    static {
        cost = 1;
        price = 12;
        affectToUp = 1;
        affectToMiddle = 1;
        affectToDown = 1;
    }

    public CupRamen() {
        super(
            ID,
            Type.REWARD,
            cost,
            Target.SOLDIER,
            Rarity.COMMON,
            price,
            affectToUp,
            affectToMiddle,
            affectToDown,
            false,
            true
        );
    }

    @Override
    public void execute(List<Soldier> soldiers) {
        for (Soldier soldier : soldiers) {
            soldier.setEmpowerLevel(EmpowerLevel.EMPOWERED);
            soldier.setRunAwayProbability((byte) (soldier.getRunAwayProbability() + 10));
        }

    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = CupRamen.class.getSimpleName();
    }

}
