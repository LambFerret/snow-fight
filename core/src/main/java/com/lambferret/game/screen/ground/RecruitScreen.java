package com.lambferret.game.screen.ground;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.player.Player;
import com.lambferret.game.screen.event.Tutorial;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecruitScreen implements AbstractGround {
    private static final Logger logger = LogManager.getLogger(RecruitScreen.class.getName());

    TextButton.TextButtonStyle style;
    TextButton textButton;
    BitmapFont font;
    Stage stage;

    public RecruitScreen() {
        this.stage = new Stage();
        font = GlobalSettings.font;
        style = new TextButton.TextButtonStyle();
        style.font = font;
        textButton = new TextButton("RECRUIT", style);
        stage.addActor(textButton);
    }

    public void create() {
//        stage.addActor(makeButton());
    }

    @Override
    public void init(Player player) {
        Tutorial tutorial = new Tutorial(GlobalSettings.skin);
        if (tutorial.isFirstTime()) stage.addActor(new Tutorial(GlobalSettings.skin));
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
