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
        return switch (LevelTier.values()[levelNumber]) {
            case NATION_1 -> new LevelN1();
            case NATION_2 -> new LevelN1();
            case NATION_3 -> new LevelN1();
            case NATION_4 -> new LevelN1();
            case NATION_5 -> new LevelN1();
            case NATION_6 -> new LevelN1();
            case NATION_7 -> new LevelN1();
            case NATION_8 -> new LevelN1();
            case NATION_9 -> new LevelN1();
            case NATION_10 -> new LevelN1();
            case RURAL_1 -> new LevelN1();
            case RURAL_2 -> new LevelN1();
            case RURAL_3 -> new LevelN1();
            case RURAL_4 -> new LevelN1();
            case RURAL_5 -> new LevelN1();
            case RURAL_6 -> new LevelN1();
            case RURAL_7 -> new LevelN1();
            case RURAL_8 -> new LevelN1();
            case RURAL_9 -> new LevelN1();
            case RURAL_10 -> new LevelN1();
            case URBAN_1 -> new LevelN1();
            case URBAN_2 -> new LevelN1();
            case URBAN_3 -> new LevelN1();
            case URBAN_4 -> new LevelN1();
            case URBAN_5 -> new LevelN1();
            case URBAN_6 -> new LevelN1();
            case URBAN_7 -> new LevelN1();
            case URBAN_8 -> new LevelN1();
            case URBAN_9 -> new LevelN1();
            case URBAN_10 -> new LevelN1();
        };
    }

    public enum LevelTier {
        NATION_1,
        NATION_2,
        NATION_3,
        NATION_4,
        NATION_5,
        NATION_6,
        NATION_7,
        NATION_8,
        NATION_9,
        NATION_10,
        RURAL_1,
        RURAL_2,
        RURAL_3,
        RURAL_4,
        RURAL_5,
        RURAL_6,
        RURAL_7,
        RURAL_8,
        RURAL_9,
        RURAL_10,
        URBAN_1,
        URBAN_2,
        URBAN_3,
        URBAN_4,
        URBAN_5,
        URBAN_6,
        URBAN_7,
        URBAN_8,
        URBAN_9,
        URBAN_10,
    }
}
