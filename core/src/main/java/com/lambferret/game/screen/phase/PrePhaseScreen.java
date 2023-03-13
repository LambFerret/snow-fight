package com.lambferret.game.screen.phase;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.SnowFight;
import com.lambferret.game.level.Level;
import com.lambferret.game.level.LevelFinder;
import com.lambferret.game.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrePhaseScreen {
    private static final Logger logger = LogManager.getLogger(PrePhaseScreen.class.getName());

    TextButton.TextButtonStyle style;
    TextButton textButton;
    BitmapFont font;
    Stage stage;
    Player player;
    Level level;


    public PrePhaseScreen() {
        stage = new Stage();
        font = new BitmapFont();
        style = new TextButton.TextButtonStyle();
        style.font = font;
        textButton = new TextButton("PrePhaseScreen", style);
        stage.addActor(textButton);
        this.player = SnowFight.player;

        this.level = LevelFinder.get(player.getCurrentRegion(), player.getLevelNumber());
    }

    public void render(SpriteBatch batch) {
        stage.act();
        stage.draw();
    }

    public void update(float delta) {

    }
}
