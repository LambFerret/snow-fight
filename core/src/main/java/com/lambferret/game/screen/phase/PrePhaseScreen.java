package com.lambferret.game.screen.phase;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrePhaseScreen implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(PrePhaseScreen.class.getName());
    Container<Table> mapContainer;
    TextButton textButton;
    public static final Stage stage = new Stage();
    Player player;

    public PrePhaseScreen(Container<Table> mapContainer) {
        this.mapContainer = mapContainer;
        Image background = new Image(AssetFinder.getTexture("prePhase"));
        textButton = new TextButton("PrePhaseScreen", GlobalSettings.skin);

        stage.addActor(background);
        stage.addActor(textButton);
        stage.addActor(mapContainer);
    }


    public void create() {

    }

    public void init(Player player) {
        this.player = player;
    }

    @Override
    public void startPhase() {

    }

    @Override
    public void executePhase() {

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
