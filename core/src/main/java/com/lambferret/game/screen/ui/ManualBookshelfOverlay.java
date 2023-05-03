package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.manual.Manual;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.GroundText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ManualBookshelfOverlay extends Table implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(ManualBookshelfOverlay.class.getName());
    private static final GroundText text;

    private static final float MANUAL_WIDTH = 300;
    private static final float MANUAL_HEIGHT = 150;
    private static final float MANUAL_X = GlobalSettings.currWidth - (MANUAL_WIDTH + OVERLAY_BORDERLINE_WIDTH);
    private static final float MANUAL_Y = GlobalSettings.currHeight - MANUAL_HEIGHT;
    private static final float MANUAL_EACH_WIDTH = 25;
    private static final float MANUAL_EACH_HEIGHT = 50;
    private final Container<CustomButton> infoContainer = new Container<>();

    Player player;

    public ManualBookshelfOverlay(Stage stage) {
        this.setSize(MANUAL_WIDTH, MANUAL_HEIGHT);
        this.setPosition(MANUAL_X, MANUAL_Y);
        this.setDebug(true, true);

        infoContainer.setVisible(false);

        infoContainer.setSize(100, 100);
        infoContainer.setPosition(200, 200);

        stage.addActor(infoContainer);
        stage.addActor(this);
    }

    @Override
    public void onPlayerReady() {
        player = SnowFight.player;
        makeTable();
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {
        makeTable();
    }

    private void makeTable() {
        clear();
        for (Manual manual : player.getManuals()) {
            Group manualButton = manual.renderSideCover();
            manualButton.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);
                    if (pointer == -1) {
                        infoContainer.setVisible(true);
                        infoContainer.setActor(manual.renderInfo());
                    }
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                    infoContainer.setVisible(false);
                }
            });
            add(manualButton).size(MANUAL_EACH_WIDTH, MANUAL_EACH_HEIGHT);
        }
    }

    static {
        text = LocalizeConfig.uiText.getGroundText();
    }

}
