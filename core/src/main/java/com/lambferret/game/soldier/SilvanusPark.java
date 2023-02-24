package com.lambferret.game.soldier;

import com.lambferret.game.component.constant.Affiliation;
import com.lambferret.game.component.constant.Branch;
import com.lambferret.game.component.constant.Rank;
import com.lambferret.game.component.constant.Terrain;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.TitleMenuText;

import java.util.List;

public class SilvanusPark extends Soldier {
    public static final String ID;
    private static final TitleMenuText text;

    static {
        ID = SilvanusPark.class.getName();
        text = LocalizeConfig.uiText.getTitleMenuText();
    }

    public SilvanusPark() {
        super(
            ID, Affiliation.ARMY, Rank.PRIVATE, text.getContinueGame(), Branch.SNIPER,
            List.of(Terrain.LAKE), text.getNewGame(), "silvanusPark",
            0.1F, false, 3, 4
            );
    }

}
