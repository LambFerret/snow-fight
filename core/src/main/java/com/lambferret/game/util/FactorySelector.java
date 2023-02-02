package com.lambferret.game.util;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class FactorySelector extends Sprite {
    Vector2 pos;
    int id;

    public FactorySelector(Vector2 pos, int id) {
        super();
        this.pos = pos;
        switch (id) {
            case 1:
                this.set(Resources.getInstance().factoryP1);
        }
    }
}
