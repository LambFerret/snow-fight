package com.lambferret.game.text.dto.dialogue;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class Dialogue {
    /**
     * 대화창의 왼쪽에 서게되는 등장인물
     */
    List<String> actorLeft;
    /**
     * 대화창의 오른쪽에 서게되는 등장인물
     */
    List<String> actorRight;
    /**
     * index 가 대화의 순서
     */
    List<DialogContext> context;
    /**
     * 선택지 리스트
     */
    List<List<String>> option;
}
