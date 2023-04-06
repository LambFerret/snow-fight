package com.lambferret.game.screen.event.ending;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lambferret.game.screen.event.EventWindow;
import com.lambferret.game.text.dto.dialogue.DialogueNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class EndingWindow extends EventWindow {
    private static final Logger logger = LogManager.getLogger(EndingWindow.class.getName());

    public EndingWindow(String endingID, Skin skin) {
        super(endingID, skin);

        conversationContainer.setSize(ENDING_TEXT_WIDTH, ENDING_TEXT_HEIGHT);
        conversationContainer.setPosition(0, 0);

    }

    protected abstract DialogueNode getDialogueNode();

    @Override
    protected void setDialog(int dialogNumber) {
        // no dialog
    }
}
