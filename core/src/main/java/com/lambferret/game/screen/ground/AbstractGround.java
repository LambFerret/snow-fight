package com.lambferret.game.screen.ground;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.lambferret.game.player.Player;

public interface AbstractGround {

    void create();

    void init(Player player);

    Stage getStage();

    void render();

    void update();

}
