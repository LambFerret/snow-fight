package com.lambferret.game.util;

import com.badlogic.gdx.graphics.Texture;
import com.lambferret.game.component.constant.Rank;

public class TextureFinder {

    AssetFinder assetFinder;

    public static Texture rank(Rank rank) {
        return switch (rank) {
            case RECRUIT -> AssetFinder.getTexture("rank1");
            case PRIVATE -> AssetFinder.getTexture("rank2");
            case PRIVATE_FIRST_CLASS -> AssetFinder.getTexture("rank3");
            case CORPORAL -> AssetFinder.getTexture("rank4");
            case SERGEANT -> AssetFinder.getTexture("rank5");
            case STAFF_SERGEANT -> AssetFinder.getTexture("rank6");
            case SERGEANT_FIRST_CLASS -> AssetFinder.getTexture("rank7");
            case FIRST_SERGEANT -> AssetFinder.getTexture("rank8");
            case SERGENT_MAJOR -> AssetFinder.getTexture("rank9");
            case COMMAND_SERGEANT_MAJOR -> AssetFinder.getTexture("rank10");
            case SERGEANT_MAJOR_OF_THE_ARMY -> AssetFinder.getTexture("rank11");
            case MASTER_WARRANT_OFFICER -> AssetFinder.getTexture("rank12");
            case SECOND_LIEUTENANT -> AssetFinder.getTexture("rank13");
            case FIRST_LIEUTENANT -> AssetFinder.getTexture("rank14");
            case CAPTAIN -> AssetFinder.getTexture("rank15");
            case MAJOR -> AssetFinder.getTexture("rank16");
            case LIEUTENANT_COLONEL -> AssetFinder.getTexture("rank17");
            case COLONEL -> AssetFinder.getTexture("rank18");
            case BRIGADIER_GENERAL -> AssetFinder.getTexture("rank19");
            case MAJOR_GENERAL -> AssetFinder.getTexture("rank20");
            case LIEUTENANT_GENERAL -> AssetFinder.getTexture("rank21");
            case GENERAL -> AssetFinder.getTexture("rank22");
        };
    }
}
