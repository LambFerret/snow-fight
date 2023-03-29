package com.lambferret.game.soldier;

import com.lambferret.game.buff.Buff;
import com.lambferret.game.command.Command;
import com.lambferret.game.constant.Branch;
import com.lambferret.game.constant.Rank;
import com.lambferret.game.constant.Terrain;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.SoldierInfo;

import java.util.List;
import java.util.Map;

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
    public void talent(List<Soldier> s, Map<Command, List<Soldier>> c, Level l, Player p) {
        for (Soldier soldier : s) {
            if (soldier.getBranch() == Branch.ADMINISTRATIVE) {
                PhaseScreen.addBuff(
                    Buff.figure(Buff.Figure.SOLDIER_SPEED).to(List.of(soldier)).turn(1)
                        .operation(Buff.Operation.MUL).value(2),
                    Buff.figure(Buff.Figure.SOLDIER_SPEED).to(List.of(soldier)).turn(99).turnAfter(1)
                        .operation(Buff.Operation.DIV).value(5)
                );
            }
        }
    }

    @Override
    protected void empowered() {
        this.setRangeX((byte) (neutralRangeX + 5));
        this.setRangeY((byte) (neutralRangeY + 5));
        this.setSpeed((short) (neutralSpeed + 50));
    }

    @Override
    protected void neutralized() {
        this.setRangeX(neutralRangeX);
        this.setRangeY(neutralRangeY);
        this.setSpeed(neutralSpeed);
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
