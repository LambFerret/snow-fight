package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.SnowFight;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.quest.Quest;
import com.lambferret.game.save.Item;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class QuestOverlay extends Window implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(QuestOverlay.class.getName());
    private final Stage stage;
    private List<Quest> quests;
    private boolean isHide = false;
    private final float labelSize = this.getTitleLabel().getPrefHeight();
    private final CustomButton minimizeButton;
    ScrollPane scrollPane;

    public QuestOverlay(Stage stage) {
        super("Quest", GlobalSettings.skin);
        this.stage = stage;
        this.setResizable(false);
        setKeepWithinStage(true);

        minimizeButton = GlobalUtil.simpleButton("minimizeIcon");
        minimizeButton.getStyle().over = new TextureRegionDrawable(AssetFinder.getTexture("minimizeIconHighlight"));

        minimizeButton.addListener(Input.click(this::hide));
        this.getTitleTable().add(minimizeButton).size(labelSize - 4, labelSize - 2);
        stage.addActor(this);
    }

    @Override
    public void onPlayerReady() {
        this.setPosition(QUEST_INIT_X, QUEST_INIT_Y);
        this.setSize(QUEST_INIT_WIDTH, QUEST_INIT_HEIGHT);
        quests = SnowFight.player.getQuests();
    }

    @Override
    public void onPlayerUpdate(Item.Type type) {
        if (type == Item.Type.QUEST) {
            quests = SnowFight.player.getQuests();
            makeTable(quests);
        }
    }

    private void makeTable(List<Quest> quests) {
        this.removeActor(scrollPane);
        VerticalGroup table = new VerticalGroup();
        for (Quest quest : quests) {
            table.addActor(quest.getQuestItem());
        }
        table.fill();
        scrollPane = new ScrollPane(table);
        scrollPane.addListener(Input.setScrollFocusWhenHover(stage, scrollPane));
        this.add(scrollPane);
    }

    private void hide() {
        float oldHeight = this.getHeight();
        if (!isHide) {
            isHide = true;
            this.setHeight(labelSize * 2);
        } else {
            isHide = false;
            this.setHeight(QUEST_INIT_HEIGHT);
        }
        float newY = this.getY() + oldHeight - this.getHeight();
        this.setY(newY);
    }

    @Override
    public void setVisible(boolean visible) {
        minimizeButton.setVisible(visible);
        super.setVisible(visible);
    }

}
