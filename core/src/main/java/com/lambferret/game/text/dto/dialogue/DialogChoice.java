package com.lambferret.game.text.dto.dialogue;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

/**
 * 선택지가 있을 경우 사용하는 대화창
 */
@Getter
@ToString
public class DialogChoice {
    /**
     * 선택지
     */
    List<String> option;
}
