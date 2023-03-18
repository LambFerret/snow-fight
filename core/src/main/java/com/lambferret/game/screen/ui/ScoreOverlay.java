package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScoreOverlay extends Table implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(ScoreOverlay.class.getName());
    private final Stage stage;

    public ScoreOverlay(Stage stage) {
        this.stage = stage;
    }

    public void create() {
        stage.addActor(this);
        setProperty();
    }

    private void setProperty() {
        this.clear();
        //        this.add(button(GroundScreen.Screen.RECRUIT)).pad(10);
        this.setPosition(50, GlobalSettings.currHeight - BAR_HEIGHT - 50);
        this.setSize(100, 100);

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
