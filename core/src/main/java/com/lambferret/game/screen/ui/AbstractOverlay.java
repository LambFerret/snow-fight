package com.lambferret.game.screen.ui;

import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;

/**
 * 작성 방법 (중요합니다)
 * <p>
 * field :
 * 최우선적으로 Logger
 * public static final
 * private static final
 * public
 * private
 * 순으로 작성하고 각각 life-cycle 이 먼저인 순으로, 크기가 큰순으로 정렬
 * <p>
 * method :
 * public - private 순으로 작성하며
 * 그 안에서는 life-cycle 순서대로 작성
 * 예외적으로 단순 getter setter, render(), update() 는 맨 밑단에 차례로 배치
 * 보통은 static -> constructor -> create -> init -> ETC... -> render -> update
 * <p>
 * private enum :
 * 순서는 상관 없지만 클래스 변수가 아닐경우 enum class 를 constant directory에 작성
 * <p>
 * setProperty 에 대한 고찰
 * 가독성을 위한다고는 하나 그렇게 까지 필요할까 싶다
 * create 와 마찬가지
 * setProperty, create 는 필요 없다고 느껴지면 바로 버릴것
 */
public interface AbstractOverlay {
    float OVERLAY_WIDTH = GlobalSettings.currWidth / 3.0F;
    float OVERLAY_HEIGHT = 200.0F;
    float BAR_HEIGHT = 50.0F;

    void create();

    void init(Player player);

    void setVisible(boolean visible);
}
