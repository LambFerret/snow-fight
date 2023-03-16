package com.lambferret.game.screen.ui;

import com.badlogic.gdx.Input;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.component.constant.Direction;
import com.lambferret.game.screen.ground.MapButton;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.CustomInputProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MapOverlay extends AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(MapOverlay.class.getName());

    private static final float MAP_WIDTH = 400.0F;
    private static final float MAP_HEIGHT = 300.0F;
    public static final float s = GlobalSettings.scale;
    private final List<MapButton> buttons = new ArrayList<>();
    private final Hitbox plate;
    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private boolean isHidden = false;


    public MapOverlay() {
        x = GlobalSettings.currWidth - (MAP_WIDTH * s);
        y = 0.0F;
        width = MAP_WIDTH * s;
        height = MAP_HEIGHT * s;
        int index = 0;
        plate = new Hitbox(x, y, width, height);
        buttons.add(new MapButton(MapButton.GroundButtonAction.RECRUIT, index++));
        buttons.add(new MapButton(MapButton.GroundButtonAction.TRAINING_GROUND, index++));
        buttons.add(new MapButton(MapButton.GroundButtonAction.SHOP, index++));

        MapButton.setTotal(buttons.size());
    }

    @Override
    public void create() {
    }

    @Override
    public void render() {
        plate.render();
        boolean isPrevious = false;

        for (MapButton button : buttons) {
            if (!plate.isHovered) {
                button.zoomMode = MapButton.ZoomMode.N;
            } else {
                button.zoomMode = MapButton.ZoomMode.OUT;
                if (button.box.isHovered) {
                    button.isPreviousZoomed = isPrevious;
                    isPrevious = !isPrevious;
                    button.zoomMode = MapButton.ZoomMode.IN;
                } else {
                    button.isPreviousZoomed = isPrevious;
                }
            }
            button.render(plate);
        }
    }

    @Override
    public void hide(Direction direction) {
        if (isHidden) return;
        this.plate.hide(direction);
        isHidden = true;
    }

    @Override
    public void show(boolean instantly) {
        if (!isHidden) return;
        if (instantly) {
            this.plate.showInstantly();
        } else {
            this.plate.show();
        }
        isHidden = false;
    }

    @Override
    public void update() {
        for (MapButton button : buttons) {
            button.update();
        }
        plate.update();
        if (!isHidden && CustomInputProcessor.pressedKey(Input.Keys.Y)) {
            this.hide(Direction.INSTANTLY);
        } else if (isHidden && CustomInputProcessor.pressedKey(Input.Keys.U)) {
            this.show(true);
        } else if (CustomInputProcessor.pressedKey(Input.Keys.I)) {
            plate.move(100, 100);
        }
    }
}
