package com.lambferret.game.soldier;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.component.constant.Affiliation;
import com.lambferret.game.component.constant.Branch;
import com.lambferret.game.component.constant.Rank;
import com.lambferret.game.component.constant.Terrain;
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
    private List<Terrain> preferenceTerrain;
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

    public Soldier(
        String ID, Affiliation affiliation, Rank rank, String name, Branch branch,
        List<Terrain> preferenceTerrain, String description, String texturePath,
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

    public void create(Hitbox plate) {

    }

    public void render(SpriteBatch batch) {
//        batch.draw(texture);
    }
    private void renderFront(SpriteBatch batch) {

    }
    private void renderBack(SpriteBatch batch) {

    }
    public void update(float delta) {
    }

    @Override
    public int compareTo(Soldier o) {
        return this.ID.compareTo(o.ID);
    }


}






















