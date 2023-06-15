package com.lambferret.game.command;

import com.lambferret.game.constant.Rarity;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class LeaveTheWounded extends Command {

    public static final String ID;

    static {
        cost = 3;
        price = 10;
        affectToUp = 0;
        affectToMiddle = 0;
        affectToDown = 0;
    }

    public LeaveTheWounded() {
        super(
            ID,
            Type.OPERATION,
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
    public void execute(List<Soldier> soldiers) {
        for (Soldier soldier : soldiers) {
            switch (soldier.getEmpowerLevel()) {
                case WEAKEN -> {
                    soldier.setRangeX((byte) 1);
                    soldier.setRangeY((byte) 1);
                }
                case NEUTRAL -> {
                }
                case EMPOWERED -> soldier.setRunAwayProbability((byte) (soldier.getRunAwayProbability() * 4 / 10));
            }
        }
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = LeaveTheWounded.class.getSimpleName();
    }

}
