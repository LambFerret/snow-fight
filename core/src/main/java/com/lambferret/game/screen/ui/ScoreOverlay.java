package com.lambferret.game.screen.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.lambferret.game.player.Player;
import com.lambferret.game.quest.Quest;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ScoreOverlay extends Window implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(ScoreOverlay.class.getName());
    private final Stage stage;
    private ScrollPane scrollPane;
    private VerticalGroup verticalGroup;
    private List<Quest> quests;

    public ScoreOverlay(Stage stage) {
        super("Score", GlobalSettings.skin);
        this.stage = stage;
        verticalGroup = new VerticalGroup();
        scrollPane = new ScrollPane(verticalGroup);
        this.add(scrollPane);
        stage.addActor(this);
        this.setMovable(true);
    }

    public void create() {
        this.setPosition(500,  500);
        this.setSize(100, 100);
        this.setBackground(GlobalSettings.debugTexture);
        this.setColor(GlobalSettings.debugColorGreen);
    }

    @Override
    public void init(Player player) {
        quests = player.getQuests();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = GlobalSettings.font;
        for (Quest quest : quests) {
            verticalGroup.addActor(new TextButton(quest.getDescription(), style));
        }
    }

}
