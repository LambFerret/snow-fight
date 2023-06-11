package com.lambferret.game.screen.ground;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.lambferret.game.SnowFight;
import com.lambferret.game.command.Command;
import com.lambferret.game.command.CupNoodle;
import com.lambferret.game.command.ThreeShift;
import com.lambferret.game.command.TricksOfTheTrade;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.manual.DisciplineAndPunish;
import com.lambferret.game.manual.Manual;
import com.lambferret.game.player.Player;
import com.lambferret.game.quest.TutorialQuest;
import com.lambferret.game.save.Item;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;
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
    public static final float ITEM_ANIMATION_DURATION = 0.05F;
    public static final int STAND_WIDTH = 800;
    public static final int STAND_HEIGHT = 500;
    public static final int STAND_X = 100;
    public static final int STAND_Y = 100;
    public static final int MONEY_WIDTH = 200;
    public static final int MONEY_HEIGHT = 100;
    public static final int MONEY_X = STAND_WIDTH - MONEY_WIDTH;
    public static final int MONEY_Y = STAND_HEIGHT - MONEY_HEIGHT;

    Group forSaleList = new Group();
    List<Manual> manualStock = new ArrayList<>();
    List<Command> commandStock = new ArrayList<>();
    Player player;
    CustomButton playerMoney;

    public ShopScreen() {
        stage.addActor(forSaleList);
    }

    @Override
    public void onPlayerReady() {
        this.player = SnowFight.player;

        forSaleList.setSize(STAND_WIDTH, STAND_HEIGHT);
        forSaleList.setPosition(STAND_X, STAND_Y);

        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-//
        List<Command> allCommand = List.of(new TricksOfTheTrade(), new CupNoodle(), new ThreeShift());
        List<Manual> allManual = List.of(new DisciplineAndPunish());
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-//

        this.forSaleList.clear();
        setPlayerMoney();
        fillCommandStock(allCommand);
        fillManualStock(allManual);
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {

    }

    @Override
    public void init() {
        logger.info(" SYSTEM : Shop Screen ");
        player.addQuest(new TutorialQuest());
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
        logger.info(" SHOP : fill command stock - " + commandStock);
        int i = 0;
        for (Command command : commandStock) {
            CustomButton imageButton = GlobalUtil.simpleButton(command.renderIcon(), command.getID());

            imageButton.setSize(ITEM_SIZE, ITEM_SIZE);
            imageButton.setPosition(i++ * (forSaleList.getWidth() / COMMAND_STOCK_AMOUNT), 0);

            imageButton.addAction(oscillate(false));
            imageButton.addListener(Input.click(() -> {
                    if (player.getMoney() >= command.getPrice()) {
                        updatePlayerMoney(command.getPrice());
                        player.getCommands().add(command);
                        imageButton.setVisible(false);
                    } else {
                        imageButton.addAction(rejectAction());
                    }
                }
            ));
            imageButton.addListener(addHover(imageButton));
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
        logger.info(" SHOP : fill manual stock - " + manualStock);
        int i = 0;
        for (Manual manual : manualStock) {
            CustomButton imageButton = GlobalUtil.simpleButton(manual.renderIcon(), manual.getID());

            imageButton.setSize(ITEM_SIZE, ITEM_SIZE);
            imageButton.setPosition(i++ * (forSaleList.getWidth() / MANUAL_STOCK_AMOUNT), forSaleList.getHeight() / 2);

            imageButton.addAction(oscillate(false));
            imageButton.addListener(Input.click(() -> {
                    if (player.getMoney() >= manual.getPrice()) {
                        updatePlayerMoney(manual.getPrice());
                        player.getManuals().add(manual);
                        imageButton.setVisible(false);
                    } else {
                        imageButton.addAction(rejectAction());
                    }
                }
            ));
            imageButton.addListener(addHover(imageButton));
            forSaleList.addActor(imageButton);
        }
    }

    private InputListener addHover(Actor button) {
        float scaleUp = 1.2F;
        float scaleDown = 1.0F;
        return Input.hover(
            () -> {
                button.clearActions();
                button.addAction(
                    Actions.scaleTo(scaleUp, scaleUp, ITEM_ANIMATION_DURATION)
                );
            },
            () -> button.addAction(
                Actions.sequence(
                    Actions.scaleTo(scaleDown, scaleDown, ITEM_ANIMATION_DURATION * 2),
                    oscillate(true)
                )
            )
        );
    }

    private Action oscillate(boolean instant) {
        float targetScaleUp = 1.1f;
        float targetScaleDown = 1f;
        float oscillationDuration = 1f;
        var actionSequence = Actions.sequence();
        if (!instant) {
            float randomDelay = MathUtils.random.nextFloat() * 1.5F;
            actionSequence.addAction(Actions.delay(randomDelay));
        }
        actionSequence.addAction(
            Actions.repeat(RepeatAction.FOREVER,
                Actions.sequence(
                    Actions.scaleTo(targetScaleUp, targetScaleUp, oscillationDuration),
                    Actions.scaleTo(targetScaleDown, targetScaleDown, oscillationDuration)
                )
            )
        );
        return actionSequence;
    }

    public static Action rejectAction() {
        return Actions.parallel(
            Actions.repeat(4,
                Actions.sequence(
                    Actions.moveBy(5, 0, 0.05f),
                    Actions.moveBy(-5, 0, 0.05f)
                )
            ),
            Actions.repeat(2,
                Actions.sequence(
                    Actions.color(new Color(255, 0, 0, 0.6F), 0.1f),
                    Actions.color(Color.WHITE, 0.1f)
                )
            )
        );
    }

    private void setPlayerMoney() {
        playerMoney = GlobalUtil.simpleButton("button_up", "null");
        playerMoney.setPosition(MONEY_X, MONEY_Y);
        playerMoney.setSize(MONEY_WIDTH, MONEY_HEIGHT);
        playerMoney.setText("money : " + player.getMoney());
        forSaleList.addActor(playerMoney);
    }

    private void updatePlayerMoney(int cost) {
        int moneyLeft = player.getMoney() - cost;
        player.setMoney(moneyLeft);
        playerMoney.setText("money : " + player.getMoney());
    }

    @Override
    public void render() {
        stage.draw();
        stage.act();
    }

}
