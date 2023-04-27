package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.component.PolygonButton;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.PhaseText;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SoldierOverlay extends Container<ScrollPane> implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(SoldierOverlay.class.getName());
    private static final PhaseText text;
    private final Stage stage;
    private final ScrollPane scrollPane;
    private final PolygonButton hideButton;
    private Player player;
    private boolean isSimple = true;
    private boolean isHide = true;


    public SoldierOverlay(Stage stage) {
        this.stage = stage;
        this.scrollPane = new ScrollPane(new Table());
        hideButton = new PolygonButton(text.getSoldierOverlay(), getHideButtonStyle(), SOLDIER_HIDE_BUTTON_VERTICES);

        stage.addActor(hideButton);
        stage.addActor(this);
        this.setActor(this.scrollPane);
    }

    public void create() {
        this.setPosition(SOLDIER_X, SOLDIER_Y);
        this.setSize(SOLDIER_WIDTH, SOLDIER_HEIGHT);
        this.setDebug(true, true);

        hideButton.setPosition(SOLDIER_HIDE_BUTTON_X, SOLDIER_HIDE_BUTTON_Y);
        hideButton.setSize(SOLDIER_HIDE_BUTTON_WIDTH, SOLDIER_HIDE_BUTTON_HEIGHT);

        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setPosition(this.getX(), this.getY());
        scrollPane.setSize(this.getWidth(), this.getHeight());

        if (isHide) {
            this.setX(SOLDIER_HIDE_X);
            hideButton.setX(SOLDIER_HIDE_BUTTON_HIDE_X);
        }
    }

    @Override
    public void init(Player player) {
        var overlay = this;
        this.player = player;

        this.scrollPane.setActor(makeSoldierContainer(player.getSoldiers()));

        hideButton.addListener(new DragListener() {
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                hideButton.moveBy(x - getDragStartX(), 0);
                overlay.moveBy(x - getDragStartX(), 0);
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

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                logger.info("clicked |  🐳 ? | " + event);
            }
        });
    }

    private Table makeSoldierContainer(List<Soldier> soldiers) {
        Table table = new Table();
        int i = 0;
        for (Soldier soldier : soldiers) {
            var card = renderSoldier(soldier);
            table.add(card).pad(SOLDIER_EACH_PAD);
            if ((i++ + 1) * (card.getWidth() + SOLDIER_EACH_PAD) > scrollPane.getWidth() - SOLDIER_CARD_MARGIN) {
                table.row();
                i = 0;
            }
        }
        return table;
    }

    private CustomButton renderSoldier(Soldier soldier) {
        CustomButton soldierButton = new CustomButton(soldier.getName(), soldierButtonStyle(soldier));

        soldierButton.setSize(SOLDIER_EACH_WIDTH, SOLDIER_EACH_HEIGHT);
        soldierButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                //Hover Information
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                //unHover Information
            }
        });
        return soldierButton;
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

    public void changeContainer(Player player) {
        if (this.isSimple) {
            makeSoldierContainer(player.getSoldiers());
            this.isSimple = false;
        } else {
            makeDogTagContainer(player.getSoldiers());
            this.isSimple = true;
        }
    }

    private void makeDogTagContainer(List<Soldier> soldiers) {
        var soldierContainer = new Table();
        int numRows = 2;
        int index = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < soldiers.size() / numRows + 1; j++) {
                if (index >= soldiers.size()) continue;
                soldierContainer.add(renderSoldier(soldiers.get(index++))).pad(10);
            }
            soldierContainer.row();
        }
        this.scrollPane.setActor(soldierContainer);
    }

    private ImageTextButton.ImageTextButtonStyle getHideButtonStyle() {
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = new TextureRegionDrawable(AssetFinder.getTexture("fileIron"));
        style.font = GlobalSettings.font;
        return style;
    }

    private ImageTextButton.ImageTextButtonStyle soldierButtonStyle(Soldier soldier) {
        var style = new ImageTextButton.ImageTextButtonStyle();
        style.up = new TextureRegionDrawable(soldier.renderFront());
        style.font = GlobalSettings.font;
        return style;
    }

    static {
        text = LocalizeConfig.uiText.getPhaseText();
    }

}
