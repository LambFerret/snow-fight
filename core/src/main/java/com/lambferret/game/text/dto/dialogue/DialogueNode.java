package com.lambferret.game.text.dto.dialogue;

import com.lambferret.game.character.Character;
import com.lambferret.game.constant.Expression;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.lambferret.game.constant.Expression.IDLE;

@ToString
public class DialogueNode {
    private static final Logger logger = LogManager.getLogger(DialogueNode.class.getName());

    private final String text;
    private final List<DialogueNode> children;
    private final Character character;
    private final Expression characterExpression;
    private String backgroundName;
    private boolean isDialog;
    private int dialogNumber;

    public DialogueNode(String text, Character character, Expression characterExpression) {
        this.text = text;
        this.children = new ArrayList<>();
        this.character = character;
        this.characterExpression = characterExpression;
        this.isDialog = false;
        this.dialogNumber = -1;
    }

    public DialogueNode(String text, Character character) {
        this(text, character, IDLE);
    }

    public DialogueNode(String text, Character character, int dialogNumber) {
        this(text, character, IDLE, dialogNumber);
    }

    public DialogueNode(String text, Character character, Expression characterExpression, int dialogNumber) {
        this(text, character, characterExpression);
        isDialog = true;
        this.dialogNumber = dialogNumber;
    }

    public void addChild(DialogueNode child) {
        children.add(child);
    }

    public String getText() {
        return text;
    }

    public DialogueNode select(int index) {
        return children.get(index);
    }

    public boolean isDialog() {
        return isDialog;
    }

    public boolean isEnd() {
        return children.isEmpty();
    }

    public void setBackgroundName(String backgroundName) {
        this.backgroundName = backgroundName;
    }

    public Character getCharacter() {
        return character;
    }

    public int getDialogNumber() {
        return dialogNumber;
    }
}
