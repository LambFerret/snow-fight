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

public class Powder extends Soldier {

    public static final String ID;
    public static final SoldierInfo INFO;

    static {
        neutralSpeed = 10;
        neutralRangeX = 3;
        neutralRangeY = 4;
        neutralRunAwayProbability = 30;
    }

    public Powder() {
        super(
            ID,
            INFO,
            Rank.CORPORAL,
            Branch.INFANTRY,
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
        for (Soldier soldier : s) {
            // 자기보다 높으면 무시 즉 양수면 자기보다 높다는 뜻입니다
            if (soldier.compareTo(this) > 0) continue;
            short speed = soldier.getSpeed();
            switch (getEmpowerLevel()) {
                case WEAKEN -> {
                    soldier.setSpeed((short) (speed * 1.05));
                }
                case NEUTRAL -> {
                    soldier.setSpeed((short) (speed * 1.1));
                }
                case EMPOWERED -> {
                    soldier.setSpeed((short) (speed * 1.25));
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
        ID = Powder.class.getSimpleName();
        INFO = LocalizeConfig.soldierText.getID().get(ID);
    }

}
