package com.lambferret.game.screen.event;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.lambferret.game.component.TypewriterLabel;
import com.lambferret.game.constant.StoryType;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.dto.dialogue.Dialogue;
import com.lambferret.game.text.dto.dialogue.DialogueNode;
import com.lambferret.game.text.dto.dialogue.Option;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class EventWindow extends Window {
    private static final Logger logger = LogManager.getLogger(EventWindow.class.getName());

    private static final float DIALOGUE_WIDTH = GlobalSettings.currWidth;
    private static final float DIALOGUE_HEIGHT = 300.0F;
    private static final float SPEAKERS_WIDTH = GlobalSettings.currWidth / 3.0F;
    private static final float SPEAKERS_HEIGHT = GlobalSettings.currHeight - DIALOGUE_HEIGHT;
    private List<String> leftActor;
    private List<String> rightActor;
    private final Skin skin;
    protected final Dialogue currentEvent;
    private DialogueNode dialogueNode;
    private final Container<Label> conversationContainer = new Container<>();
    private final Container<Dialog> dialogContainer = new Container<>();
    private final HorizontalGroup leftSpeakers = new HorizontalGroup();
    private final HorizontalGroup rightSpeakers = new HorizontalGroup();
    private TypewriterLabel textLabel;
    private List<Option> options;
    private int optionNumber;
    private boolean isFirstTime = true;

    public abstract List<String> getLeftActor();

    public abstract List<String> getRightActor();

    public abstract DialogueNode getDialogueNode();

    public abstract void solveEvent(int dialogNumber, int optionNumber);

    public EventWindow(String dialogueID, Skin skin, StoryType storyType, boolean repeatable) {
        super(dialogueID, skin);
        this.clear();
        this.skin = GlobalSettings.skin;
        this.currentEvent = DialogueFinder.get(dialogueID);
        this.dialogueNode = getDialogueNode();

        if (GlobalSettings.isDev) this.setDebug(true, true);

        this.setPosition(0, 0);
        this.setSize(GlobalSettings.currWidth, GlobalSettings.currHeight);
        this.setColor(0, 0, 0, 0.3F);

        this.addActor(rightSpeakers);
        this.addActor(leftSpeakers);
        this.addActor(dialogContainer);
        this.addActor(conversationContainer);

        leftSpeakers.setSize(SPEAKERS_WIDTH, SPEAKERS_HEIGHT);
        rightSpeakers.setSize(SPEAKERS_WIDTH, SPEAKERS_HEIGHT);
        leftSpeakers.setPosition(0, DIALOGUE_HEIGHT);
        rightSpeakers.setPosition(DIALOGUE_WIDTH - SPEAKERS_WIDTH, DIALOGUE_HEIGHT);
        dialogContainer.setSize(DIALOGUE_WIDTH, DIALOGUE_HEIGHT);
        conversationContainer.setSize(DIALOGUE_WIDTH, DIALOGUE_HEIGHT);
        dialogContainer.setPosition(0, 0);
        conversationContainer.setPosition(0, 0);

        dialogContainer.setVisible(false);

    }

    protected void setContext() {
        this.options = currentEvent.getOption();

        setSpeakers();
        setConversationBox();
    }

    private void setSpeakers() {
        this.leftActor = getLeftActor();
        this.rightActor = getRightActor();

        TextButton button;
        leftSpeakers.setDebug(true, true);
        rightSpeakers.setDebug(true, true);

        for (int i = leftActor.size() - 1; i >= 0; i--) {
            button = new TextButton(leftActor.get(i), skin);
            button.setPosition(20, 0);
            button.setSize(300, 500);
            leftSpeakers.addActor(button);
            leftSpeakers.pad(5);
        }

        for (String name : rightActor) {
            button = new TextButton(name, skin);
            button.setPosition(20, 0);
            button.setSize(300, 500);
            rightSpeakers.addActor(button);
            rightSpeakers.pad(5);
        }
    }

    private void setConversationBox() {
        textLabel = new TypewriterLabel("");

        conversationContainer.setActor(textLabel);
        conversationContainer.align(Align.center);
        conversationContainer.fill();

        setTypewriter(dialogueNode);

        conversationContainer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (textLabel.isEnd()) {
                    if (dialogueNode.isDialog()) {
                        dialogContainer.setVisible(true);
                        conversationContainer.setVisible(false);
                        setDialog(dialogueNode.getDialogNumber());
                    } else {
                        if (dialogueNode.isEnd()) {
                            exitEvent();
                        } else {
                            setTypewriter(dialogueNode.select(0));
                        }
                    }
                } else {
                    textLabel.instantShow();
                }
            }
        });
    }

    private void setDialog(int number) {

        var dialog = new Dialog("dialog", GlobalSettings.skin) {
            @Override
            protected void result(Object object) {
                optionNumber = (int) object;
                solveEvent(number, optionNumber);
                dialogContainer.setVisible(false);
                conversationContainer.setVisible(true);
                textLabel.startTyping();
                setTypewriter(dialogueNode.select(optionNumber));
            }
        };

        for (int i = 0; i < options.get(number).getElement().size(); i++) {
            dialog.button(options.get(number).getElement().get(i), i);
        }

        dialogContainer.setActor(dialog);
        dialog.setMovable(false);
        dialog.setResizable(false);

//        thisDialog.getContentTable().add(speakerLabel).pad(10).row();
//        thisDialog.getContentTable().add(textLabel).width(300).pad(10).row();

    }

    private void setTypewriter(DialogueNode node) {
//        highlightSpeaker(node.getSpeaker());
        this.dialogueNode = node;
        textLabel = new TypewriterLabel(node.getText());
        conversationContainer.setActor(textLabel);
        textLabel.setAlignment(Align.center);
        textLabel.setWrap(true);
        textLabel.startTyping();
    }

    private void highlightSpeaker(short index) {
        for (Actor speaker : leftSpeakers.getChildren()) {
            speaker.setVisible(false);
        }
        for (Actor speaker : rightSpeakers.getChildren()) {
            speaker.setVisible(false);
        }
        if (index < 0) {
            leftSpeakers.getChild(leftSpeakers.getChildren().size + index).setVisible(true);
        } else {
            rightSpeakers.getChild(index).setVisible(true);
        }
    }

    public String getContextByIndex(int index) {
        return this.currentEvent.getContext().get(index);
    }

    public boolean isFirstTime() {
        return isFirstTime;
    }

    private void exitEvent() {
        isFirstTime = false;
        this.remove();
    }

}
