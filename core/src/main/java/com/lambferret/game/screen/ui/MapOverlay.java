package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.player.Player;
import com.lambferret.game.screen.ground.GroundScreen;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.GroundText;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MapOverlay extends Table implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(MapOverlay.class.getName());
    private final Stage stage;
    private final GroundText text;


    public MapOverlay(Stage stage) {
        this.stage = stage;
        stage.addActor(this);
        text = LocalizeConfig.uiText.getGroundText();
    }

    public void create() {
        this.add(button(GroundScreen.Screen.RECRUIT)).pad(10);
        this.add(button(GroundScreen.Screen.SHOP)).pad(10);
        this.add(button(GroundScreen.Screen.TRAINING_GROUND)).pad(10);
        this.setPosition(GlobalSettings.currWidth - 100, 100);
    }

    @Override
    public void init(Player player) {
    }

    private Image setHoverImage(GroundScreen.Screen screen) {
        Texture texture = switch (screen) {
            case RECRUIT -> AssetFinder.getTexture(GroundScreen.Screen.RECRUIT.name().toLowerCase());
            case SHOP -> AssetFinder.getTexture(GroundScreen.Screen.SHOP.name().toLowerCase());
            case TRAINING_GROUND -> AssetFinder.getTexture(GroundScreen.Screen.TRAINING_GROUND.name().toLowerCase());
        };
        Image mapHoverInfo = new Image(texture);

        mapHoverInfo.setSize(300, 600);
        mapHoverInfo.setPosition(100, 100);

        return mapHoverInfo;
    }

    private ImageTextButton.ImageTextButtonStyle getButtonStyle() {
        TextureRegionDrawable texture = GlobalSettings.debugTexture;
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = texture;
        style.font = GlobalSettings.font;
        return style;
    }

    private ImageTextButton button(GroundScreen.Screen action) {
        Image hoverImage = setHoverImage(action);
        String label = switch (action) {
            case RECRUIT -> text.getRecruit();
            case SHOP -> text.getShop();
            case TRAINING_GROUND -> text.getTrainingGround();
        };
        var button = new ImageTextButton(label, getButtonStyle());
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setAction(action);
            }
        });
        button.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                stage.addActor(hoverImage);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                hoverImage.remove();
            }
        });
        return button;
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

}
