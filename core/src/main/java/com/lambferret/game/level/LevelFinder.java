package com.lambferret.game.level;

import com.lambferret.game.component.constant.Region;
import com.lambferret.game.level.nation.LevelN1;
import com.lambferret.game.level.nation.LevelN2;
import com.lambferret.game.level.rural.LevelR1;
import com.lambferret.game.level.urban.LevelU1;
import com.lambferret.game.level.urban.LevelU2;

public class LevelFinder {
    public Level level;

    public LevelFinder() {
    }

    public static Level get(Region region, int levelNumber) {
        return switch (region) {
            case NATION -> switch (levelNumber) {
                case 1 -> new LevelN1();
                case 2 -> new LevelN2();
                default -> throw new RuntimeException("no level : " + levelNumber + " in " + region);
            };
            case RURAL -> switch (levelNumber) {
                case 1 -> new LevelR1();
                case 2 -> new LevelN2();
                default -> throw new RuntimeException("no level : " + levelNumber + " in " + region);
            };
            case URBAN -> switch (levelNumber) {
                case 1 -> new LevelU1();
                case 2 -> new LevelU2();
                default -> throw new RuntimeException("no level : " + levelNumber + " in " + region);
            };
        };
    }
}
