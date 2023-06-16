package com.lambferret.game.screen.ground;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.SnowFight;
import com.lambferret.game.command.Command;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.component.PagingTable;
import com.lambferret.game.manual.Manual;
import com.lambferret.game.player.Player;
import com.lambferret.game.save.Item;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class RecruitScreen implements AbstractGround {
    private static final Logger logger = LogManager.getLogger(RecruitScreen.class.getName());

    public static final Stage stage = new Stage();

    private final List<PagingTable> testAddTableList = new ArrayList<>();
    private final List<PagingTable> testDeleteTableList = new ArrayList<>();
    private final List<CustomButton> deleteAllButtonList = new ArrayList<>();
    CustomButton label;
    CustomButton guideLabel;
    private Player player;
    private boolean isAdd = false;
    List<String> soldiers;
    List<String> commands;
    List<String> manuals;

    public RecruitScreen() {
        setBackground();
        setInformationLabel();
        switchTable();
        deleteAllList();

        soldiers = new ArrayList<>(LocalizeConfig.soldierText.getID().keySet());
        commands = new ArrayList<>(LocalizeConfig.commandText.getID().keySet());
        manuals = new ArrayList<>(LocalizeConfig.manualText.getID().keySet());

        setTestAddSoldierTableList();
        setTestAddCommandTableList();
        setTestAddManualTableList();

        int i = 1;
        for (Table table : testAddTableList) {
            table.setPosition(GlobalSettings.currWidth / 4F * i++, 500);
            table.setDebug(true, true);
        }
    }

    @Override
    public void onPlayerReady() {
        this.player = SnowFight.player;
//        stage.addActor(new Tutorial());
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {
        if (testDeleteTableList.size() != 0) {
            for (Table table : testDeleteTableList) {
                table.remove();
            }
        }
        testDeleteTableList.clear();
        setTestDeleteSoldierTableList();
        setTestDeleteCommandTableList();
        setTestDeleteManualTableList();
        int i = 1;
        for (Table table : testDeleteTableList) {
            table.setPosition(GlobalSettings.currWidth / 4F * i++, 500);
            table.setDebug(true, true);
        }
    }

    @Override
    public void init() {
        logger.info(" SYSTEM : Recruit Screen ");
    }

    private void setBackground() {
        Image background = new Image(AssetFinder.getTexture("recruitReal"));
        background.setSize(GlobalSettings.currWidth, GlobalSettings.currHeight);
        stage.addActor(background);
    }

    private void deleteAllList() {
        deleteAllButtonList.add(deleteAllButton("soldier"));
        deleteAllButtonList.add(deleteAllButton("command"));
        deleteAllButtonList.add(deleteAllButton("manual"));
        for (CustomButton button : deleteAllButtonList) {
            stage.addActor(button);
        }
    }

    private CustomButton deleteAllButton(String type) {
        CustomButton button = GlobalUtil.simpleButton("white", "delete " + type + " all");
        button.setSize(100, 100);
        button.setY(200);
        switch (type) {
            case "soldier" -> button.setX(GlobalSettings.currWidth / 4F);
            case "command" -> button.setX(GlobalSettings.currWidth / 4F * 2);
            case "manual" -> button.setX(GlobalSettings.currWidth / 4F * 3);
        }
        button.addListener(Input.click(() -> {
            switch (type) {
                case "soldier" -> {
                    player.getSoldiers().clear();
                    if (!isAdd) onPlayerUpdate(Item.Type.SOLDIER);
                }
                case "command" -> {
                    player.getCommands().clear();
                    if (!isAdd) onPlayerUpdate(Item.Type.COMMAND);
                }
                case "manual" -> {
                    player.getManuals().clear();
                    if (!isAdd) onPlayerUpdate(Item.Type.MANUAL);
                }
            }
        }));
        return button;
    }

    private void setInformationLabel() {
        label = GlobalUtil.simpleButton("white", "recruit");
        label.setSize(400, label.getLabel().getHeight() * 2);
        label.setPosition((GlobalSettings.currWidth - label.getWidth()) / 2F, GlobalSettings.currHeight - label.getHeight());
        guideLabel = GlobalUtil.simpleButton("white", "click to add or delete");
        guideLabel.setSize(400, guideLabel.getLabel().getHeight());
        guideLabel.setPosition((GlobalSettings.currWidth - guideLabel.getWidth()) / 2F, 300);
        TextButton textButton = new TextButton("click to switch between add and remove", GlobalSettings.skin);
        textButton.setSize(100, 100);
        textButton.setPosition(GlobalSettings.currWidth - 100, 0);
        textButton.addListener(Input.click(this::switchTable));
        stage.addActor(label);
        stage.addActor(guideLabel);
        stage.addActor(textButton);
    }

    private void switchTable() {
        if (isAdd) {
            label.setText("this deletes the item");
            this.onPlayerUpdate(Item.Type.EVENT);
            for (Table table : testDeleteTableList) {
                table.setVisible(true);
            }
            for (Table table : testAddTableList) {
                table.setVisible(false);
            }
            isAdd = false;
        } else {
            label.setText("this adds you a item");
            for (Table table : testAddTableList) {
                table.setVisible(true);
            }
            for (Table table : testDeleteTableList) {
                table.setVisible(false);
            }
            isAdd = true;
        }
    }

    private void setTestAddSoldierTableList() {
        List<CustomButton> list = new ArrayList<>();
        for (String s : soldiers) {
            Soldier soldier = GlobalSettings.getSoldier(s);
            CustomButton button = GlobalUtil.simpleButton("white", soldier.getName());
            button.addListener(Input.click(() -> {
                player.addSoldier(soldier);
                guideLabel.setText("soldier added | " + soldier.getName());
                guideLabel.addAction(
                    Actions.repeat(
                        2, Actions.sequence(Actions.color(Color.GREEN, 0.1F), Actions.color(Color.WHITE, 0.1F))
                    )
                );
            }));
            list.add(button);
        }
        PagingTable soldierCreateTable = new PagingTable(list);
        testAddTableList.add(soldierCreateTable);
        stage.addActor(soldierCreateTable);
    }

    private void setTestAddCommandTableList() {
        List<CustomButton> list = new ArrayList<>();
        for (String s : commands) {
            Command command = GlobalSettings.getCommand(s);
            CustomButton button = GlobalUtil.simpleButton("white", command.getName());
            button.addListener(Input.click(() -> {
                player.addCommand(command);
                guideLabel.setText("command added | " + command.getName());
                guideLabel.addAction(
                    Actions.repeat(
                        2, Actions.sequence(Actions.color(Color.GREEN, 0.1F), Actions.color(Color.WHITE, 0.1F))
                    )
                );
            }));
            list.add(button);
        }
        PagingTable commandCreateTable = new PagingTable(list);

        testAddTableList.add(commandCreateTable);
        stage.addActor(commandCreateTable);
    }

    private void setTestAddManualTableList() {
        List<CustomButton> list = new ArrayList<>();
        for (String s : manuals) {
            Manual manual = GlobalSettings.getManual(s);
            CustomButton button = GlobalUtil.simpleButton("white", manual.getName());
            button.addListener(Input.click(() -> {
                player.addManual(manual);
                guideLabel.setText("manual added | " + manual.getName());
                guideLabel.addAction(
                    Actions.repeat(
                        2, Actions.sequence(Actions.color(Color.GREEN, 0.1F), Actions.color(Color.WHITE, 0.1F))
                    )
                );
            }));
            list.add(button);
        }
        PagingTable commandCreateTable = new PagingTable(list);

        testAddTableList.add(commandCreateTable);
        stage.addActor(commandCreateTable);

    }

    private void setTestDeleteSoldierTableList() {
        List<CustomButton> list = new ArrayList<>();
        for (Soldier soldier : player.getSoldiers()) {
            CustomButton button = GlobalUtil.simpleButton("white", soldier.getName());
            button.addListener(Input.click(() -> {
                player.deleteSoldier(soldier);
                button.remove();
                guideLabel.setText("soldier deleted | " + soldier.getName());
                guideLabel.addAction(
                    Actions.repeat(
                        2, Actions.sequence(Actions.color(Color.GREEN, 0.1F), Actions.color(Color.WHITE, 0.1F))
                    )
                );
            }));
            list.add(button);

        }
        PagingTable soldierDeleteTable = new PagingTable(list);
        testDeleteTableList.add(soldierDeleteTable);
        stage.addActor(soldierDeleteTable);
    }

    private void setTestDeleteCommandTableList() {
        List<CustomButton> list = new ArrayList<>();
        for (Command command : player.getCommands()) {
            CustomButton button = GlobalUtil.simpleButton("white", command.getName());
            button.addListener(Input.click(() -> {
                player.deleteCommand(command);
                button.remove();
                guideLabel.setText("command deleted | " + command.getName());
                guideLabel.addAction(
                    Actions.repeat(
                        2, Actions.sequence(Actions.color(Color.GREEN, 0.1F), Actions.color(Color.WHITE, 0.1F))
                    )
                );
            }));
            list.add(button);
        }
        PagingTable commandDeleteTable = new PagingTable(list);
        testDeleteTableList.add(commandDeleteTable);
        stage.addActor(commandDeleteTable);
    }

    private void setTestDeleteManualTableList() {
        List<CustomButton> list = new ArrayList<>();
        for (Manual manual : player.getManuals()) {
            CustomButton button = GlobalUtil.simpleButton("white", manual.getName());
            button.addListener(Input.click(() -> {
                player.deleteManual(manual);
                button.remove();
                guideLabel.setText("manual deleted | " + manual.getName());
                guideLabel.addAction(
                    Actions.repeat(
                        2, Actions.sequence(Actions.color(Color.GREEN, 0.1F), Actions.color(Color.WHITE, 0.1F))
                    )
                );
            }));
            list.add(button);
        }
        PagingTable manualDeleteTable = new PagingTable(list);
        testDeleteTableList.add(manualDeleteTable);
        stage.addActor(manualDeleteTable);
    }

    @Override
    public void render() {
        stage.draw();
        stage.act();
    }

}
