package com.lambferret.game.command;

import com.lambferret.game.SnowFight;
import com.lambferret.game.constant.Rarity;
import com.lambferret.game.player.Player;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class Sabotage extends Command {
    public static final String ID;

    static {
        cost = 3;
        price = 10;
        affectToUp = 0;
        affectToMiddle = 0;
        affectToDown = 0;
    }

    public Sabotage() {
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
    public void execute(List<Soldier> soldiers) {
        Player p = SnowFight.player;
        int t = p.getCurrentCost();
        p.setCurrentCost(0);
        SnowFight.player.setSnowAmount(SnowFight.player.getSnowAmount() + t * 100);

    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = Sabotage.class.getSimpleName();
    }
}
