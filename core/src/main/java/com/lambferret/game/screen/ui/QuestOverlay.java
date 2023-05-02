package com.lambferret.game.screen.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.lambferret.game.SnowFight;
import com.lambferret.game.quest.Quest;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.GlobalUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class QuestOverlay extends Window implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(QuestOverlay.class.getName());
    public static final int MIN_WINDOW_SIZE = 100;
    private final Stage stage;
    private ScrollPane scrollPane;
    private VerticalGroup verticalGroup;
    private List<Quest> quests;
    private Cursor horizontalResizeCursor;
    private Cursor verticalResizeCursor;
    private Cursor leftUpResizeCursor;
    private Cursor leftDownResizeCursor;
    private final Cursor.SystemCursor defaultCursor = Cursor.SystemCursor.Arrow;

    private void setCursor() {
        TextureAtlas atlas = AssetFinder.getAtlas("cursor");

        Pixmap horizontalResizeCursorPix = GlobalUtil.regionToPixmap(atlas.findRegion("horizontal"));
        Pixmap verticalResizeCursorPix = GlobalUtil.regionToPixmap(atlas.findRegion("vertical"));
        Pixmap leftUpResizeCursorPix = GlobalUtil.regionToPixmap(atlas.findRegion("rightUp"));
        Pixmap leftDownResizeCursorPix = GlobalUtil.regionToPixmap(atlas.findRegion("rightDown"));

        int center = horizontalResizeCursorPix.getWidth() / 2;

        horizontalResizeCursor = Gdx.graphics.newCursor(horizontalResizeCursorPix, center, center);
        verticalResizeCursor = Gdx.graphics.newCursor(verticalResizeCursorPix, center, center);
        leftUpResizeCursor = Gdx.graphics.newCursor(leftUpResizeCursorPix, center, center);
        leftDownResizeCursor = Gdx.graphics.newCursor(leftDownResizeCursorPix, center, center);

    }

    public QuestOverlay(Stage stage) {
        super("Quest", GlobalSettings.skin);
        setCursor();
        this.stage = stage;
        verticalGroup = new VerticalGroup();
        scrollPane = new ScrollPane(verticalGroup);
        this.add(scrollPane);
        verticalGroup.fill();
        stage.addActor(this);
        this.setMovable(true);
        this.setResizable(true);
        this.setKeepWithinStage(true);

        this.addListener(new InputListener() {

            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                int boundaryThickness = 8; // Thickness of the boundary area where the cursor should change
                setResizeBorder(boundaryThickness);

                boolean onLeftBoundary = x >= 0 && x <= boundaryThickness;
                boolean onRightBoundary = x >= getWidth() - boundaryThickness && x <= getWidth();
                boolean onBottomBoundary = y >= 0 && y <= boundaryThickness;
                boolean onBottomLeftCorner = onLeftBoundary && onBottomBoundary;
                boolean onBottomRightCorner = onRightBoundary && onBottomBoundary;

                setResizable(true);
                if (getX() == GlobalSettings.currWidth - (OVERLAY_BORDERLINE_WIDTH + getWidth())) {
                    onRightBoundary = false;
                    onBottomRightCorner = false;
                } else if (getY() == SNOW_BAR_HEIGHT + SNOW_BAR_X) {
                    onBottomLeftCorner = false;
                    onBottomRightCorner = false;
                    onBottomBoundary = false;
                }

                if (onBottomLeftCorner) {
                    Gdx.graphics.setCursor(leftUpResizeCursor);
                } else if (onBottomRightCorner) {
                    Gdx.graphics.setCursor(leftDownResizeCursor);
                } else if (onLeftBoundary) {
                    Gdx.graphics.setCursor(horizontalResizeCursor);
                } else if (onRightBoundary) {
                    Gdx.graphics.setCursor(horizontalResizeCursor);
                } else if (onBottomBoundary) {
                    Gdx.graphics.setCursor(verticalResizeCursor);
                } else {
                    setResizable(false);
                    Gdx.graphics.setSystemCursor(defaultCursor);
                }

                return super.mouseMoved(event, x, y);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    Gdx.graphics.setSystemCursor(defaultCursor);
                }
                super.exit(event, x, y, pointer, toActor);
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

    @Override
    public void setBounds(float x, float y, float width, float height) {
        if (width < MIN_WINDOW_SIZE) {
            width = MIN_WINDOW_SIZE;
        }
        if (height < MIN_WINDOW_SIZE) {
            height = MIN_WINDOW_SIZE;
        }
        float yStartLimit = SNOW_BAR_HEIGHT + SNOW_BAR_X;
        float xEndLimit = GlobalSettings.currWidth - (OVERLAY_BORDERLINE_WIDTH + getWidth());

        if (x < 0 && y < yStartLimit) {
            super.setBounds(0, yStartLimit, width, height);
        } else if (x > xEndLimit && y < yStartLimit) {
            super.setBounds(xEndLimit, yStartLimit, width, height);
        } else if (x < 0) {
            super.setBounds(0, y, width, height);
        } else if (x > xEndLimit) {
            super.setBounds(xEndLimit, y, width, height);
        } else super.setBounds(x, Math.max(y, yStartLimit), width, height);
    }

    @Override
    public void onPlayerReady() {

        this.setPosition(100, 500);
        this.setSize(100, 100);
        quests = SnowFight.player.getQuests();
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


    @Override
    public void onPlayerUpdate() {

    }

}
