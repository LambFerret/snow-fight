package com.lambferret.game.book;

public class ExampleBook extends Book {
    static {
        ID = "ExampleBook";
        texturePath = "exampleBook";
        description = "This is an example book.";
    }

    static String ID;
    static String texturePath;
    static String description;

    public ExampleBook() {
        super(ID, texturePath, description, (byte) 0);
    }
}
