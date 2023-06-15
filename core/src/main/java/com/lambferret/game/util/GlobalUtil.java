package com.lambferret.game.util;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.crypt.j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GlobalUtil {
    private static final Logger logger = LogManager.getLogger(GlobalUtil.class.getName());

    public static String encrypt(String var0) {
        String var2 = null;

        try {
            var2 = j.a(var0);
        } catch (Exception var3) {
            var3.printStackTrace();
            System.out.println("$ Encrypt Error Occurred!");
        }

        return var2;
    }

    public static String decrypt(String var0) {
        String var2 = null;

        try {
            var2 = j.b(var0);
        } catch (Exception var3) {
            var3.printStackTrace();
            System.out.println("$ Decrypt Error Occurred");
        }

        return var2;
    }

    public static Pixmap readyPixmap(Texture texture) {
        TextureData data = texture.getTextureData();
        data.prepare();
        return data.consumePixmap();
    }

    public static Pixmap regionToPixmap(TextureAtlas.AtlasRegion region) {
        Pixmap regionPixmap = readyPixmap(region.getTexture());
        Pixmap result = new Pixmap(region.getRegionWidth(), region.getRegionHeight(), Pixmap.Format.RGBA8888);
        result.drawPixmap(regionPixmap, 0, 0, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight());
        regionPixmap.dispose();
        return result;
    }

    public static NinePatchDrawable getNinePatchDrawableFromTexture(String name, int pad) {
        return new NinePatchDrawable(new NinePatch(new TextureRegion(AssetFinder.getTexture(name + ".9")), pad, pad, pad, pad));
    }

    private static ImageTextButton.ImageTextButtonStyle style() {
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.font = GlobalSettings.font;
        return style;
    }

    /**
     * region to button
     *
     * @param region AtlasRegion
     * @param text   Label
     * @return CustomButton
     */
    public static CustomButton simpleButton(TextureAtlas.AtlasRegion region, String text) {
        ImageTextButton.ImageTextButtonStyle style = style();
        style.up = new TextureRegionDrawable(region);
        return new CustomButton(text, style);
    }

    /**
     * region to button
     *
     * @param region AtlasRegion
     * @return CustomButton
     */
    public static CustomButton simpleButton(TextureAtlas.AtlasRegion region) {
        ImageTextButton.ImageTextButtonStyle style = style();
        style.up = new TextureRegionDrawable(region);
        return new CustomButton("", style);
    }

    public static CustomButton simpleButton(String texturePath, String text) {
        ImageTextButton.ImageTextButtonStyle style = style();
        style.up = new TextureRegionDrawable(AssetFinder.getTexture(texturePath));
        return new CustomButton(text, style);
    }

    public static CustomButton simpleButton(TextureRegionDrawable texture, String text) {
        ImageTextButton.ImageTextButtonStyle style = style();
        style.up = texture;
        return new CustomButton(text, style);
    }

    public static CustomButton simpleButton(String texturePath) {
        return simpleButton(texturePath, "");
    }

    public static String strPad(String str) {
        return String.format("%-15s", str);
    }

    public static String listToString(List<?> list) {
        StringBuilder sb = new StringBuilder();
        for (Object o : list) {
            sb.append(o.getClass().getSimpleName()).append(", ");
        }
        return sb.toString();
    }

    public static StringBuilder addNewlines(String str, int lineLength) {
        String[] words = str.split(" ");
        StringBuilder result = new StringBuilder();
        int wordCount = 0;

        for (String word : words) {
            result.append(word).append(" ");
            wordCount++;
            if (wordCount % lineLength == 0) {
                result.append("\n");
            }
        }
        return result;
    }

    public static StringBuilder addNewlines(String str) {
        StringBuilder result = new StringBuilder();
        result.append(str.replace(", ", ",\n"));
        return result;
    }

}
