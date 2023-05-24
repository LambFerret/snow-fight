package com.lambferret.game.screen.event;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.lambferret.game.character.Character;
import com.lambferret.game.character.*;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.component.TypewriterLabel;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.dto.dialogue.Dialogue;
import com.lambferret.game.text.dto.dialogue.DialogueNode;
import com.lambferret.game.text.dto.dialogue.Option;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class EventWindow extends Group {
    private static final Logger logger = LogManager.getLogger(EventWindow.class.getName());

    protected static final int DIALOGUE_X_PAD = 50;
    protected static final int DIALOGUE_Y_PAD = 30;
    protected static final int DIALOGUE_WIDTH = GlobalSettings.currWidth - DIALOGUE_X_PAD * 2;
    protected static final int DIALOGUE_HEIGHT = 300 - DIALOGUE_Y_PAD;
    protected static final int SPEAKERS_WIDTH = GlobalSettings.currWidth / 3;
    protected static final int SPEAKERS_HEIGHT = GlobalSettings.currHeight - DIALOGUE_HEIGHT;
    protected static final int ENDING_TEXT_HEIGHT = 300;
    protected static final int ENDING_TEXT_WIDTH = GlobalSettings.currWidth;
    protected static final int NAMEPLATE_WIDTH = 200;
    protected static final int NAMEPLATE_HEIGHT = 50;
    protected static final int NAMEPLATE_X_LEFT = 200;
    protected static final int NAMEPLATE_X_RIGHT = GlobalSettings.currWidth - (NAMEPLATE_X_LEFT + NAMEPLATE_WIDTH);
    protected static final int NAMEPLATE_Y = 300;
    protected static final Character ME = new Me();
    protected static final Character BOSS = new ImmediateBoss();
    protected static final Character CHOCO = new Choco();
    protected static final Character CHILI = new Chili();

    protected DialogueNode dialogueNode;
    protected static Dialogue text;
    protected static List<Option> options;
    protected static List<String> context;
    protected final Container<Label> conversationContainer = new Container<>();
    protected TypewriterLabel typewriteText;
    protected CustomButton namePlate;
    protected Image background;

    public EventWindow(String eventID) {
        var text = setText();
        options = text.getOption();
        context = text.getContext();
        Overlay.hideAll();
        this.clear();

        this.dialogueNode = getDialogueNode();

        this.setPosition(0, 0);
        this.setSize(GlobalSettings.currWidth, GlobalSettings.currHeight);

        background = new Image(AssetFinder.getTexture("blurBackground"));
        background.setSize(this.getWidth(), this.getHeight());
        this.addActor(background);

        namePlate = GlobalUtil.simpleButton("namePlate");
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

        conversationContainer.addListener(Input.click(() -> {
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
        }));
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

    protected abstract Dialogue setText();

    protected void setSpeakerLabel(String name) {
        this.namePlate.setText(name);
    }

}
