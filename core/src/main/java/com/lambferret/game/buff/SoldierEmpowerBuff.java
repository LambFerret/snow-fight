package com.lambferret.game.buff;

import com.lambferret.game.constant.EmpowerLevel;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class SoldierEmpowerBuff extends Buff {
    private final List<Soldier> soldiers;
    private final EmpowerLevel empowerLevel;

    public SoldierEmpowerBuff(String from, List<Soldier> to, EmpowerLevel empowerLevel, int turn) {
        super(from, Target.SOLDIER, null, 0, turn);
        this.soldiers = to;
        this.empowerLevel = empowerLevel;
    }

    @Override
    protected void useEffect() {
        for (Soldier s : soldiers) {
            s.setEmpowerLevel(empowerLevel);
        }
    }

    @Override
    protected Result isIncreased() {
        return switch (empowerLevel) {
            case EMPOWERED -> Result.INCREASE;
            case NEUTRAL -> Result.NEUTRAL;
            case WEAKEN -> Result.DECREASE;
        };
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

}
