package com.lambferret.game.screen.ground;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
    private final Table soldierCreateTable;
    private final Table CommandCreateTable;
    private final Table ManualCreateTable;
    private final Table soldierDeleteTable;
    private final Table CommandDeleteTable;
    private final Table ManualDeleteTable;
    private final List<Table> testAddTableList;
    private final List<Table> testDeleteTableList;
    CustomButton label;
    CustomButton guideLabel;
    private Player player;
    private boolean isDelete = false;
    List<String> soldiers;
    List<String> commands;
    List<String> manuals;

    public RecruitScreen() {
        Image background = new Image(AssetFinder.getTexture("recruitReal"));
        background.setSize(GlobalSettings.currWidth, GlobalSettings.currHeight);
        soldierCreateTable = new Table(GlobalSettings.skin);
        CommandCreateTable = new Table(GlobalSettings.skin);
        ManualCreateTable = new Table(GlobalSettings.skin);
        soldierDeleteTable = new Table(GlobalSettings.skin);
        CommandDeleteTable = new Table(GlobalSettings.skin);
        ManualDeleteTable = new Table(GlobalSettings.skin);

        label = GlobalUtil.simpleButton("white", "recruit");
        label.setSize(200, 100);
        label.setPosition(GlobalSettings.currWidth / 2F, label.getHeight());
        guideLabel = GlobalUtil.simpleButton("white");
        guideLabel.setSize(200, 100);
        guideLabel.setPosition(GlobalSettings.currWidth / 2F, label.getHeight() + guideLabel.getHeight());

        testAddTableList = List.of(soldierCreateTable, CommandCreateTable, ManualCreateTable);
        testDeleteTableList = List.of(soldierDeleteTable, CommandDeleteTable, ManualDeleteTable);

        stage.addActor(background);
        stage.addActor(soldierCreateTable);
        stage.addActor(CommandCreateTable);
        stage.addActor(ManualCreateTable);
        stage.addActor(soldierDeleteTable);
        stage.addActor(CommandDeleteTable);
        stage.addActor(ManualDeleteTable);
        stage.addActor(label);
        stage.addActor(guideLabel);

        soldiers = new ArrayList<>(LocalizeConfig.soldierText.getID().keySet());
        commands = new ArrayList<>(LocalizeConfig.commandText.getID().keySet());
        manuals = new ArrayList<>(LocalizeConfig.manualText.getID().keySet());
        int index = 0;
        for (Table table : testAddTableList) {
            table.setSize(100, 100);
            table.setPosition(100, 100 + index * 100);
            table.setFillParent(true);
            index++;
        }
        index = 0;
        for (Table table : testDeleteTableList) {
            table.setSize(100, 100);
            table.setPosition(100, 100 + index * 100);
            table.setFillParent(true);
            index++;
        }

        setTestAddSoldierTableList();
        setTestAddCommandTableList();
        setTestAddManualTableList();
    }

    @Override
    public void onPlayerReady() {
        this.player = SnowFight.player;

        switchTable();

        TextButton textButton = new TextButton("click to switch between add and remove", GlobalSettings.skin);
        textButton.setSize(100, 100);
        textButton.setPosition(GlobalSettings.currWidth - 100, 0);
        stage.addActor(textButton);
        textButton.addListener(Input.click(this::switchTable));
//        stage.addActor(new Tutorial());
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {
        setTestDeleteSoldierTableList();
        setTestDeleteCommandTableList();
        setTestDeleteManualTableList();
    }

    @Override
    public void init() {

    }

    private void switchTable() {
        if (isDelete) {
            label.setText("this deletes the item");
            setTestDeleteSoldierTableList();
            setTestDeleteCommandTableList();
            setTestDeleteManualTableList();
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
        for (String s : soldiers) {
            Soldier soldier = GlobalSettings.getSoldier(s);
            ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
            style.font = GlobalSettings.font;
            style.up = new TextureRegionDrawable(AssetFinder.getTexture(soldier.getTexturePath()));
            ImageTextButton button = new ImageTextButton(soldier.getName(), style);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    player.addSoldier(soldier);
                    guideLabel.setText("soldier added | " + soldier.getName());
                }
            });
            button.setSize(20, 20);
            soldierCreateTable.add(button).width(50).height(50);
        }
    }

    private void setTestAddCommandTableList() {
        for (String s : commands) {
            Command command = GlobalSettings.getCommand(s);
            ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
            style.font = GlobalSettings.font;
            style.up = command.renderIcon();
            ImageTextButton button = new ImageTextButton(command.getName(), style);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    player.addCommand(command);
                    guideLabel.setText("command added | " + command.getName());
                }
            });
            button.setSize(20, 20);
            CommandCreateTable.add(button).width(50).height(50);
        }
    }

    private void setTestAddManualTableList() {
        for (String s : manuals) {
            Manual manual = GlobalSettings.getManual(s);
            ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
            style.font = GlobalSettings.font;
            style.up = manual.renderIcon();
            ImageTextButton button = new ImageTextButton(manual.getName(), style);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    player.addManual(manual);
                    guideLabel.setText("manual added | " + manual.getName());
                }
            });
            button.setSize(20, 20);
            ManualCreateTable.add(button).width(50).height(50);
        }
    }

    private void setTestDeleteSoldierTableList() {
        soldierDeleteTable.clear();
        for (Soldier soldier : player.getSoldiers()) {
            ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
            style.font = GlobalSettings.font;
            style.up = new TextureRegionDrawable(AssetFinder.getTexture(soldier.getTexturePath()));
            ImageTextButton button = new ImageTextButton(soldier.getName(), style);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    player.deleteSoldier(soldier);
                    button.remove();
                    guideLabel.setText("soldier deleted | " + soldier.getName());
                }
            });
            button.setSize(20, 20);
            soldierDeleteTable.add(button).width(50).height(50);
        }
    }

    private void setTestDeleteCommandTableList() {
        CommandDeleteTable.clear();
        for (Command command : player.getCommands()) {
            ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
            style.font = GlobalSettings.font;
            style.up = command.renderIcon();
            ImageTextButton button = new ImageTextButton(command.getName(), style);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    player.deleteCommand(command);
                    button.remove();
                    guideLabel.setText("command deleted | " + command.getName());
                }
            });
            button.setSize(20, 20);
            CommandDeleteTable.add(button).width(50).height(50);

        }
    }

    private void setTestDeleteManualTableList() {
        ManualDeleteTable.clear();
        for (Manual manual : player.getManuals()) {
            ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
            style.font = GlobalSettings.font;
            style.up = manual.renderIcon();
            ImageTextButton button = new ImageTextButton(manual.getName(), style);
            button.addListener(Input.click(() -> {
                    player.deleteManual(manual);
                    button.remove();
                    guideLabel.setText("manual deleted | " + manual.getName());
                }
            ));
            button.setSize(20, 20);
            ManualDeleteTable.add(button).width(50).height(50);
        }
    }

    @Override
    public void render() {
        stage.draw();
        stage.act();
    }

}
