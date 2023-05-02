package com.lambferret.game.screen.phase;

import com.lambferret.game.player.PlayerObserver;

public interface AbstractPhase extends PlayerObserver {

    /**
     * 본 Phase 가 stage 를 넘겨받는 즉시 호출
     */
    void startPhase();

    /**
     * 본 Phase 를 마치고 넘겨주는 즉시 호출
     */
    void executePhase();

    void render();

}
