package com.lambferret.game.command;

public class FieldInstructor extends Command {

    static {
//        text = LocalizeConfig.soldierText;
        texturePath = "fieldInstructor";
        cost = 1;
        affectToUp = 10;
        affectToMiddle = 0;
        affectToDown = -10;
        isPersistentEffect = true;
        isReusable = true;
        isEvil = false;
        target = Target.SOLDIER;
    }

    public static final String ID = FieldInstructor.class.getSimpleName();
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


    public FieldInstructor() {
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
