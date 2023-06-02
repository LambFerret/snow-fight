package com.lambferret.game.screen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lambferret.game.SnowFight;
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
    private Cursor leftUpResizeCursor;
    private final Cursor.SystemCursor defaultCursor = Cursor.SystemCursor.Arrow;
    float yStartLimit = SNOW_BAR_HEIGHT + SNOW_BAR_Y;
    private boolean isHide = false;

    public QuestOverlay(Stage stage) {
        super("Quest", GlobalSettings.skin);
        setCursor();
        this.stage = stage;
        stage.addActor(this);
        this.setMovable(true);
        this.setResizable(false);
        this.setKeepWithinStage(true);
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (x > getWidth() - 20 && y > getHeight() - 20) {
                    hide();
                }
                super.clicked(event, x, y);
            }

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                if (x > getWidth() - 20 && y > getHeight() - 20) Gdx.graphics.setCursor(leftUpResizeCursor);
                else Gdx.graphics.setSystemCursor(defaultCursor);
                return super.mouseMoved(event, x, y);
            }
        });
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
        this.clear();
        VerticalGroup table = new VerticalGroup();
        for (Quest quest : quests) {
            table.addActor(quest.getQuestItem());
        }
        table.fill();
        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.addListener(Input.setScrollFocusWhenHover(stage, scrollPane));
        this.add(scrollPane);
    }

    private void setCursor() {
        TextureAtlas atlas = AssetFinder.getAtlas("cursor");
        Pixmap leftUpResizeCursorPix = GlobalUtil.regionToPixmap(atlas.findRegion("rightUp"));
        int center = leftUpResizeCursorPix.getWidth() / 2;
        leftUpResizeCursor = Gdx.graphics.newCursor(leftUpResizeCursorPix, center, center);
        leftUpResizeCursorPix.dispose();
    }

    private void hide() {
        float oldHeight = this.getHeight();
        if (!isHide) {
            isHide = true;
            this.setHeight(50);
        } else {
            isHide = false;
            this.setHeight(QUEST_INIT_HEIGHT);
        }
        float newY = this.getY() + oldHeight - this.getHeight();
        this.setY(newY);
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        super.setBounds(x, Math.max(y, yStartLimit), width, height);
    }

}
