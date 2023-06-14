package com.lambferret.game.nonbuff;

public abstract class NonBuff {
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
