package com.lambferret.game.book;

public class Bunkering extends Book {

    static {
//        text = LocalizeConfig.soldierText;
        texturePath = "bunkering";
        cost = 1;
        affectToUp = 20;
        affectToMiddle = 0;
        affectToDown = 0;
        isPersistentEffect = true;
        isReusable = true;
        isEvil = false;
        target = Target.SOLDIER;

    }

    public static final String ID = Bunkering.class.getSimpleName();
    //    private static final SoldierText text;
    static String texturePath;
    static int cost;
    static int affectToUp;
    static int affectToMiddle;
    static int affectToDown;
    static boolean isPersistentEffect;
    static boolean isReusable;
    static boolean isEvil;
    static Target target;


    public Bunkering() {
        super(
            ID,
            texturePath,
            cost,
            affectToUp,
            affectToMiddle,
            affectToDown,
            isPersistentEffect,
            isReusable,
            isEvil,
            target
        );
    }
}
