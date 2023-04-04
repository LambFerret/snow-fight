package com.lambferret.game.screen.event.ending;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lambferret.game.text.dto.dialogue.DialogueNode;

public class NormalEnding extends EndingWindow {
    public NormalEnding(String endingID, Skin skin) {
        super(endingID, skin);
    }

    @Override
    protected DialogueNode getDialogueNode() {
        return null;
    }

}
