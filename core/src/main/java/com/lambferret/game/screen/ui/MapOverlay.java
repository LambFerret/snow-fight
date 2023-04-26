package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.player.Player;
import com.lambferret.game.screen.ground.GroundScreen;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.GroundText;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MapOverlay extends Group implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(MapOverlay.class.getName());
    private static final GroundText text;
    private final Image hoveredImageGround;
    private final Image hoveredImageShop;
    private final Image hoveredImageRecruit;


    public MapOverlay(Stage stage) {
        this.setSize(OVERLAY_BORDERLINE_WIDTH, OVERLAY_BORDERLINE_HEIGHT);
        this.setPosition(GlobalSettings.currWidth - OVERLAY_BORDERLINE_WIDTH, 0);

        hoveredImageGround = makeHoverImage(GroundScreen.Screen.TRAINING_GROUND);
        hoveredImageShop = makeHoverImage(GroundScreen.Screen.SHOP);
        hoveredImageRecruit = makeHoverImage(GroundScreen.Screen.RECRUIT);

        stage.addActor(this);

        this.addActor(hoveredImageGround);
        this.addActor(hoveredImageShop);
        this.addActor(hoveredImageRecruit);
    }

    public void create() {
        this.setDebug(true, true);


    }

    @Override
    public void init(Player player) {
        CustomButton recruit = button(GroundScreen.Screen.RECRUIT);
        CustomButton shop = button(GroundScreen.Screen.SHOP);
        CustomButton trainingGround = button(GroundScreen.Screen.TRAINING_GROUND);

        recruit.setPosition(30, 30);
        shop.setPosition(90, 90);
        trainingGround.setPosition(150, 150);
        recruit.setSize(50, 50);
        shop.setSize(50, 50);
        trainingGround.setSize(50, 50);

        this.addActor(recruit);
        this.addActor(shop);
        this.addActor(trainingGround);
    }

    private Image makeHoverImage(GroundScreen.Screen screen) {
        Texture texture = AssetFinder.getTexture(screen.name().toLowerCase());
        Image mapHoverImage = new Image(texture);
        mapHoverImage.setSize(MAP_HOVER_IMAGE_WIDTH, MAP_HOVER_IMAGE_HEIGHT);
        mapHoverImage.setPosition(this.getWidth(), this.getHeight() + MAP_HOVER_IMAGE_Y_PAD);
        return mapHoverImage;
    }

    private CustomButton button(GroundScreen.Screen action) {
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = new TextureRegionDrawable(AssetFinder.getTexture("12"));
        style.font = GlobalSettings.font;
        String label = switch (action) {
            case RECRUIT -> text.getRecruit();
            case SHOP -> text.getShop();
            case TRAINING_GROUND -> text.getTrainingGround();
        };
        var button = new CustomButton("", style);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setAction(action);
                hideHoverImage(action);
            }
        });
        button.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) {
                    showHoverImage(action);
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    hideHoverImage(action);
                }
            }
        });
        return button;
    }

    private void showHoverImage(GroundScreen.Screen screen) {
        Image imageToShown = switch (screen) {
            case RECRUIT -> hoveredImageRecruit;
            case SHOP -> hoveredImageShop;
            case TRAINING_GROUND -> hoveredImageGround;
        };
        imageToShown.addAction(
            Actions.moveTo(
                this.getWidth() - (MAP_HOVER_IMAGE_WIDTH + MAP_HOVER_IMAGE_X_PAD),
                this.getHeight() + MAP_HOVER_IMAGE_Y_PAD,
                MAP_HOVER_IMAGE_ANIMATION_DURATION
            )
        );

        imageToShown.setZIndex(1);
    }

    private void hideHoverImage(GroundScreen.Screen screen) {
        Image imageToHide = switch (screen) {
            case RECRUIT -> hoveredImageRecruit;
            case SHOP -> hoveredImageShop;
            case TRAINING_GROUND -> hoveredImageGround;
        };
        imageToHide.addAction(
            Actions.moveTo(
                this.getWidth(),
                this.getHeight() + MAP_HOVER_IMAGE_Y_PAD,
                MAP_HOVER_IMAGE_ANIMATION_DURATION
            )
        );
        imageToHide.setZIndex(0);
    }

    private void setAction(GroundScreen.Screen action) {
        switch (action) {
            case RECRUIT -> {
                GroundScreen.changeScreen(GroundScreen.Screen.RECRUIT);
            }
            case SHOP -> {
                GroundScreen.changeScreen(GroundScreen.Screen.SHOP);
            }
            case TRAINING_GROUND -> {
                GroundScreen.changeScreen(GroundScreen.Screen.TRAINING_GROUND);
            }
        }
    }

    static {
        text = LocalizeConfig.uiText.getGroundText();
    }
}
