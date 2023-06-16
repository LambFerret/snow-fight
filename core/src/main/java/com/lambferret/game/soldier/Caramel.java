package com.lambferret.game.soldier;

import com.lambferret.game.constant.Branch;
import com.lambferret.game.constant.Rank;
import com.lambferret.game.constant.Terrain;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.SoldierInfo;

import java.util.List;

public class Caramel extends Soldier {

    public static final String ID;
    public static final SoldierInfo INFO;

    static {
        neutralSpeed = 10;
        neutralRangeX = 3;
        neutralRangeY = 4;
        neutralRunAwayProbability = 30;
    }

    public Caramel() {
        super(
            ID,
            INFO,
            Rank.SERGEANT_FIRST_CLASS,
            Branch.SUPPLY,
            List.of(Terrain.LAKE),
            false,
            neutralSpeed,
            neutralRangeX,
            neutralRangeY,
            neutralRunAwayProbability
        );
    }

    @Override
    public void talent(List<Soldier> s, Level l, Player p) {
        for (Soldier soldier : s) {
            switch (getEmpowerLevel()) {
                case WEAKEN -> {
                    soldier.setRangeY((byte) (soldier.getRangeY() - 1));
                }
                case NEUTRAL -> {
                    soldier.setRangeY((byte) (soldier.getRangeY() + 1));
                }
                case EMPOWERED -> {
                    soldier.setRangeX((byte) (soldier.getRangeX() + 1));
                    soldier.setRangeY((byte) (soldier.getRangeY() + 1));
                }
            }
        }
    }

    @Override
    protected void empowered() {
    }

    @Override
    protected void neutralized() {
    }

    @Override
    protected void weaken() {
    }

    private static final short neutralSpeed;
    private static final byte neutralRangeX;
    private static final byte neutralRangeY;
    private static final byte neutralRunAwayProbability;

    static {
        ID = Caramel.class.getSimpleName();
        INFO = LocalizeConfig.soldierText.getID().get(ID);
    }

}
