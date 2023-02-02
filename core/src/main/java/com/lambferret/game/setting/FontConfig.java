package com.lambferret.game.setting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FontConfig {
    private static final Logger logger = LogManager.getLogger(FontConfig.class.getName());
    private static FileHandle fontFile;
    Setting setting;

    public static void init() {
        fontFile = switch (GlobalSettings.language) {
            case KO -> Gdx.files.internal(" TODO ");
            case EN -> Gdx.files.internal(" TODO1 ");
            case JP -> Gdx.files.internal(" TODO2 ");
            case RU -> Gdx.files.internal(" TODO3 ");
        };

    }


}
