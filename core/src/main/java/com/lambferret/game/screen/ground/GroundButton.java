package com.lambferret.game.screen.ground;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.GroundText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GroundButton {
    private static final Logger logger = LogManager.getLogger(GroundButton.class.getName());
    private Hitbox box;
    private String name;
    private GroundButtonAction action;
    private static final float s = GlobalSettings.scale;
    private static GroundText text;

    public GroundButton(GroundButtonAction action) {
        text = LocalizeConfig.uiText.getGroundText();
        this.action = action;
        this.name = setName(this.action);
        this.box = setBox(this.action);

    }

    public void render(SpriteBatch batch) {
        this.box.render(batch);
    }

    public void update() {
        this.box.update();
        logger.info("update |  🐳 UPD | ");
        setAction();
    }

    private String setName(GroundButtonAction action) {
        return switch (action) {
            case RECRUIT -> text.getRecruit();
            case SHOP -> text.getShop();
            case TRAINING_GROUND -> text.getTrainingGround();
            case STAGE -> text.getStage();
        };
    }
    private Hitbox setBox(GroundButtonAction action) {
        return switch (action) {
            case RECRUIT -> new Hitbox(100, 100, 100, 100);
            case SHOP -> new Hitbox(200, 200, 100, 100);
            case TRAINING_GROUND -> new Hitbox(300, 300, 100, 100);
            case STAGE -> new Hitbox(400, 400, 100, 100);
        };
    }

    private void setAction() {
        if (this.box.isClicked) switch (this.action) {
            case RECRUIT -> {
                logger.info("setAction |  🐳 RE | " );
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
    public enum GroundButtonAction {
        RECRUIT,
        SHOP,
        TRAINING_GROUND,
        STAGE,
        ;
    }



}
