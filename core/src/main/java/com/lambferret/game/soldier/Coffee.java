package com.lambferret.game.soldier;

import com.lambferret.game.constant.Branch;
import com.lambferret.game.constant.Rank;
import com.lambferret.game.constant.Terrain;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.SoldierInfo;

import java.util.List;

public class Coffee extends Soldier {

    public static final String ID;
    public static final SoldierInfo INFO;

    static {
        neutralSpeed = 5;
        neutralRangeX = 7;
        neutralRangeY = 7;
        neutralRunAwayProbability = 7;
    }

    public Coffee() {
        super(
            ID,
            INFO,
            Rank.RECRUIT,
            Branch.ADMINISTRATIVE,
            List.of(Terrain.LAKE),
            false,
            neutralSpeed,
            neutralRangeX,
            neutralRangeY,
            neutralRunAwayProbability
        );
    }

    @Override
    public void talent() {
    }

    @Override
    public void empowered() {
        this.setRangeX((byte) (neutralRangeX + 1));
    }

    @Override
    public void neutralized() {
        this.setRangeX(neutralRangeX);
    }

    @Override
    public void weaken() {
        this.setRangeX((byte) (neutralRangeX - 1));
    }

    private static final short neutralSpeed;
    private static final byte neutralRangeX;
    private static final byte neutralRangeY;
    private static final byte neutralRunAwayProbability;

    static {
        ID = Coffee.class.getSimpleName();
        INFO = LocalizeConfig.soldierText.getID().get(ID);
    }

}
