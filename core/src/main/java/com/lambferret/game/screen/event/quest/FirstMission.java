package com.lambferret.game.screen.event.quest;

import com.lambferret.game.SnowFight;
import com.lambferret.game.character.Character;
import com.lambferret.game.quest.TutorialQuest;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.dialogue.Dialogue;
import com.lambferret.game.text.dto.dialogue.DialogueNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FirstMission extends QuestWindow {
    private static final Logger logger = LogManager.getLogger(FirstMission.class.getName());

    public static final String ID;
    private static final List<Character> actors = List.of(ME, BOSS);

    public FirstMission() {
        super(ID);
    }

    @Override
    protected DialogueNode getDialogueNode() {
        DialogueNode titleNode = new DialogueNode(context.get(0), BOSS);
        DialogueNode node1 = new DialogueNode(context.get(1), BOSS, 0);
        DialogueNode node1Option1 = new DialogueNode(context.get(2), ME);
        DialogueNode node1Option2 = new DialogueNode(context.get(3), ME);
        DialogueNode node1Option3 = new DialogueNode(context.get(4), ME);

        titleNode.addChild(node1);
        node1.addChild(node1Option1);
        node1.addChild(node1Option2);
        node1.addChild(node1Option3);

        return titleNode;
    }

    @Override
    protected List<Character> getActor() {
        return actors;
    }

    @Override
    protected void solveEvent(int optionNumber) {
        switch (optionNumber) {
            case 0, 1 -> {
                SnowFight.player.addQuest(new TutorialQuest());
            }
            case 2 -> {
                SnowFight.player.setMiddleAffinityBy(-50);
            }
        }
    }

    @Override
    protected Dialogue setText() {
        return LocalizeConfig.dialogText.getQUEST().get(ID);
    }

    static {
        ID = FirstMission.class.getSimpleName();
    }

}
