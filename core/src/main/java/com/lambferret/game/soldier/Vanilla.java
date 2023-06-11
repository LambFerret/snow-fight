package com.lambferret.game.soldier;

import com.lambferret.game.command.Command;
import com.lambferret.game.constant.Branch;
import com.lambferret.game.constant.EmpowerLevel;
import com.lambferret.game.constant.Rank;
import com.lambferret.game.constant.Terrain;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.SoldierInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class Vanilla extends Soldier {
    private static final Logger logger = LogManager.getLogger(Vanilla.class.getName());

    public static final String ID;
    public static final SoldierInfo INFO;

    static {
        neutralSpeed = 10;
        neutralRangeX = 3;
        neutralRangeY = 4;
        neutralRunAwayProbability = 30;
    }

    public Vanilla() {
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
    public void talent(List<Soldier> s, Map<Command, List<Soldier>> c, Level l, Player p) {
        for (Soldier soldier : s) {
            soldier.setEmpowerLevel(EmpowerLevel.EMPOWERED);
//            soldier.setEmpowerLevel(EmpowerLevel.WEAKEN);
        }
    }

    @Override
    protected void empowered() {
        this.setRangeX((byte) (neutralRangeX + 4));
    }

    @Override
    protected void neutralized() {
        this.setRangeX(neutralRangeX);
    }

    @Override
    protected void weaken() {
        this.setRangeX((byte) (neutralRangeX - 3));
    }

    private static final short neutralSpeed;
    private static final byte neutralRangeX;
    private static final byte neutralRangeY;
    private static final byte neutralRunAwayProbability;

    static {
        ID = Vanilla.class.getSimpleName();
        INFO = LocalizeConfig.soldierText.getID().get(ID);
    }

}
