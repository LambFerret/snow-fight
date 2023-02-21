package com.lambferret.game.save;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Save {
    private long time;
    private boolean init = false;
}
