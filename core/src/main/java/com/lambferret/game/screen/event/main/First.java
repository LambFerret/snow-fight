package com.lambferret.game.screen.event.main;

import com.lambferret.game.character.Character;
import com.lambferret.game.character.*;
import com.lambferret.game.constant.StoryType;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.dialogue.Dialogue;
import com.lambferret.game.text.dto.dialogue.DialogueNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class First extends StoryWindow {
    private static final Logger logger = LogManager.getLogger(First.class.getName());

    public static final String ID;
    private static final Character ME = new Me();
    public static final Character BOSS = new ImmediateBoss();
    public static final Character CHOCO = new Choco();
    public static final Character CHILI = new Chili();
    private static final List<Character> leftActor = List.of(
        ME, CHOCO
    );
    private static final List<Character> rightActor = List.of(
        BOSS, CHILI
    );

    public First() {
        super(ID, StoryType.MAIN);
    }

    @Override
    public DialogueNode getDialogueNode() {
        DialogueNode titleNode = new DialogueNode(context.get(0), ME);
        DialogueNode node1 = new DialogueNode(context.get(1), BOSS, 0);

        titleNode.addChild(node1);

        return titleNode;
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

    }

    private static final Dialogue text;
    private static final List<String> context;

    static {
        ID = First.class.getSimpleName();
        text = LocalizeConfig.dialogText.getID().get(ID);
        context = text.getContext();
        options = text.getOption();
    }
}
