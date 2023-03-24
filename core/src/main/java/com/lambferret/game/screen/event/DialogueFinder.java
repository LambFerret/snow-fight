package com.lambferret.game.screen.event;

import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.DialogText;
import com.lambferret.game.text.dto.dialogue.Dialogue;

/**
 * 대화를 찾아준다 <p>
 * 새 대화창 생성시 여기다가 등록후
 *
 * @see DialogText
 * 여기에도 등록
 */
public class DialogueFinder {
    private static final DialogText dialogText;

    static {
        dialogText = LocalizeConfig.dialogText;
    }

    public static Dialogue get(String ID) {
        return switch (EventTitle.valueOf(ID)) {
            case TUTORIAL -> dialogText.getTutorial();
        };
    }

    enum EventTitle {
        TUTORIAL,
    }

}
