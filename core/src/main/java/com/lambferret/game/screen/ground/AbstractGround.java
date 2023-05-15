package com.lambferret.game.screen.ground;

import com.lambferret.game.player.PlayerObserver;

public interface AbstractGround extends PlayerObserver {

    void init();

    void render();

}
