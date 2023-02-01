package com.lambferret.game.text;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambferret.game.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class LocalizeConfig {

    private static final Logger logger = LogManager.getLogger(LocalizeConfig.class.getName());
    private static final String LOCALIZATION = "localization";
    private static final String JSON = ".json";
    private static final String SEP = File.separator;
    public static UIText uiText;
    private static final Gson gson = new GsonBuilder().create();

    public static void init() {
//        uiText = gson.fromJson(getTextFromJSON(Context.OPTION), UIText.class);
        uiText = gson.fromJson(getTextFromJSON(Context.UI), UIText.class);

    }

    private static String getTextFromJSON(Context content) {
        String code = GlobalSettings.language.toString();
        String path = LOCALIZATION + SEP + code + SEP + content + JSON;
        return Gdx.files.internal(path).readString(String.valueOf(StandardCharsets.UTF_8));
    }

    // UI, OPTION 등등 현지화 작업이 필요한 분류의 폴더명
    private enum Context {
        UI, OPTION,
    }
}
