package com.lambferret.game.setting;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FontConfig {
    private static final Logger logger = LogManager.getLogger(FontConfig.class.getName());
    public static BitmapFont optionFont;
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
                generator = AssetFinder.getFont("ATypewriterForMe");
                FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                parameter.size = 14;
                parameter.color = Color.WHITE;
                // The width of the border to add around each glyph.
                parameter.borderWidth = 1;
                // The color of the border.
                parameter.borderColor = Color.BLACK;
                // The gamma correction for rendering the border.
                parameter.borderGamma = 0.5f;
                // The X offset in pixels of the shadow.
                parameter.shadowOffsetX = 3;
                // The Y offset in pixels of the shadow.
                parameter.shadowOffsetY = 3;
                // The gamma correction for rendering the font.
                parameter.gamma = 0.5f;
                // Extra spacing between characters.
                parameter.spaceX = 1;
                // Extra spacing above and below characters.
                parameter.spaceY = 1;
                //  padLeft, padBottom, padRight Extra padding around each glyph.
                parameter.padTop = 1;
                optionFont = generator.generateFont(parameter);
                uiFont = optionFont; //AssetFinder.getFont("ATypewriterForMe");
                soldierFont = optionFont; //AssetFinder.getFont("ATypewriterForMe");
                commandFont = optionFont; //AssetFinder.getFont("ATypewriterForMe");
                manualFont = optionFont; //AssetFinder.getFont("ATypewriterForMe");
                characterFont = optionFont; //AssetFinder.getFont("ATypewriterForMe");
                dialogFont = optionFont; //AssetFinder.getFont("ATypewriterForMe");
                questFont = optionFont; //AssetFinder.getFont("ATypewriterForMe");
                GlobalSettings.font = optionFont;
            }
            case KR -> {
                generator = AssetFinder.getFont("nanum");
                FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                parameter.size = 14;
                parameter.color = Color.WHITE;
                optionFont = generator.generateFont(parameter);
                uiFont = optionFont; // AssetFinder.getFont("nanum");
                soldierFont = optionFont; // AssetFinder.getFont("nanum");
                commandFont = optionFont; // AssetFinder.getFont("nanum");
                manualFont = optionFont; // AssetFinder.getFont("nanum");
                characterFont = optionFont; // AssetFinder.getFont("nanum");
                dialogFont = optionFont; // AssetFinder.getFont("nanum");
                questFont = optionFont; // AssetFinder.getFont("nanum");
                GlobalSettings.font = optionFont;
            }
            case JP -> {
                generator = AssetFinder.getFont("ShipporiSans");
                FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                parameter.size = 12;
                parameter.color = Color.BLACK;
                optionFont = generator.generateFont(parameter);
                uiFont = optionFont; //AssetFinder.getFont("2");
                soldierFont = optionFont; //AssetFinder.getFont("");
                commandFont = optionFont; //AssetFinder.getFont("");
                manualFont = optionFont; //AssetFinder.getFont("");
                characterFont = optionFont; //AssetFinder.getFont("");
                dialogFont = optionFont; //AssetFinder.getFont("");
                questFont = optionFont; //AssetFinder.getFont("");
                GlobalSettings.font = optionFont;
            }
            case RU -> {
                generator = AssetFinder.getFont("kremlin");
                FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
                parameter.size = 12;
                parameter.color = Color.BLACK;
                optionFont = generator.generateFont(parameter);
                uiFont = optionFont; //AssetFinder.getFont("");
                soldierFont = optionFont; //AssetFinder.getFont("");
                commandFont = optionFont; //AssetFinder.getFont("");
                manualFont = optionFont; //AssetFinder.getFont("");
                characterFont = optionFont; //AssetFinder.getFont("");
                dialogFont = optionFont; //AssetFinder.getFont("");
                questFont = optionFont; //AssetFinder.getFont("");
                GlobalSettings.font = optionFont;
            }
        }
        generator.dispose();
    }

}
