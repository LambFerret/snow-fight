package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.GroundText;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.GlobalUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PhaseOrderOverlay extends Table implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(PhaseOrderOverlay.class.getName());
    private static final GroundText text;

    private static final float ORDER_WIDTH = 300;
    private static final float ORDER_HEIGHT = 150;
    private static final float ORDER_X = (GlobalSettings.currWidth - ORDER_WIDTH) / 2;
    private static final float ORDER_Y = GlobalSettings.currHeight - ORDER_HEIGHT;
    private final Container<CustomButton> infoContainer = new Container<>();
    int currPhase = 0;

    Player player;

    public PhaseOrderOverlay(Stage stage) {
        this.setSize(ORDER_WIDTH, ORDER_HEIGHT);
        this.setPosition(ORDER_X, ORDER_Y);
        this.setDebug(true, true);

        infoContainer.setVisible(false);

        infoContainer.setSize(100, 100);
        infoContainer.setPosition(200, 200);

        infoContainer.setBackground(new TextureRegionDrawable(AssetFinder.getTexture("ui/phaseOrderInfo")));
        stage.addActor(infoContainer);
        stage.addActor(this);
    }

    @Override
    public void onPlayerReady() {
        player = SnowFight.player;
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {
    }

    public void makeTable() {
        clear();
        currPhase = 0;
        addPre();
        for (int i = 0; i <= PhaseScreen.level.getMaxIteration(); i++) {
            addPhase();
        }
    }

    private void addPre() {
        CustomButton pre = GlobalUtil.simpleButton("ui/prePhase");
        pre.setColor(Color.RED);
        pre.addAction(shake());
        pre.addListener(showInfoListener("TODO : pre phase"));
        add(pre).pad(5);
    }

    private void addPhase() {
        addSpace();
        CustomButton ready = GlobalUtil.simpleButton("ui/readyPhase");
        ready.setColor(Color.GREEN);
        add(ready).pad(5);
        ready.addListener(showInfoListener("TODO : readyPhase"));
        addSpace();
        CustomButton action = GlobalUtil.simpleButton("ui/actionPhase");
        action.setColor(Color.BLUE);
        action.addListener(showInfoListener("TODO : actionPhase"));
        add(action).pad(5);
    }

    private void addSpace() {
        CustomButton space = GlobalUtil.simpleButton("ui/space");
        space.setColor(Color.DARK_GRAY);
        space.addListener(showInfoListener("TODO : EMPTY SPACE!!! this will have arrow or something"));
        add(space).pad(5);
    }

    public void next() {
        try {
            getCells().get(currPhase).getActor().clearActions();
            currPhase = currPhase + 2;
            getCells().get(currPhase).getActor().addAction(shake());
        } catch (IndexOutOfBoundsException e) {
            logger.info("Phase order finished");
        }
    }

    private InputListener showInfoListener(String description) {
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = GlobalUtil.getNinePatchDrawableFromTexture("itemUI_description", 5);
        style.font = GlobalSettings.font;
        return new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (pointer == -1) {
                    infoContainer.setVisible(true);
                    infoContainer.setActor(new CustomButton(description, style));
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                infoContainer.setVisible(false);
            }
        };
    }

    private RepeatAction shake() {
        return Actions.forever(
            Actions.sequence(
                Actions.moveBy(5, 0, 0.5f),
                Actions.moveBy(-5, 0, 0.5f)
            )
        );
    }


    static {
        text = LocalizeConfig.uiText.getGroundText();
    }

}
