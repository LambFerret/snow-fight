package com.lambferret.game.screen.ground;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.GroundText;
import com.lambferret.game.util.AssetPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MapButton {
    public static final Interpolation INTER = Interpolation.pow5;
    private static final Logger logger = LogManager.getLogger(MapButton.class.getName());
    private static final float s = GlobalSettings.scale;
    private static final float zoomScale = 2.5F;
    private static final float speed = 30F;

    private static int total;
    private static GroundText text;
    private final int index;
    public Hitbox box;
    private String nameString;
    private GroundButtonAction action;
    private Hitbox plate;
    private boolean initSize = false;
    public boolean isPreviousZoomed = false;
    public ZoomMode zoomMode = ZoomMode.N;
    private Texture texture;
    private float x;
    private float width;
    private float drawX;
    private float drawW;

    public MapButton(GroundButtonAction action, int index) {
        this.index = index;
        this.action = action;
        this.box = new Hitbox();
        text = LocalizeConfig.uiText.getGroundText();
        setProperty();

    }

    public void render(SpriteBatch batch, Hitbox plate) {

        if (!initSize) {
            this.plate = plate;
            setInitSize();
            resetSize();
            initSize = !initSize;
        }
        switch (this.zoomMode) {
            case N -> {
                resetSize();
            }
            case IN -> {
                setZoomInSize();
            }
            case OUT -> {
                setZoomOutSize();
            }
        }

        box.move(this.drawX, plate.getY());
        box.resize(this.drawW, plate.getHeight());
        batch.setColor(1, 1, 1, 1);
        batch.draw(texture, this.drawX, plate.getY(), this.drawW, plate.getHeight());
        this.box.render(batch);
    }


    public void update(float delta) {
        setAction();
        this.box.update(delta);
        sizeWithLerp(delta);
    }

    private void setInitSize() {
        this.drawW = plate.getWidth() / (float) total;
        this.drawX = plate.getX() + plate.getWidth() / (float) total * (float) index;
    }

    private void sizeWithLerp(float delta) {
        this.drawX = INTER.apply(this.drawX, x, delta * speed);
        this.drawW = INTER.apply(this.drawW, width, delta * speed);
    }

    private void resetSize() {
        x = plate.getX() + plate.getWidth() / (float) total * (float) index;
        width = plate.getWidth() / (float) total;
    }

    private void setZoomInSize() {
        width = plate.getWidth() / (float) total * zoomScale;
        x = index * (plate.getWidth() - plate.getWidth() /
            (float) total * zoomScale) / (total - 1) + plate.getX();
    }

    private void setZoomOutSize() {
        width = (plate.getWidth() - plate.getWidth() / (float) total * zoomScale) / (total - 1);
        if (!isPreviousZoomed) {
            x = index * (plate.getWidth() - plate.getWidth() /
                (float) total * zoomScale) / (total - 1) + plate.getX();
        } else {
            x = plate.getX() + plate.getWidth() / (float) total * zoomScale + (index - 1) *
                (plate.getWidth() - plate.getWidth() / (float) total * zoomScale) / (total - 1);
        }
    }

    private void setProperty() {
        switch (this.action) {
            case RECRUIT -> {
                texture = AssetPath.getTexture("recruit");
                this.nameString = text.getRecruit();
            }
            case SHOP -> {
                texture = AssetPath.getTexture("shop");
                this.nameString = text.getShop();
            }
            case TRAINING_GROUND -> {
                texture = AssetPath.getTexture("ground");
                this.nameString = text.getTrainingGround();
            }
            case STAGE -> {
                texture = AssetPath.getTexture("stage");
                this.nameString = text.getStage();
            }
        }
    }

    private void setAction() {
        if (this.box.isClicked) switch (this.action) {
            case RECRUIT -> {
                SnowFight.changeScreen = SnowFight.AddedScreen.RECRUIT_SCREEN;
            }
            case SHOP -> {
                SnowFight.changeScreen = SnowFight.AddedScreen.SHOP_SCREEN;
            }
            case TRAINING_GROUND -> {
                SnowFight.changeScreen = SnowFight.AddedScreen.TRAINING_GROUND_SCREEN;
            }
            case STAGE -> {
                SnowFight.changeScreen = SnowFight.AddedScreen.STAGE_SCREEN;
            }
        }
    }

    public static void setTotal(int total) {
        MapButton.total = total;
    }

    public enum ZoomMode {
        N, IN, OUT
    }

    public enum GroundButtonAction {
        RECRUIT,
        SHOP,
        TRAINING_GROUND,
        STAGE,
        ;
    }

}
