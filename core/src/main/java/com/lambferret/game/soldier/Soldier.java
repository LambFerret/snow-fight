package com.lambferret.game.soldier;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lambferret.game.constant.Affiliation;
import com.lambferret.game.constant.Branch;
import com.lambferret.game.constant.Rank;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.TextureFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class Soldier implements Comparable<Soldier> {
    private static final Logger logger = LogManager.getLogger(Soldier.class.getName());
    /**
     * 카드 id. 군번도 가능할까?
     */
    private String ID;
    /**
     * 소속
     */
    private Affiliation affiliation;
    /**
     * 직급
     */
    private Rank rank;
    /**
     * 이름
     */
    private String name;
    /**
     * 병과
     */
    private Branch branch;
    /**
     * 선호 지형
     */
    private List<Short> preferenceTerrain;
    /**
     * 한마디?
     */
    private String description;
    /**
     * 텍스쳐 관련
     */
    private String texturePath;
    /**
     * 속도
     */
    private float speed;
    /**
     * 특수한 가로세로 범위
     */
    private boolean isUncommonRange;
    /**
     * 가로 범위
     */
    private int rangeX;
    /**
     * 세로 범위
     */
    private int rangeY;
    /**
     * 뺑끼 확률 (아직안씀)
     */
    private float runAwayProbability;

    private Texture texture;
    private float locationX;
    private float locationY;
    private Texture simpleTexture;
    private BitmapFont font = new BitmapFont();
    private boolean isFront = true;
    private boolean isDetail = false;

    public Soldier(
        String ID, Affiliation affiliation, Rank rank, String name, Branch branch,
        List<Short> preferenceTerrain, String description, String texturePath,
        float speed, boolean isUncommonRange, int rangeX, int rangeY
    ) {
        this.ID = ID;
        this.affiliation = affiliation;
        this.rank = rank;
        this.name = name;
        this.branch = branch;
        this.preferenceTerrain = preferenceTerrain;
        this.description = description;
        this.texturePath = texturePath;
        this.speed = speed;
        this.isUncommonRange = isUncommonRange;
        this.rangeX = rangeX;
        this.rangeY = rangeY;
    }

    private void renderSimple() {
//        batch.draw(AssetFinder.getTexture(texturePath + "DogTag"), offsetX, offsetY, width, height);
//        batch.draw(TextureFinder.rank(rank), offsetX/* + 위치조정 */, offsetY, width / 3.0F, height);
//        font.draw(batch, rank.name(), offsetX + width * 2 / 3.0F, offsetY + height / 2.0F);
//        font.draw(batch, name, offsetX + width * 2 / 3.0F, offsetY + height);
//        this.box.render();
    }


    //
//    public void setOffset(Hitbox plate, boolean isDetail) {
//        this.isDetail = isDetail;
//        box.move(plate.getX(), plate.getY());
//        box.resize(plate.getWidth(), plate.getHeight());
//
//        this.offsetX = plate.getX();
//        this.offsetY = plate.getY();
//        this.width = plate.getWidth();
//        this.height = plate.getHeight();
//    }

    public TextureRegion renderFront() {
        BitmapFont font = GlobalSettings.font;
        Skin skin = GlobalSettings.skin;

        TextureData backgroundFrame = AssetFinder.getTexture("soldierFront").getTextureData();
        TextureData portrait = AssetFinder.getTexture(this.texturePath).getTextureData();
        TextureData rank = TextureFinder.rank(this.rank).getTextureData();

        backgroundFrame.prepare();
        portrait.prepare();
        rank.prepare();

        Pixmap backgroundFramePix = backgroundFrame.consumePixmap();
        Pixmap portraitPix = portrait.consumePixmap();
        Pixmap rankPix = rank.consumePixmap();

        Label rankNameLabel = new Label(this.rank.name(), skin);
        Label nameLabel = new Label(this.getName(), skin);
        Group group = new Group();

        backgroundFramePix.drawPixmap(portraitPix, 50, 60);
        backgroundFramePix.drawPixmap(rankPix, 0, 0);
        return new TextureRegion(new Texture(backgroundFramePix));


    }

    private void renderBack() {
//        batch.draw(AssetFinder.getTexture("soldierBack"), offsetX, offsetY, width, height);
//        batch.draw(AssetFinder.getTexture("3by3"), offsetX, offsetY + height / 2.0F, width, height / 2.0F);
//        font.draw(batch, this.description, offsetX + 5.0F, offsetY + height / 2);

    }

    private void renderHover() {
//        float x = CustomInputProcessor.getMouseLocationX();
//        float y = CustomInputProcessor.getMouseLocationY();
        float width = 300;
        float height = 450;
//        batch.draw(AssetFinder.getTexture("soldierBack"), x, y, width, height);
//        batch.draw(AssetFinder.getTexture("3by3"), x, y + height / 2.0F, width, height / 2.0F);
//        font.draw(batch, this.description, x + 5.0F, y + height / 2.0F);
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Soldier o) {
        return this.ID.compareTo(o.ID);
    }


}






















