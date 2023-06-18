package com.lambferret.game.command;

import com.lambferret.game.constant.Rarity;
import com.lambferret.game.soldier.Soldier;

import java.util.List;

public class CupRamenXL extends Command {
    public static final String ID;

    static {
        cost = 3;
        price = 10;
        affectToUp = 0;
        affectToMiddle = 0;
        affectToDown = 0;
    }

    public CupRamenXL() {
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
        // "페이즈 시작시 속도 300%, 이후 빠르게 0%까지 감소"
        for (Soldier soldier : soldiers) {
            var animation = soldier.getAnimation();
            animation.setFrameDuration(animation.getFrameDuration() * 0.3F);
        }
    }

    private static final int cost;
    private static final int price;
    private static final int affectToUp;
    private static final int affectToMiddle;
    private static final int affectToDown;

    static {
        ID = CupRamenXL.class.getSimpleName();
    }

}
