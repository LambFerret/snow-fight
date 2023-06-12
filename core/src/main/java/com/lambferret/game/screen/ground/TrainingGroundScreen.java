package com.lambferret.game.screen.ground;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.AnimationImage;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.setting.ScreenConfig;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TrainingGroundScreen implements AbstractGround {
    private static final Logger logger = LogManager.getLogger(TrainingGroundScreen.class.getName());

    public static final Stage stage = new Stage();
    private final Skin skin;
    private final TextureAtlas atlas;
    private Player player;
    private int snowLevel;
    private Image platform;
    private Image snow1;
    private Image snow2;
    private Image snow3;
    private Image snow4;
    private int snowCount = 0;
    List<Soldier> soldiers;
    int[] soldierIndex;

    public TrainingGroundScreen() {
        skin = GlobalSettings.skin;
        atlas = AssetFinder.getAtlas("trainingGround");
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
        logger.info(" SYSTEM : Training Ground Screen ");
        soldiers = new ArrayList<>(player.getSoldiers());
        soldiers.sort(Comparator.comparing(s -> s.getRank().ordinal()));
        soldierIndex = new int[soldiers.size()];
        addSnowClick(snow1);
        addSnowClick(snow2);
        addSnowClick(snow3);
        addSnowClick(snow4);
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
            snow1.remove();
            snow2.remove();
            snow3.remove();
            snow4.remove();
        }
        snowCount = 0;
        setSnowLevel();
        platform = makePlatform();
        stage.addActor(platform);
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
        int amount = MathUtils.random(300);
        //-=-=-=-=-=-//

        if (amount < 100) {
            snowLevel = 1;
        } else if (amount < 200) {
            snowLevel = 2;
        } else {
            snowLevel = 3;
        }
    }

    private Image makePlatform() {
        Image platform = new Image(atlas.findRegions("platform").get(snowLevel - 1));
        float centerX = (stage.getWidth() - platform.getWidth()) / 2;
        float centerY = (stage.getHeight() - platform.getHeight() / 2) / 2;
        platform.setPosition(centerX, centerY - 40);
        return platform;
    }

    private Image makeSnow() {
        Image snow = new Image(atlas.findRegions("snowLevel" + snowLevel).get(MathUtils.random.nextInt(1, 7)));
        if (MathUtils.random.nextBoolean()) {
            snow.setX(stage.getWidth() - MathUtils.random.nextInt((int) (snow.getWidth() + 200)));
        } else {
            snow.setX(MathUtils.random.nextInt(200));
        }
        snow.setY((5 - snowCount) * 10);
        snow.setOrigin(Align.center);
        snow.setScale(0.75F);

        snowCount++;
        return snow;
    }

    private void addSnowClick(Actor snow) {
        snow.addListener(new ClickListener() {
            int clickCount = 0;

            @Override
            public void clicked(InputEvent event, float x, float y) {
                // making soldier animation
                int index = getSoldierIndex();
                if (index == -1) return;
                Soldier victim = soldiers.get(index);
                AnimationImage soldierAnimation = soldierDiggingAnimation(snow, victim);
                stage.addActor(soldierAnimation);

                soldierAnimation.addAction(Actions.sequence(
                    Actions.run(() -> {
                        if (!checkSoldierIndex()) snow.setTouchable(Touchable.disabled);
                    }),
                    Actions.delay(soldierAnimation.getAnimationDuration() * 2),
                    Actions.run(() -> {
                        if (clickCount == snowLevel * 3) {
                            snow.remove();
                            clickCount = 0;
                        }
                        soldierIndex[index] = 0;
                        snow.setTouchable(Touchable.enabled);
                    }),
                    Actions.removeActor()
                ));
                // snow dug short animation;
                clickCount++;
                snow.addAction(
                    Actions.repeat(4,
                        Actions.sequence(
                            Actions.moveBy(5, 0, 0.01f),
                            Actions.moveBy(-5, 0, 0.01f)
                        )
                    )
                );
            }
        });
    }

    private int getSoldierIndex() {
        for (int i = 0; i < soldierIndex.length; i++) {
            if (soldierIndex[i] == 0) {
                soldierIndex[i] = 1;
                return i;
            }
        }
        return -1;
    }

    private boolean checkSoldierIndex() {
        for (int index : soldierIndex) {
            if (index == 0) {
                return true;
            }
        }
        return false;
    }

    private AnimationImage soldierDiggingAnimation(Actor snow, Soldier victim) {
        Animation<TextureRegion> animation = victim.getAnimation();
        AnimationImage newImage = new AnimationImage(animation);
        float randomLocation = MathUtils.random();
        float animationX = snow.getWidth() * randomLocation + snow.getX();
        newImage.setPosition(animationX, snow.getY() + 5);
        newImage.setSize(100, 100);
        newImage.setTouchable(Touchable.disabled);
        if (randomLocation > 0.5) newImage.setScale(-1, 1);
        return newImage;
    }

    private void makeClouds() {
        for (int i = 0; i < 5; i++) {
            int cloudType = MathUtils.random.nextInt(5) + 1;
            Image cloud = new Image(atlas.findRegions("cloud").get(cloudType - 1));
            cloud.setOrigin(Align.center);
            float initialXPosition = (i + 1) * GlobalSettings.currWidth / 5F;
            float yPosition = cloudType * 100;
            cloud.setPosition(initialXPosition, yPosition);
            cloud.setScale(0.5F);
            float speed = 100 + (cloudType / -5F);
            cloud.addAction(
                Actions.forever(
                    Actions.sequence(
                        Actions.moveTo(1000 + cloud.getWidth(), yPosition, (1000 + cloud.getWidth() - initialXPosition) / speed),
                        Actions.moveTo(-cloud.getWidth(), yPosition, 0),
                        Actions.moveTo(1000 + cloud.getWidth(), yPosition, (1000 + 2 * cloud.getWidth()) / speed)
                    )
                )
            );
            stage.addActor(cloud);
        }
    }


    private TextButton makeButton() {
        TextButton button = new TextButton("GO TO Phase", this.skin);
        button.addListener(Input.click(() -> ScreenConfig.changeScreen = ScreenConfig.AddedScreen.PHASE_SCREEN));
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
