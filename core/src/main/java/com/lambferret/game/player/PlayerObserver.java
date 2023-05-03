package com.lambferret.game.player;

import com.lambferret.game.save.Item;

public interface PlayerObserver {
    void onPlayerReady();

    void onPlayerUpdate(Item.Type type);

}
