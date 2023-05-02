package com.lambferret.game.util;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.lambferret.game.util.crypt.j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public static NinePatchDrawable getNinePatchDrawable(String name, int pad) {
        return new NinePatchDrawable(new NinePatch(new TextureRegion(AssetFinder.getTexture(name + ".9")), pad, pad, pad, pad));
    }

}
