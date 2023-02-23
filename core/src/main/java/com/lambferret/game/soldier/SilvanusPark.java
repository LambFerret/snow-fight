package com.lambferret.game.soldier;

public class SilvanusPark extends Soldier {
    public static final String ID;

    static {
        ID = SilvanusPark.class.getName();

    }

    public SilvanusPark() {
        super(ID, "반갑", "description", Rank.PRIVATE, "SilvanusPark", 0.3F, 2, 2);
        this.runAwayProbability = 0.1F;
    }

}
