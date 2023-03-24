package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.lambferret.game.player.Player;
import com.lambferret.game.soldier.Soldier;

import java.util.ArrayList;
import java.util.List;

public interface AbstractPhase {
    List<Soldier> confirmedMember = new ArrayList<>();

    void create();

    /**
     * 세이브파일을 로드 즉시 초기화
     *
     * @param player 가독성을 위해 추가 (삭제가능)
     */
    void init(Player player);

    /**
     * 본 Phase 가 stage 를 넘겨받는 즉시 호출
     */
    void startPhase();

    /**
     * 본 Phase 를 마치고 넘겨주는 즉시 호출
     */
    void executePhase();

    Stage getStage();

    void render();

    void update();

}
