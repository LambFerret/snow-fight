package com.lambferret.game.command;

import com.lambferret.game.constant.Rarity;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.CommandInfo;

public class CupNoodle extends Command {
    public static final String ID;
    public static final CommandInfo INFO;

    static {
        cost = 1;
        price = 12;
        affectToUp = 1;
        affectToMiddle = 1;
        affectToDown = 1;
    }

    public CupNoodle() {
        super(
            ID,
            INFO,
            Type.REWARD,
            cost,
            Target.SOLDIER,
            Rarity.COMMON,
            price,
            affectToUp,
            affectToMiddle,
            affectToDown,
            false,
            true
        );
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = CupNoodle.class.getSimpleName();
        INFO = LocalizeConfig.commandText.getID().get(ID);
    }
}
