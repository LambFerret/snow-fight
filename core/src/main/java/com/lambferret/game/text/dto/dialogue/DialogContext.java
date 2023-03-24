package com.lambferret.game.text.dto.dialogue;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DialogContext {
    /**
     * 화자. actorLeft 말단 - ..., -2, -1, 0(actorRight 의 첫번째 인물), 1, 2, ... - actorRight 말단
     */
    short speaker;
    /**
     * 대화
     * 길어지면 좀 알아서 끊어지는 걸 고려해야할까?
     */
    String text;
    /**
     * 선택지 리스트의 인덱스
     * 중요! 0은 empty value 이므로 1부터 시작한다!!! 진짜 중요
     */
    short optionIndex;
}
