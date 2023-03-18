package com.lambferret.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lambferret.game.SnowFight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AssetFinder {
    private static final Logger logger = LogManager.getLogger(AssetFinder.class.getName());
    private static final String SEP = "/"; //File.separator;
    private static final String MUSIC = Type.MUSIC.name().toLowerCase() + SEP;
    private static final String SOUND = Type.SOUND.name().toLowerCase() + SEP;
    private static final String SKIN = Type.SKIN.name().toLowerCase() + SEP;
    private static final String TEXTURE = Type.TEXTURE.name().toLowerCase() + SEP;
    private static final String ATLAS = Type.ATLAS.name().toLowerCase() + SEP;
    private static final String FONT = Type.FONT.name().toLowerCase() + SEP;
    private static final String EFFECT = Type.EFFECT.name().toLowerCase() + SEP;
    private static final String PIXMAP = Type.PIXMAP.name().toLowerCase() + SEP;
    private static final String REGION = Type.REGION.name().toLowerCase() + SEP;
    private static final String MODEL = Type.MODEL.name().toLowerCase() + SEP;
    private static final String LEVEL = Type.LEVEL.name().toLowerCase() + SEP;

    public static AssetManager manager;

    static {
        manager = SnowFight.assetManager;
    }

    public static Music getMusic(String name) {
        return manager.get(MUSIC + name, Music.class);
    }

    public static Sound getSound(String name) {
        return manager.get(SOUND + name, Sound.class);
    }

    public static Skin getSkin(String name) {
        return manager.get(SKIN + name, Skin.class);
    }

    public static Texture getTexture(String name) {
        String fileName = TEXTURE + name + ".png";
        if (!Gdx.files.absolute(fileName).exists()) {
            logger.info("this texture doesn't exist. please check : " + name);
            fileName = TEXTURE + "yellow.png";
        }
        return manager.get(fileName, Texture.class);
    }

    /**
     * font 를 freetype 으로 로드
     * 폰트에 관한 옵션은 여기서 처리
     */
    public static BitmapFont getFont(String name) {
        String fileName = FONT + name + ".ttf";
        if (!Gdx.files.absolute(fileName).exists()) {
            fileName = FONT + name + ".otf";
        }

        FileHandle file = Gdx.files.internal(fileName);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(file);
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 12;
        params.color = Color.BLACK;

        BitmapFont font = generator.generateFont(params);
        generator.dispose();
        return font;
    }

    public static TextureAtlas getAtlas(String name) {
        return manager.get(ATLAS + name, TextureAtlas.class);
    }

    public static ParticleEffect getEffect(String name) {
        return manager.get(EFFECT + name, ParticleEffect.class);
    }

    public static Pixmap getPixmap(String name) {
        return manager.get(PIXMAP + name, Pixmap.class);
    }

    public static PolygonRegion getRegion(String name) {
        return manager.get(REGION + name, PolygonRegion.class);
    }

    public static Model getModel(String name) {
        return manager.get(MODEL + name, Model.class);
    }

    public static TiledMap getLevel(String name) {
        return manager.get(LEVEL + name, TiledMap.class);
    }

    private enum Type {
        MUSIC,
        SOUND,
        SKIN,
        TEXTURE,
        ATLAS,
        FONT,
        EFFECT,
        PIXMAP,
        REGION,
        MODEL,
        LEVEL,
    }
}
