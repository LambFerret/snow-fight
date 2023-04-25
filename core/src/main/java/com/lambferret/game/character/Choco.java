package com.lambferret.game.character;

import com.lambferret.game.text.LocalizeConfig;

public class Choco extends Character {
    private static final String ID;
    private static final String name;

    public Choco() {
        super(ID, name);
    }

    static {
        ID = Choco.class.getSimpleName();
        name = LocalizeConfig.characterText.getID().get(ID).getName();
    }

}
