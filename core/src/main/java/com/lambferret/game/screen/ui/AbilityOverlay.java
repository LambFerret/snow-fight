package com.lambferret.game.screen.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.book.Book;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AbilityOverlay extends Container<ScrollPane> implements AbstractOverlay {
    private static final Logger logger = LogManager.getLogger(AbilityOverlay.class.getName());
    private final Stage stage;
    private final ScrollPane scrollPane;
    private List<Book> book;
    private int a;
    public int b;


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

//        stage.addActor(hideSwitch());

        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setPosition(this.getX(), this.getY());
        scrollPane.setSize(this.getWidth(), this.getHeight());
    }

    @Override
    public void init(Player player) {
        makeAbilityContainer(player.getBooks());

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


}
