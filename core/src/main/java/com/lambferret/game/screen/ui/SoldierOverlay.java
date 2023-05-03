package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.SnowFight;
import com.lambferret.game.command.Command;
import com.lambferret.game.component.CustomButton;
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

public class SoldierOverlay extends Container<ScrollPane> implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(SoldierOverlay.class.getName());
    private static final OverlayText text;
    private final Stage stage;
    private final ScrollPane scrollPane;
    private Table soldierTable = new Table();
    private Table commandTable = new Table();
    private Table manualTable = new Table();
    private Table radioButtonTable = new Table();
    ButtonGroup<CustomButton> radio = new ButtonGroup<>();
    private final PolygonButton hideButton;
    private boolean isHide = true;

    public SoldierOverlay(Stage stage) {
        this.stage = stage;
        this.scrollPane = new ScrollPane(new Table());
        hideButton = new PolygonButton(text.getSoldierOverlayName(), getHideButtonStyle(), SOLDIER_HIDE_BUTTON_VERTICES);
        this.background(new TextureRegionDrawable(AssetFinder.getTexture("")));

        this.setPosition(SOLDIER_X, SOLDIER_Y);
        this.setSize(SOLDIER_WIDTH, SOLDIER_HEIGHT);

        hideButton.setPosition(SOLDIER_HIDE_BUTTON_X, SOLDIER_HIDE_BUTTON_Y);
        hideButton.setSize(SOLDIER_HIDE_BUTTON_WIDTH, SOLDIER_HIDE_BUTTON_HEIGHT);

        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setPosition(this.getX(), this.getY());
        scrollPane.setSize(this.getWidth(), this.getHeight());

        stage.addActor(this);
        stage.addActor(hideButton);
        this.setActor(this.scrollPane);
        makeRadioButtonContainer();
        stage.addActor(radioButtonTable);

        hideButton.addListener(hideButtonDragListener());

        scrollPane.addListener(scrollPaneInputListener());

        if (isHide) {
            this.setX(SOLDIER_HIDE_X);
            hideButton.setX(SOLDIER_HIDE_BUTTON_HIDE_X);
            radioButtonTable.setX(SOLDIER_HIDE_X);
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
        logger.info("onPlayerUpdate |  🐳 player soldier | " + player.getSoldiers());
        switch (type) {
            case SOLDIER -> makeSoldierContainer(player);
            case MANUAL -> makeManualContainer(player);
            case COMMAND -> makeCommandContainer(player);
        }
    }

    private DragListener hideButtonDragListener() {
        return new DragListener() {
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
            var card =  manual.renderFrontCover();
            card.setSize(SOLDIER_EACH_WIDTH, SOLDIER_EACH_HEIGHT);
            manualTable.add(card).pad(SOLDIER_EACH_PAD);
            if (i++ == 6) {
                manualTable.row();
                i = 0;
            }
        }
        scrollPane.setActor(manualTable);
    }


    private void makeRadioButtonContainer() {
        radioButtonTable = new Table();
        radioButtonTable.setDebug(true, true);
        CustomButton soldierButton = makeRadioButton(Type.SOLDIER);
        CustomButton commandButton = makeRadioButton(Type.COMMAND);
        CustomButton manualButton = makeRadioButton(Type.MANUAL);

        radio.add(soldierButton, commandButton, manualButton);
        radioButtonTable.add(soldierButton, commandButton, manualButton);
        radio.setMaxCheckCount(1);
        radio.setMinCheckCount(1);
        radio.setUncheckLast(true);

        radioButtonTable.setSize(OVERLAY_BORDERLINE_WIDTH / 5, OVERLAY_BORDERLINE_HEIGHT / 2);
    }

    private CustomButton makeRadioButton(Type type) {
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = new TextureRegionDrawable(AssetFinder.getTexture("button/checkbox_unchecked"));
        style.down = new TextureRegionDrawable(AssetFinder.getTexture("button/checkbox_checked"));
        style.font = GlobalSettings.font;
        CustomButton button = new CustomButton(type.name(), style);
        switch (type) {
            case SOLDIER -> {
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        scrollPane.setActor(soldierTable);
                    }
                });
            }
            case COMMAND -> {
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        scrollPane.setActor(commandTable);

                    }
                });
            }
            case MANUAL -> {
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        scrollPane.setActor(manualTable);
                    }
                });
            }
        }
        return button;
    }

    private void hide() {
        this.addAction(
            Actions.moveBy(-hideButton.getX(), 0, SOLDIER_HIDE_ANIMATION_DURATION)
        );
        hideButton.addAction(
            Actions.moveBy(-hideButton.getX(), 0, SOLDIER_HIDE_ANIMATION_DURATION)
        );
        radioButtonTable.addAction(
            Actions.moveTo(-(GlobalSettings.currWidth - OVERLAY_BORDERLINE_WIDTH) / 2, SNOW_BAR_HEIGHT, SOLDIER_HIDE_ANIMATION_DURATION)
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
        radioButtonTable.addAction(
            Actions.moveTo((GlobalSettings.currWidth - OVERLAY_BORDERLINE_WIDTH) / 2, SNOW_BAR_HEIGHT, SOLDIER_HIDE_ANIMATION_DURATION)
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

    private ImageTextButton.ImageTextButtonStyle getHideButtonStyle() {
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = new TextureRegionDrawable(AssetFinder.getTexture("fileIron"));
        style.font = GlobalSettings.font;
        return style;
    }

    @Override
    public void setVisible(boolean visible) {
        this.hideButton.setVisible(visible);
        super.setVisible(visible);
    }

    static {
        text = LocalizeConfig.uiText.getOverlayText();
    }

    enum Type {
        SOLDIER, COMMAND, MANUAL
    }

}
