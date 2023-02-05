package com.lambferret.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class AssetLoader {

    private Map<String, Object> map = new HashMap<>();
    private Array<Texture> array = new Array<>();
    public AssetLoader() {
//        map.put("LOAD", )

    }

    public void load(String name) {
        SnowFight.assetManager.getAll(Texture.class, array);
    }

}
