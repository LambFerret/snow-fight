package com.lambferret.game.screen.event;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.lambferret.game.component.TypewriterLabel;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.dto.dialogue.Dialogue;
import com.lambferret.game.text.dto.dialogue.DialogueNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class EventWindow extends Window {
    private static final Logger logger = LogManager.getLogger(EventWindow.class.getName());

    protected static final float DIALOGUE_WIDTH = GlobalSettings.currWidth;
    protected static final float DIALOGUE_HEIGHT = 300.0F;
    protected static final float SPEAKERS_WIDTH = GlobalSettings.currWidth / 3.0F;
    protected static final float SPEAKERS_HEIGHT = GlobalSettings.currHeight - DIALOGUE_HEIGHT;
    protected static final float ENDING_TEXT_HEIGHT = 300.0F;
    protected static final float ENDING_TEXT_WIDTH = GlobalSettings.currWidth;

    protected final Skin skin;
    protected final Dialogue currentEvent;
    protected DialogueNode dialogueNode;
    protected final Container<Label> conversationContainer = new Container<>();
    protected TypewriterLabel textLabel;
    protected boolean isFirstTime = true;

    public EventWindow(String eventID, Skin skin) {
        super(eventID, skin);
        this.clear();
        this.skin = skin;
        this.currentEvent = DialogueFinder.get(eventID);
        this.dialogueNode = getDialogueNode();

        if (GlobalSettings.isDev) this.setDebug(true, true);

        this.setPosition(0, 0);
        this.setSize(GlobalSettings.currWidth, GlobalSettings.currHeight);
        this.setColor(0, 0, 0, 0.3F);

    }

    protected void setTypewriter(DialogueNode node) {
        this.dialogueNode = node;
        textLabel = new TypewriterLabel(node.getText());
        conversationContainer.setActor(textLabel);
        textLabel.setAlignment(Align.center);
        textLabel.setWrap(true);
        textLabel.startTyping();
    }

    protected void setConversationBox() {
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

    protected String getContextByIndex(int index) {
        return this.currentEvent.getContext().get(index);
    }

    private void exitEvent() {
        isFirstTime = false;
        this.remove();
    }

    protected abstract DialogueNode getDialogueNode();

    protected abstract void setDialog(int dialogNumber);

}
