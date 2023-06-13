package com.lambferret.game.screen.ground;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.lambferret.game.SnowFight;
import com.lambferret.game.command.Command;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.manual.Manual;
import com.lambferret.game.player.Player;
import com.lambferret.game.quest.TutorialQuest;
import com.lambferret.game.save.Item;
import com.lambferret.game.setting.GlobalSettings;
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

        this.forSaleList.clear();
        setPlayerMoney();

        if (player.getShopItems().size() == 0) {
            fillCommandStock();
            fillManualStock();
        } else {
            load();
        }
        makeCommandButtons();
        makeManualButtons();
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {

    }

    @Override
    public void init() {
        logger.info(" SYSTEM : Shop Screen ");
        player.addQuest(new TutorialQuest());
    }

    private void fillCommandStock() {
        this.commandStock.clear();
        while (commandStock.size() < COMMAND_STOCK_AMOUNT) {
            commandStock.add(GlobalSettings.getCommand());
            if (GlobalSettings.getCommandSize() < COMMAND_STOCK_AMOUNT) break;
        }
        logger.info(" SHOP : fill command stock - " + GlobalUtil.listToString(commandStock));
    }

    private void fillManualStock() {
        this.manualStock.clear();
        while (manualStock.size() < MANUAL_STOCK_AMOUNT) {
            manualStock.add(GlobalSettings.popManual());
            if (GlobalSettings.getManualSize() < MANUAL_STOCK_AMOUNT) break;
        }
        logger.info(" SHOP : fill manual stock - " + GlobalUtil.listToString(manualStock));
    }

    public void makeCommandButtons() {
        int i = 0;
        for (Command command : commandStock) {
            CustomButton imageButton;
            if (command == null) {
                imageButton = GlobalUtil.simpleButton("shop empty stock");
            } else {
                int finalI = i;
                imageButton = GlobalUtil.simpleButton(command.renderIcon(), command.getID());
                imageButton.addListener(Input.click(() -> {
                    if (player.getMoney() >= command.getPrice()) {
                        updatePlayerMoney(command.getPrice());
                        player.addCommand(command);
                        imageButton.setVisible(false);
                        commandStock.set(finalI, null);
                        save();
                    } else {
                        imageButton.addAction(rejectAction());
                    }
                }));
            }
            imageButton.setSize(ITEM_SIZE, ITEM_SIZE);
            imageButton.setPosition(i++ * (forSaleList.getWidth() / COMMAND_STOCK_AMOUNT), 0);
            imageButton.addAction(oscillate(false));
            imageButton.addListener(addHover(imageButton));
            forSaleList.addActor(imageButton);
        }
    }

    private void makeManualButtons() {
        int i = 0;
        for (Manual manual : manualStock) {
            CustomButton imageButton;
            if (manual == null) {
                imageButton = GlobalUtil.simpleButton("empty manual stock");
            } else {
                imageButton = GlobalUtil.simpleButton(manual.renderIcon(), manual.getID());
                int finalI = i;
                imageButton.addListener(Input.click(() -> {
                        if (player.getMoney() >= manual.getPrice()) {
                            updatePlayerMoney(manual.getPrice());
                            player.addManual(manual);
                            imageButton.setVisible(false);
                            manualStock.set(finalI, null);
                            save();
                        } else {
                            imageButton.addAction(rejectAction());
                        }
                    }
                ));
            }
            imageButton.setSize(ITEM_SIZE, ITEM_SIZE);
            imageButton.setPosition(i++ * (forSaleList.getWidth() / MANUAL_STOCK_AMOUNT), forSaleList.getHeight() / 2);
            imageButton.addAction(oscillate(false));
            imageButton.addListener(addHover(imageButton));
            forSaleList.addActor(imageButton);
        }
    }

    private void save() {
        List<Item> shopItems = new ArrayList<>();
        for (Command command : commandStock) {
            if (command != null) {
                shopItems.add(Item.builder().type(Item.Type.COMMAND).ID(command.getID()).build());
            } else {
                shopItems.add(Item.builder().type(Item.Type.MANUAL).ID(null).build());
            }
        }
        for (Manual manual : manualStock) {
            if (manual != null) {
                shopItems.add(Item.builder().type(Item.Type.MANUAL).ID(manual.getID()).build());
            } else {
                shopItems.add(Item.builder().type(Item.Type.MANUAL).ID(null).build());
            }
        }
        player.setShopItems(shopItems);
    }

    private void load() {
        List<Item> shopItems = player.getShopItems();
        for (Item item : shopItems) {
            if (item.getType() == Item.Type.COMMAND) {
                if (item.getID() == null) {
                    commandStock.add(null);
                } else {
                    commandStock.add(GlobalSettings.getCommand(item.getID()));
                }
            } else {
                if (item.getID() == null) {
                    manualStock.add(null);
                } else {
                    manualStock.add(GlobalSettings.getManual(item.getID()));
                }
            }
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
