package com.lambferret.game.screen.event;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lambferret.game.text.dto.dialogue.DialogueNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Tutorial extends EventWindow {
    private static final Logger logger = LogManager.getLogger(Tutorial.class.getName());

    public static final String ID = "TUTORIAL";
    public static final String G_MAN = "G-man";
    public static final String HELL_HOUND = "hell hound";
    public static final String ME = "me";
    public static final String SOMEONE = "someone";
    public static final String SOMEONE_2 = "someone2";
    public final DialogueNode dialogueNode;
    public final List<String> leftActor = List.of(
        G_MAN,
        HELL_HOUND
    );
    public final List<String> rightActor = List.of(
        ME,
        SOMEONE,
        SOMEONE_2
    );

    public Tutorial(Skin skin) {
        super(ID, skin);
        this.dialogueNode = currentEvent.getDialogueNode();

        //TODO : 우야면좋을꼬
        setContext();
    }

    @Override
    public DialogueNode getDialogueNode() {
        DialogueNode titleNode = new DialogueNode(getContextByIndex(0), ME);
        DialogueNode node1 = new DialogueNode(getContextByIndex(1), ME, 0);
        DialogueNode node1Option1 = new DialogueNode(getContextByIndex(2), ME);
        DialogueNode node1Option2 = new DialogueNode(getContextByIndex(3), ME);
        DialogueNode node1Option3 = new DialogueNode(getContextByIndex(4), ME);
        DialogueNode node1Option1answer = new DialogueNode(getContextByIndex(5), ME);
        DialogueNode node1Option2answer = new DialogueNode(getContextByIndex(6), ME);
        DialogueNode node1Option3answer = new DialogueNode(getContextByIndex(7), ME);
        DialogueNode node8 = new DialogueNode(getContextByIndex(8), G_MAN, 1);
        DialogueNode node9 = new DialogueNode(getContextByIndex(9), HELL_HOUND);
        DialogueNode node10 = new DialogueNode(getContextByIndex(10), SOMEONE_2);
        DialogueNode node11 = new DialogueNode(getContextByIndex(11), SOMEONE);
        DialogueNode node12 = new DialogueNode(getContextByIndex(12), SOMEONE_2);

        titleNode.addChild(node1);
        node1.addChild(node1Option1);
        node1.addChild(node1Option2);
        node1.addChild(node1Option3);
        node1Option1.addChild(node1Option1answer);
        node1Option2.addChild(node1Option2answer);
        node1Option3.addChild(node1Option3answer);
        node1Option1answer.addChild(node8);
        node1Option2answer.addChild(node8);
        node1Option3answer.addChild(node9);
        node8.addChild(node10);
        node8.addChild(node11);
        node9.addChild(node1Option2);
        node11.addChild(node12);

        return titleNode;
    }

    @Override
    public List<String> getLeftActor() {
        return leftActor;
    }

    @Override
    public List<String> getRightActor() {
        return rightActor;
    }

    @Override
    public void solveEvent(int dialogNumber, int optionNumber) {
        logger.info("solveEvent |  🐳  dialog / option | " + dialogNumber + " / " + optionNumber);
    }
}
