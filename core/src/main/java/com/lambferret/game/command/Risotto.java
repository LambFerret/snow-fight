package com.lambferret.game.command;

import com.lambferret.game.constant.Rarity;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class Risotto extends Command {

    public static final String ID;

    static {
        cost = 3;
        price = 20;
        affectToUp = -20;
        affectToMiddle = +20;
        affectToDown = +20;
    }

    public Risotto() {
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
        for (Soldier s : soldiers) {
            s.setRunAwayProbability((byte) (s.getRunAwayProbability() * 9 / 10));
            s.setSpeed((short) (s.getSpeed() * 9 / 10));
        }
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = Risotto.class.getSimpleName();
    }

}
