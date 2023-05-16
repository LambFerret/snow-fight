package com.lambferret.game.level;

import com.lambferret.game.level.nation.LevelN1;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LevelFinder {
    private static final Logger logger = LogManager.getLogger(LevelFinder.class.getName());

    public Level level;

    public LevelFinder() {
    }

    public static Level get(int levelNumber) {
        return LevelTier.values()[levelNumber].getTier();
    }

    public enum LevelTier {
        NATION_1(new LevelN1()),
        NATION_2(new LevelN1()),
        NATION_3(new LevelN1()),
        NATION_4(new LevelN1()),
        NATION_5(new LevelN1()),
        NATION_6(new LevelN1()),
        NATION_7(new LevelN1()),
        NATION_8(new LevelN1()),
        NATION_9(new LevelN1()),
        NATION_10(new LevelN1()),
        RURAL_1(new LevelN1()),
        RURAL_2(new LevelN1()),
        RURAL_3(new LevelN1()),
        RURAL_4(new LevelN1()),
        RURAL_5(new LevelN1()),
        RURAL_6(new LevelN1()),
        RURAL_7(new LevelN1()),
        RURAL_8(new LevelN1()),
        RURAL_9(new LevelN1()),
        RURAL_10(new LevelN1()),
        URBAN_1(new LevelN1()),
        URBAN_2(new LevelN1()),
        URBAN_3(new LevelN1()),
        URBAN_4(new LevelN1()),
        URBAN_5(new LevelN1()),
        URBAN_6(new LevelN1()),
        URBAN_7(new LevelN1()),
        URBAN_8(new LevelN1()),
        URBAN_9(new LevelN1()),
        URBAN_10(new LevelN1()),
        ;
        final Level tier;

        LevelTier(Level tier) {
            this.tier = tier;
        }

        public Level getTier() {
            return tier;
        }
    }
}
