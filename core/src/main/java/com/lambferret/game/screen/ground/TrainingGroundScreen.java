package com.lambferret.game.screen.ground;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.setting.ScreenConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrainingGroundScreen {
    private static final Logger logger = LogManager.getLogger(TrainingGroundScreen.class.getName());

    TextButton.TextButtonStyle style;
    TextButton textButton;
    BitmapFont font;
    Stage stage;
    Hitbox box;

    public TrainingGroundScreen() {
        stage = new Stage();
        font = new BitmapFont();
        style = new TextButton.TextButtonStyle();
        style.font = font;
        textButton = new TextButton("TrainGround", style);
        stage.addActor(textButton);
        box = new Hitbox(100, 100, 100, 100);
    }

    public void render() {
        stage.act();
        stage.draw();
//        batch.setColor(Color.CORAL);
        box.render();
    }


    public void update() {
        this.box.update();
        if (this.box.isClicked) {
            ScreenConfig.changeScreen = ScreenConfig.AddedScreen.PHASE_SCREEN;
        }
    }
}
