package com.lambferret.game.screen.ground;

import com.lambferret.game.player.Player;

public interface AbstractGround {

    void create();

    void init(Player player);

    void show();

    void render();

    void update();

}
