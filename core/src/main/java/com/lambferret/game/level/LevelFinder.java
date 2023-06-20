package com.lambferret.game.level;

import com.lambferret.game.level.nation.*;
import com.lambferret.game.level.rural.*;
import com.lambferret.game.level.urban.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LevelFinder {
    private static final Logger logger = LogManager.getLogger(LevelFinder.class.getName());

    public Level level;

    public LevelFinder() {
    }

    public static Level get(int levelNumber) {
        logger.info("LEVEL : trying to get number " + levelNumber + " and their tier is " + LevelTier.values()[levelNumber]);
        return switch (LevelTier.values()[levelNumber]) {
            case NATION_1 -> new LevelN1();
            case NATION_2 -> new LevelN2();
            case NATION_3 -> new LevelN3();
            case NATION_4 -> new LevelN4();
            case NATION_5 -> new LevelN5();
            case NATION_6 -> new LevelN6();
            case NATION_7 -> new LevelN7();
            case NATION_8 -> new LevelN8();
            case NATION_9 -> new LevelN9();
            case NATION_10 -> new LevelN10();
            case RURAL_1 -> new LevelR1();
            case RURAL_2 -> new LevelR2();
            case RURAL_3 -> new LevelR3();
            case RURAL_4 -> new LevelR4();
            case RURAL_5 -> new LevelR5();
            case RURAL_6 -> new LevelR6();
            case RURAL_7 -> new LevelR7();
            case RURAL_8 -> new LevelR8();
            case RURAL_9 -> new LevelR9();
            case RURAL_10 -> new LevelR10();
            case URBAN_1 -> new LevelU1();
            case URBAN_2 -> new LevelU2();
            case URBAN_3 -> new LevelU3();
            case URBAN_4 -> new LevelU4();
            case URBAN_5 -> new LevelU5();
            case URBAN_6 -> new LevelU6();
            case URBAN_7 -> new LevelU7();
            case URBAN_8 -> new LevelU8();
            case URBAN_9 -> new LevelU9();
            case URBAN_10 -> new LevelU10();
        };
    }

    public enum LevelTier {
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
    }
}
