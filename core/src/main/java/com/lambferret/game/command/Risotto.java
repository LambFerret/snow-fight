package com.lambferret.game.command;

import com.badlogic.gdx.math.MathUtils;
import com.lambferret.game.constant.EmpowerLevel;
import com.lambferret.game.constant.Rarity;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class Risotto extends Command {

    public static final String ID;

    static {
        cost = 3;
        price = 10;
        affectToUp = 0;
        affectToMiddle = 0;
        affectToDown = 0;
    }

    public Risotto() {
        super(
            ID,
            Type.REWARD,
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
        // TODO RANDOM SEED
        for (Soldier s : soldiers) {
            if (MathUtils.randomBoolean()) {
                s.setEmpowerLevel(EmpowerLevel.EMPOWERED);
            } else {
                s.setEmpowerLevel(EmpowerLevel.WEAKEN);
            }
        }
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = Risotto.class.getSimpleName();
    }

}
