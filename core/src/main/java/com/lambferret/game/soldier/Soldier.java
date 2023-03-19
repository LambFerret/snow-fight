package com.lambferret.game.soldier;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.lambferret.game.constant.Affiliation;
import com.lambferret.game.constant.Branch;
import com.lambferret.game.constant.Rank;
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
    private float width;
    private float height;
    private float offsetX;
    private float offsetY;
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

    private void renderSimple() {
//        batch.draw(AssetFinder.getTexture(texturePath + "DogTag"), offsetX, offsetY, width, height);
//        batch.draw(TextureFinder.rank(rank), offsetX/* + 위치조정 */, offsetY, width / 3.0F, height);
//        font.draw(batch, rank.name(), offsetX + width * 2 / 3.0F, offsetY + height / 2.0F);
//        font.draw(batch, name, offsetX + width * 2 / 3.0F, offsetY + height);
//        this.box.render();
    }


    private void renderFront() {
//        batch.draw(AssetFinder.getTexture("soldierFront"), offsetX, offsetY, width, height);
//        batch.draw(AssetFinder.getTexture(this.texturePath), offsetX + height / 3.0F, offsetY, width, height * 2.0F / 3.0F);
//        batch.draw(TextureFinder.rank(rank), offsetX/* + 위치조정 */, offsetY, width / 3.0F, height / 3.0F);
//        font.draw(batch, rank.name(), offsetX + width * 2 / 3.0F, offsetY + height / 6.0F);
//        font.draw(batch, name, offsetX + width * 2 / 3.0F, offsetY + height / 3.0F);
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

    public void render() {
        if (!isDetail) {
            renderSimple();
//            if (this.box.isHovered) {
//                renderHover();
//            }
        } else if (isFront) {
            renderFront();
        } else {
            renderBack();
        }
    }


    public void update() {
        isFront = false;
        isFront = true;
    }

    @Override
    public int compareTo(Soldier o) {
        return this.ID.compareTo(o.ID);
    }


}






















