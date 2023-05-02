package com.lambferret.game.screen.ground;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.setting.ScreenConfig;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrainingGroundScreen implements AbstractGround {
    private static final Logger logger = LogManager.getLogger(TrainingGroundScreen.class.getName());
    public static final Stage stage = new Stage();
    private final Skin skin;

    public TrainingGroundScreen() {
        skin = GlobalSettings.skin;
        stage.addActor(background());
        stage.addActor(makeButton());
    }

    @Override
    public void onPlayerUpdate() {

    }

    @Override
    public void onPlayerReady() {
    }


    public Image background() {
        Image image = new Image(AssetFinder.getTexture("groundReal"));
        image.setSize(GlobalSettings.currWidth, GlobalSettings.currHeight);
        return image;
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

    @Override
    public void render() {
        stage.draw();
        stage.act();
    }

}
