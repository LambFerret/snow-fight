package com.lambferret.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Resources {
    public TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(""));
    public Sprite factoryP1 = atlas.createSprite("factoryP1");

    public static Resources instance;

    public static Resources getInstance() {
        if (instance == null) {
            instance = new Resources();
        }
        return instance;
    }
}
