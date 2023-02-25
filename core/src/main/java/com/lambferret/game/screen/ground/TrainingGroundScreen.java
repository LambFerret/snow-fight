package com.lambferret.game.screen.ground;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.component.constant.Direction;
import com.lambferret.game.screen.ui.Overlay;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrainingGroundScreen extends GroundUIScreen {
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

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        stage.act();
        stage.draw();
        batch.setColor(Color.CORAL);
        box.render(batch);
    }


    public void update(float delta) {
        super.update(delta);
        this.box.update(delta);
        if (this.box.isClicked) {
            this.box.hide(Direction.INSTANTLY);
            Overlay.hideAll();
            SnowFight.changeScreen = SnowFight.AddedScreen.READY_SCREEN;
        }
    }
}
