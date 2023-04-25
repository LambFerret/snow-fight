package com.lambferret.game.screen.event.quest;

import com.lambferret.game.character.Character;
import com.lambferret.game.character.Me;
import com.lambferret.game.text.dto.dialogue.DialogueNode;

import java.util.List;

public class FirstMission extends QuestWindow {

    private static final List<Character> actors = List.of(new Me());

    public FirstMission(String eventID) {
        super(eventID);
    }

    @Override
    protected DialogueNode getDialogueNode() {
        return null;
    }

    @Override
    protected List<Character> getActor() {
        return actors;
    }

    @Override
    protected void solveEvent(int optionNumber) {

    }

}
