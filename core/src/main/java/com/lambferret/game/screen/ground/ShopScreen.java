package com.lambferret.game.screen.ground;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.lambferret.game.book.Book;
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
        this.magicStock.clear();
        this.table.clear();
        while (magicStock.size() < 2) {
            int random = (int) (Math.random() * allMagic.size());
            if (!magicStock.contains(allMagic.get(random))) {
                magicStock.add(allMagic.get(random));
            }
        }
        for (Magic magic : magicStock) {
            ImageTextButton imageButton = new ImageTextButton("", GlobalSettings.imageButtonStyle);
            imageButton.setText(magic.getID());
            table.add(imageButton);
        }

        logger.info("init |  🐳 you are now | " + magicStock);

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
