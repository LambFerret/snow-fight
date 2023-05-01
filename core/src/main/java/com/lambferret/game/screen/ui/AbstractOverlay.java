package com.lambferret.game.screen.ui;

import com.lambferret.game.player.Player;
import com.lambferret.game.player.PlayerObserver;
import com.lambferret.game.setting.GlobalSettings;

public interface AbstractOverlay extends PlayerObserver {
    /**
     * UI 기준 너비
     */
    float OVERLAY_BORDERLINE_WIDTH = GlobalSettings.currWidth / 5.0F;
    /**
     * UI 기준 높이
     */
    float OVERLAY_BORDERLINE_HEIGHT = 200.0F;
    /**
     * 눈통 높이
     */
    float SNOW_BAR_HEIGHT = 50.0F;
    float SNOW_BAR_X = 0;
    float SNOW_BAR_Y = 0;
    /**
     * 패딩
     */
    float COMMAND_PAD = 5.0F;

    /**
     * 병사창 숨김 버튼 너비
     */
    int SOLDIER_HIDE_BUTTON_WIDTH = 100;
    /**
     * 병사창 X 좌표
     */
    int SOLDIER_X = 0;
    /**
     * 병사창 Y 좌표
     */
    int SOLDIER_Y = 0;
    /**
     * 병사창 너비
     */
    int SOLDIER_WIDTH = GlobalSettings.currWidth - SOLDIER_HIDE_BUTTON_WIDTH;
    /**
     * 병사창 높이
     */
    int SOLDIER_HEIGHT = GlobalSettings.currHeight;
    /**
     * 명령창 숨김 X 좌표
     */
    int SOLDIER_HIDE_X = SOLDIER_X - SOLDIER_WIDTH;

    /**
     * 병사창 숨김 버튼 폴리곤 좌표
     */
    float[] SOLDIER_HIDE_BUTTON_VERTICES = new float[]{0, 0, 100, 50, 100, 400, 0, 400};
    /**
     * 병사창 숨김 버튼 X 좌표
     */
    int SOLDIER_HIDE_BUTTON_X = GlobalSettings.currWidth - SOLDIER_HIDE_BUTTON_WIDTH;
    /**
     * 병사창 숨김 버튼 Y 좌표
     */
    int SOLDIER_HIDE_BUTTON_Y = 300;
    /**
     * 병사창 숨김 버튼 높이
     */
    int SOLDIER_HIDE_BUTTON_HEIGHT = 300;
    /**
     * 병사창 숨김버튼 숨김 X 좌표
     */
    int SOLDIER_HIDE_BUTTON_HIDE_X = SOLDIER_HIDE_BUTTON_X - SOLDIER_WIDTH;
    /**
     * 병사창 숨김 애니메이션 시간
     */
    float SOLDIER_HIDE_ANIMATION_DURATION = 0.1F;

    /**
     * 병사 각 너비
     */
    float SOLDIER_EACH_WIDTH = OVERLAY_BORDERLINE_HEIGHT * 2 / 3.0F;
    /**
     * 병사 각 높이
     */
    float SOLDIER_EACH_HEIGHT = OVERLAY_BORDERLINE_HEIGHT;
    /**
     * 병사 각 패딩
     */
    int SOLDIER_EACH_PAD = 5;
    /**
     * 병사 테이블과 윈도우 사이의 마진
     */
    int SOLDIER_CARD_MARGIN = 300;
    /**
     * 숨김 토글 활성화 X 경계선
     */
    int SOLDIER_HIDE_MOVEMENT_THRESHOLD_X = 200;

    /**
     * 명령창 너비
     */
    float COMMAND_WIDTH = OVERLAY_BORDERLINE_WIDTH;
    /**
     * 명령창 높이
     */
    float COMMAND_HEIGHT = GlobalSettings.currHeight - OVERLAY_BORDERLINE_HEIGHT;
    /**
     * 명령창 X 좌표
     */
    float COMMAND_X = GlobalSettings.currWidth - OVERLAY_BORDERLINE_WIDTH;
    /**
     * 명령창 Y 좌표
     */
    float COMMAND_Y = OVERLAY_BORDERLINE_HEIGHT;
    /**
     * 명령창 숨김 X 좌표
     */
    float COMMAND_HIDE_X = COMMAND_X + COMMAND_WIDTH + COMMAND_PAD;

    /**
     * 명령창 숨김 버튼 너비
     */
    float COMMAND_HIDE_BUTTON_WIDTH = 50;
    /**
     * 명령창 숨김 버튼 높이
     */
    float COMMAND_HIDE_BUTTON_HEIGHT = 50;
    /**
     * 명령창 숨김 버튼 X 좌표
     */
    float COMMAND_HIDE_BUTTON_X = COMMAND_X - (COMMAND_HIDE_BUTTON_WIDTH + COMMAND_PAD);
    /**
     * 명령창 숨김 버튼 Y 좌표
     */
    float COMMAND_HIDE_BUTTON_Y = COMMAND_Y + COMMAND_PAD;
    /**
     * 명령창 숨김버튼 숨김 X 좌표
     */
    float COMMAND_HIDE_BUTTON_HIDE_X = COMMAND_HIDE_BUTTON_X + COMMAND_WIDTH + COMMAND_PAD;
    /**
     * 멍령창 숨김 애니메이션 시간
     */
    float COMMAND_HIDE_ANIMATION_DURATION = 0.1F;

    /**
     * 명령 각 능력 사이 패딩
     */
    int COMMAND_EACH_PADDING = 10;
    /**
     * 명령 각 능력 너비
     */
    float COMMAND_EACH_WIDTH = COMMAND_WIDTH;
    /**
     * 명령 각 능력 높이
     */
    float COMMAND_EACH_HEIGHT = COMMAND_HEIGHT / 3.0F;

    /**
     * 맵 정보 이미지 너비
     */
    int MAP_HOVER_IMAGE_WIDTH = 200;
    /**
     * 맵 정보 이미지 높이
     */
    int MAP_HOVER_IMAGE_HEIGHT = 300;
    /**
     * 맵 정보 이미지 Y 패딩
     */
    float MAP_HOVER_IMAGE_Y_PAD = 25.0F;
    /**
     * 맵 정보 이미지 X 패딩
     */
    float MAP_HOVER_IMAGE_X_PAD = 30.0F;
    /**
     * 맵 정보 이미지 호버 애니메이션 시간
     */
    float MAP_HOVER_IMAGE_ANIMATION_DURATION = 0.1F;


    void create();

    void init(Player player);

    void setVisible(boolean visible);
}
/*
 * 작성 방법 (중요합니다)
 * <p>
 * field :
 * 최우선적으로 Logger (예외사항 : Manual 이나 Soldier 각자 객체엔 없음)
 * public static final
 * private static final
 * public
 * private
 * 순으로 작성하고 각각 life-cycle 이 먼저인 순으로, 크기가 큰순으로 정렬
 * static field :
 * 필드 선언 이후 작성. 예외적으로 Manual 이나 Soldier 각자 객체는 최상단으로 재배치
 * <p>
 * method :
 * public - private 순으로 작성하며
 * 그 안에서는 life-cycle 순서대로 작성
 * 예외적으로 단순 getter setter, render(), update() 는 맨 밑단에 차례로 배치
 * 보통은 static -> constructor -> create -> init -> ETC... -> render -> update
 * <p>
 * private enum :
 * 순서는 상관 없지만 클래스 변수가 아닐경우 enum class 를 constant directory 에 작성
 * <p>
 * constructor, create, init
 * constructor  : 외부와 관련된 설정들. constructor 의 parameter 와 관련된 설정, 작업 등을 시행하고 필드값을 초기화 한다
 * create       : 내부와 관련된 설정들. parameter 이외의 설정, 작업등을 시행한다.
 * (즉 init 과 constructor 에서 할 수 없는 설정을 한다)
 * init         : 화면이 띄워질 때, 즉 changeScreen 에서 주로 사용한다
 */
