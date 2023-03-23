package com.lambferret.game.book;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lambferret.game.SnowFight;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.util.AssetFinder;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Setter
@Getter
public abstract class Book implements Comparable<Book> {
    private static final Logger logger = LogManager.getLogger(Book.class.getName());

    private String ID;
    private String texturePath;
    private int cost;
    private int affectToUp;
    private int affectToMiddle;
    private int affectToDown;
    private boolean isPersistentEffect;
    private boolean isReusable;
    private boolean isEvil;
    private Target target;
    private int itemCount = 0;

    public Book(
        String ID,
        String texturePath,
        int cost,
        int affectToUp,
        int affectToMiddle,
        int affectToDown,
        boolean isPersistentEffect,
        boolean isReusable,
        boolean isEvil,
        Target target
    ) {
        this.ID = ID;
        this.texturePath = texturePath;
        this.cost = cost;
        this.affectToUp = affectToUp;
        this.affectToMiddle = affectToMiddle;
        this.affectToDown = affectToDown;
        this.isPersistentEffect = isPersistentEffect;
        this.isReusable = isReusable;
        this.isEvil = isEvil;
        this.target = target;
    }

    public void execute() {
        switch (this.target) {
            case PLAYER -> {
                executeToPlayer();
            }
            case SOLDIER -> {
//                executeToPlayer();
            }
            case UI, ENEMY -> {
            }
        }
    }

    public void executeToPlayer() {
        Player player = SnowFight.player;
    }

    public void executeToSoldier(Soldier soldier) {

    }

    public TextureRegion renderSimple() {
        BitmapFont font = GlobalSettings.font;
        Skin skin = GlobalSettings.skin;

        TextureData backgroundFrame = AssetFinder.getTexture("execute").getTextureData();
        TextureData portrait = AssetFinder.getTexture("dojang").getTextureData();

        backgroundFrame.prepare();
        portrait.prepare();

        Pixmap backgroundFramePix = backgroundFrame.consumePixmap();
        Pixmap portraitPix = portrait.consumePixmap();

        Label nameLabel = new Label(this.ID, skin);
        Group group = new Group();

        backgroundFramePix.drawPixmap(portraitPix, 10, 10);
        return new TextureRegion(new Texture(backgroundFramePix));

    }

    public void renderInfo() {

    }

    @Override
    public int compareTo(Book o) {
        return this.ID.compareTo(o.ID);
    }

    enum Target {
        PLAYER, SOLDIER, UI, ENEMY
    }

}
