package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.constant.Direction;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractOverlay {
    public static final Logger logger = LogManager.getLogger(AbstractOverlay.class.getName());

    public static final float OVERLAY_WIDTH = GlobalSettings.currWidth / 3.0F;
    public static final float OVERLAY_HEIGHT = 200.0F;
    public static final float BAR_HEIGHT = 50.0F;

    public abstract void create();

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch);

    public abstract void hide(Direction direction);

    public abstract void show(boolean instantly);
}
