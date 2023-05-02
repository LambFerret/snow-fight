package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.GroundText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ManualBookshelfOverlay extends Table implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(ManualBookshelfOverlay.class.getName());
    private static final GroundText text;

    private static final float MANUAL_WIDTH = 50;
    private static final float MANUAL_HEIGHT = 50;
    private static final float MANUAL_X = GlobalSettings.currWidth - (MANUAL_WIDTH + OVERLAY_BORDERLINE_WIDTH);
    private static final float MANUAL_Y = GlobalSettings.currHeight - MANUAL_HEIGHT;


    public ManualBookshelfOverlay(Stage stage) {
        this.setSize(MANUAL_WIDTH, MANUAL_HEIGHT);
        this.setPosition(MANUAL_X, MANUAL_Y);
        this.setDebug(true, true);

        stage.addActor(this);
    }

    @Override
    public void onPlayerReady() {

    }

    @Override
    public void onPlayerUpdate() {

    }

    static {
        text = LocalizeConfig.uiText.getGroundText();
    }
}
