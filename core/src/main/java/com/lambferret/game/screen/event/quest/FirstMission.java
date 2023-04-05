package com.lambferret.game.screen.event.quest;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lambferret.game.text.dto.dialogue.DialogueNode;

import java.util.List;

public class FirstMission extends QuestWindow {

    private static final List<String> actors = List.of("Lamb");

    public FirstMission(String eventID, Skin skin) {
        super(eventID, skin);
    }

    @Override
    protected DialogueNode getDialogueNode() {
        return null;
    }

    @Override
    protected List<String> getActor() {
        return actors;
    }

    @Override
    protected void solveEvent(int optionNumber) {

    }

}
