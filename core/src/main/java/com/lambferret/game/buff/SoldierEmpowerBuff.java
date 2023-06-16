package com.lambferret.game.buff;

import com.lambferret.game.constant.EmpowerLevel;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class SoldierEmpowerBuff extends Buff {
    private final List<Soldier> soldiers;
    private final EmpowerLevel empowerLevel;
    private EmpowerLevel empowerCondition;

    public SoldierEmpowerBuff(String from, List<Soldier> to, EmpowerLevel empowerLevel, int turn) {
        super(from, Target.SOLDIER, null, empowerLevel.ordinal(), turn);
        this.soldiers = to;
        this.empowerLevel = empowerLevel;
    }

    @Override
    protected void useEffect() {
        for (Soldier s : soldiers) {
            if (empowerCondition != null) {
                if (s.getEmpowerLevel() == empowerCondition) {
                    s.setEmpowerLevel(empowerLevel);
                }
            } else {
                s.setEmpowerLevel(empowerLevel);
            }
        }
    }

    public void hasCondition(EmpowerLevel empowerCondition) {
        this.empowerCondition = empowerCondition;
    }

    @Override
    protected Result isIncreased() {
        return switch ((int) value) {
            case 0 -> Result.DECREASE;
            case 1 -> Result.NEUTRAL;
            case 2 -> Result.INCREASE;
            default -> throw new IllegalStateException("Unexpected value: " + value);
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
