package com.lambferret.game.screen.ui;

import com.lambferret.game.player.PlayerObserver;
import com.lambferret.game.setting.GlobalSettings;

public interface AbstractOverlay extends PlayerObserver {
    int width = GlobalSettings.currWidth;
    int height = GlobalSettings.currHeight;
    /**
     * UI 기준 너비
     */
    int OVERLAY_BORDERLINE_WIDTH = width / 5;
    /**
     * UI 기준 높이
     */
    int OVERLAY_BORDERLINE_HEIGHT = 200;

    /**
     * 눈통 높이
     */
    int SNOW_BAR_HEIGHT = height / 30;
    /**
     * 눈통 너비
     */
    int SNOW_BAR_WIDTH = width / 2;
    /**
     * 눈통 아래 패딩
     */
    int SNOW_BAR_BOTTOM_PAD = 30;
    /**
     * 눈통 X 좌표
     */
    int SNOW_BAR_X = (width - SNOW_BAR_WIDTH) / 2;
    /**
     * 눈통 Y 좌표
     */
    int SNOW_BAR_Y = SNOW_BAR_BOTTOM_PAD;
    /**
     * 눈통 애니메이션 시간
     */
    int SNOW_BAR_ANIMATION_DURATION = 1;
    /**
     * 눈통 클리어 기준선 버튼 너비
     */
    int SNOW_BAR_BORDERLINE_WIDTH = 50;
    /**
     * 눈통 클리어 기준선 버튼 높이
     */
    int SNOW_BAR_BORDERLINE_HEIGHT = 30;

    /**
     * 눈통 클리어 기준선 아래 패딩
     */
    int SNOW_BAR_BORDER_BOTTOM_PAD = 10;
    /**
     * 눈통 라벨 너비
     */
    int SNOW_BAR_LABEL_WIDTH = 20;
    /**
     * 눈통 라벨 높이
     */
    int SNOW_BAR_LABEL_HEIGHT = 20;
    /**
     * 눈통 기준선 설명 너비
     */
    int SNOW_BAR_DESCRIPTION_WIDTH = 30;
    /**
     * 눈통 기준선 설명 높이
     */
    int SNOW_BAR_DESCRIPTION_HEIGHT = 15;

    /**
     * 인벤토리 숨김 버튼 너비
     */
    int INVENTORY_HIDE_BUTTON_WIDTH = 50;
    /**
     * 인벤토리 X 좌표
     */
    int INVENTORY_X = 0;
    /**
     * 인벤토리 Y 좌표
     */
    int INVENTORY_Y = 0;
    /**
     * 인벤토리 너비
     */
    int INVENTORY_WIDTH = width - INVENTORY_HIDE_BUTTON_WIDTH;
    /**
     * 인벤토리 높이
     */
    int INVENTORY_HEIGHT = height;
    /**
     * 명령창 숨김 X 좌표
     */
    int INVENTORY_HIDE_X = INVENTORY_X - INVENTORY_WIDTH;

    /**
     * 인벤토리 숨김 버튼 폴리곤 좌표
     */
    float[] INVENTORY_HIDE_BUTTON_VERTICES = new float[]{0, 0, 50, 25, 50, 150, 0, 200};
    /**
     * 인벤토리 숨김 버튼 X 좌표
     */
    int INVENTORY_HIDE_BUTTON_X = width - INVENTORY_HIDE_BUTTON_WIDTH;
    /**
     * 인벤토리 숨김 버튼 Y 좌표
     */
    int INVENTORY_HIDE_BUTTON_Y = height - 200;
    /**
     * 인벤토리 숨김 버튼 높이
     */
    int INVENTORY_HIDE_BUTTON_HEIGHT = 100;
    /**
     * 인벤토리 숨김버튼 숨김 X 좌표
     */
    int INVENTORY_HIDE_BUTTON_HIDE_X = INVENTORY_HIDE_BUTTON_X - INVENTORY_WIDTH;
    /**
     * 인벤토리 숨김 애니메이션 시간
     */
    float INVENTORY_HIDE_ANIMATION_DURATION = 0.1F;

    /**
     * 인벤토리 아이템 각 너비
     */
    int INVENTORY_EACH_WIDTH = OVERLAY_BORDERLINE_HEIGHT * 2 / 3;
    /**
     * 인벤토리 아이템 각 높이
     */
    int INVENTORY_EACH_HEIGHT = OVERLAY_BORDERLINE_HEIGHT;
    /**
     * 인벤토리 아이템 각 패딩
     */
    int INVENTORY_EACH_PAD = 5;
    /**
     * 인벤토리 아이템 테이블과 윈도우 사이의 마진
     */
    int INVENTORY_CARD_MARGIN = 300;
    /**
     * 숨김 토글 활성화 X 경계선
     */
    int INVENTORY_HIDE_MOVEMENT_THRESHOLD_X = 200;

    /**
     * 명령창 위에 패딩
     */
    int COMMAND_TOP_PAD = 30;
    /**
     * 명령창 아래 패딩
     */
    int COMMAND_BOTTOM_PAD = 30;
    /**
     * 명령창 오른쪽 패딩
     */
    int COMMAND_RIGHT_PAD = 30;
    /**
     * 명령창과 숨김 버튼 사이 패딩
     */
    int COMMAND_BETWEEN_HIDE_BUTTON_PAD = 15;
    /**
     * 명령창 너비
     */
    int COMMAND_WIDTH = OVERLAY_BORDERLINE_WIDTH - COMMAND_RIGHT_PAD;
    /**
     * 명령창 높이
     */
    int COMMAND_HEIGHT = height - (OVERLAY_BORDERLINE_HEIGHT + COMMAND_BOTTOM_PAD + COMMAND_TOP_PAD);
    /**
     * 명령창 X 좌표
     */
    int COMMAND_X = width - OVERLAY_BORDERLINE_WIDTH;
    /**
     * 명령창 Y 좌표
     */
    int COMMAND_Y = OVERLAY_BORDERLINE_HEIGHT + COMMAND_BOTTOM_PAD;
    /**
     * 명령창 숨겼을때 X 좌표
     */
    int COMMAND_HIDE_X = COMMAND_X + COMMAND_WIDTH + COMMAND_RIGHT_PAD;
    /**
     * 명령창 숨기는 버튼 너비
     */
    int COMMAND_HIDE_BUTTON_WIDTH = 50;
    /**
     * 명령창 숨기는 버튼 높이
     */
    int COMMAND_HIDE_BUTTON_HEIGHT = 50;
    /**
     * 명령창 숨기는 버튼 X 좌표
     */
    int COMMAND_HIDE_BUTTON_X = COMMAND_X - (COMMAND_HIDE_BUTTON_WIDTH + COMMAND_BETWEEN_HIDE_BUTTON_PAD);
    /**
     * 명령창 숨기는 버튼 Y 좌표
     */
    int COMMAND_HIDE_BUTTON_Y = COMMAND_Y + COMMAND_BETWEEN_HIDE_BUTTON_PAD;
    /**
     * 명령창 숨기는 버튼 숨겨질때 X 좌표
     */
    int COMMAND_HIDE_BUTTON_HIDE_X = width - (COMMAND_HIDE_BUTTON_WIDTH + COMMAND_BETWEEN_HIDE_BUTTON_PAD);
    /**
     * 멍령창 숨김 애니메이션 시간
     */
    float COMMAND_HIDE_ANIMATION_DURATION = 0.1F;

    int COMMAND_EACH_SIDE_PAD = 20;
    /**
     * 명령 각 능력 사이 패딩
     */
    int COMMAND_EACH_PADDING = 10;
    /**
     * 명령 각 능력 너비
     */
    int COMMAND_EACH_WIDTH = COMMAND_WIDTH - (COMMAND_EACH_SIDE_PAD * 2);
    /**
     * 명령 각 능력 높이
     */
    int COMMAND_EACH_HEIGHT = COMMAND_HEIGHT / 4;

    /**
     * 실행창 X 좌표
     */
    int EXECUTE_X = width - OVERLAY_BORDERLINE_WIDTH;
    /**
     * 실행창 Y 좌표
     */
    int EXECUTE_Y = 0;
    /**
     * 실행창 너비
     */
    int EXECUTE_WIDTH = OVERLAY_BORDERLINE_WIDTH;
    /**
     * 실행창 높이
     */
    int EXECUTE_HEIGHT = OVERLAY_BORDERLINE_HEIGHT;
    /**
     * 실행창 숨김 버튼 X 상대 좌표
     */
    int EXECUTE_HIDE_BUTTON_RELATIVE_X = EXECUTE_WIDTH * 2 / 3;
    /**
     * 실행창 애니메이션 시간
     */
    float EXECUTE_HIDE_ANIMATION_DURATION = 0.1F;
    /**
     * action phase 가 자동으로 넘어가기까지 시간
     */
    float EXECUTE_TIMER_TO_NEXT_PHASE = 1F;
    /**
     * execute 를 얼마나 누르고 있어야 하는지 시간
     */
    float EXECUTE_PRESS_TIME = 1F;

    /**
     * 매뉴얼 책장 너비
     */
    int MANUAL_WIDTH = 300;
    /**
     * 매뉴얼 책장 높이
     */
    int MANUAL_HEIGHT = 150;
    /**
     * 매뉴얼 책장 X 좌표
     */
    int MANUAL_X = width - (MANUAL_WIDTH + OVERLAY_BORDERLINE_WIDTH);
    /**
     * 매뉴얼 책장 Y 좌표
     */
    int MANUAL_Y = height - MANUAL_HEIGHT;
    /**
     * 매뉴얼 책장 각 매뉴얼 너비
     */
    int MANUAL_EACH_WIDTH = 25;
    /**
     * 매뉴얼 책장 각 매뉴얼 높이
     */
    int MANUAL_EACH_HEIGHT = 50;

    /**
     * 페이즈 순서 위에 패딩
     */
    int ORDER_TOP_PAD = 5;
    /**
     * 페이즈 순서 버튼 크기
     */
    int ORDER_BUTTON_SIZE = 40;

    /**
     * 퀘스트 X 좌표
     */
    int QUEST_INIT_X = 100;
    /**
     * 퀘스트 Y 좌표
     */
    int QUEST_INIT_Y = 500;
    /**
     * 퀘스트 너비
     */
    int QUEST_INIT_WIDTH = 100;
    /**
     * 퀘스트 높이
     */
    int QUEST_INIT_HEIGHT = 100;

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
    int MAP_HOVER_IMAGE_Y_PAD = 25;
    /**
     * 맵 정보 이미지 X 패딩
     */
    int MAP_HOVER_IMAGE_X_PAD = 30;
    /**
     * 맵 정보 이미지 호버 애니메이션 시간
     */
    float MAP_HOVER_IMAGE_ANIMATION_DURATION = 0.1F;

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
 */
