package com.lambferret.game.screen.phase;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.lambferret.game.SnowFight;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.screen.phase.container.MapContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrePhaseScreen {
    private static final Logger logger = LogManager.getLogger(PrePhaseScreen.class.getName());

    Player player;
    Level map;
    MapContainer mapContainer;
    ImageButton imageButton = new ImageButton(new ImageButton.ImageButtonStyle());



    public PrePhaseScreen(MapContainer mapContainer) {
        this.mapContainer = mapContainer;

        this.player = SnowFight.player;
        this.map = PhaseScreen.currentLevel;
        this.mapContainer = new MapContainer(this.map);
    }

    public void render() {
        mapContainer.renderMap();
    }

    public void update() {
        mapContainer.updateMap();
    }
}
