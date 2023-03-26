package com.lambferret.game.text.dto.dialogue;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class Dialogue {
    /**
     * 대화의 순서를 나타내는 노드
     */
    DialogueNode dialogueNode;
    /**
     * 대화의 내용
     */
    List<String> context;
    /**
     * 선택지 리스트
     */
    List<Option> option;
}

