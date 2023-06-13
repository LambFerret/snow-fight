package com.lambferret.game.manual;

import com.lambferret.game.constant.Rarity;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.ManualInfo;

public class DisciplineAndPunish extends Manual {

    public static final String ID;
    public static final ManualInfo INFO;

    static {
        price = 100;
    }

    @Override
    public void effect(ManualTiming timing) {
//        player.getSummonList().add(new Turret());
    }

    public DisciplineAndPunish() {
        super(ID, INFO, Rarity.COMMON, price);
    }

    private static final int price;

    static {
        ID = DisciplineAndPunish.class.getSimpleName();
        INFO = LocalizeConfig.manualText.getID().get(ID);
    }

}
