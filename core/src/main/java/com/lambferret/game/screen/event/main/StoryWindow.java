package com.lambferret.game.screen.event.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Null;
import com.lambferret.game.character.Character;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.component.CustomDialog;
import com.lambferret.game.constant.StoryType;
import com.lambferret.game.screen.event.EventWindow;
import com.lambferret.game.screen.ground.ShopScreen;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.dto.dialogue.DialogueNode;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class StoryWindow extends EventWindow {
    private static final Logger logger = LogManager.getLogger(StoryWindow.class.getName());
    public static final int CHARACTER_IMAGE_WIDTH = 300;
    public static final int CHARACTER_IMAGE_HEIGHT = 600;
    public static final int CHARACTER_IMAGE_PAD = 100;
    public static final float DIALOGUE_X = DIALOGUE_X_PAD;
    public static final float SPEAKER_X = DIALOGUE_X;
    public static final float DIALOGUE_Y = DIALOGUE_Y_PAD;
    public static final float SPEAKER_Y = DIALOGUE_Y + DIALOGUE_HEIGHT;

    private final Group leftSpeakers = new Group();
    private final Group rightSpeakers = new Group();

    private final Container<Dialog> dialogContainer = new Container<>();
    private int optionNumber;
    private final StoryType storyType;
    List<Character> leftActor = getLeftActor();
    List<Character> rightActor = getRightActor();

    public StoryWindow(String dialogueID, StoryType storyType) {
        super(dialogueID);
        this.storyType = storyType;

        this.addActor(rightSpeakers);
        this.addActor(leftSpeakers);
        this.addActor(dialogContainer);
        this.addActor(conversationContainer);
        this.addActor(namePlate);

        leftSpeakers.setSize(SPEAKERS_WIDTH, SPEAKERS_HEIGHT);
        rightSpeakers.setSize(SPEAKERS_WIDTH, SPEAKERS_HEIGHT);
        leftSpeakers.setPosition(SPEAKER_X, SPEAKER_Y);
        rightSpeakers.setPosition(GlobalSettings.currWidth - (SPEAKER_X + SPEAKERS_WIDTH), SPEAKER_Y);

        dialogContainer.setSize(DIALOGUE_WIDTH, DIALOGUE_HEIGHT);
        conversationContainer.setSize(DIALOGUE_WIDTH, DIALOGUE_HEIGHT);
        dialogContainer.setPosition(DIALOGUE_X, DIALOGUE_Y);
        conversationContainer.setPosition(DIALOGUE_X, DIALOGUE_Y);

        dialogContainer.setVisible(false);

        setContext();
    }

    protected void setContext() {
        setSpeakers();
        setConversationBox();
    }

    private void setSpeakers() {
        CustomButton characterImage;
        for (int i = leftActor.size() - 1; i >= 0; i--) {
            Character character = leftActor.get(i);
            characterImage = GlobalUtil.simpleButton(new TextureRegionDrawable(character.render()), character.getName());
            characterImage.setPosition(i * CHARACTER_IMAGE_PAD, 0);
            characterImage.setSize(CHARACTER_IMAGE_WIDTH, CHARACTER_IMAGE_HEIGHT);
            leftSpeakers.addActor(characterImage);
        }
        int i = 0;
        for (Character character : rightActor) {
            characterImage = GlobalUtil.simpleButton(new TextureRegionDrawable(character.render()), character.getName());
            characterImage.setPosition(i++ * CHARACTER_IMAGE_PAD, 0);
            characterImage.setSize(CHARACTER_IMAGE_WIDTH, CHARACTER_IMAGE_HEIGHT);
            rightSpeakers.addActor(characterImage);
        }
    }

    @Override
    protected void setDialog(int number) {
        dialogContainer.setVisible(true);
        conversationContainer.setVisible(false);
        CustomDialog dialog = new CustomDialog(options.get(number).getDescription(), number) {
            @Override
            protected void result(Object object) {
                optionNumber = (int) object;
                setResult(number, optionNumber);
            }
        };

        setOption(dialog, number);

        //TODO set dialog UI style here
        dialogContainer.setActor(dialog);
        dialogContainer.fill();
        dialog.setMovable(false);
        dialog.setResizable(false);
    }

    private void setResult(int dialogNumber, int optionNumber) {
        solveEvent(dialogNumber, optionNumber);
        dialogContainer.setVisible(false);
        conversationContainer.setVisible(true);
        typewriteText.startTyping();
        setTypewriter(dialogueNode.select(optionNumber));
    }

    protected void setOption(CustomDialog dialog, int number) {
        Skin skin = GlobalSettings.skin;
        Table buttonTable = new Table(skin);
        int id = dialog.getID();
        for (int i = 0; i < options.get(number).getElement().size(); i++) {
            TextButton option = new TextButton(options.get(number).getElement().get(i), skin);
            int finalI = i;
            if (isOutOfCondition(id, i)) {
                option.addListener(Input.click(() -> {
                    if (isOutOfCondition(id, finalI)) {
                        option.addAction(ShopScreen.rejectAction());
                    } else {
                        setResult(id, finalI);
                    }
                }));
            } else {
                option.addListener(Input.click(() -> setResult(id, finalI)));
            }
            buttonTable.add(option).row();
        }
        dialog.getButtonTable().clear();
        dialog.getButtonTable().add(buttonTable);
    }

    @Override
    protected void setTypewriter(DialogueNode node) {
        super.setTypewriter(node);
        highlightSpeaker(node.getCharacter());
    }

    private void highlight(Actor actor) {
        actor.setColor(Color.WHITE);
        actor.setZIndex(100);
    }

    private void blur(Actor actor) {
        actor.setColor(Color.GRAY);
        actor.setZIndex(1);
    }

    private void highlightSpeaker(@Null Character character) {
        if (character == null) {
            for (Actor actor : leftSpeakers.getChildren()) {
                blur(actor);
            }
            for (Actor actor : rightSpeakers.getChildren()) {
                blur(actor);
            }
            return;
        }
        for (int i = 0; i < leftActor.size() + rightActor.size(); i++) {
            if (i < leftActor.size()) {
                if (leftActor.get(i).equals(character)) {
                    setNameplatePos(true);
                    highlight(leftSpeakers.getChild(i));
                } else {
                    blur(leftSpeakers.getChild(i));
                }
            } else {
                if (rightActor.get(i - leftActor.size()).equals(character)) {
                    setNameplatePos(false);
                    highlight(rightSpeakers.getChild(i - leftActor.size()));
                } else {
                    blur(rightSpeakers.getChild(i - leftActor.size()));
                }
            }
        }
    }

    public abstract List<Character> getLeftActor();

    public abstract List<Character> getRightActor();

    public abstract void solveEvent(int dialogNumber, int optionNumber);

    abstract boolean isOutOfCondition(int dialog, int optionNumber);

}
