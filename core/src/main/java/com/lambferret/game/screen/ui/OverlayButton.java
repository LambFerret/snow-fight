package com.lambferret.game.screen.ui;

import com.lambferret.game.screen.component.Hitbox;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.OverlayText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OverlayButton {
    private static final Logger logger = LogManager.getLogger(OverlayButton.class.getName());
    private static OverlayText text;
    private Hitbox box;
    private String name;
    private String description;
    private OverlayButtonAction action;
    private static final float s = GlobalSettings.scale;

    public OverlayButton(OverlayButtonAction action, int index) {
        text = LocalizeConfig.uiText.getOverlayText();
        this.action = action;
        this.name = setName(action);
        this.box = new Hitbox(500 * s* (index + 1), 50.0F * s , 30 * s, 30 * s);

    }
    private String setName(OverlayButtonAction action) {
        return switch (action) {
            case TEST -> text.getTEST();
        };
    }
    enum OverlayButtonAction {
            TEST
    }
}
