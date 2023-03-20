package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.Stage;

public interface AbstractPhase {

    /**
     * constructor, create, init
     * constructor : 화면이 등록될 때 이므로 게임 시작과 거의 동시에 호출된다
     * create : 보통은 constructor 와 비슷한 시간에 호출되나 constructor 보다 늦는다
     * init : 화면이 띄워질 때, 즉 changeScreen 에서 주로 사용한다
     */
    void create();

    void init();

    Stage getStage();

    void render();

    void update();

}
