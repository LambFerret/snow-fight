package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BarOverlay extends Table implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(BarOverlay.class.getName());
    private final Stage stage;
    private final TextButton money;
    Player player;

    public BarOverlay(Stage stage) {
        this.stage = stage;
        stage.addActor(this);
        this.money = new TextButton("", GlobalSettings.skin);
    }

    public void create() {
        this.setPosition(0, GlobalSettings.currHeight - BAR_HEIGHT);
        this.setSize(GlobalSettings.currWidth, BAR_HEIGHT);

        this.setBackground(GlobalSettings.debugTexture);
        this.setColor(GlobalSettings.debugColorGreen);

        this.money.setPosition(400, 0);
        this.add(money).fillY();
    }

    @Override
    public void init(Player player) {
        this.player = player;
        this.money.setText("Money: " + this.player.getMoney());
    }

    public void updateMoney() {
        money.setText("Money: " + this.player.getMoney());
    }

}
