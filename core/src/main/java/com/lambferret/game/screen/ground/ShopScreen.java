package com.lambferret.game.screen.ground;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShopScreen extends GroundUIScreen {
    private static final Logger logger = LogManager.getLogger(ShopScreen.class.getName());

    TextButton.TextButtonStyle style;
    TextButton textButton;
    BitmapFont font;
    Stage stage;

    public ShopScreen() {
        stage = new Stage();
        font = new BitmapFont();
        style = new TextButton.TextButtonStyle();
        style.font = font;
        textButton = new TextButton("SHOP", style);

        Hitbox plate_V = new Hitbox(500.0F, 0, 50.0F, GlobalSettings.currHeight);
        stage.addActor(textButton);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        stage.act();
        stage.draw();
    }

    public void update(float delta) {
        super.update(delta);

    }
}
