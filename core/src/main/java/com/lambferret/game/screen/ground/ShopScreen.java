package com.lambferret.game.screen.ground;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
    public static final int STAND_WIDTH = 500;
    public static final int STAND_HEIGHT = 200;
    public static final int STAND_X = 100;
    public static final int STAND_Y = 100;
    public static final int MONEY_WIDTH = 200;
    public static final int MONEY_HEIGHT = 100;
    public static final int MONEY_X = (GlobalSettings.currWidth - MONEY_WIDTH) / 2;
    public static final int MONEY_Y = GlobalSettings.currHeight - MONEY_HEIGHT - 50;

    Table commandForSale = new Table();
    Table manualForSale = new Table();
    List<Manual> manualStock = new ArrayList<>();
    List<Command> commandStock = new ArrayList<>();
    Player player;
    CustomButton playerMoney;

    public ShopScreen() {
        stage.addActor(commandForSale);
        stage.addActor(manualForSale);
    }

    @Override
    public void onPlayerReady() {
        this.player = SnowFight.player;

        commandForSale.setSize(STAND_WIDTH, STAND_HEIGHT);
        manualForSale.setSize(STAND_WIDTH, STAND_HEIGHT);
        commandForSale.setPosition(STAND_X, STAND_Y);
        manualForSale.setPosition(STAND_X, STAND_Y + commandForSale.getHeight());

        this.commandForSale.clear();
        this.manualForSale.clear();
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
        List<Label> labelList = new ArrayList<>();
        for (Command command : commandStock) {
            CustomButton imageButton;
            Label howMuch;
            if (command == null) {
                imageButton = GlobalUtil.simpleButton("shop empty stock");
                howMuch = new Label("empty", GlobalSettings.skin);
            } else {
                howMuch = new Label(command.getPrice() + "", GlobalSettings.skin);
                imageButton = GlobalUtil.simpleButton(command.renderIcon(), command.getID());
                imageButton.addListener(Input.click(() -> {
                    if (player.getMoney() >= command.getPrice()) {
                        updatePlayerMoney(command.getPrice());
                        player.addCommand(command);
                        imageButton.setVisible(false);
                        commandStock.set(commandStock.indexOf(command), null);
                        howMuch.setText("");
                        save();
                    } else {
                        imageButton.addAction(rejectAction());
                    }
                }));
            }
            commandForSale.add(imageButton).pad(5).size(ITEM_SIZE, ITEM_SIZE).expandX().uniformX();
            imageButton.addAction(oscillate(false));
            imageButton.addListener(addHover(imageButton));
            labelList.add(howMuch);
        }
        commandForSale.row();
        for (Label howMuch : labelList) {
            commandForSale.add(howMuch).height(howMuch.getHeight()).expandX().uniformX();
        }
    }

    private void makeManualButtons() {
        List<Label> labelList = new ArrayList<>();
        for (Manual manual : manualStock) {
            CustomButton imageButton;
            Label howMuch;
            if (manual == null) {
                imageButton = GlobalUtil.simpleButton("empty manual stock");
                howMuch = new Label("empty", GlobalSettings.skin);
            } else {
                howMuch = new Label(manual.getPrice() + "", GlobalSettings.skin);
                imageButton = GlobalUtil.simpleButton(manual.renderIcon(), manual.getID());
                imageButton.addListener(Input.click(() -> {
                        if (player.getMoney() >= manual.getPrice()) {
                            updatePlayerMoney(manual.getPrice());
                            player.addManual(manual);
                            imageButton.setVisible(false);
                            manualStock.set(manualStock.indexOf(manual), null);
                            howMuch.setText("");
                            save();
                        } else {
                            imageButton.addAction(rejectAction());
                        }
                    }
                ));
            }
            manualForSale.add(imageButton).pad(5).size(ITEM_SIZE, ITEM_SIZE).expandX().uniformX();
            imageButton.addAction(oscillate(false));
            imageButton.addListener(addHover(imageButton));
            labelList.add(howMuch);
        }
        manualForSale.row();
        for (Label howMuch : labelList) {
            manualForSale.add(howMuch).height(howMuch.getHeight()).expandX().uniformX();
        }
    }

    private void save() {
        List<Item> shopItems = new ArrayList<>();
        for (Command command : commandStock) {
            if (command != null) {
                shopItems.add(Item.builder().type(Item.Type.COMMAND).ID(command.getID()).build());
            } else {
                shopItems.add(Item.builder().type(Item.Type.COMMAND).ID(null).build());
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
        stage.addActor(playerMoney);
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
