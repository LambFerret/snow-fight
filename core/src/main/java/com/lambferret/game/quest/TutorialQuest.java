package com.lambferret.game.quest;

import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.QuestInfo;

public class TutorialQuest extends Quest {

    public static final String ID;
    public static final QuestInfo INFO;

    public TutorialQuest() {
        super(ID, INFO.getName(), INFO.getDescription());
    }

    @Override
    public void getReword() {
        player.setSnowAmount(player.getSnowAmount() - 100);
    }

    static {
        ID = TutorialQuest.class.getSimpleName();
        INFO = LocalizeConfig.questText.getID().get(ID);
    }
}
