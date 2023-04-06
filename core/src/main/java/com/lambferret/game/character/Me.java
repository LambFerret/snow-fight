package com.lambferret.game.character;

import com.lambferret.game.text.LocalizeConfig;

public class Me extends Character {
    private static final String ID;
    private static final String name;

    public Me() {
        super(ID, name);
    }

    static {
        ID = Me.class.getSimpleName();
        name = LocalizeConfig.characterText.getID().get(ID).getName();
    }

}
