package com.lambferret.game.command;

import com.lambferret.game.SnowFight;
import com.lambferret.game.constant.Rarity;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.CommandInfo;

import java.util.List;

public class TricksOfTheTrade extends Command {
    public static final String ID;
    public static final CommandInfo INFO;

    static {
        cost = 8;
        price = 100;
        affectToUp = -100;
        affectToMiddle = 0;
        affectToDown = +100;
    }

    public TricksOfTheTrade() {
        super(
            ID,
            INFO,
            Type.BETRAYAL,
            cost,
            Target.PLAYER,
            Rarity.RARE,
            price,
            affectToUp,
            affectToMiddle,
            affectToDown,
            true,
            false
        );
    }

    @Override
    public void execute(List<Soldier> soldiers) {
        SnowFight.player.setSnowAmount(SnowFight.player.getSnowAmount() * 4 / 5);
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = TricksOfTheTrade.class.getSimpleName();
        INFO = LocalizeConfig.commandText.getID().get(ID);
    }
}
