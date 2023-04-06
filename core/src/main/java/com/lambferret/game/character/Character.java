package com.lambferret.game.character;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lambferret.game.util.AssetFinder;

public abstract class Character {
    private final String ID;
    private final String name;
    private final String texturePath;


    protected Character(String ID, String name) {
        this.ID = ID;
        this.name = name;
        this.texturePath = ID;
    }

    public String getName() {
        return name;
    }

    public TextureRegion render() {
        return new TextureRegion(AssetFinder.getTexture(texturePath));
    }

}
