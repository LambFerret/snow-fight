package com.lambferret.game.command;

import com.lambferret.game.constant.EmpowerLevel;
import com.lambferret.game.constant.Rarity;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class OffBasePass extends Command {
    public static final String ID;

    static {
        cost = 3;
        price = 10;
        affectToUp = 0;
        affectToMiddle = 0;
        affectToDown = 0;
    }

    public OffBasePass() {
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
        for (Soldier soldier : soldiers) {
            switch (soldier.getEmpowerLevel()) {
                case WEAKEN -> soldier.setEmpowerLevel(EmpowerLevel.NEUTRAL);
                case NEUTRAL -> soldier.setEmpowerLevel(EmpowerLevel.EMPOWERED);
                case EMPOWERED -> soldier.setRunAwayProbability((byte) 100);
            }
        }
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = OffBasePass.class.getSimpleName();
    }

}
