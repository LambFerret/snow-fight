package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SoldierOverlay extends Container<ScrollPane> implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(SoldierOverlay.class.getName());

    private final Stage stage;
    private final ScrollPane scrollPane;
    private ImageButton hideButton;
    private boolean isSimple = true;
    public boolean isHide = false;


    public SoldierOverlay(Stage stage) {
        this.stage = stage;
        this.stage.addActor(this);
        this.scrollPane = new ScrollPane(new Table());
        this.setActor(this.scrollPane);
    }

    public void create() {
        this.setPosition(0, 0);
        this.setSize(GlobalSettings.currWidth - OVERLAY_WIDTH, OVERLAY_HEIGHT);
        this.setDebug(true, true);

        this.hideButton = hideSwitch();
        stage.addActor(hideButton);

        scrollPane.setScrollingDisabled(false, true);
        scrollPane.setPosition(this.getX(), this.getY());
        scrollPane.setSize(this.getWidth(), this.getHeight());
    }

    @Override
    public void init(Player player) {
        makeSoldierContainer(player.getSoldiers());
        instantHide();

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
        for (Soldier soldier : soldiers) {
            table.add(renderSoldier(soldier)).pad(5);
        }
        this.scrollPane.setActor(table);
    }

    private ImageTextButton renderSoldier(Soldier soldier) {
        ImageTextButton soldierButton = new ImageTextButton(soldier.getName(), soldierButtonStyle(soldier));
        soldierButton.setSize(scrollPane.getHeight() * 2 / 3.0F, scrollPane.getHeight());
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
                logger.info("exit |  🐳  | " + event);
                logger.info("exit |  🐳  | " + pointer);
            }
        });
        return soldierButton;
    }

    private ImageTextButton.ImageTextButtonStyle soldierButtonStyle(Soldier soldier) {
        var a = new ImageTextButton.ImageTextButtonStyle();
        a.font = GlobalSettings.font;
        a.up = new TextureRegionDrawable(soldier.renderFront());
        return a;
    }

    private ImageButton.ImageButtonStyle hideButtonStyle() {
        var a = new ImageButton.ImageButtonStyle();
        a.up = new TextureRegionDrawable(AssetFinder.getTexture("scrollPointer_H"));
        return a;
    }

    private ImageButton hideSwitch() {
        SoldierOverlay thisOverlay = this;
        ImageButton hideSwitch = new ImageButton(hideButtonStyle());

        hideSwitch.setSize(HIDE_BUTTON_WIDTH, HIDE_BUTTON_HEIGHT);
        hideSwitch.setPosition(
            thisOverlay.getX() + PADDING,
            thisOverlay.getY() + thisOverlay.getHeight() + PADDING
        );
        hideSwitch.setOrigin(Align.center);

        hideSwitch.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isHide) {
                    show();
                } else {
                    hide();
                }
            }
        });
        return hideSwitch;
    }

    private void hide() {
        hide(false);
    }

    private void instantHide() {
        hide(true);
    }

    private void hide(boolean isInstant) {
        if (isHide) return;
        float instant = isInstant ? 0.0F : ANIMATION_DURATION;
        this.addAction(
            Actions.moveBy(0, -(this.getHeight() + PADDING), instant)
        );
        hideButton.addAction(
            Actions.moveBy(0, -(this.getHeight() + PADDING), instant)
        );
        hideButton.addAction(
            Actions.rotateBy(90, instant)
        );
        isHide = true;
    }

    private void show() {
        if (!isHide) return;
        this.addAction(
            Actions.moveBy(0, this.getHeight() + PADDING, ANIMATION_DURATION)
        );
        hideButton.addAction(
            Actions.moveBy(0, this.getHeight() + PADDING, ANIMATION_DURATION)
        );

        hideButton.addAction(
            Actions.rotateBy(90, ANIMATION_DURATION)
        );
        isHide = false;
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
