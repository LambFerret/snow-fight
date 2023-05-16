package com.lambferret.game.screen.phase;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.lambferret.game.SnowFight;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.GlobalUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PrePhaseScreen implements AbstractPhase {
    private static final Logger logger = LogManager.getLogger(PrePhaseScreen.class.getName());
    Container<Table> mapContainer;
    public static final Stage stage = new Stage();
    TextButton textButton;
    TextureAtlas atlas;
    Texture backgroundTexture;
    NinePatchDrawable briefBoard;
    Player player;
    Level level;

    public PrePhaseScreen(Container<Table> mapContainer) {
        this.mapContainer = mapContainer;
        atlas = AssetFinder.getAtlas("briefingRoom");
        renderBackground();
        Image background = new Image(backgroundTexture);
        background.setSize(GlobalSettings.currWidth, GlobalSettings.currHeight);
        textButton = new TextButton("PrePhaseScreen", GlobalSettings.skin);

        stage.addActor(background);
        stage.addActor(textButton);
    }

    public void onPlayerReady() {
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {
    }

    @Override
    public void startPhase() {
        this.player = SnowFight.player;
        this.level = PhaseScreen.level;
        mapContainer.setBackground(briefBoard);
        stage.addActor(mapContainer);
        mapContainer.setDebug(true, true);
    }

    @Override
    public void executePhase() {

    }

    private void renderBackground() {
        Pixmap background = GlobalUtil.regionToPixmap(atlas.findRegion("background"));
        Pixmap cabinet1 = GlobalUtil.regionToPixmap(atlas.findRegion("cabinet1"));
        Pixmap cabinet2 = GlobalUtil.regionToPixmap(atlas.findRegion("cabinet2"));
        Pixmap etc1 = GlobalUtil.regionToPixmap(atlas.findRegion("etc1"));
        Pixmap etc2 = GlobalUtil.regionToPixmap(atlas.findRegion("etc2"));
        Pixmap pictureFrame = GlobalUtil.regionToPixmap(atlas.findRegion("pictureFrame"));
        Pixmap rightTable = GlobalUtil.regionToPixmap(atlas.findRegion("rightTable"));
        Pixmap leftTable = GlobalUtil.regionToPixmap(atlas.findRegion("leftTable"));
        Pixmap character = GlobalUtil.regionToPixmap(atlas.findRegion("character"));

        background.drawPixmap(cabinet1, 0, 0);
        background.drawPixmap(cabinet2, 0, 0);
        background.drawPixmap(etc1, 0, 0);
        background.drawPixmap(etc2, 0, 0);
        background.drawPixmap(rightTable, 0, 0);
        background.drawPixmap(leftTable, 0, 0);
        background.drawPixmap(pictureFrame, 0, 0);
        background.drawPixmap(character, 0, 0);

        backgroundTexture = new Texture(background);
        briefBoard = new NinePatchDrawable(atlas.createPatch("briefBoard")); //9 patch
        background.dispose();
        cabinet1.dispose();
        cabinet2.dispose();
        etc1.dispose();
        etc2.dispose();
        pictureFrame.dispose();
        rightTable.dispose();
        leftTable.dispose();
        character.dispose();
    }

    @Override
    public void render() {
        stage.draw();
        stage.act();
    }

}
