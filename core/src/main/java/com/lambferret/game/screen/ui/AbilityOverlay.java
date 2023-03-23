package com.lambferret.game.screen.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.lambferret.game.book.Book;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AbilityOverlay extends Container<ScrollPane> implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(AbilityOverlay.class.getName());
    public static final int HIDE_BUTTON_WIDTH = 50;
    public static final int HIDE_BUTTON_HEIGHT = 50;
    public static final float PADDING = 5.0F;
    public static final float ANIMATION_DURATION = 0.1F;
    private final Stage stage;
    private final ScrollPane scrollPane;
    private List<Book> book;
    private boolean isHide = false;
    ImageButton hideButton;


    public AbilityOverlay(Stage stage) {
        this.stage = stage;
        this.stage.addActor(this);
        this.scrollPane = new ScrollPane(new Table());
        this.setActor(this.scrollPane);
        stage.setKeyboardFocus(this);
    }

    public void create() {
        this.setPosition(GlobalSettings.currWidth - OVERLAY_WIDTH, OVERLAY_HEIGHT);
        this.setSize(OVERLAY_WIDTH, GlobalSettings.currHeight - OVERLAY_HEIGHT - BAR_HEIGHT);
        this.setDebug(true, true);

        hideButton = hideSwitch();
        stage.addActor(hideButton);

        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setPosition(this.getX(), this.getY());
        scrollPane.setSize(this.getWidth(), this.getHeight());
    }

    @Override
    public void init(Player player) {
        makeAbilityContainer(player.getBooks());
        instantHide();

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

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.I) {
                    logger.info("keyDown |  🐳 111 | ");
//                    changeContainer(player);
                }
                return super.keyDown(event, keycode);
            }
        });
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                logger.info("clicked |  🐳 ? | " + event);
            }
        });
    }

    private void makeAbilityContainer(List<Book> books) {
        Table table = new Table();
        for (Book book : books) {
            table.add(renderBook(book));
            table.row().pad(10);
        }
        this.scrollPane.setActor(table);
    }

    private ImageTextButton renderBook(Book book) {
        var a = new ImageTextButton("book", soldierButtonStyle(book));
        a.setSize(scrollPane.getWidth(), scrollPane.getHeight() / 3.0F);
        return a;
    }

    private ImageTextButton.ImageTextButtonStyle soldierButtonStyle(Book book) {
        var a = new ImageTextButton.ImageTextButtonStyle();
        a.font = GlobalSettings.font;
        a.up = new TextureRegionDrawable(book.renderSimple());
        return a;
    }

    private ImageButton.ImageButtonStyle hideButtonStyle() {
        var a = new ImageButton.ImageButtonStyle();
        a.up = new TextureRegionDrawable(AssetFinder.getTexture("scrollPointer_H"));
        return a;
    }

    private ImageButton hideSwitch() {
        AbilityOverlay thisOverlay = this;
        ImageButton hideSwitch = new ImageButton(hideButtonStyle());

        hideSwitch.setSize(HIDE_BUTTON_WIDTH, HIDE_BUTTON_HEIGHT);
        hideSwitch.setPosition(
            thisOverlay.getX() - (hideSwitch.getWidth() + PADDING),
            thisOverlay.getY() + PADDING
        );
        hideSwitch.setOrigin(Align.center);

        hideSwitch.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isHide) {
                    show();
                } else {
                    hide();
                }
            }
        });
        return hideSwitch;
    }

    private void hide() {
        hide(false);
    }

    private void instantHide() {
        hide(true);
    }

    // 현재 위로 사라지나 너무 멋있으므로 나중에 바꿀것
    private void hide(boolean isInstant) {
        if (isHide) return;
        float instant = isInstant ? 0.0F : ANIMATION_DURATION;
        this.addAction(
            Actions.moveBy(this.getWidth() + PADDING, 0, instant)
        );
        hideButton.addAction(
            Actions.moveBy(this.getWidth() + PADDING, 0, instant)
        );
        hideButton.addAction(
            Actions.rotateBy(90, instant)
        );
        isHide = true;
    }

    private void show() {
        if (!isHide) return;
        this.addAction(
            Actions.moveBy(-(this.getWidth() + PADDING), 0, ANIMATION_DURATION)
        );
        hideButton.addAction(
            Actions.moveBy(-(this.getWidth() + PADDING), 0, ANIMATION_DURATION)
        );
        hideButton.addAction(
            Actions.rotateBy(90, ANIMATION_DURATION)
        );
        isHide = false;
    }

}
