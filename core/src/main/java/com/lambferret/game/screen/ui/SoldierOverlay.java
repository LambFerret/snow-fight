package com.lambferret.game.screen.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

public class SoldierOverlay extends Table implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(SoldierOverlay.class.getName());
    private final Stage stage;
    private Table soldierContainer;
    private ScrollPane scrollPane;
    private boolean isSimple = true;

    public SoldierOverlay(Stage stage) {
        this.stage = stage;
        this.soldierContainer = new Table();
    }

    public void create() {
        stage.addActor(this);

        this.scrollPane = new ScrollPane(this);
        scrollPane.setScrollingDisabled(false, true);

        this.clear();
        this.setPosition(0, 0);
        this.setSize(GlobalSettings.currWidth - OVERLAY_WIDTH, OVERLAY_HEIGHT);
        scrollPane.setPosition(this.getX(), this.getY());
        scrollPane.setSize(this.getWidth(), this.getHeight());

        this.setBackground(GlobalSettings.debugTexture);
        this.setColor(GlobalSettings.debugColorGreen);
    }

    @Override
    public void init(Player player) {
        makeDogTagContainer(player.getSoldiers());
        this.add(soldierContainer);
        stage.addActor(scrollPane);

        this.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                logger.info("keyDown |  🐳 woeifjweiofwjefoijwefoiwjef | ");
                if (keycode == Input.Keys.I) {
                    makeSoldierContainer(player.getSoldiers());
                    makeDogTagContainer(player.getSoldiers());
                }
                return super.keyDown(event, keycode);
            }
        });
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                logger.info("clicked |  🐳 ? | " + event);
//                super.clicked(event, x, y);
//                if (keycode == Input.Keys.I) {
                makeSoldierContainer(player.getSoldiers());
                makeDogTagContainer(player.getSoldiers());
//                }
            }
        });
    }

    private void makeSoldierContainer(List<Soldier> soldiers) {
        if (!this.isSimple) return;
        logger.info("makeSoldierContainer |  🐳 woefijwoefijzzzzz | " );
        var soldierContainer = new Table();
        for (Soldier soldier : soldiers) {
            soldierContainer.add(renderSoldier(soldier)).pad(50);
        }
        this.soldierContainer = soldierContainer;
        this.isSimple = false;
    }

    private void makeDogTagContainer(List<Soldier> soldiers) {
        if (this.isSimple) return;
        logger.info("makeDogTagContainer |  🐳 ???? | " + this.isSimple);
        var soldierContainer = new Table();

        int numRows = 2;
        int index = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < soldiers.size() / numRows + 1; j++) {
                if (index == soldiers.size()) continue;
                soldierContainer.add(renderSoldier(soldiers.get(index++))).pad(50);
            }
            soldierContainer.row();
        }
        this.soldierContainer = soldierContainer;
        this.isSimple = true;
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
