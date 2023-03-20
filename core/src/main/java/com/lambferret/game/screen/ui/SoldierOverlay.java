package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.Soldier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SoldierOverlay extends Table implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(SoldierOverlay.class.getName());
    private final Stage stage;
    private final Table soldierContainer;
    private final ScrollPane scrollPane;

    public SoldierOverlay(Stage stage) {
        this.stage = stage;
        this.soldierContainer = new Table();
        this.scrollPane = new ScrollPane(soldierContainer);
        scrollPane.setScrollingDisabled(false, true);

    }

    public void create() {
        stage.addActor(this);
        stage.addActor(scrollPane);
        setProperty();
    }

    private void setProperty() {
        this.clear();
        //        this.add(button(GroundScreen.Screen.RECRUIT)).pad(10);
        this.setPosition(0, 0);
        this.setSize(GlobalSettings.currWidth - OVERLAY_WIDTH, OVERLAY_HEIGHT);

        this.setBackground(GlobalSettings.debugTexture);
        this.setColor(GlobalSettings.debugColorGreen);
    }

    private void makeSoldierContainer(List<Soldier> soldiers) {

        for (Soldier soldier : soldiers) {
            soldierContainer.add(renderSoldier(soldier));
        }

        soldierContainer.setPosition(300, 300);
        soldierContainer.setSize(300, 300);


    }

    private ImageTextButton renderSoldier(Soldier soldier) {
        var a = new ImageTextButton("soldier.getName()", soldierButtonStyle());


return a;
    }

    private ImageTextButton.ImageTextButtonStyle soldierButtonStyle() {
        var a = new ImageTextButton.ImageTextButtonStyle();
        a.font = GlobalSettings.font;
        return a;
    }

    @Override
    public void init() {

    }

}
