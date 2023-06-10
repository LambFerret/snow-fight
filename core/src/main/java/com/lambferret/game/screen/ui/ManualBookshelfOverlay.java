package com.lambferret.game.screen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.component.WindowDialog;
import com.lambferret.game.manual.Manual;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.setting.GlobalSettings;
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
    Stage stage;

    public ManualBookshelfOverlay(Stage stage) {
        this.stage = stage;
        this.setSize(MANUAL_WIDTH, MANUAL_HEIGHT);
        this.setPosition(MANUAL_X, MANUAL_Y);
        setBackground(GlobalUtil.getNinePatchDrawableFromTexture("bookshelf", 5));

        infoContainer.setVisible(false);

        infoContainer.setSize(100, 100);
        infoContainer.setPosition(200, 200);
        cursorCoord.setSize(200, 100);
        cursorCoord.setPosition(GlobalSettings.currWidth - cursorCoord.getWidth(), GlobalSettings.currHeight - cursorCoord.getHeight());
        stage.addActor(infoContainer);
        stage.addActor(this);
        stage.addActor(cursorCoord);
    }

    Label cursorCoord = new Label("", GlobalSettings.skin);

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
            manualButton.addListener(Input.hover(() -> {
                    infoContainer.setVisible(true);
                    infoContainer.setActor(manual.renderInfo());
                },
                () -> infoContainer.setVisible(false)
            ));
            manualButton.addListener(Input.click(() -> {
                if (Overlay.isPhaseUI) return;
                Dialog ask = new WindowDialog("", WindowDialog.WarnLevel.ERROR, "TODO remove this?") {
                    @Override
                    protected void result(Object object) {
                        if (object.equals(true)) {
                            manualButton.remove();
                            player.getManuals().remove(manual);
                        }
                    }
                };
                ask.show(stage);
            }));

            add(manualButton).size(MANUAL_EACH_WIDTH, MANUAL_EACH_HEIGHT);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        this.cursorCoord.setText("x: " + Gdx.input.getX() + " y: " + (GlobalSettings.currHeight - Gdx.input.getY()));
    }

    static {
        text = LocalizeConfig.uiText.getGroundText();
    }

}
