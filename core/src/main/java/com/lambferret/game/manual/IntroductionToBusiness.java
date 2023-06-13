package com.lambferret.game.manual;

import com.lambferret.game.constant.Rarity;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.ManualInfo;

public class IntroductionToBusiness extends Manual {

    public static final String ID;
    public static final ManualInfo INFO;

    static {
        price = 1000;
    }

    @Override
    public void effect(ManualTiming timing) {

    }

    public IntroductionToBusiness() {
        super(ID, INFO, Rarity.RARE, price);
    }

    private static final int price;

    static {
        ID = IntroductionToBusiness.class.getSimpleName();
        INFO = LocalizeConfig.manualText.getID().get(ID);
    }

}
