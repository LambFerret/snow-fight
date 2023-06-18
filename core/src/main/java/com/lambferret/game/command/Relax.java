package com.lambferret.game.command;

import com.lambferret.game.constant.Rarity;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class Relax extends Command {

    public static final String ID;

    static {
        cost = 3;
        price = 10;
        affectToUp = 0;
        affectToMiddle = 0;
        affectToDown = 0;
    }

    public Relax() {
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
        Soldier target = null;
        for (Soldier soldier : soldiers) {
            if (soldier.getRangeX() * soldier.getRangeY() > max) {
                max = soldier.getRangeX() * soldier.getRangeY();
                target = soldier;
            }
        }
        if (target != null) {
            target.setSpeed((short) (target.getSpeed() * target.getRangeY()));
        }
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = Relax.class.getSimpleName();
    }

}
