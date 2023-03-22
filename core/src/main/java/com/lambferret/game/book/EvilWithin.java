package com.lambferret.game.book;

public class EvilWithin extends Book {

    static {
//        text = LocalizeConfig.soldierText;
        texturePath = "evilWithin";
        cost = 2;
        affectToUp = -30;
        affectToMiddle = -10;
        affectToDown = 30;
        isPersistentEffect = false;
        isReusable = false;
        isEvil = true;

    }

    public static final String ID = EvilWithin.class.getSimpleName();
    //    private static final SoldierText text;
    static String texturePath;
    static int cost;
    static int affectToUp;
    static int affectToMiddle;
    static int affectToDown;
    static boolean isPersistentEffect;
    static boolean isReusable;
    static boolean isEvil;


    public EvilWithin() {
        super(
            ID,
            texturePath,
            cost,
            affectToUp,
            affectToMiddle,
            affectToDown,
            isPersistentEffect,
            isReusable,
            isEvil
        );
    }
}
