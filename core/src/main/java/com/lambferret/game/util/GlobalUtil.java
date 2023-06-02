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
import com.badlogic.gdx.utils.Array;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.crypt.j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

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
     * random image from regions
     *
     * @param regions regions
     * @return CustomButton that random image from regions
     */
    public static CustomButton simpleButton(Array<TextureAtlas.AtlasRegion> regions) {
        if (regions.size == 0) return simpleButton("");
        ImageTextButton.ImageTextButtonStyle style = style();
        Random random = new Random();
        style.up = new TextureRegionDrawable(regions.get(random.nextInt(regions.size)));
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

}
