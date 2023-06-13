package com.lambferret.game.command;

import com.lambferret.game.constant.Rarity;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class Snowplow extends Command {

    public static final String ID;

    static {
        cost = 3;
        price = 20;
        affectToUp = -20;
        affectToMiddle = +20;
        affectToDown = +20;
    }

    public Snowplow() {
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
            int t = s.getRangeY() - 1;
            s.setRangeY((byte) 1);
            s.setRangeX((byte) (s.getRangeX() + t));
        }
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = Snowplow.class.getSimpleName();
    }

}