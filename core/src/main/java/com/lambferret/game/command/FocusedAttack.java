package com.lambferret.game.command;

import com.lambferret.game.constant.Rarity;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class FocusedAttack extends Command {

    public static final String ID;

    static {
        cost = 3;
        price = 20;
        affectToUp = -20;
        affectToMiddle = +20;
        affectToDown = +20;
    }

    public FocusedAttack() {
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
        int min = 999;
        Soldier target = null;
        for (Soldier soldier : soldiers) {
            if (soldier.getRangeX() * soldier.getRangeY() < min) {
                min = soldier.getRangeX() * soldier.getRangeY();
                target = soldier;
            }
        }
        if (target != null) {
            target.setSpeed((short) (target.getSpeed() * (min - 1)));
        }
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = FocusedAttack.class.getSimpleName();
    }

}
