package com.lambferret.game.screen.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.Soldier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SoldierOverlay extends Container<ScrollPane> implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(SoldierOverlay.class.getName());
    private final Stage stage;
    private final ScrollPane scrollPane;
    private boolean isSimple = true;

    public SoldierOverlay(Stage stage) {
        this.stage = stage;
        this.stage.addActor(this);
        this.scrollPane = new ScrollPane(new Table());
        this.setActor(this.scrollPane);
        stage.setKeyboardFocus(this);
        stage.addActor(scrollPane);
    }

    public void create() {
        this.setPosition(0, 0);
        this.setSize(GlobalSettings.currWidth - OVERLAY_WIDTH, OVERLAY_HEIGHT);
        this.setBackground(GlobalSettings.debugTexture);
        this.setColor(GlobalSettings.debugColorGreen);

        scrollPane.setScrollingDisabled(false, true);
        scrollPane.setPosition(this.getX(), this.getY());
        scrollPane.setSize(this.getWidth(), this.getHeight());
    }

    @Override
    public void init(Player player) {
        makeDogTagContainer(player.getSoldiers());

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

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.I) {
                    changeContainer(player);
                }
                return super.keyDown(event, keycode);
            }
        });
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                logger.info("clicked |  🐳 ? | " + event);
            }
        });
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

    private void makeSoldierContainer(List<Soldier> soldiers) {
        var soldierContainer = new Table();
        for (Soldier soldier : soldiers) {
            soldierContainer.add(renderSoldier(soldier)).pad(50);
        }
        this.scrollPane.setActor(soldierContainer);
    }

    private void makeDogTagContainer(List<Soldier> soldiers) {
        var soldierContainer = new Table();
        int numRows = 2;
        int index = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < soldiers.size() / numRows + 1; j++) {
                if (index >= soldiers.size()) continue;
                soldierContainer.add(renderSoldier(soldiers.get(index++))).pad(50);
            }
            soldierContainer.row();
        }
        this.scrollPane.setActor(soldierContainer);
    }

    private ImageTextButton renderSoldier(Soldier soldier) {
        var a = new ImageTextButton(soldier.getName(), soldierButtonStyle());
        return a;
    }

    private ImageTextButton.ImageTextButtonStyle soldierButtonStyle() {
        var a = new ImageTextButton.ImageTextButtonStyle();
        a.font = GlobalSettings.font;
        return a;
    }

}
