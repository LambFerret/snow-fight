package com.lambferret.game.screen.event.main;

import com.lambferret.game.character.Character;
import com.lambferret.game.constant.StoryType;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.dialogue.Dialogue;
import com.lambferret.game.text.dto.dialogue.DialogueNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Template extends StoryWindow {
    private static final Logger logger = LogManager.getLogger(First.class.getName());
    public static final String ID = Template.class.getSimpleName();
    private static final List<Character> leftActor = List.of(
    );
    private static final List<Character> rightActor = List.of(
    );

    public Template() {
        super(ID, StoryType.MAIN);
    }

    @Override
    protected DialogueNode getDialogueNode() {
        return null;
    }

    @Override
    public List<Character> getLeftActor() {
        return leftActor;
    }

    @Override
    public List<Character> getRightActor() {
        return rightActor;
    }

    @Override
    public void solveEvent(int dialogNumber, int optionNumber) {
        switch (dialogNumber) {
            case 0:
                switch (optionNumber) {
                    case 0 -> {
                    }
                    case 1 -> {
                    }
                }
            case 1:
                switch (optionNumber) {
                    case 0 -> {
                    }
                    case 1 -> {
                    }
                }
        }
    }

    @Override
    boolean isOutOfCondition(int dialog, int optionNumber) {
        return false;
    }

    @Override
    protected Dialogue setText() {
        return LocalizeConfig.dialogText.getMAIN().get(ID);
    }

}
