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

public class Salt extends Soldier {

    public static final String ID;
    public static final SoldierInfo INFO;

    static {
        neutralSpeed = 10;
        neutralRangeX = 3;
        neutralRangeY = 4;
        neutralRunAwayProbability = 30;
    }

    public Salt() {
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
        // 테스트 필요
        switch (this.getEmpowerLevel()) {
            case WEAKEN -> {
                if (p.getMaxCost() - p.getCurrentCost() < 10) {
                    this.setRunAwayProbability((byte) 100);
                }
            }
            case NEUTRAL -> {
                PhaseScreen.addBuff(
                    Buff.figure(Buff.Figure.NEXT_COMMAND).to(p).permanently()
                        .operation(Buff.Operation.SUB).value(1)
                );
            }
            case EMPOWERED -> {
                PhaseScreen.addBuff(
                    Buff.figure(Buff.Figure.NEXT_COMMAND).to(p).permanently().count(1)
                        .operation(Buff.Operation.SUB).value(99)
                );
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
        ID = Salt.class.getSimpleName();
        INFO = LocalizeConfig.soldierText.getID().get(ID);
    }

}
