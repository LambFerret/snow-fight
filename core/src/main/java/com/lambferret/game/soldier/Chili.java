package com.lambferret.game.soldier;

import com.lambferret.game.constant.Branch;
import com.lambferret.game.constant.Rank;
import com.lambferret.game.constant.Terrain;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.SoldierInfo;

import java.util.ArrayList;
import java.util.List;

public class Chili extends Soldier {

    public static final String ID;
    public static final SoldierInfo INFO;

    static {
        neutralSpeed = 50;
        neutralRangeX = 1;
        neutralRangeY = 1;
        neutralRunAwayProbability = 0;
    }

    public Chili() {
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
    public void talent(List<Soldier> s, Level l, Player p) {
        List<Soldier> temp = new ArrayList<>();
        for (Soldier soldier : s) {
            if (soldier.compareTo(this) == 0) {
                temp.add(soldier);
            }
            if (soldier.compareTo(this) > 0) {
                return;
            }
        }
        this.setRunAwayProbability((byte) 100);
        for (Soldier soldier : temp) {
            soldier.setRunAwayProbability((byte) 100);
        }

    }

    @Override
    protected void empowered() {
        this.setRangeX((byte) (neutralRangeX + 1));
    }

    @Override
    protected void neutralized() {
        this.setRangeX(neutralRangeX);
    }

    @Override
    protected void weaken() {
        this.setRangeX((byte) (neutralRangeX - 1));
    }

    private static final short neutralSpeed;
    private static final byte neutralRangeX;
    private static final byte neutralRangeY;
    private static final byte neutralRunAwayProbability;

    static {
        ID = Chili.class.getSimpleName();
        INFO = LocalizeConfig.soldierText.getID().get(ID);
    }

}
