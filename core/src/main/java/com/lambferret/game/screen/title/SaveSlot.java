package com.lambferret.game.screen.title;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.save.SaveLoader;
import com.lambferret.game.screen.ui.Overlay;
import com.lambferret.game.setting.ScreenConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveSlot {
    private static final Logger logger = LogManager.getLogger(SaveSlot.class.getName());

    private Hitbox box;

    private final float WIDTH = 60.0F;
    private final float HEIGHT = 30.0F;
    private final float HEIGHT_SPACING = 5.0F;
    private float offsetX;
    private float offsetY;
    private int slotNumber;

    public SaveSlot(Hitbox plate, int index) {
        slotNumber = index;
        offsetX = plate.getX();
        offsetY = plate.getY();

        box = new Hitbox();
        box.resize(WIDTH, HEIGHT);
        box.move(offsetX, offsetY + (HEIGHT + HEIGHT_SPACING) * -index + HEIGHT);

    }

    public void create(Hitbox plate) {
    }

    public void render(SpriteBatch batch) {
        if (SaveLoader.isSaveExist(slotNumber)) {
            this.box.render(batch);
        }
    }

    public void update(float delta) {
        this.box.update(delta);
        if (box.isClicked) {
            SaveLoader.load(this.slotNumber);
            Overlay.setPlayer();
            ScreenConfig.changeScreen = ScreenConfig.AddedScreen.GROUND_SCREEN;
        }
    }
}
