package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.component.PolygonButton;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.OverlayText;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoldierOverlay extends Container<ScrollPane> implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(SoldierOverlay.class.getName());
    private static final OverlayText text;
    private final Stage stage;
    private final ScrollPane scrollPane;
    private final PolygonButton hideButton;
    private Player player;
    private boolean isSimple = true;
    private boolean isHide = true;


    public SoldierOverlay(Stage stage) {
        this.stage = stage;
        this.scrollPane = new ScrollPane(new Table());
        hideButton = new PolygonButton(text.getSoldierOverlayName(), getHideButtonStyle(), SOLDIER_HIDE_BUTTON_VERTICES);

        stage.addActor(hideButton);
        stage.addActor(this);
        this.setActor(this.scrollPane);
    }

    public void create() {
        this.setPosition(SOLDIER_X, SOLDIER_Y);
        this.setSize(SOLDIER_WIDTH, SOLDIER_HEIGHT);

        hideButton.setPosition(SOLDIER_HIDE_BUTTON_X, SOLDIER_HIDE_BUTTON_Y);
        hideButton.setSize(SOLDIER_HIDE_BUTTON_WIDTH, SOLDIER_HIDE_BUTTON_HEIGHT);

        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setPosition(this.getX(), this.getY());
        scrollPane.setSize(this.getWidth(), this.getHeight());

        this.background(new TextureRegionDrawable(AssetFinder.getTexture("")));

        if (isHide) {
            this.setX(SOLDIER_HIDE_X);
            hideButton.setX(SOLDIER_HIDE_BUTTON_HIDE_X);
        }
    }

    @Override
    public void init(Player player) {
        this.player = player;

        makeSoldierContainer();

        hideButton.addListener(new DragListener() {
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                hideButton.moveBy(x - getDragStartX(), 0);
                moveBy(x - getDragStartX(), 0);
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if (hideButton.getX() > SOLDIER_HIDE_MOVEMENT_THRESHOLD_X && isHide) {
                    show();
                } else if (hideButton.getX() < SOLDIER_WIDTH - SOLDIER_HIDE_MOVEMENT_THRESHOLD_X && !isHide) {
                    hide();
                } else {
                    resetLocation();
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (!isDragging()) {
                    if (isHide) {
                        show();
                    } else {
                        hide();
                    }
                } else {
                    dragStop(event, x, y, pointer);
                }
                cancel();
            }
        });

        scrollPane.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                stage.setScrollFocus(scrollPane);
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    stage.setScrollFocus(null);
                }
                super.exit(event, x, y, pointer, toActor);
            }
        });
    }

    public void makeSoldierContainer() {
        Table table = new Table();
        int i = 0;
        for (Soldier soldier : player.getSoldiers()) {
            Container<Group> card = soldier.card();
            card.setSize(SOLDIER_EACH_WIDTH, SOLDIER_EACH_HEIGHT);
            table.add(card).pad(SOLDIER_EACH_PAD);
            if (i++ == 6) {
                table.row();
                i = 0;
            }
        }
        this.scrollPane.setActor(table);
    }

    private void hide() {
        this.addAction(
            Actions.moveBy(-hideButton.getX(), 0, SOLDIER_HIDE_ANIMATION_DURATION)
        );
        hideButton.addAction(
            Actions.moveBy(-hideButton.getX(), 0, SOLDIER_HIDE_ANIMATION_DURATION)
        );
        isHide = true;
    }

    private void show() {
        this.addAction(
            Actions.moveBy(SOLDIER_WIDTH - hideButton.getX(), 0, SOLDIER_HIDE_ANIMATION_DURATION)
        );
        hideButton.addAction(
            Actions.moveBy(SOLDIER_WIDTH - hideButton.getX(), 0, SOLDIER_HIDE_ANIMATION_DURATION)
        );
        isHide = false;
    }

    private void resetLocation() {
        if (isHide) {
            hide();
        } else {
            show();
        }
    }

    @Override
    public void setVisible(boolean visible) {
        this.hideButton.setVisible(visible);
        super.setVisible(visible);
    }

    private ImageTextButton.ImageTextButtonStyle getHideButtonStyle() {
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = new TextureRegionDrawable(AssetFinder.getTexture("fileIron"));
        style.font = GlobalSettings.font;
        return style;
    }


    static {
        text = LocalizeConfig.uiText.getOverlayText();
    }

    @Override
    public void onPlayerReady() {

    }

    @Override
    public void onPlayerUpdate() {
        makeSoldierContainer();
    }

}
