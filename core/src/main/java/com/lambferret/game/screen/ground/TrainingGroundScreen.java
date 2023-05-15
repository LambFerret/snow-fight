package com.lambferret.game.screen.ground;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.lambferret.game.SnowFight;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.setting.ScreenConfig;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.GlobalUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class TrainingGroundScreen implements AbstractGround {
    private static final Logger logger = LogManager.getLogger(TrainingGroundScreen.class.getName());

    public static final Stage stage = new Stage();
    Random random = new Random();

    private final Skin skin;
    private final TextureAtlas atlas;
    private Player player;
    private int snowLevel;
    private Image platform;
    private Image stair;
    private Image snow1;
    private Image snow2;
    private Image snow3;
    private Image snow4;
    private int snowCount = 0;

    public TrainingGroundScreen() {
        skin = GlobalSettings.skin;
        atlas = AssetFinder.getAtlas("TrainingGround");
        makeBackground();
        makeClouds();
        makeAssets(false);
        stage.addActor(makeButton());
    }

    @Override
    public void onPlayerReady() {
        player = SnowFight.player;
        makeAssets(true);
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {

    }

    @Override
    public void init() {

    }

    private void makeBackground() {
        Pixmap sky = GlobalUtil.regionToPixmap(atlas.findRegion("sky"));
        Pixmap ground = GlobalUtil.regionToPixmap(atlas.findRegion("ground"));
        Pixmap groundSnow = GlobalUtil.regionToPixmap(atlas.findRegion("groundSnow"));
        Pixmap combinedSky = new Pixmap(GlobalSettings.WIDTH_PIXEL, GlobalSettings.HEIGHT_PIXEL, Pixmap.Format.RGBA8888);

        for (int x = 0; x < combinedSky.getWidth(); x += sky.getWidth()) {
            for (int y = 0; y < combinedSky.getHeight(); y += sky.getHeight()) {
                combinedSky.drawPixmap(sky, x, y);
            }
        }
        combinedSky.drawPixmap(ground, 0, combinedSky.getHeight() - ground.getHeight());
        combinedSky.drawPixmap(groundSnow, 0, 0, groundSnow.getWidth(), groundSnow.getHeight(), 0, combinedSky.getHeight() - ground.getHeight(), ground.getWidth(), ground.getHeight());

        Image image = new Image(new Texture(combinedSky));
        image.setSize(GlobalSettings.currWidth, GlobalSettings.currHeight);

        sky.dispose();
        ground.dispose();
        combinedSky.dispose();
        groundSnow.dispose();
        stage.addActor(image);
    }

    private void makeAssets(boolean isPlayerReady) {
        if (isPlayerReady) {
            platform.remove();
            stair.remove();
            snow1.remove();
            snow2.remove();
            snow3.remove();
            snow4.remove();
        }
        snowCount = 0;
        setSnowLevel();
        stair = makeStair();
        platform = makePlatform();
        stage.addActor(platform);
        stage.addActor(stair);
        snow1 = makeSnow();
        snow2 = makeSnow();
        snow3 = makeSnow();
        snow4 = makeSnow();
        stage.addActor(snow1);
        stage.addActor(snow2);
        stage.addActor(snow3);
        stage.addActor(snow4);
    }

    private void setSnowLevel() {
        //-=-=-=-=-=-//
        int amount = 03000;
        //-=-=-=-=-=-//

        if (0 <= amount && amount < 100) {
            snowLevel = 1;
        } else if (100 <= amount && amount < 200) {
            snowLevel = 2;
        } else {
            snowLevel = 3;
        }
    }

    private Image makePlatform() {
        Image platform = new Image(atlas.findRegions("platform").get(snowLevel));
        float centerX = (stage.getWidth() - platform.getWidth()) / 2;
        float centerY = stair.getY() + stair.getHeight();
        platform.setPosition(centerX, centerY - 40);
        return platform;
    }

    private Image makeStair() {
        Image stair = new Image(atlas.findRegions("stair").get(snowLevel));
        float centerX = (stage.getWidth() - stair.getWidth()) / 2;
        float centerY = (stage.getHeight() - stair.getHeight()) / 2;
        stair.setPosition(centerX, centerY - 100);
        return stair;
    }

    private Image makeSnow() {
        Image snow = new Image(atlas.findRegions("snowLevel" + snowLevel).get(random.nextInt(1, 7)));
        if (random.nextBoolean()) {
            snow.setX(stage.getWidth() - random.nextInt((int) (snow.getWidth() + 200)));
        } else {
            snow.setX(random.nextInt(200));
        }
        snow.setY((5 - snowCount) * 10);
        snow.setOrigin(Align.center);
        snow.setScale(0.75F);
        snow.addListener(new ClickListener() {
            int clickCount = 0;

            @Override
            public void clicked(InputEvent event, float x, float y) {
                clickCount++;
                snow.addAction(
                    Actions.repeat(4,
                        Actions.sequence(
                            Actions.moveBy(5, 0, 0.01f),
                            Actions.moveBy(-5, 0, 0.01f)
                        )
                    )
                );
                if (clickCount == snowLevel * 3) {
                    snow.remove();
                    clickCount = 0;
                }
            }
        });
        snowCount++;
        return snow;
    }

    private void makeClouds() {
        for (int i = 0; i < 5; i++) {
            int cloudType = random.nextInt(5) + 1;  // Get a random cloud type between 1 and 5
            Image cloud = new Image(atlas.findRegions("cloud").get(cloudType - 1));  // Assume cloud regions are 0-indexed
            cloud.setOrigin(Align.center);
            float initialXPosition = (i + 1) * GlobalSettings.currWidth / 5F;  // Random start X position between 0 and 1000
            float yPosition = cloudType * 100;  // Y position is type index * 100
            cloud.setPosition(initialXPosition, yPosition);
            cloud.setScale(0.5F);
            float speed = 100 + (cloudType / -5F);  // Set this to the desired speed
            cloud.addAction(
                Actions.forever(
                    Actions.sequence(
                        Actions.moveTo(1000 + cloud.getWidth(), yPosition, (1000 + cloud.getWidth() - initialXPosition) / speed),  // Move to the right of the screen
                        Actions.moveTo(-cloud.getWidth(), yPosition, 0),  // Instantly move to the left of the screen
                        Actions.moveTo(1000 + cloud.getWidth(), yPosition, (1000 + 2 * cloud.getWidth()) / speed)  // Move to the right of the screen
                    )
                )
            );
            stage.addActor(cloud);
        }
    }


    private TextButton makeButton() {
        TextButton button = new TextButton("GO TO Phase", this.skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenConfig.changeScreen = ScreenConfig.AddedScreen.PHASE_SCREEN;
            }
        });
        button.setSize(50, 50);
        button.setPosition(GlobalSettings.currWidth / 2.0F, 0);
        return button;
    }

    @Override
    public void render() {
        stage.draw();
        stage.act();
    }

}
