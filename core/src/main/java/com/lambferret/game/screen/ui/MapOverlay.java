package com.lambferret.game.screen.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.screen.ground.MapButton;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.CustomInputProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MapOverlay extends Overlay {
    private static final Logger logger = LogManager.getLogger(MapOverlay.class.getName());

    private static final float WIDTH_FIXED = 400.0F;
    private static final float HEIGHT_FIXED = 300.0F;
    public static final float s = GlobalSettings.scale;
    private final List<MapButton> buttons = new ArrayList<>();
    private final Hitbox plate;
    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private boolean isHidden;


    public MapOverlay() {
        x = GlobalSettings.WIDTH - (WIDTH_FIXED * s);
        y = 0.0F;
        width = WIDTH_FIXED * s;
        height = HEIGHT_FIXED * s;
        isHidden = false;
        int index = 0;
        plate = new Hitbox(x, y, width, height);
        buttons.add(new MapButton(MapButton.GroundButtonAction.RECRUIT, index++));
        buttons.add(new MapButton(MapButton.GroundButtonAction.TRAINING_GROUND, index++));
        buttons.add(new MapButton(MapButton.GroundButtonAction.SHOP, index++));

        MapButton.setTotal(buttons.size());
    }

    @Override
    public void render(SpriteBatch batch) {
        plate.render(batch);
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
            button.render(batch, plate);
        }
    }

    @Override
    public void hide() {
        if (isHidden) return;
        this.plate.hide(Hitbox.Direction.RIGHT);
    }

    @Override
    public void show() {
        if (!isHidden) return;
        this.plate.show();
    }

    @Override
    public void update(float delta) {
        for (MapButton button : buttons) {
            button.update(delta);
        }
        plate.update(delta);
        if (!isHidden && CustomInputProcessor.getPressedKey() == Input.Keys.Y) {
            this.hide();
            isHidden = true;
        } else if (isHidden && CustomInputProcessor.getPressedKey() == Input.Keys.U) {
            this.show();
            isHidden = false;
        } else if (CustomInputProcessor.getPressedKey() == Input.Keys.Q) {
            plate.move(100, 100);
        }
    }
}
