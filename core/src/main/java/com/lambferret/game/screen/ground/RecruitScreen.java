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

    private final List<Table> testAddTableList = new ArrayList<>();
    private final List<Table> testDeleteTableList = new ArrayList<>();
    CustomButton label;
    CustomButton guideLabel;
    private Player player;
    private boolean isDelete = false;
    List<String> soldiers;
    List<String> commands;
    List<String> manuals;

    public RecruitScreen() {
        setBackground();
        setInformationLabel();
        switchTable();

        soldiers = new ArrayList<>(LocalizeConfig.soldierText.getID().keySet());
        commands = new ArrayList<>(LocalizeConfig.commandText.getID().keySet());
        manuals = new ArrayList<>(LocalizeConfig.manualText.getID().keySet());

        setTestAddSoldierTableList();
        setTestAddCommandTableList();
        setTestAddManualTableList();

        int i = 1;
        for (Table table : testAddTableList) {
            table.setPosition(200 * i, 500);
            table.setDebug(true, true);
            i++;
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
            table.setPosition(200 * i, 500);
            table.setDebug(true, true);
            i++;
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
        if (isDelete) {
            label.setText("this deletes the item");
            this.onPlayerUpdate(Item.Type.EVENT);
            for (Table table : testDeleteTableList) {
                table.setVisible(true);
            }
            for (Table table : testAddTableList) {
                table.setVisible(false);
            }
            isDelete = false;
        } else {
            label.setText("this adds you a item");
            for (Table table : testAddTableList) {
                table.setVisible(true);
            }
            for (Table table : testDeleteTableList) {
                table.setVisible(false);
            }
            isDelete = true;
        }
    }

    private void setTestAddSoldierTableList() {
        Table soldierCreateTable = new Table();
        int col = soldiers.size() / 15 + 1;
        int i = 0;
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
            soldierCreateTable.add(button).left();
            i++;
            if (i == col) {
                soldierCreateTable.row();
                i = 0;
            }
        }
        testAddTableList.add(soldierCreateTable);
        stage.addActor(soldierCreateTable);
    }

    private void setTestAddCommandTableList() {
        Table commandCreateTable = new Table();
        int col = commands.size() / 15 + 1;
        int i = 0;
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
            commandCreateTable.add(button).left();
            i++;
            if (i == col) {
                commandCreateTable.row();
                i = 0;
            }
        }
        testAddTableList.add(commandCreateTable);
        stage.addActor(commandCreateTable);
    }

    private void setTestAddManualTableList() {
        Table manualCreateTable = new Table();
        int col = manuals.size() / 15 + 1;
        int i = 0;
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
            manualCreateTable.add(button).left();
            i++;
            if (i == col) {
                manualCreateTable.row();
                i = 0;
            }
        }
        testAddTableList.add(manualCreateTable);
        stage.addActor(manualCreateTable);
    }

    private void setTestDeleteSoldierTableList() {
        Table soldierDeleteTable = new Table();
        int col = player.getSoldiers().size() / 15 + 1;
        int i = 0;
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
            soldierDeleteTable.add(button).left();
            i++;
            if (i == col) {
                soldierDeleteTable.row();
                i = 0;
            }
        }
        testDeleteTableList.add(soldierDeleteTable);
        stage.addActor(soldierDeleteTable);
    }

    private void setTestDeleteCommandTableList() {
        Table CommandDeleteTable = new Table();
        int col = player.getCommands().size() / 15 + 1;
        int i = 0;
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
            CommandDeleteTable.add(button).left();
            i++;
            if (i == col) {
                CommandDeleteTable.row();
                i = 0;
            }
        }
        testDeleteTableList.add(CommandDeleteTable);
        stage.addActor(CommandDeleteTable);
    }

    private void setTestDeleteManualTableList() {
        Table ManualDeleteTable = new Table();
        int col = player.getManuals().size() / 15 + 1;
        int i = 0;
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
            ManualDeleteTable.add(button).left();
            i++;
            if (i == col) {
                ManualDeleteTable.row();
                i = 0;
            }
        }
        testAddTableList.add(ManualDeleteTable);
        stage.addActor(ManualDeleteTable);
    }

    @Override
    public void render() {
        stage.draw();
        stage.act();
    }

}
