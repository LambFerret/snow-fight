package com.lambferret.game.buff.nonbuff;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class NonBuff {
    protected static final Logger logger = LogManager.getLogger(NonBuff.class.getName());

    private final Target to;
    protected int count;
    private boolean isDisabled = false;

    public NonBuff(Target to, int count) {
        this.to = to;
        this.count = count;
    }

    public void countDown() {
        count--;
        if (count == 0) {
            isDisabled = true;
        }
    }

    public Target getTarget() {
        return to;
    }

    public boolean isDisabled() {
        return isDisabled;
    }


    public enum Target {
        COMMAND,
        SOLDIER
    }


}
