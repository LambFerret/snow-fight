package com.lambferret.game.text;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.dto.BookText;
import com.lambferret.game.text.dto.OptionText;
import com.lambferret.game.text.dto.SoldierText;
import com.lambferret.game.text.dto.UIText;
import com.lambferret.game.util.AssetFinder;
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
    public static BookText bookText;
    private static final Gson gson = new GsonBuilder().create();

    public static void init() {
        GlobalSettings.font = switch (GlobalSettings.language) {
            case KR -> AssetFinder.getFont("KR_nanumBold");
            case EN -> AssetFinder.getFont("EN_Archivo_Condensed-Light");
            case JP -> AssetFinder.getFont("JP_ShipporiSans");
            case RU -> AssetFinder.getFont("RU_kremlin");
        };

        optionText = gson.fromJson(getTextFromJSON(Context.OPTION), OptionText.class);
        uiText = gson.fromJson(getTextFromJSON(Context.UI), UIText.class);
        soldierText = gson.fromJson(getTextFromJSON(Context.SOLDIER), SoldierText.class);
        bookText = gson.fromJson(getTextFromJSON(Context.BOOK), BookText.class);
    }

    private static String getTextFromJSON(Context content) {
        String code = GlobalSettings.language.toString();
        String path = LOCALIZATION + SEP + code + SEP + content + JSON;
        return Gdx.files.internal(path).readString(String.valueOf(StandardCharsets.UTF_8));
    }

    // UI, OPTION 등등 현지화 작업이 필요한 분류의 폴더명
    private enum Context {
        UI, OPTION, SOLDIER, BOOK,
    }
}
