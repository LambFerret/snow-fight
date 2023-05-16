package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.*;
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
import com.lambferret.game.manual.Manual;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.OverlayText;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InventoryOverlay extends Container<ScrollPane> implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(InventoryOverlay.class.getName());
    private static final OverlayText text;
    private final Stage stage;
    private final ScrollPane scrollPane;
    private Table soldierTable = new Table();
    private Table commandTable = new Table();
    private Table manualTable = new Table();
    private Type currentType = Type.SOLDIER;
    private final PolygonButton soldierButton;
    private final PolygonButton commandButton;
    private boolean isHide = true;

    public InventoryOverlay(Stage stage) {
        this.stage = stage;
        this.scrollPane = new ScrollPane(new Table());
        this.background(new TextureRegionDrawable(AssetFinder.getTexture("ui/inventory")));
        soldierButton = new PolygonButton(text.getSoldierOverlayName(), getHideButtonStyle(), SOLDIER_HIDE_BUTTON_VERTICES);
        commandButton = new PolygonButton(text.getSoldierOverlayName(), getHideButtonStyle(), SOLDIER_HIDE_BUTTON_VERTICES);

        this.setPosition(SOLDIER_X, SOLDIER_Y);
        this.setSize(SOLDIER_WIDTH, SOLDIER_HEIGHT);

        soldierButton.setPosition(SOLDIER_HIDE_BUTTON_X, SOLDIER_HIDE_BUTTON_Y);
        soldierButton.setSize(SOLDIER_HIDE_BUTTON_WIDTH, SOLDIER_HIDE_BUTTON_HEIGHT);

        commandButton.setPosition(SOLDIER_HIDE_BUTTON_X, SOLDIER_HIDE_BUTTON_Y);
        commandButton.setSize(SOLDIER_HIDE_BUTTON_WIDTH, SOLDIER_HIDE_BUTTON_HEIGHT);
        commandButton.setVisible(false);
        commandButton.setColor(Color.ORANGE);

        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setPosition(this.getX(), this.getY());
        scrollPane.setSize(this.getWidth(), this.getHeight());

        stage.addActor(this);
        stage.addActor(commandButton);
        stage.addActor(soldierButton);
        this.setActor(this.scrollPane);

        soldierButton.addListener(hideButtonDragListener(Type.SOLDIER));
        commandButton.addListener(hideButtonDragListener(Type.COMMAND));
        scrollPane.addListener(scrollPaneInputListener());

        if (isHide) {
            this.setX(SOLDIER_HIDE_X);
            soldierButton.setX(SOLDIER_HIDE_BUTTON_HIDE_X);
        }
    }

    @Override
    public void onPlayerReady() {
        Player player = SnowFight.player;

        makeSoldierContainer(player);
        makeManualContainer(player);
        makeCommandContainer(player);

        this.scrollPane.setActor(soldierTable);
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {
        Player player = SnowFight.player;
        switch (type) {
            case SOLDIER -> makeSoldierContainer(player);
            case MANUAL -> makeManualContainer(player);
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
                if (soldierButton.getX() > SOLDIER_HIDE_MOVEMENT_THRESHOLD_X && isHide) {
                    show();
                } else if (soldierButton.getX() < SOLDIER_WIDTH - SOLDIER_HIDE_MOVEMENT_THRESHOLD_X && !isHide) {
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

    private InputListener scrollPaneInputListener() {
        return new InputListener() {
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
        };
    }

    private void setScrollActor(Type type) {
        switch (type) {
            case SOLDIER -> {
                scrollPane.setActor(soldierTable);
                currentType = Type.SOLDIER;
                soldierButton.toFront();
            }
            case COMMAND -> {
                scrollPane.setActor(commandTable);
                currentType = Type.COMMAND;
                commandButton.toFront();
            }
            case MANUAL -> {
                scrollPane.setActor(manualTable);
                currentType = Type.MANUAL;
//                manualButton.toFront();
            }
        }
    }

    private void makeSoldierContainer(Player player) {
        soldierTable = new Table();
        int i = 0;
        for (Soldier soldier : player.getSoldiers()) {
            Container<Group> card = soldier.card();
            card.setSize(SOLDIER_EACH_WIDTH, SOLDIER_EACH_HEIGHT);
            soldierTable.add(card).pad(SOLDIER_EACH_PAD);
            if (i++ == 6) {
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
            card.setSize(SOLDIER_EACH_WIDTH, SOLDIER_EACH_HEIGHT);
            commandTable.add(card).pad(SOLDIER_EACH_PAD);
            if (i++ == 6) {
                commandTable.row();
                i = 0;
            }
        }
        scrollPane.setActor(commandTable);
    }

    private void makeManualContainer(Player player) {
        manualTable = new Table();
        int i = 0;
        for (Manual manual : player.getManuals()) {
            var card = manual.renderFrontCover();
            card.setSize(SOLDIER_EACH_WIDTH, SOLDIER_EACH_HEIGHT);
            manualTable.add(card).pad(SOLDIER_EACH_PAD);
            if (i++ == 6) {
                manualTable.row();
                i = 0;
            }
        }
        scrollPane.setActor(manualTable);
    }

    private void hide() {
        this.addAction(
            Actions.moveBy(-soldierButton.getX(), 0, SOLDIER_HIDE_ANIMATION_DURATION)
        );
        soldierButton.addAction(
            Actions.moveBy(-soldierButton.getX(), 0, SOLDIER_HIDE_ANIMATION_DURATION)
        );
        commandButton.setVisible(false);
        commandButton.setPosition(SOLDIER_HIDE_BUTTON_X, SOLDIER_HIDE_BUTTON_Y);
        isHide = true;
    }

    private void show() {
        setScrollActor(Type.SOLDIER);
        this.addAction(
            Actions.moveBy(SOLDIER_WIDTH - soldierButton.getX(), 0, SOLDIER_HIDE_ANIMATION_DURATION)
        );
        soldierButton.addAction(
            Actions.moveBy(SOLDIER_WIDTH - soldierButton.getX(), 0, SOLDIER_HIDE_ANIMATION_DURATION)
        );
        commandButton.addAction(
            Actions.sequence(
                Actions.delay(SOLDIER_HIDE_ANIMATION_DURATION),
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
        SOLDIER, COMMAND, MANUAL
    }

}
