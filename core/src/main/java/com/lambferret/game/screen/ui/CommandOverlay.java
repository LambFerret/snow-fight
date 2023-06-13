package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.lambferret.game.SnowFight;
import com.lambferret.game.buff.Buff;
import com.lambferret.game.command.Command;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.screen.ground.ShopScreen;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;
import com.lambferret.game.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CommandOverlay extends Container<ScrollPane> implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(CommandOverlay.class.getName());

    private final Stage stage;
    private final ScrollPane scrollPane;
    private final CustomButton hideButton;
    private final Container<CustomButton> infoContainer = new Container<>();
    private Player player;
    private int maxCommand;
    private boolean isHide = false;
    private static Random handRandom;


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

    List<Command> selectedCommands = new ArrayList<>();

    @Override
    public void onPlayerReady() {
        this.player = SnowFight.player;
        this.maxCommand = player.getMaxCommandInHand();
        handRandom = new Random();
        selectCommand();
        makeCommandContainer();
    }

    private void selectCommand() {
        selectedCommands.clear();
        List<Command> commands = player.getCommands();
        while (selectedCommands.size() < Math.min(commands.size(), maxCommand)) {
            Command pickedCommand = commands.get(handRandom.random(commands.size()));
            if (!selectedCommands.contains(pickedCommand)) {
                selectedCommands.add(pickedCommand);
            }
        }
        logger.info("Command : selected " + GlobalUtil.listToString(selectedCommands));
    }

    public void makeCommandContainer() {
        Table table = new Table();
        for (Command command : selectedCommands) {
            if (command == null) {
                table.add(emptyCommand());
            } else {
                table.add(renderCommand(command));
            }
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
        commandButton.addListener(Input.click(() -> {
            if (PhaseScreen.getCurrentScreen() == PhaseScreen.Screen.READY) {
                if (player.getCurrentCost() >= finalCost) {
                    for (Buff buff : PhaseScreen.buffList) {
                        if (!buff.effectCountdown()) return;
                    }
                    player.useCost(finalCost);
                    PhaseScreen.getCommands().put(command, null);
                    selectedCommands.set(selectedCommands.indexOf(command), null);
                    makeCommandContainer();
                } else {
                    commandButton.addAction(ShopScreen.rejectAction());
                }
            }
        }));
        commandButton.addListener(Input.hover(
            () -> {
                infoContainer.setVisible(true);
                infoContainer.setActor(infoButton);
            },
            () -> {
                infoContainer.setVisible(false);
            }
        ));

        return commandButton;
    }

    private CustomButton emptyCommand() {
        return GlobalUtil.simpleButton("emptyCommand");
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
