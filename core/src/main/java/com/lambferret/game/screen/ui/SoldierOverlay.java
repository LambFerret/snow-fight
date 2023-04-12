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
import com.lambferret.game.player.Player;
import com.lambferret.game.screen.ui.container.PolygonButton;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SoldierOverlay extends Container<ScrollPane> implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(SoldierOverlay.class.getName());
    protected static final float[] VERTICES = new float[]{0, 0, 100, 50, 100, 350, 0, 400};
    protected static final int CARD_PAD = 5;
    protected static final int SCROLL_PAD = 300;
    protected static final int BUTTON_WIDTH = 200;
    protected static final int BUTTON_HEIGHT = 200;
    protected static final int BUTTON_Y = 300;
    protected static final int HIDE_THRESHOLD_X = 200;


    private final Stage stage;
    private final ScrollPane scrollPane;
    private final PolygonButton hideButton;
    private boolean isSimple = true;
    private boolean isHide = false;


    public SoldierOverlay(Stage stage) {
        this.stage = stage;
        this.scrollPane = new ScrollPane(new Table());
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = new TextureRegionDrawable(AssetFinder.getTexture("fileIron"));
        style.font = GlobalSettings.font;
        hideButton = new PolygonButton("ui text todo", style, VERTICES);

        stage.addActor(hideButton);
        stage.addActor(this);
        this.setActor(this.scrollPane);
    }

    public void create() {
        this.setPosition(0, 0);
        this.setDebug(true, true);

        hideButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        hideButton.setPosition(GlobalSettings.currWidth - BUTTON_WIDTH, BUTTON_Y);
        this.setSize(GlobalSettings.currWidth - BUTTON_WIDTH, GlobalSettings.currHeight);

        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setPosition(this.getX(), this.getY());
        scrollPane.setSize(this.getWidth(), this.getHeight());
    }

    @Override
    public void init(Player player) {
        makeSoldierContainer(player.getSoldiers());
        hide(true);

        var overlay = this;
        hideButton.addListener(new DragListener() {
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                hideButton.moveBy(x - getDragStartX(), 0);
                overlay.moveBy(x - getDragStartX(), 0);
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if (hideButton.getX() > HIDE_THRESHOLD_X && isHide) {
                    show();
                } else if (hideButton.getX() < overlay.getWidth() - HIDE_THRESHOLD_X && !isHide) {
                    hide(false);
                } else {
                    resetLocation();
                }
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

    private void makeSoldierContainer(List<Soldier> soldiers) {
        Table table = new Table();
        int i = 0;
        for (Soldier soldier : soldiers) {
            var card = renderSoldier(soldier);
            table.add(card).pad(CARD_PAD);
            if ((i++ + 1) * (card.getWidth() + CARD_PAD) > scrollPane.getWidth() - SCROLL_PAD) {
                table.row();
                i = 0;
            }
        }
        this.scrollPane.setActor(table);
    }

    private ImageTextButton renderSoldier(Soldier soldier) {
        var style = new ImageTextButton.ImageTextButtonStyle();
        style.font = GlobalSettings.font;
        style.up = new TextureRegionDrawable(soldier.renderFront());
        ImageTextButton soldierButton = new ImageTextButton(soldier.getName(), style);
        soldierButton.setSize(OVERLAY_HEIGHT * 2 / 3.0F, OVERLAY_HEIGHT);
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

    private void hide(boolean isInstant) {
        if (isInstant) {
            hideButton.setX(0);
            this.setX(-this.getWidth());
        } else {
            hideButton.addAction(
                Actions.moveBy(-hideButton.getX(), 0, ANIMATION_DURATION)
            );
            this.addAction(
                Actions.moveTo(-this.getWidth(), 0, ANIMATION_DURATION)
            );
        }
        isHide = true;
    }

    private void show() {
        this.addAction(
            Actions.moveTo(0, 0, ANIMATION_DURATION)
        );
        hideButton.addAction(
            Actions.moveBy(this.getWidth() - hideButton.getX(), 0, ANIMATION_DURATION)
        );
        isHide = false;
    }

    private void resetLocation() {
        if (isHide) {
            hide(false);
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

}
