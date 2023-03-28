package com.lambferret.game.screen.phase;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefeatScreen implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(DefeatScreen.class.getName());
    TextButton.TextButtonStyle style;
    TextButton textButton;
    BitmapFont font;
    Stage stage;

    public DefeatScreen() {
        this.stage = new Stage();
        font = GlobalSettings.font;
        style = new TextButton.TextButtonStyle();
        style.font = font;
        textButton = new TextButton("DefeatScreen", style);
        textButton.setPosition(300, 300);
        stage.addActor(textButton);
    }

    public void create() {
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });
    }

    @Override
    public void init(Player player) {
    }

    @Override
    public void startPhase() {

    }

    @Override
    public void executePhase() {

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
