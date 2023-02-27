package com.lambferret.game.screen.title;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.save.SaveLoader;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SelectSaveScreen {

    private static final Logger logger = LogManager.getLogger(SelectSaveScreen.class.getName());
    private Hitbox save1;
    private Hitbox save2;
    private Hitbox save3;
    private float offsetX;
    private float offsetY;
    private final float WIDTH = 60.0F;
    private final float HEIGHT = 30.0F;
    private final float HEIGHT_SPACING = 5.0F;


    public SelectSaveScreen() {
        save1 = new Hitbox();
        save2 = new Hitbox();
        save3 = new Hitbox();

        save1.resize(WIDTH, HEIGHT);
        save2.resize(WIDTH, HEIGHT);
        save3.resize(WIDTH, HEIGHT);

        save1.move(GlobalSettings.currWidth / 2.0F, HEIGHT);
        save2.move(GlobalSettings.currWidth / 2.0F, HEIGHT + HEIGHT_SPACING + HEIGHT);
        save3.move(GlobalSettings.currWidth / 2.0F, HEIGHT + HEIGHT_SPACING + HEIGHT + HEIGHT_SPACING + HEIGHT);

    }

    public void create() {

    }

    public void render(SpriteBatch batch) {
        save1.render(batch);
        save2.render(batch);
        save3.render(batch);

    }

    public void update(float delta) {
        save1.update(delta);
        save2.update(delta);
        save3.update(delta);

        if (save1.isClicked) {
            SaveLoader.load(0);
        } else if (save2.isClicked) {
            SaveLoader.load(1);
        } else if (save3.isClicked) {
            SaveLoader.load(2);
        }
    }

    private void action() {

    }
}
