package com.lambferret.game.screen.ground;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.setting.ScreenConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrainingGroundScreen {
    private static final Logger logger = LogManager.getLogger(TrainingGroundScreen.class.getName());
    private final Stage stage;
    private Skin skin;

    public TrainingGroundScreen() {
        this.stage = new Stage();
    }

    public Stage getStage() {
        return this.stage;
    }

    public void create() {
        skin = GlobalSettings.skin;
        stage.addActor(makeButton());
    }

    private void setProperty() {

    }

    private TextButton makeButton() {
        TextButton button = new TextButton("GO TO Phase", this.skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenConfig.changeScreen = ScreenConfig.AddedScreen.PHASE_SCREEN;
            }
        });
        button.setSize(50, 50);
        button.setPosition(GlobalSettings.currWidth / 2.0F, 0);
        return button;
    }

    public void render() {
        stage.draw();
    }

    public void update() {
        stage.act();
    }
}
