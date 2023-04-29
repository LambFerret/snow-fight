package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.lambferret.game.command.Command;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.player.Player;
import com.lambferret.game.screen.phase.PhaseScreen;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CommandOverlay extends Container<ScrollPane> implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(CommandOverlay.class.getName());

    private final Stage stage;
    private final ScrollPane scrollPane;
    private final ImageButton hideButton;
    private Player player;
    private boolean isHide = false;


    public CommandOverlay(Stage stage) {
        this.stage = stage;
        this.scrollPane = new ScrollPane(new Table());
        hideButton = new ImageButton(hideButtonStyle());

        this.setActor(this.scrollPane);
        this.stage.addActor(hideButton);
        this.stage.addActor(this);
    }

    public void create() {
        this.setPosition(COMMAND_X, COMMAND_Y);
        this.setSize(COMMAND_WIDTH, COMMAND_HEIGHT);

        hideButton.setSize(COMMAND_HIDE_BUTTON_WIDTH, COMMAND_HIDE_BUTTON_HEIGHT);
        hideButton.setPosition(COMMAND_HIDE_BUTTON_X, COMMAND_HIDE_BUTTON_Y);
        hideButton.setOrigin(Align.center);

        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setPosition(this.getX(), this.getY());
        scrollPane.setSize(this.getWidth(), this.getHeight());
        this.background(new TextureRegionDrawable(AssetFinder.getTexture("conversationWindow")));

        if (isHide) {
            this.setX(COMMAND_HIDE_X);
            hideButton.setX(COMMAND_HIDE_BUTTON_HIDE_X);
        }
    }

    @Override
    public void init(Player player) {
        this.player = player;
        this.scrollPane.setActor(makeCommandContainer(player.getCommands()));

        hideButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isHide) {
                    show();
                } else {
                    hide();
                }
            }
        });

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
        });
    }

    private Table makeCommandContainer(List<Command> commands) {
        Table table = new Table();
        for (Command command : commands) {
            table.add(renderCommand(command));
            table.row().pad(COMMAND_EACH_PADDING);
        }
        return table;
    }

    private CustomButton renderCommand(Command command) {
        CustomButton commandButton = new CustomButton(command.getName(), commandButtonStyle(command));

        commandButton.setSize(COMMAND_EACH_WIDTH, COMMAND_EACH_HEIGHT);
        commandButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (PhaseScreen.getCurrentScreen() == PhaseScreen.Screen.READY && player.useCost(command.getCost())) {
                    logger.info("command Info |  🐳 | " + command.getName() + " is active ");
                    logger.info("cost info    |  🐳 | " + command.getCost() + " is used and" + player.getCurrentCost() + " is left");
                    PhaseScreen.getCommands().put(command, null);
                    commandButton.remove();
                } else {
                    logger.info("clicked |  🐳 not ready phase currently | ");
                }
                super.clicked(event, x, y);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                if (pointer == -1) {
                    logger.info("command Info | 🐳 | " + command.getName());
                    logger.info("command Info | 🐳 | " + command.getEffectDescription());
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                //unHover Information
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
        isHide = true;
    }

    private void show() {
        this.addAction(
            Actions.moveTo(COMMAND_X, COMMAND_Y, COMMAND_HIDE_ANIMATION_DURATION)
        );
        hideButton.addAction(
            Actions.moveTo(COMMAND_HIDE_BUTTON_X, COMMAND_HIDE_BUTTON_Y, COMMAND_HIDE_ANIMATION_DURATION)
        );
        isHide = false;
    }

    @Override
    public void setVisible(boolean visible) {
        this.hideButton.setVisible(visible);
        super.setVisible(visible);
    }

    private ImageButton.ImageButtonStyle hideButtonStyle() {
        var style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(AssetFinder.getTexture("scrollPointer_H"));
        return style;
    }

    private ImageTextButton.ImageTextButtonStyle commandButtonStyle(Command command) {
        var style = new ImageTextButton.ImageTextButtonStyle();
        style.up = new TextureRegionDrawable(command.render());
        style.font = GlobalSettings.font;
        return style;
    }

}
