package com.lambferret.game.text.dto.dialogue;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class Option {
    /**
     * 선택지 설명
     */
    String description;
    /**
     * 선택지의 내용
     */
    List<String> element;
}
