package com.lambferret.game.command;

import com.lambferret.game.constant.Rarity;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.CommandInfo;

public class ThreeShift extends Command {

    public static final String ID;
    public static final CommandInfo INFO;

    static {
        cost = 3;
        price = 20;
        affectToUp = -20;
        affectToMiddle = +20;
        affectToDown = +20;
    }

    public ThreeShift() {
        super(
            ID,
            INFO,
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

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = ThreeShift.class.getSimpleName();
        INFO = LocalizeConfig.commandText.getID().get(ID);
    }
}
