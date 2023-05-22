package com.lambferret.game.component;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.GlobalUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;

public class SnowBarStyle extends ProgressBar.ProgressBarStyle {
    private static final Logger logger = LogManager.getLogger(SnowBarStyle.class.getName());

    public TextureRegionDrawable frame1;
    public TextureRegionDrawable frame2;
    private TextureAtlas atlas;
    int width;
    int height;

    public SnowBarStyle(List<Soldier> soldierList, int width, int height) {
        super();
        this.width = width;
        this.height = height;
        atlas = AssetFinder.getAtlas("Soldiers");
        this.frame1 = new TextureRegionDrawable(atlas.findRegions(soldierList.get(0).getName()).get(1));
        frame1.setMinWidth(width / 20F);
        frame1.setMinHeight(height);
        this.frame2 = new TextureRegionDrawable(atlas.findRegions(soldierList.get(0).getName()).get(3));
        frame2.setMinWidth(width / 20F);
        frame2.setMinHeight(height);
        this.background = makeBackground();
        // if 50% filled, left side is knobBefore
        this.knobBefore = new TextureRegionDrawable(AssetFinder.getTexture("yellow"));
        this.knobBefore.setMinWidth(width);
        this.knobBefore.setMinHeight(height);
    }

    private TextureRegionDrawable makeBackground() {
        Random random = new Random();
        var snows = AssetFinder.getAtlas("TrainingGround").findRegions("snowLevel3");
        Pixmap background = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        int whereToDrawX = 0;
        do {
            Pixmap snowPix = GlobalUtil.regionToPixmap(snows.get(random.nextInt(snows.size)));
            background.drawPixmap(snowPix,
                0, 0, snowPix.getWidth(), snowPix.getHeight(),
                whereToDrawX, 0, background.getHeight() * snowPix.getWidth() / snowPix.getHeight(), background.getHeight()
            );
            whereToDrawX += (background.getHeight() * snowPix.getWidth() / snowPix.getHeight()) / 2;
            snowPix.dispose();
        } while (whereToDrawX < background.getWidth());
        TextureRegionDrawable image = new TextureRegionDrawable(new TextureRegion(new Texture(background)));
        background.dispose();
        return image;
    }

    public void updateAnimationFrame(float value) {
        if (value % 2 == 0) {
            knob = frame1;
        } else {
            knob = frame2;
        }
    }

    public void reset() {
        knob = null;
    }

}
