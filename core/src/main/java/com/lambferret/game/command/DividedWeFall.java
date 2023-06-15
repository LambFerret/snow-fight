package com.lambferret.game.command;

import com.lambferret.game.constant.Branch;
import com.lambferret.game.constant.Rarity;
import com.lambferret.game.soldier.Soldier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DividedWeFall extends Command {

    public static final String ID;

    static {
        cost = 3;
        price = 10;
        affectToUp = 0;
        affectToMiddle = 0;
        affectToDown = 0;
    }

    public DividedWeFall() {
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
    public void execute(List<Soldier> soldiers) {
        Branch minBranch = getMinBranch(soldiers);
        List<Soldier> minBranchSoldiers = new ArrayList<>();
        int count = 0;
        for (Soldier s : soldiers) {
            if (s.getBranch() == minBranch) {
                minBranchSoldiers.add(s);
                count++;
            }
        }
        for (Soldier s : minBranchSoldiers) {
            s.setSpeed((short) (s.getSpeed() + count * 2));
            s.setRunAwayProbability((byte) (s.getRunAwayProbability() + count));
        }
    }

    private Branch getMinBranch(List<Soldier> soldiers) {
        Map<Branch, Integer> branchCount = new HashMap<>();
        for (Soldier soldier : soldiers) {
            Branch branch = soldier.getBranch();
            if (branchCount.containsKey(branch)) {
                branchCount.put(branch, branchCount.get(branch) + 1);
            } else {
                branchCount.put(branch, 1);
            }
        }
        int minCount = Integer.MAX_VALUE;
        Branch minBranch = null;
        for (Map.Entry<Branch, Integer> entry : branchCount.entrySet()) {
            if (entry.getValue() < minCount) {
                minCount = entry.getValue();
                minBranch = entry.getKey();
            }
        }
        return minBranch;
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = DividedWeFall.class.getSimpleName();
    }

}
