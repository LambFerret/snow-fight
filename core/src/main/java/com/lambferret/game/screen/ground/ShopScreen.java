package com.lambferret.game.screen.ground;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lambferret.game.manual.Manual;
import com.lambferret.game.manual.DisciplineAndPunish;
import com.lambferret.game.command.Bunkering;
import com.lambferret.game.command.EvilWithin;
import com.lambferret.game.command.FieldInstructor;
import com.lambferret.game.command.Command;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ShopScreen implements AbstractGround {
    private static final Logger logger = LogManager.getLogger(ShopScreen.class.getName());
    public static final int MANUAL_STOCK_AMOUNT = 2;
    public static final int COMMAND_STOCK_AMOUNT = 2;

    Stage stage;
    Table table = new Table();
    List<Manual> manualStock = new ArrayList<>();
    List<Command> commandStock = new ArrayList<>();
    Player player;

    public ShopScreen() {
        this.stage = new Stage();
        stage.addActor(table);
    }

    public void create() {
        table.setDebug(true, true);

        table.setSkin(GlobalSettings.skin);
        table.setSize(300, 300);
        table.setPosition(100, 100);

    }

    @Override
    public void init(Player player) {
        this.player = player;
        List<Command> allCommand = List.of(new FieldInstructor(), new EvilWithin(), new Bunkering());
        List<Manual> allManual = List.of(new DisciplineAndPunish());
        this.table.clear();
        fillCommandStock(allCommand);
        table.row();
        fillManualStock(allManual);
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

        for (Command command : commandStock) {
            ImageTextButton imageButton = new ImageTextButton("", GlobalSettings.imageButtonStyle);
            imageButton.setText(command.getID());
            imageButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (imageButton.isDisabled()) return;
                    if (player.getMoney() >= command.getPrice()) {
                        player.setMoney(player.getMoney() - command.getPrice());
                        player.getCommands().add(command);
                        imageButton.setDisabled(true);
//                        updateMoney();
                        logger.info("clicked |  🐳  money last | " + player.getMoney());
                    } else {
                        logger.info("돈이 부족합니다.");
                    }
                }
            });
            table.add(imageButton);
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
        for (Manual manual : manualStock) {
            ImageTextButton imageButton = new ImageTextButton("", GlobalSettings.imageButtonStyle);
            imageButton.setText(manual.getID());
            imageButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (imageButton.isDisabled()) return;
                    if (player.getMoney() >= manual.getPrice()) {
                        player.setMoney(player.getMoney() - manual.getPrice());
                        player.getManuals().add(manual);
                        imageButton.setDisabled(true);
                        logger.info("clicked |  🐳  money last | " + player.getMoney());

                    } else {
                        logger.info("돈이 부족합니다.");
                    }
                }
            });
            table.add(imageButton);
        }
    }

    public Stage getStage() {
        return this.stage;
    }

    public void render() {
        stage.draw();
    }

    public void update() {
        stage.act();
    }

}
