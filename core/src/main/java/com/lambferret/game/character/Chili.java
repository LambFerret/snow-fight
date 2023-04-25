package com.lambferret.game.character;

import com.lambferret.game.text.LocalizeConfig;

public class Chili extends Character {
    private static final String ID;
    private static final String name;

    public Chili() {
        super(ID, name);
    }

    static {
        ID = Chili.class.getSimpleName();
        name = LocalizeConfig.characterText.getID().get(ID).getName();
    }

}
