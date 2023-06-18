package com.lambferret.game.command;

import com.lambferret.game.SnowFight;
import com.lambferret.game.constant.Rarity;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class TricksOfTheTrade extends Command {
    public static final String ID;

    static {
        cost = 3;
        price = 10;
        affectToUp = 0;
        affectToMiddle = 0;
        affectToDown = 0;
    }

    public TricksOfTheTrade() {
        super(
            ID,
            Type.BETRAYAL,
            cost,
            Target.PLAYER,
            Rarity.RARE,
            price,
            affectToUp,
            affectToMiddle,
            affectToDown
        );
    }

    @Override
    protected void execute(List<Soldier> soldiers) {
        SnowFight.player.setSnowAmount(SnowFight.player.getSnowAmount() * 4 / 5);
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = TricksOfTheTrade.class.getSimpleName();
    }
}
