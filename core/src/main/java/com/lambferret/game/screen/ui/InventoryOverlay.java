package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.SnowFight;
import com.lambferret.game.command.Command;
import com.lambferret.game.component.PolygonButton;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.OverlayText;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InventoryOverlay extends Container<ScrollPane> implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(InventoryOverlay.class.getName());
    private static final OverlayText text;
    public static final int INVENTORY_MAX_ITEM_PER_ROW = 6;
    private final Stage stage;
    private final ScrollPane scrollPane;
    private Table soldierTable = new Table();
    private Table commandTable = new Table();
    private Type currentType = Type.INVENTORY;
    private final PolygonButton soldierButton;
    private final PolygonButton commandButton;
    private boolean isHide = true;

    public InventoryOverlay(Stage stage) {
        this.stage = stage;
        this.scrollPane = new ScrollPane(new Table());
        this.background(new TextureRegionDrawable(AssetFinder.getTexture("ui/inventory")));
        soldierButton = new PolygonButton(text.getSoldierOverlayName(), getHideButtonStyle(), INVENTORY_HIDE_BUTTON_VERTICES);
        commandButton = new PolygonButton(text.getSoldierOverlayName(), getHideButtonStyle(), INVENTORY_HIDE_BUTTON_VERTICES);

        this.setPosition(INVENTORY_X, INVENTORY_Y);
        this.setSize(INVENTORY_WIDTH, INVENTORY_HEIGHT);

        soldierButton.setPosition(INVENTORY_HIDE_BUTTON_X, INVENTORY_HIDE_BUTTON_Y);
        soldierButton.setSize(INVENTORY_HIDE_BUTTON_WIDTH, INVENTORY_HIDE_BUTTON_HEIGHT);

        commandButton.setPosition(INVENTORY_HIDE_BUTTON_X, INVENTORY_HIDE_BUTTON_Y);
        commandButton.setSize(INVENTORY_HIDE_BUTTON_WIDTH, INVENTORY_HIDE_BUTTON_HEIGHT);
        commandButton.setVisible(false);
        commandButton.setColor(Color.ORANGE);

        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setPosition(this.getX(), this.getY());
        scrollPane.setSize(this.getWidth(), this.getHeight());

        stage.addActor(this);
        stage.addActor(commandButton);
        stage.addActor(soldierButton);
        this.setActor(this.scrollPane);

        soldierButton.addListener(hideButtonDragListener(Type.INVENTORY));
        commandButton.addListener(hideButtonDragListener(Type.COMMAND));
        scrollPane.addListener(Input.setScrollFocusWhenHover(stage, scrollPane));

        if (isHide) {
            this.setX(INVENTORY_HIDE_X);
            soldierButton.setX(INVENTORY_HIDE_BUTTON_HIDE_X);
        }
    }

    @Override
    public void onPlayerReady() {
        Player player = SnowFight.player;

        makeSoldierContainer(player);
        makeCommandContainer(player);

        this.scrollPane.setActor(soldierTable);
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {
        Player player = SnowFight.player;
        switch (type) {
            case SOLDIER -> makeSoldierContainer(player);
            case COMMAND -> makeCommandContainer(player);
        }
    }

    private DragListener hideButtonDragListener(Type itemType) {
        return new DragListener() {
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                soldierButton.moveBy(x - getDragStartX(), 0);
                moveBy(x - getDragStartX(), 0);
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if (soldierButton.getX() > INVENTORY_HIDE_MOVEMENT_THRESHOLD_X && isHide) {
                    show();
                } else if (soldierButton.getX() < INVENTORY_WIDTH - INVENTORY_HIDE_MOVEMENT_THRESHOLD_X && !isHide) {
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
                        if (currentType == itemType) {
                            hide();
                        } else {
                            setScrollActor(itemType);
                        }
                    }
                } else {
                    dragStop(event, x, y, pointer);
                }
                cancel();
            }
        };
    }

    private void setScrollActor(Type type) {
        switch (type) {
            case INVENTORY -> {
                scrollPane.setActor(soldierTable);
                currentType = Type.INVENTORY;
                soldierButton.toFront();
            }
            case COMMAND -> {
                scrollPane.setActor(commandTable);
                currentType = Type.COMMAND;
                commandButton.toFront();
            }
        }
    }

    private void makeSoldierContainer(Player player) {
        soldierTable = new Table();
        int i = 0;
        for (Soldier soldier : player.getSoldiers()) {
            Container<Group> card = soldier.card();
            card.setSize(INVENTORY_EACH_WIDTH, INVENTORY_EACH_HEIGHT);
            soldierTable.add(card).pad(INVENTORY_EACH_PAD);
            if (i++ == INVENTORY_MAX_ITEM_PER_ROW) {
                soldierTable.row();
                i = 0;
            }
        }
        scrollPane.setActor(soldierTable);
    }

    private void makeCommandContainer(Player player) {
        commandTable = new Table();
        int i = 0;
        for (Command command : player.getCommands()) {
            Group card = command.renderSimple();
            card.setSize(INVENTORY_EACH_WIDTH, INVENTORY_EACH_HEIGHT);
            commandTable.add(card).pad(INVENTORY_EACH_PAD);
            if (i++ == INVENTORY_MAX_ITEM_PER_ROW) {
                commandTable.row();
                i = 0;
            }
        }
        scrollPane.setActor(commandTable);
    }

    private void hide() {
        this.addAction(
            Actions.moveBy(-soldierButton.getX(), 0, INVENTORY_HIDE_ANIMATION_DURATION)
        );
        soldierButton.addAction(
            Actions.moveBy(-soldierButton.getX(), 0, INVENTORY_HIDE_ANIMATION_DURATION)
        );
        commandButton.setVisible(false);
        commandButton.setPosition(INVENTORY_HIDE_BUTTON_X, INVENTORY_HIDE_BUTTON_Y);
        isHide = true;
    }

    private void show() {
        setScrollActor(Type.INVENTORY);
        this.addAction(
            Actions.moveBy(INVENTORY_WIDTH - soldierButton.getX(), 0, INVENTORY_HIDE_ANIMATION_DURATION)
        );
        soldierButton.addAction(
            Actions.moveBy(INVENTORY_WIDTH - soldierButton.getX(), 0, INVENTORY_HIDE_ANIMATION_DURATION)
        );
        commandButton.addAction(
            Actions.sequence(
                Actions.delay(INVENTORY_HIDE_ANIMATION_DURATION),
                Actions.moveBy(0, -100, 0.3F)
            )
        );
        commandButton.setVisible(true);
        isHide = false;
    }

    private void resetLocation() {
        if (isHide) {
            hide();
        } else {
            show();
        }
    }

    private ImageTextButton.ImageTextButtonStyle getHideButtonStyle() {
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = new TextureRegionDrawable(AssetFinder.getTexture("fileIron"));
        style.font = GlobalSettings.font;
        return style;
    }

    @Override
    public void setVisible(boolean visible) {
        this.soldierButton.setVisible(visible);
        super.setVisible(visible);
    }

    static {
        text = LocalizeConfig.uiText.getOverlayText();
    }

    enum Type {
        INVENTORY, COMMAND
    }

}
