package com.lambferret.game.screen.ground;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.lambferret.game.command.Command;
import com.lambferret.game.command.CupNoodle;
import com.lambferret.game.command.ThreeShift;
import com.lambferret.game.command.TricksOfTheTrade;
import com.lambferret.game.manual.DisciplineAndPunish;
import com.lambferret.game.manual.Manual;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ShopScreen implements AbstractGround {
    private static final Logger logger = LogManager.getLogger(ShopScreen.class.getName());

    public static final Stage stage = new Stage();
    public static final int MANUAL_STOCK_AMOUNT = 2;
    public static final int COMMAND_STOCK_AMOUNT = 2;
    public static final int ITEM_SIZE = 60;
    public static final int ITEM_SIZE_HOVER = 70;
    public static final float ITEM_ANIMATION_DURATION = 0.05F;

    Group forSaleList = new Group();
    List<Manual> manualStock = new ArrayList<>();
    List<Command> commandStock = new ArrayList<>();
    Player player;

    public ShopScreen() {
        stage.addActor(forSaleList);
    }

    @Override
    public void create() {
        forSaleList.setDebug(true, true);
    }

    @Override
    public void init(Player player) {
        this.player = player;

        forSaleList.setSize(800, 700);
        forSaleList.setPosition(100, 100);

        List<Command> allCommand = List.of(new TricksOfTheTrade(), new CupNoodle(), new ThreeShift());
        List<Manual> allManual = List.of(new DisciplineAndPunish());
        this.forSaleList.clear();
        fillCommandStock(allCommand);
        fillManualStock(allManual);
    }

    @Override
    public void show() {

    }

    private void fillCommandStock(List<Command> allCommand) {
        this.commandStock.clear();
        while (commandStock.size() < COMMAND_STOCK_AMOUNT) {
            int random = (int) (Math.random() * allCommand.size());
            if (!commandStock.contains(allCommand.get(random))) {
                commandStock.add(allCommand.get(random));
            }
            if (allCommand.size() < COMMAND_STOCK_AMOUNT) break;
        }
        int i = 0;
        for (Command command : commandStock) {
            ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
            style.font = GlobalSettings.font;
            style.up = command.render();
            ImageTextButton imageButton = new ImageTextButton(command.getID(), style);
            imageButton.setSize(ITEM_SIZE, ITEM_SIZE);
            imageButton.setPosition(i++ * (forSaleList.getWidth() / commandStock.size()), 0);

            imageButton.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    if (pointer == -1) {
                        imageButton.addAction(
                            Actions.sizeTo(ITEM_SIZE_HOVER, ITEM_SIZE_HOVER, ITEM_ANIMATION_DURATION)
                        );
                    }
                    super.enter(event, x, y, pointer, fromActor);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if (pointer == -1) {
                        imageButton.addAction(
                            Actions.sizeTo(ITEM_SIZE, ITEM_SIZE, ITEM_ANIMATION_DURATION * 2)
                        );
                    }
                    super.exit(event, x, y, pointer, toActor);
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (player.getMoney() >= command.getPrice()) {
                        player.setMoney(player.getMoney() - command.getPrice());
                        player.getCommands().add(command);
                        imageButton.setVisible(false);
                        logger.info("clicked |  🐳  money last | " + player.getMoney());
                    } else {
                        logger.info("돈이 부족합니다.");
                    }
                }
            });
            forSaleList.addActor(imageButton);
        }
    }

    private void fillManualStock(List<Manual> allManual) {
        this.manualStock.clear();
        while (manualStock.size() < MANUAL_STOCK_AMOUNT) {
            int random = (int) (Math.random() * allManual.size());
            if (!manualStock.contains(allManual.get(random))) {
                manualStock.add(allManual.get(random));
            }
            if (allManual.size() < MANUAL_STOCK_AMOUNT) break;
        }
        int i = 0;
        for (Manual manual : manualStock) {
            ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
            style.font = GlobalSettings.font;
            style.up = manual.render();
            ImageTextButton imageButton = new ImageTextButton("", style);
            imageButton.setSize(ITEM_SIZE, ITEM_SIZE);
            imageButton.setPosition(i++ * (forSaleList.getWidth() / manualStock.size()), forSaleList.getHeight() / 2);
            imageButton.setOrigin(Align.center);

            imageButton.addListener(new ClickListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    if (pointer == -1) {
                        imageButton.addAction(
                            Actions.sizeTo(ITEM_SIZE_HOVER, ITEM_SIZE_HOVER, ITEM_ANIMATION_DURATION)
                        );
                    }
                    super.enter(event, x, y, pointer, fromActor);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    if (pointer == -1) {
                        imageButton.addAction(
                            Actions.sizeTo(ITEM_SIZE, ITEM_SIZE, ITEM_ANIMATION_DURATION * 2)
                        );
                    }
                    super.exit(event, x, y, pointer, toActor);
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (player.getMoney() >= manual.getPrice()) {
                        player.setMoney(player.getMoney() - manual.getPrice());
                        player.getManuals().add(manual);
                        imageButton.setVisible(false);
                        logger.info("clicked |  🐳  money last | " + player.getMoney());

                    } else {
                        logger.info("돈이 부족합니다.");
                    }
                }
            });
            forSaleList.addActor(imageButton);
        }
    }

    @Override
    public void render() {
        stage.draw();
    }

    @Override
    public void update() {
        stage.act();
    }

}
