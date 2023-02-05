package com.lambferret.game.screen.ground;

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
    private String setName(GroundButtonAction action) {
        return switch (action) {
            case RECRUIT -> text.getRecruit();
            case SHOP -> text.getShop();
            case TRAINING_GROUND -> text.getTrainingGround();
        };
    }
    private Hitbox setBox(GroundButtonAction action) {
        return switch (action) {
            case RECRUIT -> new Hitbox(100, 100, 100, 100);
            case SHOP -> new Hitbox(100, 200, 100, 100);
            case TRAINING_GROUND -> new Hitbox(100, 300, 100, 100);
        };
    }
    enum GroundButtonAction {
        RECRUIT,
        SHOP,
        TRAINING_GROUND,
        ;
    }



}
