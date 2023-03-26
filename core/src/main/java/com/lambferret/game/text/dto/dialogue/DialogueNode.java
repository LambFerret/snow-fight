package com.lambferret.game.text.dto.dialogue;

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

    private String text;
    private List<DialogueNode> children;
    private String characterName;
    private Expression characterExpression;
    private boolean isDialog;
    private int dialogNumber;

    public DialogueNode(String text, String characterName, Expression characterExpression) {
        this.text = text;
        this.children = new ArrayList<>();
        this.characterName = characterName;
        this.characterExpression = characterExpression;
        this.isDialog = false;
        this.dialogNumber = -1;
    }

    public DialogueNode(String text, String characterName, int dialogNumber) {
        this(text, characterName, IDLE, dialogNumber);
    }

    public DialogueNode(String text, String characterName, Expression characterExpression, int dialogNumber) {
        this(text, characterName, characterExpression);
        isDialog = true;
        this.dialogNumber = dialogNumber;
    }

    public DialogueNode(String text, String characterName) {
        this(text, characterName, IDLE);
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

    public int getDialogNumber() {
        return dialogNumber;
    }
}
