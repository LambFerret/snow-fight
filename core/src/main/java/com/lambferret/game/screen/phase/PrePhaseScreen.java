package com.lambferret.game.screen.phase;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lambferret.game.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrePhaseScreen implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(PrePhaseScreen.class.getName());
    TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
    TextButton textButton;
    BitmapFont font = new BitmapFont();
    Stage stage;
    Table map;

    public PrePhaseScreen() {
        this.stage = new Stage();
        style.font = font;
        textButton = new TextButton("PrePhaseScreen", style);
        stage.addActor(textButton);
    }


    public void create() {
        textButton.setPosition(300, 300);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                logger.info("clicked |  🐳 i am working | ");
            }
        });
    }

    public void init(Player player) {
        map = PhaseScreen.map;
        map.setPosition(500, 100);
        map.setSize(500, 500);
        stage.addActor(map);
    }

    public Stage getStage() {
        return this.stage;
    }

    public void render() {
        stage.draw();
    }

    public void update() {
        stage.act();
    }
}
