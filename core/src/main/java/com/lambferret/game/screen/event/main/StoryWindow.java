package com.lambferret.game.screen.event.main;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.lambferret.game.constant.StoryType;
import com.lambferret.game.screen.event.EventWindow;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.dto.dialogue.DialogueNode;
import com.lambferret.game.text.dto.dialogue.Option;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class StoryWindow extends EventWindow {
    private static final Logger logger = LogManager.getLogger(StoryWindow.class.getName());

    private final HorizontalGroup leftSpeakers = new HorizontalGroup();
    private final HorizontalGroup rightSpeakers = new HorizontalGroup();

    private final Container<Dialog> dialogContainer = new Container<>();
    private List<Option> options;
    private int optionNumber;

    public StoryWindow(String dialogueID, Skin skin, StoryType storyType, boolean repeatable) {
        super(dialogueID, skin);

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

        setContext();

    }

    protected void setContext() {
        this.options = currentEvent.getOption();

        setSpeakers();
        setConversationBox();
    }

    private void setSpeakers() {
        List<String> leftActor = getLeftActor();
        List<String> rightActor = getRightActor();

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

    protected void setDialog(int number) {
        dialogContainer.setVisible(true);
        conversationContainer.setVisible(false);
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

    @Override
    protected void setTypewriter(DialogueNode node) {
        super.setTypewriter(node);
//        highlightSpeaker(node.getSpeaker());
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

    public boolean isFirstTime() {
        return isFirstTime;
    }

    public abstract List<String> getLeftActor();

    public abstract List<String> getRightActor();

    public abstract void solveEvent(int dialogNumber, int optionNumber);

}
