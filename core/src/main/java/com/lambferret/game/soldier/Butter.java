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

public class Butter extends Soldier {

    public static final String ID;
    public static final SoldierInfo INFO;

    static {
        neutralSpeed = 10;
        neutralRangeX = 3;
        neutralRangeY = 4;
        neutralRunAwayProbability = 30;
    }

    public Butter() {
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
        int sub = 0;
        if (this.initialRangeX * this.initialRangeY > this.getRangeX() * this.getRangeY()) {
            sub = this.initialRangeX * this.initialRangeY - this.getRangeX() * this.getRangeY();
        }
        switch (getEmpowerLevel()) {
            case WEAKEN -> {
                this.setRunAwayProbability((byte) (this.getRunAwayProbability() + sub * 0.1));
            }
            case NEUTRAL -> {
                this.setSpeed((short) (this.getSpeed() + sub * 0.1));
            }
            case EMPOWERED -> {
                this.setSpeed((short) (this.getSpeed() + sub * 0.1 * 1.5));
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
        ID = Butter.class.getSimpleName();
        INFO = LocalizeConfig.soldierText.getID().get(ID);
    }

}
