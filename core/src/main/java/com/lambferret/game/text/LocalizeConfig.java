package com.lambferret.game.text;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.dto.OptionText;
import com.lambferret.game.text.dto.SoldierText;
import com.lambferret.game.text.dto.UIText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * .트리 계층 구조
 * └── Text(LocalizeConfig)/
 * ├── UI/
 * │   ├── title/
 * │   │   ├── components1
 * │   │   └── components2
 * │   └── ground
 * └── Option/
 * ├── optionComponents1
 * └── optionComponents2
 */
public class LocalizeConfig {
    private static final Logger logger = LogManager.getLogger(LocalizeConfig.class.getName());
    private static final String LOCALIZATION = "localization";
    private static final String JSON = ".json";
    private static final String SEP = File.separator;
    public static UIText uiText;
    public static OptionText optionText;
    public static SoldierText soldierText;
    private static final Gson gson = new GsonBuilder().create();

    public static void init() {
        optionText = gson.fromJson(getTextFromJSON(Context.OPTION), OptionText.class);
        uiText = gson.fromJson(getTextFromJSON(Context.UI), UIText.class);
        soldierText = gson.fromJson(getTextFromJSON(Context.SOLDIER), SoldierText.class);
    }

    private static String getTextFromJSON(Context content) {
        String code = GlobalSettings.language.toString();
        String path = LOCALIZATION + SEP + code + SEP + content + JSON;
        return Gdx.files.internal(path).readString(String.valueOf(StandardCharsets.UTF_8));
    }

    // UI, OPTION 등등 현지화 작업이 필요한 분류의 폴더명
    private enum Context {
        UI, OPTION, SOLDIER,
    }
}
