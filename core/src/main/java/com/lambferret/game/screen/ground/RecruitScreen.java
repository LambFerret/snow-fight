package com.lambferret.game.screen.ground;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecruitScreen implements AbstractGround {
    private static final Logger logger = LogManager.getLogger(RecruitScreen.class.getName());

    public static final Stage stage = new Stage();
    TextButton textButton;

    public RecruitScreen() {
        textButton = new TextButton("RECRUIT", GlobalSettings.skin);
        stage.addActor(textButton);
    }

    @Override
    public void create() {
    }

    @Override
    public void init(Player player) {

    }

    @Override
    public void render() {
        stage.draw();
    }

    @Override
    public void update() {
        stage.act();
    }

}
