package com.lambferret.game.screen.ground;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShopScreen implements AbstractGround {
    private static final Logger logger = LogManager.getLogger(ShopScreen.class.getName());

    Stage stage;
    Table table = new Table();

    public ShopScreen() {
        this.stage = new Stage();
        stage.addActor(table);
    }

    public void create() {
        table.setDebug(true, true);

        table.setSkin(GlobalSettings.skin);
        table.setSize(300, 300);
        table.setPosition(100, 100);

    }

    @Override
    public void init(Player player) {
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
