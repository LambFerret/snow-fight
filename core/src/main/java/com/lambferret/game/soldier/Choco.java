package com.lambferret.game.soldier;

import com.lambferret.game.buff.Buff;
import com.lambferret.game.buff.SoldierStatusBuff;
import com.lambferret.game.constant.Branch;
import com.lambferret.game.constant.Rank;
import com.lambferret.game.constant.Terrain;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.screen.phase.PhaseScreen;
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
            Rank.CORPORAL,
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
        for (Soldier soldier : s) {
            if (soldier.getBranch() == Branch.ADMINISTRATIVE && !this.equals(soldier)) {
                Buff buff1 = new SoldierStatusBuff(INFO.getName(), List.of(soldier),
                    SoldierStatusBuff.Figure.SPEED, Buff.Operation.MUL, 2, 1);
                Buff buff2 = new SoldierStatusBuff(INFO.getName(), List.of(soldier),
                    SoldierStatusBuff.Figure.SPEED, Buff.Operation.MUL, 0.2F, 1);
                buff2.turnAfter(1);
                buff2.permanently();
                PhaseScreen.addBuff(buff1, buff2);
            }
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
        ID = Choco.class.getSimpleName();
        INFO = LocalizeConfig.soldierText.getID().get(ID);
    }

}
