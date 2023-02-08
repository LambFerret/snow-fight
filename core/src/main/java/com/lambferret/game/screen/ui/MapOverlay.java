package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.screen.ground.MapButton;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MapOverlay implements Overlay {
    private static final Logger logger = LogManager.getLogger(MapOverlay.class.getName());

    private static final float WIDTH_FIXED = 400.0F;
    private static final float HEIGHT_FIXED = 300.0F;
    public static final float s = GlobalSettings.scale;
    private List<MapButton> buttons = new ArrayList<>();
    private static Hitbox plate;
    private int count;

    private float x;
    private float y;
    private float width;
    private float height;
    private static MapButton selected;
    private static boolean isSelected;


    public MapOverlay() {
        x = GlobalSettings.WIDTH - (WIDTH_FIXED * s);
        y = 0.0F;
        width = WIDTH_FIXED * s;
        height = HEIGHT_FIXED * s;

        int index = 0;
        plate = new Hitbox(x, y, width, height);
        buttons.add(new MapButton(MapButton.GroundButtonAction.RECRUIT, index++));
        buttons.add(new MapButton(MapButton.GroundButtonAction.TRAINING_GROUND, index++));
        buttons.add(new MapButton(MapButton.GroundButtonAction.SHOP, index++));

        MapButton.setTotal(buttons.size());
    }

    @Override
    public void render(SpriteBatch batch) {
        plate.render();
        boolean isPrevious = false;

        for (MapButton b : buttons) {
            if (!plate.isHovered) {
                b.zoomMode = MapButton.ZoomMode.N;
            } else {
                b.zoomMode = MapButton.ZoomMode.OUT;
                if (b.box.isHovered) {
                    b.isPreviousZoomed = isPrevious;
                    isPrevious = !isPrevious;
                    b.zoomMode = MapButton.ZoomMode.IN;
                } else {
                    b.isPreviousZoomed = isPrevious;
                }
            }
        }
        for (MapButton button : buttons) {
            button.render(plate, batch);
        }

    }

    @Override
    public void hide() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void update(float delta) {
        for (MapButton b : buttons) {
            b.update(delta);
        }
        plate.update(delta);

    }
}
