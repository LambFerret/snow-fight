package com.lambferret.game.util;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Random {
    private static final Logger logger = LogManager.getLogger(Random.class.getName());
    private RandomXS128 random;
    private long counter;

    public Random() {
        this(MathUtils.random(999L));
    }

    public Random(long seed) {
        this.random = new RandomXS128(seed);
        this.counter = 0L;
    }

    public int random() {
        counter++;
        return random.nextInt();
    }

    public int random(int range) {
        counter++;
        return random.nextInt(range);
    }

    public int random(int start, int end) {
        counter++;
        return random.nextInt(end - start) + start;
    }

    public float randomFloat() {
        counter++;
        return random.nextInt();
    }

    public float random(float range) {
        counter++;
        return random.nextFloat() * range;
    }

    public float random(float start, float end) {
        counter++;
        return random.nextFloat() * (end - start) + start;
    }

    public boolean randomBoolean() {
        counter++;
        return random.nextBoolean();
    }

    public boolean randomBoolean(float chance) {
        counter++;
        return random.nextInt(100) < chance;
    }

    public long getSeed() {
        return random.getState(0);  // Get the first part of the seed
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
        for (int i = 0; i < counter; i++) {
            random.nextBoolean();  // Advance the random number generator
        }
    }

}
