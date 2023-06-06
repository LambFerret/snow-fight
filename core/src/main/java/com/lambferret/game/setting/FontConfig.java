package com.lambferret.game.setting;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FontConfig {
    private static final Logger logger = LogManager.getLogger(FontConfig.class.getName());
    public static BitmapFont titleButtonFont;
    public static BitmapFont uiFont;
    public static BitmapFont soldierFont;
    public static BitmapFont commandFont;
    public static BitmapFont manualFont;
    public static BitmapFont characterFont;
    public static BitmapFont dialogFont;
    public static BitmapFont questFont;

    public static void init() {
        FreeTypeFontGenerator generator = null;
        switch (GlobalSettings.language) {
            case EN -> {
                generator = AssetFinder.getFont("ubuntu");
                FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                parameter.size = 28;
                parameter.color = Color.WHITE;
                // The width of the border to add around each glyph.
                parameter.borderWidth = 1;
                // The color of the border.
                parameter.borderColor = Color.BLACK;
                // The gamma correction for rendering the border.
//                parameter.borderGamma = 0.5f;
                // The X offset in pixels of the shadow.
//                parameter.shadowOffsetX = 1;
                // The Y offset in pixels of the shadow.
//                parameter.shadowOffsetY = 1;
                // The color of the shadow.
//                parameter.shadowColor = Color.BLACK;
                // The gamma correction for rendering the font.
//                parameter.gamma = 0.5f;
                // Extra spacing between characters.
//                parameter.spaceX = 1;
                // Extra spacing above and below characters.
//                parameter.spaceY = 1;
                //  padLeft, padBottom, padRight Extra padding around each glyph.
//                parameter.padTop = 1;
                titleButtonFont = generator.generateFont(parameter);
                parameter.size = 14;
                uiFont = generator.generateFont(parameter); //AssetFinder.getFont("ATypewriterForMe");
                soldierFont = uiFont; //AssetFinder.getFont("ATypewriterForMe");
                commandFont = uiFont; //AssetFinder.getFont("ATypewriterForMe");
                manualFont = uiFont; //AssetFinder.getFont("ATypewriterForMe");
                characterFont = uiFont; //AssetFinder.getFont("ATypewriterForMe");
                dialogFont = uiFont; //AssetFinder.getFont("ATypewriterForMe");
                questFont = uiFont; //AssetFinder.getFont("ATypewriterForMe");
                GlobalSettings.font = uiFont;
            }
            case KR -> {
                generator = AssetFinder.getFont("nanum");
                FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                parameter.size = 14;
                parameter.color = Color.WHITE;
                titleButtonFont = generator.generateFont(parameter);
                uiFont = titleButtonFont; // AssetFinder.getFont("nanum");
                soldierFont = titleButtonFont; // AssetFinder.getFont("nanum");
                commandFont = titleButtonFont; // AssetFinder.getFont("nanum");
                manualFont = titleButtonFont; // AssetFinder.getFont("nanum");
                characterFont = titleButtonFont; // AssetFinder.getFont("nanum");
                dialogFont = titleButtonFont; // AssetFinder.getFont("nanum");
                questFont = titleButtonFont; // AssetFinder.getFont("nanum");
                GlobalSettings.font = titleButtonFont;
            }
            case JP -> {
                generator = AssetFinder.getFont("ShipporiSans");
                FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                parameter.size = 12;
                parameter.color = Color.BLACK;
                titleButtonFont = generator.generateFont(parameter);
                uiFont = titleButtonFont; //AssetFinder.getFont("2");
                soldierFont = titleButtonFont; //AssetFinder.getFont("");
                commandFont = titleButtonFont; //AssetFinder.getFont("");
                manualFont = titleButtonFont; //AssetFinder.getFont("");
                characterFont = titleButtonFont; //AssetFinder.getFont("");
                dialogFont = titleButtonFont; //AssetFinder.getFont("");
                questFont = titleButtonFont; //AssetFinder.getFont("");
                GlobalSettings.font = titleButtonFont;
            }
            case RU -> {
                generator = AssetFinder.getFont("kremlin");
                FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                parameter.size = 12;
                parameter.color = Color.BLACK;
                titleButtonFont = generator.generateFont(parameter);
                uiFont = titleButtonFont; //AssetFinder.getFont("");
                soldierFont = titleButtonFont; //AssetFinder.getFont("");
                commandFont = titleButtonFont; //AssetFinder.getFont("");
                manualFont = titleButtonFont; //AssetFinder.getFont("");
                characterFont = titleButtonFont; //AssetFinder.getFont("");
                dialogFont = titleButtonFont; //AssetFinder.getFont("");
                questFont = titleButtonFont; //AssetFinder.getFont("");
                GlobalSettings.font = titleButtonFont;
            }
        }
        generator.dispose();
    }

}
