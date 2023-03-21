package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.lambferret.game.player.Player;

public interface AbstractPhase {

    void create();

    void init(Player player);

    Stage getStage();

    void render();

    void update();

}
