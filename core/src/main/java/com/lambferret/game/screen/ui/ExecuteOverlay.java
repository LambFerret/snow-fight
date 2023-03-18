package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// TODO : table 아님
public class ExecuteOverlay extends Table implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(ExecuteOverlay.class.getName());
    private final Stage stage;

    public ExecuteOverlay(Stage stage) {
        this.stage =stage;
    }

    public void create() {
        stage.addActor(this);
        setProperty();
    }

    private void setProperty() {
        this.clear();
        //        this.add(button(GroundScreen.Screen.RECRUIT)).pad(10);
        this.setPosition(GlobalSettings.currWidth - OVERLAY_WIDTH, 0);
        this.setSize(OVERLAY_WIDTH, OVERLAY_HEIGHT);

        this.setBackground(GlobalSettings.debugTexture);
        this.setColor(GlobalSettings.debugColorGreen);
    }

    public void render() {
        stage.draw();
    }

    public void update() {
        stage.act();
    }


}
