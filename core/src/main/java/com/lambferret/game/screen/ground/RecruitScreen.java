package com.lambferret.game.screen.ground;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.component.HorizontalScroll;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecruitScreen extends GroundUIScreen {
    private static final Logger logger = LogManager.getLogger(RecruitScreen.class.getName());

    TextButton.TextButtonStyle style;
    TextButton textButton;
    BitmapFont font;
    Stage stage;
    HorizontalScroll scroll;

    public RecruitScreen() {
        Hitbox plate = new Hitbox(0,500.0F, GlobalSettings.currWidth, 100.0F);
        scroll = new HorizontalScroll();
        scroll.create(plate);
        stage = new Stage();
        font = new BitmapFont();
        style = new TextButton.TextButtonStyle();
        style.font = font;
        textButton = new TextButton("RECRUIT", style);
        stage.addActor(textButton);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        scroll.render(batch);
        stage.act();
        stage.draw();
    }

    public void update(float delta) {
        super.update(delta);
        scroll.update(delta);
    }
}
