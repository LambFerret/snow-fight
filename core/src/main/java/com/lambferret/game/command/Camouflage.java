package com.lambferret.game.command;

import com.lambferret.game.constant.Rarity;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class Camouflage extends Command {

    public static final String ID;

    static {
        cost = 3;
        price = 10;
        affectToUp = 0;
        affectToMiddle = 0;
        affectToDown = 0;
    }

    public Camouflage() {
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
        int max = -999;
        Soldier maxTarget = null;
        for (Soldier soldier : soldiers) {
            if (soldier.getSpeed() > max) {
                max = soldier.getSpeed();
                maxTarget = soldier;
            }
        }
        int min = 999;
        Soldier minTarget = null;
        for (Soldier soldier : soldiers) {
            if (soldier.getSpeed() < min) {
                min = soldier.getSpeed();
                minTarget = soldier;
            }
        }

        if (maxTarget != null && minTarget != null && !maxTarget.equals(minTarget)) {
            maxTarget.setSpeed((short) min);
            minTarget.setSpeed((short) max);
        }
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = Camouflage.class.getSimpleName();
    }

}
