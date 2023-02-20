package com.lambferret.game.save;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Save {
    private long time;
    private boolean init = false;
}
