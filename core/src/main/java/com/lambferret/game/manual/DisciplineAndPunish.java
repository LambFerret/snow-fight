package com.lambferret.game.manual;

import com.lambferret.game.constant.Rarity;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.ManualInfo;

public class DisciplineAndPunish extends Manual {

    public static final String ID;
    public static final ManualInfo INFO;

    static {
        rarity = Rarity.COMMON;
        price = 100;
    }


    public DisciplineAndPunish() {
        super(ID, INFO, rarity, price);
    }

    private static final Rarity rarity;
    private static final int price;

    static {
        ID = DisciplineAndPunish.class.getSimpleName();
        INFO = LocalizeConfig.manualText.getID().get(ID);
    }


}
