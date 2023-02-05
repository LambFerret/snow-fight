package com.lambferret.game.screen.ground;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.component.Plate;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.GroundText;
import com.lambferret.game.util.AssetPath;
import com.lambferret.game.util.CustomInputProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MapButton {
    private static final Logger logger = LogManager.getLogger(MapButton.class.getName());
    private static final float s = GlobalSettings.scale;
    private static int total;
    private static GroundText text;
    private int index;
    private Hitbox box;
    private String nameString;
    private GroundButtonAction action;
    private Plate plate;
    private boolean isJustHovered = false;
    private boolean isHovered = true;
    private Texture texture;
    private float x;
    private float y;
    private float width;
    private float height;

    public MapButton(GroundButtonAction action, int index) {
        this.index = index;
        this.action = action;
        this.box = new Hitbox();
        text = LocalizeConfig.uiText.getGroundText();
        setProperty();
    }

    public void create() {
        x = plate.getX() + plate.getWidth() * ((float) index / (float) total);
        y = plate.getY();
        width = plate.getWidth() * ((float) (index + 1) / (float) total);
        height = plate.getHeight();
        logger.info("create |  🐳 sadfasdf   adf | " + x +" "+y+" "+width+" "+height+" ");
        initializeSize();
    }

    public void render() {
        this.box.render();
    }

    public void render(Plate plate, SpriteBatch batch) {

        batch.draw(texture, this.x, this.y, this.width, this.height);
        if (CustomInputProcessor.pressedKey == Input.Keys.Y) {
            isJustHovered = true;
        } else if (CustomInputProcessor.pressedKey == Input.Keys.N) {
            isJustHovered = false;
        }
        if (isHovered != isJustHovered) {
            this.plate = plate;
            if (isJustHovered) {
                logger.info("render |  🐳 HOVERED! | " + isJustHovered);
            } else {
                initializeSize();
            }
            isHovered = isJustHovered;
        }

        this.box.render();
    }

    public void update() {
        this.box.update();
        setAction();
    }

    private void initializeSize() {
        box.setPosition(x, y);
        box.setSize(width, height);
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

    public enum GroundButtonAction {
        RECRUIT,
        SHOP,
        TRAINING_GROUND,
        STAGE,
        ;
    }


}
