package com.lambferret.game.level;

import com.lambferret.game.constant.Region;
import com.lambferret.game.level.nation.LevelN1;

public class LevelFinder {
    public Level level;

    public LevelFinder() {
    }

    public static Level get(Region region, int levelNumber) {
        return new LevelN1();

//        return switch (region) {
//            case NATION -> switch (levelNumber) {
//                case 1 -> new LevelN1();
//                case 2 -> new LevelN2();
//                default -> throw new RuntimeException("no level : " + levelNumber + " in " + region);
//            };
//            case RURAL -> switch (levelNumber) {
//                case 1 -> new LevelR1();
//                case 2 -> new LevelR2();
//                default -> throw new RuntimeException("no level : " + levelNumber + " in " + region);
//            };
//            case URBAN -> switch (levelNumber) {
//                case 1 -> new LevelU1();
//                case 2 -> new LevelU2();
//                default -> throw new RuntimeException("no level : " + levelNumber + " in " + region);
//            };
//        };
    }
}
