package com.lambferret.game.command;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lambferret.game.SnowFight;
import com.lambferret.game.constant.Rarity;
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
public abstract class Command implements Comparable<Command> {
    private static final Logger logger = LogManager.getLogger(Command.class.getName());

    /**
     * ID String
     */
    private String ID;
    /**
     * 텍스쳐 경로
     */
    private String texturePath;
    /**
     * 사용 비용
     */
    private int cost;
    /**
     * 윗선에게 영향을 미치는 정도
     */
    private int affectToUp;
    /**
     * 인간계에게 영향을 미치는 정도
     */
    private int affectToMiddle;
    /**
     * 하계에 영향을 미치는 정도
     */
    private int affectToDown;
    /**
     * 게임내 지속 효과 인지
     */
    private boolean isPersistentEffect;
    /**
     * 재사용 여부
     */
    private boolean isReusable;
    /**
     * 하계 관련인지
     */
    private boolean isEvil;
    /**
     * 대상 : 플레이어, 병사
     */
    private Target target;
    /**
     * 희귀도
     */
    private Rarity rarity;
    /**
     * 가격
     */
    private int price;
    private int itemCount = 0;

    public Command(
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
    public int compareTo(Command o) {
        return this.ID.compareTo(o.ID);
    }

    enum Target {
        PLAYER, SOLDIER, UI, ENEMY
    }

}
