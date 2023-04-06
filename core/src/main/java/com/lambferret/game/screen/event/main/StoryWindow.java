package com.lambferret.game.screen.event.main;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.character.Character;
import com.lambferret.game.constant.StoryType;
import com.lambferret.game.screen.event.EventWindow;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.dto.dialogue.DialogueNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class StoryWindow extends EventWindow {
    private static final Logger logger = LogManager.getLogger(StoryWindow.class.getName());

    private final HorizontalGroup leftSpeakers = new HorizontalGroup();
    private final HorizontalGroup rightSpeakers = new HorizontalGroup();

    private final Container<Dialog> dialogContainer = new Container<>();
    private int optionNumber;
    private final StoryType storyType;

    public StoryWindow(String dialogueID, Skin skin, StoryType storyType, boolean repeatable) {
        super(dialogueID, skin);
        this.storyType = storyType;

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
        setSpeakers();
        setConversationBox();
    }

    private void setSpeakers() {
        List<Character> leftActor = getLeftActor();
        List<Character> rightActor = getRightActor();

        // TODO : make this as Character
        ImageTextButton characterPlate;
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.font = GlobalSettings.font;
        leftSpeakers.setDebug(true, true);
        rightSpeakers.setDebug(true, true);

        for (int i = leftActor.size() - 1; i >= 0; i--) {
            Character character = leftActor.get(i);
            style.imageUp = new TextureRegionDrawable(character.render());
            characterPlate = new ImageTextButton(character.getName(), style);
            characterPlate.setPosition(20, 0);
            characterPlate.setSize(300, 500);
            leftSpeakers.addActor(characterPlate);
            leftSpeakers.pad(5);
        }

        for (Character character : rightActor) {
            style.imageUp = new TextureRegionDrawable(character.render());
            characterPlate = new ImageTextButton(character.getName(), style);
            characterPlate.setPosition(20, 0);
            characterPlate.setSize(300, 500);
            rightSpeakers.addActor(characterPlate);
            rightSpeakers.pad(5);
        }
    }

    @Override
    protected void setDialog(int number) {
        dialogContainer.setVisible(true);
        conversationContainer.setVisible(false);
        Dialog dialog = new Dialog("dialog", GlobalSettings.skin) {
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

        setOption(dialog, number);

        dialogContainer.setActor(dialog);
        dialog.setMovable(false);
        dialog.setResizable(false);

//        thisDialog.getContentTable().add(speakerLabel).pad(10).row();
//        thisDialog.getContentTable().add(textLabel).width(300).pad(10).row();

    }

    abstract void setOption(Dialog dialog, int number);

    @Override
    protected void setTypewriter(DialogueNode node) {
        super.setTypewriter(node);
//        highlightSpeaker(node.getCharacter());
    }

    private void highlightSpeaker(Character character) {
        for (Actor speaker : leftSpeakers.getChildren()) {
            speaker.setVisible(false);
        }
        for (Actor speaker : rightSpeakers.getChildren()) {
            speaker.setVisible(false);
        }
//        if (index < 0) {
//            leftSpeakers.get.getChild().setVisible(true);
//        } else {
//            rightSpeakers.getChild(index).setVisible(true);
//        }
    }

    public boolean isFirstTime() {
        return isFirstTime;
    }

    public abstract List<Character> getLeftActor();

    public abstract List<Character> getRightActor();

    public abstract void solveEvent(int dialogNumber, int optionNumber);

}
