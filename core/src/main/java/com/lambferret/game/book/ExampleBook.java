package com.lambferret.game.book;

import com.lambferret.game.constant.Rarity;

public class ExampleBook extends Book {
    static {
        ID = "ExampleBook";
        texturePath = "exampleBook";
        description = "This is an example book.";
        rarity = Rarity.COMMON;
        price = 100;
    }

    static String ID;
    static String texturePath;
    static String description;
    static Rarity rarity;
    static int price;


    public ExampleBook() {
        super(ID, texturePath, description, rarity, price, (byte) 0);
    }
}
