package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.lambferret.game.SnowFight;
import com.lambferret.game.command.Command;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SoldierWindow extends Window {
    private static final Logger logger = LogManager.getLogger(SoldierWindow.class.getName());

    protected Skin skin;
    protected Stage stage;
    List<Soldier> pickedSoldiers = new ArrayList<>();
    Table searchedSoldierTable = new Table();
    ScrollPane scrollPane;
    private final SoldierSelectionListener listener;
    CustomButton confirmButton;
    CustomButton cancelButton;
    Command command;

    Player player;

    public SoldierWindow(Stage stage, SoldierSelectionListener listener) {
        super("", GlobalSettings.skin);
        this.skin = GlobalSettings.skin;
        this.stage = stage;
        this.listener = listener;

        this.setMovable(false);
        this.setResizable(false);
        this.setKeepWithinStage(true);
        this.setModal(false);
        this.pack();
        this.setSize(GlobalSettings.currWidth - 400, GlobalSettings.currHeight - 200);
        this.setPosition(stage.getWidth() / 2 - this.getWidth() / 2, stage.getHeight() / 2 - this.getHeight() / 2);

        this.scrollPane = new ScrollPane(new Table());
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setPosition(this.getX(), this.getY());
        scrollPane.setSize(this.getWidth(), this.getHeight());
        this.addListener(Input.setScrollFocusWhenHover(stage, this.scrollPane));
        this.add(this.scrollPane);

        addCheckButton();

        this.setVisible(false);
        stage.addActor(this);
    }

    private void setAll(Command command) {

    }

    private void button(Soldier s, int targetCount) {
        Group button = s.card();
        button.addListener(Input.click(() -> {
            if (pickedSoldiers.contains(s)) {
                pickedSoldiers.remove(s);
                button.setColor(1, 1, 1, 1);
            } else {
                if (pickedSoldiers.size() >= targetCount) return;
                pickedSoldiers.add(s);
                button.setColor(255, 1, 1, 0.5F);
            }
        }));
        searchedSoldierTable.add(button);
    }


    public void show(Command command) {
        searchedSoldierTable.clear();
        pickedSoldiers.clear();
        scrollPane.clear();

        this.command = command;
        this.player = SnowFight.player;

        int count = 0;
        for (Soldier s : player.getSoldiers()) {
            button(s, command.getTargetCount());
            count++;
            if (count == 6) {
                searchedSoldierTable.row();
                count = 0;
            }
        }
        scrollPane.setActor(searchedSoldierTable);
        this.setVisible(true);
    }

    private void confirm() {
        this.setVisible(false);
        listener.onSoldierSelected(pickedSoldiers, command);
    }

    private void cancel() {
        this.setVisible(false);
    }

    @Override
    public void setVisible(boolean visible) {
        confirmButton.setVisible(visible);
        cancelButton.setVisible(visible);
        super.setVisible(visible);
    }

    private void addCheckButton() {
        confirmButton = GlobalUtil.simpleButton("", "confirm");
        cancelButton = GlobalUtil.simpleButton("", "cancel");
        stage.addActor(confirmButton);
        stage.addActor(cancelButton);
        confirmButton.addListener(Input.click(this::confirm));
        cancelButton.addListener(Input.click(this::cancel));
        confirmButton.setPosition(this.getWidth() - confirmButton.getWidth() - 10, 10);
        cancelButton.setPosition(confirmButton.getX() - cancelButton.getWidth() - 10, 10);
    }

}
