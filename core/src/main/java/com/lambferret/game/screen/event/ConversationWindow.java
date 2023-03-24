package com.lambferret.game.screen.event;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.lambferret.game.component.TypewriterLabel;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.dto.dialogue.DialogContext;
import com.lambferret.game.text.dto.dialogue.Dialogue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class ConversationWindow extends Window {
    private static final Logger logger = LogManager.getLogger(ConversationWindow.class.getName());

    private static final float DIALOGUE_WIDTH = GlobalSettings.currWidth;
    private static final float DIALOGUE_HEIGHT = 300.0F;
    private static final float SPEAKERS_WIDTH = GlobalSettings.currWidth / 3.0F;
    private static final float SPEAKERS_HEIGHT = GlobalSettings.currHeight - DIALOGUE_HEIGHT;

    private final Skin skin;
    private final Dialogue currentDialogue;
    private final Container<Label> conversationContainer = new Container<>();
    private final Container<Dialog> dialogContainer = new Container<>();
    private final HorizontalGroup leftSpeakers = new HorizontalGroup();
    private final HorizontalGroup rightSpeakers = new HorizontalGroup();
    private final Window thisWindow;
    private Label speakerLabel;
    private TypewriterLabel textLabel;
    private Dialog thisDialog;
    private int currentLine = 0;
    private boolean isDialog = false;
    private short dialogNumber = -1;


    public ConversationWindow(String dialogueID, Skin skin) {
        super(dialogueID, skin);
        this.clear();
        this.skin = GlobalSettings.skin;
        this.currentDialogue = DialogueFinder.get(dialogueID);
        this.thisWindow = this;

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

        setContext();
    }

    private void setContext() {
        setSpeakers(currentDialogue.getActorLeft(), currentDialogue.getActorRight());
        setConversations(currentDialogue.getContext());
        setDialog(currentDialogue.getOption());
    }

    private void setSpeakers(List<String> left, List<String> right) {
        TextButton button;
        leftSpeakers.setDebug(true, true);
        rightSpeakers.setDebug(true, true);

        for (int i = left.size() - 1; i >= 0; i--) {
            button = new TextButton(left.get(i), skin);
            button.setPosition(20, 0);
            button.setSize(300, 500);
            leftSpeakers.addActor(button);
            leftSpeakers.pad(5);
        }

        for (String name : right) {
            button = new TextButton(name, skin);
            button.setPosition(20, 0);
            button.setSize(300, 500);
            rightSpeakers.addActor(button);
            rightSpeakers.pad(5);
        }
    }

    private void setConversations(List<DialogContext> context) {
        currentLine = 0;
        speakerLabel = new Label("speakerLabel", skin);
        textLabel = new TypewriterLabel("");

        conversationContainer.setActor(textLabel);
        conversationContainer.align(Align.center);
        conversationContainer.fill();

        setLine(context.get(currentLine));

        conversationContainer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (textLabel.isEnd()) {
                    if (currentLine < context.size() - 1) {
                        setLine(context.get(++currentLine));
                    } else {
                        thisWindow.remove();
                    }
                } else {
                    textLabel.instantShow();
                }
            }
        });
    }

    private void setDialog(List<List<String>> options) {
        this.thisDialog = new Dialog("dialog", GlobalSettings.skin);
        dialogContainer.setActor(this.thisDialog);
        thisDialog.setMovable(false);
        thisDialog.setResizable(false);

//        thisDialog.getContentTable().add(speakerLabel).pad(10).row();
//        thisDialog.getContentTable().add(textLabel).width(300).pad(10).row();

        thisDialog.button("Close", false);
    }

    private void setLine(DialogContext lineInfo) {
        dialogNumber = -1;
        if (lineInfo.getOptionIndex() != 0) {
            isDialog = true;
            dialogNumber = lineInfo.getOptionIndex();
        } else {
            isDialog = false;
        }
        highlightSpeaker(lineInfo.getSpeaker());
        textLabel = new TypewriterLabel(lineInfo.getText());
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

}
