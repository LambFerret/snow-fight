package com.lambferret.game.soldier;

import com.lambferret.game.command.Command;
import com.lambferret.game.constant.Branch;
import com.lambferret.game.constant.Rank;
import com.lambferret.game.constant.Terrain;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.SoldierInfo;

import java.util.List;
import java.util.Map;

public class Lemon extends Soldier {

    public static final String ID;
    public static final SoldierInfo INFO;

    static {
        neutralSpeed = 10;
        neutralRangeX = 3;
        neutralRangeY = 4;
        neutralRunAwayProbability = 30;
    }

    public Lemon() {
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
    public void talent(List<Soldier> s, Map<Command, List<Soldier>> c, Level l, Player p) {
        switch (this.getEmpowerLevel()) {
            case EMPOWERED -> {
                int count = 0;
                for (Soldier soldier : s) {
                    if (soldier.compareTo(this) > 0) {
                        count++;
                    }
                }
                if (count > 1) return;
            }
            case NEUTRAL -> {
                for (Soldier soldier : s) {
                    if (soldier.compareTo(this) > 0) {
                        return;
                    }
                }
            }
            case WEAKEN -> {
                int count = 0;
                for (Soldier soldier : s) {
                    if (soldier.compareTo(this) > 0) {
                        return;
                    } else if (soldier.compareTo(this) == 0) {
                        count++;
                    }
                }
                if (count > 3) return;
            }
        }
        for (Soldier soldier : s) {
            soldier.setRunAwayProbability((byte) 100);
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
        ID = Lemon.class.getSimpleName();
        INFO = LocalizeConfig.soldierText.getID().get(ID);
    }

}
