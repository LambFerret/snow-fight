package com.lambferret.game.manual;

import com.lambferret.game.constant.Rarity;

public class ExampleManual extends Manual {
    static {
        ID = "ExampleManual";
        texturePath = "exampleManual";
        description = "This is an example manual.";
        rarity = Rarity.COMMON;
        price = 100;
    }

    static String ID;
    static String texturePath;
    static String description;
    static Rarity rarity;
    static int price;


    public ExampleManual() {
        super(ID, texturePath, description, rarity, price, (byte) 0);
    }
}
