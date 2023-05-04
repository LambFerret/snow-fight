package com.lambferret.game.screen.event.ending;

import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.dialogue.Dialogue;
import com.lambferret.game.text.dto.dialogue.DialogueNode;

public class NormalEnding extends EndingWindow {
    public NormalEnding(String endingID) {
        super(endingID);
    }

    @Override
    protected DialogueNode getDialogueNode() {
        return null;
    }

    @Override
    protected Dialogue setText() {
        return LocalizeConfig.dialogText.getMAIN().get(" TODO ");
    }

}
