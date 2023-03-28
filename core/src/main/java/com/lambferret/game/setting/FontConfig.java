package com.lambferret.game.setting;

import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FontConfig {
    private static final Logger logger = LogManager.getLogger(FontConfig.class.getName());

    public static void init() {
        GlobalSettings.font = switch (GlobalSettings.language) {
            case KR -> AssetFinder.getFont("KR_nanumBold");
            case EN -> AssetFinder.getFont("EN_Archivo_Condensed-Light");
            case JP -> AssetFinder.getFont("JP_ShipporiSans");
            case RU -> AssetFinder.getFont("RU_kremlin");
        };

    }


}
