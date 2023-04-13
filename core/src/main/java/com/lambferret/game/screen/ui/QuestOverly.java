package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lambferret.game.player.Player;
import com.lambferret.game.quest.Quest;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class QuestOverly extends Window implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(QuestOverly.class.getName());
    private final Stage stage;
    private ScrollPane scrollPane;
    private VerticalGroup verticalGroup;
    private List<Quest> quests;

    public QuestOverly(Stage stage) {
        super("Quest", GlobalSettings.skin);
        this.stage = stage;
        verticalGroup = new VerticalGroup();
        scrollPane = new ScrollPane(verticalGroup);
        this.add(scrollPane);
        verticalGroup.fill();
        stage.addActor(this);
        this.setMovable(true);
        this.setResizable(true);
        this.setKeepWithinStage(true);
        this.setDebug(true, true);

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                logger.info("clicked |  🐳 wefoiwjef | ");
            }
        });

        scrollPane.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                stage.setScrollFocus(scrollPane);
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    stage.setScrollFocus(null);
                }
                super.exit(event, x, y, pointer, toActor);
            }
        });
    }

    public void create() {
        this.setPosition(500, 500);
        this.setSize(100, 100);
    }

    @Override
    public void init(Player player) {
        quests = player.getQuests();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = GlobalSettings.font;
        for (Quest quest : quests) {
            verticalGroup.addActor(new TextButton(quest.getDescription(), style));
            verticalGroup.addActor(new TextButton(quest.getDescription(), style));
            verticalGroup.addActor(new TextButton(quest.getDescription(), style));
            verticalGroup.addActor(new TextButton(quest.getDescription(), style));
            verticalGroup.addActor(new TextButton(quest.getDescription(), style));
            verticalGroup.addActor(new TextButton(quest.getDescription(), style));
        }
    }

}
