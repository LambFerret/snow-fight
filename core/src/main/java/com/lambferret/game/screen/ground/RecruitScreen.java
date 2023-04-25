package com.lambferret.game.screen.ground;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.SnowFight;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecruitScreen implements AbstractGround {
    private static final Logger logger = LogManager.getLogger(RecruitScreen.class.getName());

    public static final Stage stage = new Stage();

    public RecruitScreen() {
        Image background = new Image(AssetFinder.getTexture("recruitReal"));
        background.setSize(GlobalSettings.currWidth, GlobalSettings.currHeight);
        stage.addActor(background);
    }

    @Override
    public void create() {
    }

    @Override
    public void init(Player player) {
        TextButton t = new TextButton("recruit screen is working", GlobalSettings.skin);
        t.setPosition(100, 100);
        t.setSize(100, 100);
        stage.addActor(t);

    }

    @Override
    public void show() {
        stage.addActor(SnowFight.player.getPlayerMainEvent());
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
