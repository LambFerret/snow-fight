package com.lambferret.game.screen.phase;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.screen.phase.container.MapContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionPhaseScreen {
    private static final Logger logger = LogManager.getLogger(ActionPhaseScreen.class.getName());

    MapContainer mapContainer;

    public ActionPhaseScreen(MapContainer mapContainer) {
        this.mapContainer = mapContainer;
    }

    public void render() {
        mapContainer.renderMap();
    }

    public void update() {
        mapContainer.updateMap();
    }

}
