package com.lambferret.game.command;

import com.lambferret.game.constant.Branch;
import com.lambferret.game.constant.Rarity;
import com.lambferret.game.soldier.Soldier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitedWeStand extends Command {

    public static final String ID;

    static {
        cost = 3;
        price = 10;
        affectToUp = 0;
        affectToMiddle = 0;
        affectToDown = 0;
    }

    public UnitedWeStand() {
        super(
            ID,
            Type.OPERATION,
            cost,
            Target.SOLDIER,
            Rarity.COMMON,
            price,
            affectToUp,
            affectToMiddle,
            affectToDown
        );
    }

    @Override
    protected void execute(List<Soldier> soldiers) {
        Branch mostBranch = getBranchCount(soldiers);
        List<Soldier> mostBranchSoldiers = new ArrayList<>();
        int count = 0;
        for (Soldier s : soldiers) {
            if (s.getBranch() == mostBranch) {
                mostBranchSoldiers.add(s);
                count++;
            }
        }
        for (Soldier s : mostBranchSoldiers) {
            s.setSpeed((short) (s.getSpeed() + count));
            s.setRunAwayProbability((byte) (s.getRunAwayProbability() + 2 * count));
        }
    }

    private Branch getBranchCount(List<Soldier> soldiers) {
        Map<Branch, Integer> branchCount = new HashMap<>();
        for (Soldier soldier : soldiers) {
            Branch branch = soldier.getBranch();
            if (branchCount.containsKey(branch)) {
                branchCount.put(branch, branchCount.get(branch) + 1);
            } else {
                branchCount.put(branch, 1);
            }
        }
        int maxCount = 0;
        Branch maxBranch = null;
        for (Map.Entry<Branch, Integer> entry : branchCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxBranch = entry.getKey();
            }
        }
        return maxBranch;
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = UnitedWeStand.class.getSimpleName();
    }

}
