package com.lambferret.game.screen.ground;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lambferret.game.book.Book;
import com.lambferret.game.book.ExampleBook;
import com.lambferret.game.magic.Bunkering;
import com.lambferret.game.magic.EvilWithin;
import com.lambferret.game.magic.FieldInstructor;
import com.lambferret.game.magic.Magic;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ShopScreen implements AbstractGround {
    private static final Logger logger = LogManager.getLogger(ShopScreen.class.getName());
    public static final int BOOK_STOCK_AMOUNT = 2;
    public static final int MAGIC_STOCK_AMOUNT = 2;

    Stage stage;
    Table table = new Table();
    List<Book> bookStock = new ArrayList<>();
    List<Magic> magicStock = new ArrayList<>();
    Player player;

    public ShopScreen() {
        this.stage = new Stage();
        stage.addActor(table);
    }

    public void create() {
        table.setDebug(true, true);

        table.setSkin(GlobalSettings.skin);
        table.setSize(300, 300);
        table.setPosition(100, 100);

    }

    @Override
    public void init(Player player) {
        this.player = player;
        List<Magic> allMagic = List.of(new FieldInstructor(), new EvilWithin(), new Bunkering());
        List<Book> allBook = List.of(new ExampleBook());
        this.table.clear();
        fillMagicStock(allMagic);
        table.row();
        fillBookStock(allBook);
    }

    private void fillMagicStock(List<Magic> allMagic) {
        this.magicStock.clear();
        while (magicStock.size() < MAGIC_STOCK_AMOUNT) {
            int random = (int) (Math.random() * allMagic.size());
            if (!magicStock.contains(allMagic.get(random))) {
                magicStock.add(allMagic.get(random));
            }
            if (allMagic.size() < MAGIC_STOCK_AMOUNT) break;
        }

        for (Magic magic : magicStock) {
            ImageTextButton imageButton = new ImageTextButton("", GlobalSettings.imageButtonStyle);
            imageButton.setText(magic.getID());
            imageButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (imageButton.isDisabled()) return;
                    if (player.getMoney() >= magic.getPrice()) {
                        player.setMoney(player.getMoney() - magic.getPrice());
                        player.getMagics().add(magic);
                        imageButton.setDisabled(true);
//                        updateMoney();
                        logger.info("clicked |  🐳  money last | " + player.getMoney());
                    } else {
                        logger.info("돈이 부족합니다.");
                    }
                }
            });
            table.add(imageButton);
        }
    }

    private void fillBookStock(List<Book> allBook) {
        this.bookStock.clear();
        while (bookStock.size() < BOOK_STOCK_AMOUNT) {
            int random = (int) (Math.random() * allBook.size());
            if (!bookStock.contains(allBook.get(random))) {
                bookStock.add(allBook.get(random));
            }
            if (allBook.size() < BOOK_STOCK_AMOUNT) break;
        }
        for (Book book : bookStock) {
            ImageTextButton imageButton = new ImageTextButton("", GlobalSettings.imageButtonStyle);
            imageButton.setText(book.getID());
            imageButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (imageButton.isDisabled()) return;
                    if (player.getMoney() >= book.getPrice()) {
                        player.setMoney(player.getMoney() - book.getPrice());
                        player.getBooks().add(book);
                        imageButton.setDisabled(true);
                        logger.info("clicked |  🐳  money last | " + player.getMoney());

                    } else {
                        logger.info("돈이 부족합니다.");
                    }
                }
            });
            table.add(imageButton);
        }
    }

    public Stage getStage() {
        return this.stage;
    }

    public void render() {
        stage.draw();
    }

    public void update() {
        stage.act();
    }

}
