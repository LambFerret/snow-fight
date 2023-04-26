package com.lambferret.game.screen.event;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.component.TypewriterLabel;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.dto.dialogue.DialogueNode;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class EventWindow extends Group {
    private static final Logger logger = LogManager.getLogger(EventWindow.class.getName());

    protected static final float DIALOGUE_X_PAD = 50;
    protected static final float DIALOGUE_Y_PAD = 30;

    protected static final float DIALOGUE_WIDTH = GlobalSettings.currWidth - DIALOGUE_X_PAD * 2;
    protected static final float DIALOGUE_HEIGHT = 300.0F - DIALOGUE_Y_PAD;
    protected static final float SPEAKERS_WIDTH = GlobalSettings.currWidth / 3.0F;
    protected static final float SPEAKERS_HEIGHT = GlobalSettings.currHeight - DIALOGUE_HEIGHT;
    protected static final float ENDING_TEXT_HEIGHT = 300.0F;
    protected static final float ENDING_TEXT_WIDTH = GlobalSettings.currWidth;
    protected static final float NAMEPLATE_WIDTH = 200.0F;
    protected static final float NAMEPLATE_HEIGHT = 50.0F;
    protected static final float NAMEPLATE_X_LEFT = 200.0F;
    protected static final float NAMEPLATE_X_RIGHT = GlobalSettings.currWidth - (NAMEPLATE_X_LEFT + NAMEPLATE_WIDTH);
    protected static final float NAMEPLATE_Y = 300.0F;

    protected DialogueNode dialogueNode;
    protected final Container<Label> conversationContainer = new Container<>();
    protected TypewriterLabel typewriteText;
    protected CustomButton namePlate;
    protected Image background;


    public EventWindow(String eventID) {
        Overlay.hideAll();
        this.clear();

        this.dialogueNode = getDialogueNode();

        if (GlobalSettings.isDev) this.setDebug(true, true);

        this.setPosition(0, 0);
        this.setSize(GlobalSettings.currWidth, GlobalSettings.currHeight);

        background = new Image(AssetFinder.getTexture("blurBackground"));
        background.setSize(this.getWidth(), this.getHeight());
        this.addActor(background);

        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = new TextureRegionDrawable(AssetFinder.getTexture("namePlate"));
        style.font = GlobalSettings.font;
        namePlate = new CustomButton("", style);
        namePlate.setSize(NAMEPLATE_WIDTH, NAMEPLATE_HEIGHT);

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
        conversationContainer.setBackground(new TextureRegionDrawable(AssetFinder.getTexture("conversationWindow")));
        setTypewriter(dialogueNode);

        conversationContainer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (typewriteText.isEnd()) {
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
                    typewriteText.instantShow();
                }
            }
        });
    }

    private void exitEvent() {
        Overlay.reset();
        this.remove();
    }

    protected void setNameplatePos(boolean isLeft) {
        if (isLeft) {
            namePlate.setPosition(NAMEPLATE_X_LEFT, NAMEPLATE_Y);
        } else {
            namePlate.setPosition(NAMEPLATE_X_RIGHT, NAMEPLATE_Y);
        }
    }

    protected abstract DialogueNode getDialogueNode();

    protected abstract void setDialog(int dialogNumber);

    protected void setSpeakerLabel(String name) {
        this.namePlate.setText(name);
    }

}
