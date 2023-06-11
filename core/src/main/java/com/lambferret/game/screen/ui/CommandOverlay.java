package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.lambferret.game.SnowFight;
import com.lambferret.game.buff.Buff;
import com.lambferret.game.command.Command;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandOverlay extends Container<ScrollPane> implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(CommandOverlay.class.getName());

    private final Stage stage;
    private final ScrollPane scrollPane;
    private final CustomButton hideButton;
    private final Container<CustomButton> infoContainer = new Container<>();
    private Player player;
    private boolean isHide = false;


    public CommandOverlay(Stage stage) {
        this.stage = stage;
        this.scrollPane = new ScrollPane(new Table());
        hideButton = GlobalUtil.simpleButton("hideButton");

        infoContainer.setVisible(false);

        this.setActor(this.scrollPane);
        this.stage.addActor(hideButton);
        this.stage.addActor(infoContainer);
        this.stage.addActor(this);
        this.setPosition(COMMAND_X, COMMAND_Y);
        this.setSize(COMMAND_WIDTH, COMMAND_HEIGHT);

        hideButton.setSize(COMMAND_HIDE_BUTTON_WIDTH, COMMAND_HIDE_BUTTON_HEIGHT);
        hideButton.setPosition(COMMAND_HIDE_BUTTON_X, COMMAND_HIDE_BUTTON_Y);
        hideButton.setOrigin(Align.center);

        // TODO set position this
        infoContainer.setSize(100, 100);
        infoContainer.setPosition(100, 100);

        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setPosition(this.getX(), this.getY());
        scrollPane.setSize(this.getWidth(), this.getHeight());
        this.background(new TextureRegionDrawable(AssetFinder.getTexture("conversationWindow")));

        if (isHide) {
            this.setX(COMMAND_HIDE_X);
            hideButton.setX(COMMAND_HIDE_BUTTON_HIDE_X);
        }
        hideButton.addListener(Input.click(() -> {
            if (isHide) {
                show();
            } else {
                hide();
            }
        }));

        scrollPane.addListener(Input.setScrollFocusWhenHover(stage, scrollPane));
    }

    @Override
    public void onPlayerReady() {
        this.player = SnowFight.player;
        makeCommandContainer();
    }

    public void makeCommandContainer() {
        Table table = new Table();
        // TODO 한 페이즈에 핸드에 최대 갯수를 정해놔야함. 지금은 그냥 무한사용가능
        for (Command command : player.getCommands()) {
            table.add(renderCommand(command));
            table.row().pad(COMMAND_EACH_PADDING);
        }
        this.scrollPane.setActor(table);
    }

    private int parsingCommandBuff(Buff buff, int initCost) {
        if (buff.isExpired() || buff.getFigure() != Buff.Figure.NEXT_COMMAND) return initCost;
        int value = buff.getValue();
        int result = initCost;
        switch (buff.getOperation()) {
            case ADD -> {
                result = initCost + value;
            }
            case SUB -> {
                result = initCost - value;
            }
            case MUL -> {
                result = initCost * value;
            }
            case DIV -> {
                result = initCost / value;
            }
        }
        if (result < 0) {
            result = 0;
        }
        return result;
    }

    private Group renderCommand(Command command) {
        Group commandButton = command.renderSimple();
        CustomButton infoButton = command.renderInfo();
        infoContainer.setActor(infoButton);

        int cost = command.getCost();
        for (Buff buff : PhaseScreen.buffList) {
            cost = parsingCommandBuff(buff, cost);
        }
        commandButton.setSize(COMMAND_EACH_WIDTH, COMMAND_EACH_HEIGHT);
        int finalCost = cost;
        commandButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (PhaseScreen.getCurrentScreen() == PhaseScreen.Screen.READY) {
                    if (player.getCurrentCost() >= finalCost) {
                        for (Buff buff : PhaseScreen.buffList) {
                            if (!buff.effectCountdown()) return;
                        }
                        player.useCost(finalCost);
                        PhaseScreen.getCommands().put(command, null);
                        commandButton.remove();
                        makeCommandContainer();
                    } else {
                        logger.info("clicked |  🐳 not enough cost you have | ");
                    }
                }
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (pointer == -1) {
                    infoContainer.setVisible(true);
                    infoContainer.setActor(infoButton);
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                infoContainer.setVisible(false);
            }
        });
        return commandButton;
    }

    private void hide() {
        this.addAction(
            Actions.moveTo(COMMAND_HIDE_X, COMMAND_Y, COMMAND_HIDE_ANIMATION_DURATION)
        );
        hideButton.addAction(
            Actions.moveTo(COMMAND_HIDE_BUTTON_HIDE_X, COMMAND_HIDE_BUTTON_Y, COMMAND_HIDE_ANIMATION_DURATION)
        );
        hideButton.addAction(
            Actions.rotateBy(180, COMMAND_HIDE_ANIMATION_DURATION * 2)
        );
        isHide = true;
    }

    private void show() {
        this.addAction(
            Actions.moveTo(COMMAND_X, COMMAND_Y, COMMAND_HIDE_ANIMATION_DURATION)
        );
        hideButton.addAction(
            Actions.moveTo(COMMAND_HIDE_BUTTON_X, COMMAND_HIDE_BUTTON_Y, COMMAND_HIDE_ANIMATION_DURATION)
        );
        hideButton.addAction(
            Actions.rotateBy(180, COMMAND_HIDE_ANIMATION_DURATION * 2)
        );
        isHide = false;
    }

    @Override
    public void setVisible(boolean visible) {
        this.hideButton.setVisible(visible);
        super.setVisible(visible);
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {
        makeCommandContainer();
    }

}
