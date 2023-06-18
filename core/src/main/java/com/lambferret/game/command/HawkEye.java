package com.lambferret.game.command;

import com.lambferret.game.constant.Rarity;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class HawkEye extends Command {

    public static final String ID;

    static {
        cost = 3;
        price = 10;
        affectToUp = 0;
        affectToMiddle = 0;
        affectToDown = 0;
    }

    public HawkEye() {
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
    protected void execute(List<Soldier> soldiers) {
        for (Soldier soldier : soldiers) {
            soldier.setRangeX((byte) (soldier.getRangeX() + 2));
            soldier.setRangeY((byte) (soldier.getRangeY() - 1));
        }
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = HawkEye.class.getSimpleName();
    }

}
