package com.lambferret.game.soldier;

import com.lambferret.game.constant.Affiliation;
import com.lambferret.game.constant.Branch;
import com.lambferret.game.constant.Rank;
import com.lambferret.game.constant.Terrain;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.SoldierText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SilvanusPark extends Soldier {
    private static final Logger logger = LogManager.getLogger(SilvanusPark.class.getName());

    public static final String ID;
    private static final SoldierText text;

    static {
        ID = SilvanusPark.class.getSimpleName();
        text = LocalizeConfig.soldierText;
    }

    public SilvanusPark() {
        super(
            ID, Affiliation.ARMY, Rank.RECRUIT, text.getName().get(ID), Branch.SNIPER,
            List.of(Terrain.LAKE), "text.getNewGame()", "silvanusPark",
            1, false, 3, 4
        );
    }

}
