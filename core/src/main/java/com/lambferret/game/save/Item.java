package com.lambferret.game.save;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Item {
    public Type type;
    public String ID;

    public enum Type {
        SOLDIER,
        COMMAND,
        MANUAL,
        QUEST,
        EVENT,
    }
}
