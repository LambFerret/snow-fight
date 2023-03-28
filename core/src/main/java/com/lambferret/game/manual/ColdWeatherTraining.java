package com.lambferret.game.manual;

import com.lambferret.game.constant.Rarity;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.ManualInfo;

public class ColdWeatherTraining extends Manual {

    public static final String ID;
    public static final ManualInfo INFO;

    static {
        price = 50;
    }

    public ColdWeatherTraining() {
        super(ID, INFO, Rarity.COMMON, price);
    }

    private static final int price;

    static {
        ID = ColdWeatherTraining.class.getSimpleName();
        INFO = LocalizeConfig.manualText.getID().get(ID);
    }

}
