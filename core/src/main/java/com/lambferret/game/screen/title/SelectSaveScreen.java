package com.lambferret.game.screen.title;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SelectSaveScreen {

    private static final Logger logger = LogManager.getLogger(SelectSaveScreen.class.getName());
    private Hitbox plate;
    private List<SaveSlot> slots = new ArrayList<>();
    protected static boolean isLoad;

    public SelectSaveScreen() {
        plate = new Hitbox();
        plate.move(GlobalSettings.currWidth / 2.0F, GlobalSettings.currHeight * 2 / 3.0F);
        plate.resize(700, 700);

        for (int i = 0; i < 3; i++) {
            slots.add(new SaveSlot(plate, i));
        }
    }

    public void create() {

    }

    public void render() {

        for (SaveSlot slot : slots) {
            slot.render();
        }
    }

    public void update() {

        for (SaveSlot slot : slots) {
            slot.update();
        }
    }
}
