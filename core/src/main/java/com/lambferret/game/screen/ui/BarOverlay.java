package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BarOverlay extends Table implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(BarOverlay.class.getName());
    private final Stage stage;

    public BarOverlay(Stage stage) {
        this.stage = stage;
    }

    public void create() {
        stage.addActor(this);
        setProperty();
    }

    private void setProperty() {
        this.clear();
//        this.add(button(GroundScreen.Screen.RECRUIT)).pad(10);
        this.setPosition(0, GlobalSettings.currHeight - BAR_HEIGHT);
        this.setSize(GlobalSettings.currWidth, BAR_HEIGHT);

        this.setBackground(GlobalSettings.debugTexture);
        this.setColor(GlobalSettings.debugColorGreen);
    }
}
