package com.lambferret.game.screen.event.main;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lambferret.game.SnowFight;
import com.lambferret.game.command.CupNoodle;
import com.lambferret.game.constant.StoryType;
import com.lambferret.game.soldier.Vanilla;
import com.lambferret.game.text.dto.dialogue.DialogueNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Tutorial extends StoryWindow {
    private static final Logger logger = LogManager.getLogger(Tutorial.class.getName());

    private static final String ID = "TUTORIAL";
    private static final String G_MAN = "G-man";
    private static final String HELL_HOUND = "hell hound";
    private static final String ME = "me";
    private static final String SOMEONE = "someone";
    private static final String SOMEONE_2 = "someone2";
    private static final List<String> leftActor = List.of(
        G_MAN,
        HELL_HOUND
    );
    private static final List<String> rightActor = List.of(
        ME,
        SOMEONE,
        SOMEONE_2
    );

    public Tutorial(Skin skin) {
        super(ID, skin, StoryType.MAIN, false);
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
        switch (dialogNumber) {
            case 0:
                switch (optionNumber) {
                    case 0 -> {
                    }
                    case 1 -> SnowFight.player.addManual(new CupNoodle());
                    case 2 -> SnowFight.player.addSoldier(new Vanilla());
                }
            case 1:
                switch (optionNumber) {
                    case 0 -> SnowFight.player.setMoneyBy(50);
                    case 1 -> SnowFight.player.setMoneyBy(-100);
                }
        }
    }
}
