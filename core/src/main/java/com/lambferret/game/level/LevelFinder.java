package com.lambferret.game.level;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.lambferret.game.constant.Terrain;
import com.lambferret.game.level.nation.LevelN1;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LevelFinder {
    private static final Logger logger = LogManager.getLogger(LevelFinder.class.getName());

    public Level level;
    public static TiledMapTileSet tiledMapTileSet;
    static int tileWidth = 64;
    static int tileHeight = 64;

    public LevelFinder() {
    }

    public static Level get(int levelNumber) {
        return LevelTier.values()[levelNumber].getTier();
    }

    public static void createTiledMapTileSet() {
        tiledMapTileSet = new TiledMapTileSet();
        int i = 0;
        for (Terrain terrain : Terrain.values()) {
            TextureRegion textureRegion = new TextureRegion(
                AssetFinder.getTexture("tile" + terrain), 0, 0, tileWidth, tileHeight
            );
            TiledMapTile tile = new StaticTiledMapTile(textureRegion);
            tile.getProperties().put(Terrain.class.getSimpleName(), terrain);
            tiledMapTileSet.putTile(i++, tile);
        }
    }

    public enum LevelTier {
        NATION_1(new LevelN1()),
        NATION_2(new LevelN1()),
        NATION_3(new LevelN1()),
        NATION_4(new LevelN1()),
        NATION_5(new LevelN1()),
        NATION_6(new LevelN1()),
        NATION_7(new LevelN1()),
        NATION_8(new LevelN1()),
        NATION_9(new LevelN1()),
        NATION_10(new LevelN1()),
        RURAL_1(new LevelN1()),
        RURAL_2(new LevelN1()),
        RURAL_3(new LevelN1()),
        RURAL_4(new LevelN1()),
        RURAL_5(new LevelN1()),
        RURAL_6(new LevelN1()),
        RURAL_7(new LevelN1()),
        RURAL_8(new LevelN1()),
        RURAL_9(new LevelN1()),
        RURAL_10(new LevelN1()),
        URBAN_1(new LevelN1()),
        URBAN_2(new LevelN1()),
        URBAN_3(new LevelN1()),
        URBAN_4(new LevelN1()),
        URBAN_5(new LevelN1()),
        URBAN_6(new LevelN1()),
        URBAN_7(new LevelN1()),
        URBAN_8(new LevelN1()),
        URBAN_9(new LevelN1()),
        URBAN_10(new LevelN1()),
        ;
        final Level tier;

        LevelTier(Level tier) {
            this.tier = tier;
        }

        public Level getTier() {
            return tier;
        }
    }
}
