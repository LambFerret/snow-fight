package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Plate;
import com.lambferret.game.screen.ground.MapButton;
import com.lambferret.game.setting.GlobalSettings;

import java.util.ArrayList;
import java.util.List;

public class MapOverlay {
    private static final float WIDTH_FIXED = 300.0F;
    private static final float HEIGHT_FIXED = 300.0F;
    public static final float s = GlobalSettings.scale;
    private List<MapButton> buttons = new ArrayList<>();
    private static Plate plate;
    private int count;

    private float x;
    private float y;
    private float width;
    private float height;


    public MapOverlay() {
        x = GlobalSettings.WIDTH - (WIDTH_FIXED * s);
        y = 0.0F;
        width = WIDTH_FIXED * s;
        height = HEIGHT_FIXED * s;

        int index = 0;
        plate = new Plate(x, y, width, height);
        buttons.add(new MapButton(MapButton.GroundButtonAction.RECRUIT, index++));
        buttons.add(new MapButton(MapButton.GroundButtonAction.TRAINING_GROUND, index++));
        buttons.add(new MapButton(MapButton.GroundButtonAction.SHOP, index++));

        MapButton.setTotal(buttons.size());
    }

    public void create() {
        for (MapButton b : buttons) {
            b.create();
        }
    }


    public void render(SpriteBatch batch) {
        plate.render(batch);
        for (MapButton b : buttons) {
            b.render(plate, batch);
        }
    }

    public void update() {
        for (MapButton b : buttons) {
            b.update();
        }
    }
}
