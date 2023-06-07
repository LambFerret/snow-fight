package com.lambferret.game.screen.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.PhaseText;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PhaseOrderOverlay extends Table implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(PhaseOrderOverlay.class.getName());
    private static final PhaseText text;
    private final Container<CustomButton> infoContainer = new Container<>();
    int currPhase = 0;
    Player player;

    public PhaseOrderOverlay(Stage stage) {
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
    }

    public void makeTable() {
        clear();
        currPhase = 0;
        add(PhaseScreen.Screen.PRE);
        for (int i = 0; i <= PhaseScreen.level.getMaxIteration(); i++) {
            addSpace();
            add(PhaseScreen.Screen.READY);
            addSpace();
            add(PhaseScreen.Screen.ACTION);
        }
        setSize(getCells().size * ORDER_BUTTON_SIZE, ORDER_BUTTON_SIZE);
        setPosition((GlobalSettings.currWidth - getWidth()) / 2, GlobalSettings.currHeight - getHeight() - ORDER_TOP_PAD);
    }

    private void add(PhaseScreen.Screen phase) {
        CustomButton button = GlobalUtil.simpleButton("ui/" + phase.name());
        button.addListener(showInfoListener("TODO : " + phase.name()));
        Color color = switch (phase) {
            case ACTION -> Color.BLUE;
            case PRE -> Color.RED;
            case READY -> Color.GREEN;
            default -> Color.WHITE;
        };
        button.setColor(color);
        if (phase == PhaseScreen.Screen.PRE) button.addAction(highlight());
        add(button);
    }

    @Override
    public <T extends Actor> Cell<T> add(T actor) {
        return super.add(actor).width(ORDER_BUTTON_SIZE).height(ORDER_BUTTON_SIZE);
    }

    private void addSpace() {
        add(GlobalUtil.simpleButton("space"));
    }

    public void next() {
        try {
            getCells().get(currPhase).getActor().clearActions();
            getCells().get(currPhase).getActor().addAction(
                Actions.alpha(1F, 0.5F)
            );
            currPhase = currPhase + 2;
            getCells().get(currPhase).getActor().addAction(highlight());
        } catch (IndexOutOfBoundsException e) {
            logger.info("Phase order finished");
        }
    }

    private InputListener showInfoListener(String description) {
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = GlobalUtil.getNinePatchDrawableFromTexture("itemUI_description", 5);
        style.font = GlobalSettings.font;
        return Input.hover(
            () -> {
                infoContainer.setVisible(true);
                infoContainer.setActor(new CustomButton(description, style));
            },
            () -> infoContainer.setVisible(false));
    }

    private Action highlight() {
        return Actions.forever(
            Actions.sequence(
                Actions.alpha(0.3F, 0.5F),
                Actions.alpha(1F, 0.5F)
            )
        );
    }

    static {
        text = LocalizeConfig.uiText.getPhaseText();
    }

}
