package com.lambferret.game.soldier;

import com.lambferret.game.constant.Branch;
import com.lambferret.game.constant.Rank;
import com.lambferret.game.constant.Terrain;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.SoldierInfo;

import java.util.List;

public class Choco extends Soldier {

    public static final String ID;
    public static final SoldierInfo INFO;

    static {
        neutralSpeed = 3;
        neutralRangeX = 2;
        neutralRangeY = 1;
        neutralRunAwayProbability = 0;
    }

    public Choco() {
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
        this.setRangeX((byte) (neutralRangeX + 5));
        this.setRangeY((byte) (neutralRangeY + 5));
        this.setSpeed((short) (neutralSpeed + 50));
    }

    @Override
    public void neutralized() {
        this.setRangeX(neutralRangeX);
        this.setRangeY(neutralRangeY);
        this.setSpeed(neutralSpeed);
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
        ID = Choco.class.getSimpleName();
        INFO = LocalizeConfig.soldierText.getID().get(ID);
    }

}
