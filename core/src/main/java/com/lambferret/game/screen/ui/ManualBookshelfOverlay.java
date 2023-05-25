package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.manual.Manual;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.GroundText;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ManualBookshelfOverlay extends Table implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(ManualBookshelfOverlay.class.getName());
    private static final GroundText text;

    private final Container<CustomButton> infoContainer = new Container<>();

    Player player;

    public ManualBookshelfOverlay(Stage stage) {
        this.setSize(MANUAL_WIDTH, MANUAL_HEIGHT);
        this.setPosition(MANUAL_X, MANUAL_Y);
        this.setDebug(true, true);
        setBackground(GlobalUtil.getNinePatchDrawableFromTexture("bookshelf", 5));
        this.pack();

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
            Image manualButton = manual.renderSideCover();
            manualButton.addListener(
                Input.hover(() -> {
                        infoContainer.setVisible(true);
                        infoContainer.setActor(manual.renderInfo());
                    },
                    () -> infoContainer.setVisible(false)
                ));
            add(manualButton).size(MANUAL_EACH_WIDTH, MANUAL_EACH_HEIGHT);
        }
    }

    static {
        text = LocalizeConfig.uiText.getGroundText();
    }

}
