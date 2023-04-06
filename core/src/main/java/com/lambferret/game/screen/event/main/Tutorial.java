package com.lambferret.game.screen.event.main;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lambferret.game.SnowFight;
import com.lambferret.game.character.Character;
import com.lambferret.game.character.ImmediateBoss;
import com.lambferret.game.character.Me;
import com.lambferret.game.command.CupNoodle;
import com.lambferret.game.constant.StoryType;
import com.lambferret.game.soldier.Vanilla;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.dialogue.Dialogue;
import com.lambferret.game.text.dto.dialogue.DialogueNode;
import com.lambferret.game.text.dto.dialogue.Option;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Tutorial extends StoryWindow {
    private static final Logger logger = LogManager.getLogger(Tutorial.class.getName());

    private static final String ID;
    private static final Character ME = new Me();
    public static final Character BOSS = new ImmediateBoss();
    private static final List<Character> leftActor = List.of(
        ME
    );
    private static final List<Character> rightActor = List.of(
        BOSS
    );

    public Tutorial(Skin skin) {
        super(ID, skin, StoryType.MAIN, false);
    }

    @Override
    public DialogueNode getDialogueNode() {
        DialogueNode titleNode = new DialogueNode(context.get(0), ME);
        DialogueNode node1 = new DialogueNode(context.get(1), BOSS, 0);
        DialogueNode node1Option1 = new DialogueNode(context.get(2), ME);
        DialogueNode node1Option2 = new DialogueNode(context.get(3), ME);
        DialogueNode node1Option3 = new DialogueNode(context.get(4), ME);
        DialogueNode node1Option1answer = new DialogueNode(context.get(5), ME);
        DialogueNode node1Option2answer = new DialogueNode(context.get(6), ME);
        DialogueNode node1Option3answer = new DialogueNode(context.get(7), ME);
        DialogueNode node8 = new DialogueNode(context.get(8), ME, 1);
        DialogueNode node9 = new DialogueNode(context.get(9), BOSS);
        DialogueNode node10 = new DialogueNode(context.get(10), ME);
        DialogueNode node11 = new DialogueNode(context.get(11), BOSS);
        DialogueNode node12 = new DialogueNode(context.get(12), ME);

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

    @Override
    void setOption(Dialog dialog, int number) {
        for (int i = 0; i < options.get(number).getElement().size(); i++) {
            dialog.button(options.get(number).getElement().get(i), i);
        }
    }

    private static final Dialogue text;
    private static final List<String> context;
    private static final List<Option> options;

    static {
        ID = Tutorial.class.getSimpleName();
        text = LocalizeConfig.dialogText.getID().get(ID);
        context = text.getContext();
        options = text.getOption();
    }
}
