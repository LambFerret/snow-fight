package com.lambferret.game.screen.event.quest;

import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.character.Character;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.constant.StoryType;
import com.lambferret.game.screen.event.EventWindow;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.GlobalUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class QuestWindow extends EventWindow {
    private static final Logger logger = LogManager.getLogger(QuestWindow.class.getName());

    private final HorizontalGroup leftSpeakers = new HorizontalGroup();
    private final Container<Dialog> dialogContainer = new Container<>();
    private int optionNumber;
    private final StoryType storyType;

    public QuestWindow(String eventID) {
        super(eventID);

        this.storyType = StoryType.QUEST;

        this.addActor(leftSpeakers);
        this.addActor(dialogContainer);
        this.addActor(conversationContainer);

        leftSpeakers.setSize(SPEAKERS_WIDTH, SPEAKERS_HEIGHT);
        leftSpeakers.setPosition(0, DIALOGUE_HEIGHT);

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
        List<Character> actor = getActor();

        CustomButton characterPlate;

        for (int i = actor.size() - 1; i >= 0; i--) {
            Character character = actor.get(i);
            characterPlate = GlobalUtil.simpleButton(new TextureRegionDrawable(character.render()), character.getName());
            characterPlate.setPosition(20, 0);
            characterPlate.setSize(300, 500);
            leftSpeakers.addActor(characterPlate);
            leftSpeakers.pad(5);
        }
    }

    @Override
    protected void setDialog(int dialogNumber) {
        dialogContainer.setVisible(true);
        conversationContainer.setVisible(false);
        Dialog dialog = new Dialog(options.get(0).getDescription(), GlobalSettings.skin) {
            @Override
            protected void result(Object object) {
                optionNumber = (int) object;
                solveEvent(optionNumber);
                dialogContainer.setVisible(false);
                conversationContainer.setVisible(true);
                typewriteText.startTyping();
                setTypewriter(dialogueNode.select(optionNumber));
            }
        };

        for (int i = 0; i < options.get(dialogNumber).getElement().size(); i++) {
            dialog.button(options.get(dialogNumber).getElement().get(i), i);
        }

        dialogContainer.setActor(dialog);
        dialog.setMovable(false);
        dialog.setResizable(false);
    }

    abstract protected List<Character> getActor();

    abstract protected void solveEvent(int optionNumber);

}
