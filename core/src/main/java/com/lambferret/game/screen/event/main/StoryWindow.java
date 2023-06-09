package com.lambferret.game.screen.event.main;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Null;
import com.lambferret.game.character.Character;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.component.WindowDialog;
import com.lambferret.game.constant.StoryType;
import com.lambferret.game.screen.event.EventWindow;
import com.lambferret.game.screen.ground.ShopScreen;
import com.lambferret.game.setting.FontConfig;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.dto.dialogue.DialogueNode;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public abstract class StoryWindow extends EventWindow {
    private static final Logger logger = LogManager.getLogger(StoryWindow.class.getName());
    public static final int CHARACTER_IMAGE_WIDTH = 300;
    public static final int CHARACTER_IMAGE_HEIGHT = 600;
    public static final int CHARACTER_IMAGE_PAD = 20;
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

    private void initLeftSpeakers() {
        CustomButton characterImage;
        leftSpeakers.clearChildren();
        for (int i = leftActor.size() - 1; i >= 0; i--) {
            characterImage = plate(leftActor.get(i));
            characterImage.setIndex(i);
            characterImage.setColor(Color.GRAY);
            leftSpeakers.addActor(characterImage);
        }
    }

    private void initRightSpeakers() {
        CustomButton characterImage;
        rightSpeakers.clearChildren();
        int j = 0;
        for (Character character : rightActor) {
            characterImage = plate(character);
            characterImage.setIndex(j++);
            characterImage.setColor(Color.GRAY);
            rightSpeakers.addActor(characterImage);
        }
    }

    public CustomButton plate(Character character) {
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.font = FontConfig.uiFont;
        style.up = new TextureRegionDrawable(character.render());
        CustomButton image = new CustomButton(character.getName(), style);
        image.setSize(CHARACTER_IMAGE_WIDTH, CHARACTER_IMAGE_HEIGHT);
        image.setID(character.getName());
        return image;
    }

    private void setSpeakers() {
        initLeftSpeakers();
        initRightSpeakers();
    }

    @Override
    protected void setDialog(int number) {
        dialogContainer.setVisible(true);
        conversationContainer.setVisible(false);
        dialogContainer.fill();
        WindowDialog dialog = new WindowDialog(options.get(number).getDescription(), number) {
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

    protected void setOption(WindowDialog dialog, int number) {
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
            buttonTable.add(option).height(DIALOGUE_HEIGHT / 10F).uniformX().pad(5).left().fillX().row();
        }
        dialog.getButtonTable().clear();
        dialog.getContentTable().add(buttonTable).height(DIALOGUE_HEIGHT).fill().pad(5);

    }

    @Override
    protected void setTypewriter(DialogueNode node) {
        super.setTypewriter(node);
        highlightSpeaker(node.getCharacter());
    }

    private void highlight(CustomButton actor, boolean isLeft) {
        setNameplatePos(isLeft);
        float moveAmount = (isLeft ? 1 : -1) * (SPEAKERS_WIDTH / 5F + actor.getIndex() * CHARACTER_IMAGE_PAD);
        actor.addAction(Actions.parallel(
                Actions.scaleTo(1.1F, 1.1F, 0.1F),
                Actions.color(Color.WHITE, 0.1F),
                Actions.moveTo(moveAmount, 10, 0.1F)
            )
        );
    }

    private void deHighlight(CustomButton actor) {
        actor.addAction(Actions.parallel(
                Actions.scaleTo(1F, 1F, 0.1F),
                Actions.color(Color.GRAY, 0.1F),
                Actions.moveTo(actor.getIndex() * CHARACTER_IMAGE_PAD, 0, 0)
            )
        );
    }

    private Character lastHighlightedInLeft;
    private Character lastHighlightedInRight;
    private Character currentHighlightedInLeft;
    private Character currentHighlightedInRight;

    private boolean isSameActor(Character character, Character lastHighlighted) {
        return lastHighlighted != null && lastHighlighted.getName().equals(character.getName());
    }

    private void updateHighlightedActor(Character character, boolean isLeft) {
        if (isLeft) {
            currentHighlightedInLeft = character;
            lastHighlightedInLeft = character;
        } else {
            currentHighlightedInRight = character;
            lastHighlightedInRight = character;
        }
    }

    private void deHighlightAllActors() {
        for (Actor actor : leftSpeakers.getChildren()) {
            deHighlight((CustomButton) actor);
        }
        for (Actor actor : rightSpeakers.getChildren()) {
            deHighlight((CustomButton) actor);
        }
    }

    private void handleHighlight(Character character, Group actorsList, boolean isLeft) {
        Character currentHighlighted = isLeft ? currentHighlightedInLeft : currentHighlightedInRight;
        for (Actor a : actorsList.getChildren()) {
            CustomButton plate = (CustomButton) a;
            if (plate.compare(character.getName())) {
                if (currentHighlighted != null) {
                    deHighlight(findButtonByCharacter(currentHighlighted, actorsList));
                }
                highlight(plate, isLeft);
                updateHighlightedActor(character, isLeft);
                break;
            }
        }
    }

    private CustomButton findButtonByCharacter(Character character, Group actorsList) {
        for (Actor a : actorsList.getChildren()) {
            CustomButton plate = (CustomButton) a;
            if (plate.compare(character.getName())) {
                return plate;
            }
        }
        return null;
    }

    private void highlightSpeaker(@Null Character character) {
        if (character == null) {
            deHighlightAllActors();
            return;
        }
        for (Group actorsList : Arrays.asList(leftSpeakers, rightSpeakers)) {
            boolean isLeft = actorsList == leftSpeakers;
            Character lastHighlighted = isLeft ? lastHighlightedInLeft : lastHighlightedInRight;
            if (!isSameActor(character, lastHighlighted)) {
                handleHighlight(character, actorsList, isLeft);
            }
        }
    }

    public abstract List<Character> getLeftActor();

    public abstract List<Character> getRightActor();

    public abstract void solveEvent(int dialogNumber, int optionNumber);

    abstract boolean isOutOfCondition(int dialog, int optionNumber);

}
