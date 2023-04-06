package com.lambferret.game.character;

import com.lambferret.game.text.LocalizeConfig;

public class ImmediateBoss extends Character {
    private static final String ID;
    private static final String name;

    public ImmediateBoss() {
        super(ID, name);
    }

    static {
        ID = ImmediateBoss.class.getSimpleName();
        name = LocalizeConfig.characterText.getID().get(ID).getName();
    }

}
