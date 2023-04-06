package com.lambferret.game.screen.event;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.lambferret.game.component.TypewriterLabel;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.dto.dialogue.DialogueNode;
import com.lambferret.game.util.AssetFinder;
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
    protected static final float NAMEPLATE_X = 200.0F;
    protected static final float NAMEPLATE_Y = 200.0F;
    protected static final float NAMEPLATE_WIDTH = 100.0F;
    protected static final float NAMEPLATE_HEIGHT = 100.0F;

    protected final Skin skin;
    protected DialogueNode dialogueNode;
    protected final Container<Label> conversationContainer = new Container<>();
    protected TypewriterLabel typewriteText;
    protected final ImageTextButton textLabel;

    protected boolean isFirstTime = true;

    public EventWindow(String eventID, Skin skin) {
        super(eventID, skin);
        this.clear();
        this.skin = skin;
        this.dialogueNode = getDialogueNode();

        if (GlobalSettings.isDev) this.setDebug(true, true);

        this.setPosition(0, 0);
        this.setSize(GlobalSettings.currWidth, GlobalSettings.currHeight);
        this.setColor(0, 0, 0, 0.3F);

        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = new TextureRegionDrawable(AssetFinder.getTexture("wei"));
        style.font = GlobalSettings.font;
        textLabel = new ImageTextButton("", style);
        textLabel.setPosition(NAMEPLATE_X, NAMEPLATE_Y);
        textLabel.setSize(NAMEPLATE_WIDTH, NAMEPLATE_HEIGHT);

        this.addActor(textLabel);

        this.addListener(new InputListener() {
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                return true;
            }
        });

    }

    protected void setTypewriter(DialogueNode node) {
        this.dialogueNode = node;
        setSpeakerLabel(dialogueNode.getCharacter().getName());
        typewriteText = new TypewriterLabel(dialogueNode.getText());
        conversationContainer.setActor(typewriteText);
        typewriteText.setAlignment(Align.center);
        typewriteText.setWrap(true);
        typewriteText.startTyping();
    }

    protected void setConversationBox() {
        typewriteText = new TypewriterLabel("");
        setSpeakerLabel(dialogueNode.getCharacter().getName());

        conversationContainer.setActor(typewriteText);
        conversationContainer.align(Align.center);
        conversationContainer.fill();

        setTypewriter(dialogueNode);

        conversationContainer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (typewriteText.isEnd()) {
                    if (dialogueNode.isDialog()) {
                        setSpeakerLabel("TODO make this name or direct this");
                        setDialog(dialogueNode.getDialogNumber());
                    } else {
                        if (dialogueNode.isEnd()) {
                            exitEvent();
                        } else {
                            setTypewriter(dialogueNode.select(0));
                        }
                    }
                } else {
                    typewriteText.instantShow();
                }
            }
        });
    }

    private void exitEvent() {
        isFirstTime = false;
        this.remove();
    }

    protected abstract DialogueNode getDialogueNode();

    protected abstract void setDialog(int dialogNumber);

    protected void setSpeakerLabel(String name) {
        this.textLabel.setText(name);
    }

}
