package com.lambferret.game.util;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Random {
    private static final Logger logger = LogManager.getLogger(Random.class.getName());
    public RandomXS128 random;

    public Random() {
        this(MathUtils.random(999L));
    }

    public Random(Long seed) {
        this.random = new RandomXS128(seed);
    }

}
