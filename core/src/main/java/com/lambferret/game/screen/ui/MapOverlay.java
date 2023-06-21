package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.save.Item;
import com.lambferret.game.screen.ground.GroundScreen;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.GroundText;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MapOverlay extends Group implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(MapOverlay.class.getName());
    private static final GroundText text;
    public static final int MAP_ICON_SIZE = 50;
    private final Image hoveredImageGround;
    private final Image hoveredImageShop;
    private final Image hoveredImageRecruit;


    public MapOverlay(Stage stage) {
        this.setSize(OVERLAY_BORDERLINE_WIDTH, OVERLAY_BORDERLINE_HEIGHT);
        this.setPosition(0, 0);


        hoveredImageGround = makeHoverImage(GroundScreen.Screen.TRAINING_GROUND);
        hoveredImageShop = makeHoverImage(GroundScreen.Screen.SHOP);
        hoveredImageRecruit = makeHoverImage(GroundScreen.Screen.RECRUIT);

        stage.addActor(this);

        this.addActor(hoveredImageGround);
        this.addActor(hoveredImageShop);
        this.addActor(hoveredImageRecruit);
    }

    @Override
    public void onPlayerReady() {
        CustomButton recruit = button(GroundScreen.Screen.RECRUIT);
        CustomButton shop = button(GroundScreen.Screen.SHOP);
        CustomButton trainingGround = button(GroundScreen.Screen.TRAINING_GROUND);

        recruit.setPosition(MAP_ICON_SIZE, MAP_ICON_SIZE);
        shop.setPosition(recruit.getX() + MAP_ICON_SIZE * 3 / 2F, MAP_ICON_SIZE);
        trainingGround.setPosition(shop.getX() + MAP_ICON_SIZE * 3 / 2F, MAP_ICON_SIZE);

        recruit.setSize(MAP_ICON_SIZE, MAP_ICON_SIZE);
        shop.setSize(MAP_ICON_SIZE, MAP_ICON_SIZE);
        trainingGround.setSize(MAP_ICON_SIZE, MAP_ICON_SIZE);

        this.addActor(recruit);
        this.addActor(shop);
        this.addActor(trainingGround);
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {

    }

    private Image makeHoverImage(GroundScreen.Screen screen) {
        Texture texture = AssetFinder.getTexture(screen.name().toLowerCase());
        Image mapHoverImage = new Image(texture);
        mapHoverImage.setSize(MAP_HOVER_IMAGE_WIDTH, MAP_HOVER_IMAGE_HEIGHT);
        mapHoverImage.setPosition(-this.getWidth(), this.getHeight() + MAP_HOVER_IMAGE_Y_PAD);
        return mapHoverImage;
    }

    private CustomButton button(GroundScreen.Screen action) {
        String label = switch (action) {
            case RECRUIT -> text.getRecruit();
            case SHOP -> text.getShop();
            case TRAINING_GROUND -> text.getTrainingGround();
        };
        var button = GlobalUtil.simpleButton("Map/button", label);
        button.addListener(Input.click(() -> {
            setAction(action);
            hideHoverImage(action);
        }));
        button.addListener(Input.soundWhenClick("mapFootstep"));
        button.addListener(Input.hover(() -> showHoverImage(action), () -> hideHoverImage(action)));
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
                MAP_HOVER_IMAGE_X_PAD,
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
                -this.getWidth(),
                this.getHeight() + MAP_HOVER_IMAGE_Y_PAD,
                MAP_HOVER_IMAGE_ANIMATION_DURATION
            )
        );
        imageToHide.setZIndex(0);
    }

    private void setAction(GroundScreen.Screen action) {
        switch (action) {
            case RECRUIT -> GroundScreen.changeScreen(GroundScreen.Screen.RECRUIT);
            case SHOP -> GroundScreen.changeScreen(GroundScreen.Screen.SHOP);
            case TRAINING_GROUND -> GroundScreen.changeScreen(GroundScreen.Screen.TRAINING_GROUND);
        }
    }

    static {
        text = LocalizeConfig.uiText.getGroundText();
    }

}
