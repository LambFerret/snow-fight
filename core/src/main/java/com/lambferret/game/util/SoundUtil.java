package com.lambferret.game.util;

import com.badlogic.gdx.audio.Sound;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.setting.ScreenConfig;

public class SoundUtil {
    private static Sound bgm;
    private static long bgmID;

    public static void playEffect(String name) {
        AssetFinder.getSound(name).play((GlobalSettings.effectVolume / 100F) * (GlobalSettings.masterVolume / 100F));
    }

    public static void setBGM(ScreenConfig.AddedScreen screen) {
        if (bgm != null) bgm.stop();
        switch (screen) {
            case TITLE_SCREEN -> bgm = AssetFinder.getSound("appassionata");
            case GROUND_SCREEN -> bgm = AssetFinder.getSound("appassionata");
            case PHASE_SCREEN -> bgm = AssetFinder.getSound("appassionata");
        }
        bgmID = bgm.loop((GlobalSettings.bgmVolume / 100F) * (GlobalSettings.masterVolume / 100F));
    }

    public static void setBgmVolume() {
        float value = (GlobalSettings.bgmVolume / 100F) * (GlobalSettings.masterVolume / 100F);
        if (bgm != null) bgm.setVolume(bgmID, value);
    }

    public static void pauseBGM() {
        if (bgm != null) bgm.pause();
    }

    public static void resumeBGM() {
        if (bgm != null) bgm.resume();
    }

}
