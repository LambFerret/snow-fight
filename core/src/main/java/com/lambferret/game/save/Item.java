package com.lambferret.game.save;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Item {
    public TYPE type;
    public String ID;

    public enum TYPE {
        SOLDIER,
        COMMAND,
        MANUAL
    }
}
