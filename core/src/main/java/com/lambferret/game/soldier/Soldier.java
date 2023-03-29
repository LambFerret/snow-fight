package com.lambferret.game.soldier;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.lambferret.game.command.Command;
import com.lambferret.game.constant.Branch;
import com.lambferret.game.constant.EmpowerLevel;
import com.lambferret.game.constant.Rank;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.dto.SoldierInfo;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.TextureFinder;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public abstract class Soldier implements Comparable<Soldier> {
    private static final Logger logger = LogManager.getLogger(Soldier.class.getName());
    /**
     * ID, 밑으로 기본적인 정보들
     */
    private String ID;
    /**
     * 이름
     */
    private String name;
    /**
     * 한마디?
     */
    private String description;
    /**
     * 텍스쳐 관련
     */
    private String texturePath;
    /**
     * 선호 지형
     */
    private List<Short> preferenceTerrain;

    /**
     * 속도, 밑으로 수치관련 정보
     */
    private short speed;
    /**
     * 특수한 가로세로 범위
     */
    private boolean isUncommonRange;
    /**
     * 가로 범위
     */
    private byte rangeX;
    /**
     * 세로 범위
     */
    private byte rangeY;
    /**
     * 현재 강화 수치
     */
    private EmpowerLevel empowerLevel;

    /**
     * 직급, 밑으로 능력관련 정보
     */
    private Rank rank;
    /**
     * 병과
     */
    private Branch branch;
    /**
     * 재능 관련 설명
     */
    private String talent;
    /**
     * 강화
     */
    private String empower;
    /**
     * 약화
     */
    private String weaken;
    /**
     * 뺑끼 확률 (아직안씀)
     */
    private byte runAwayProbability;

    //인게임 정보 관련

    List<Short> initialPreferenceTerrain;
    boolean initialIsUncommonRange;
    short initialSpeed;
    byte initialRangeX;
    byte initialRangeY;
    byte initialRunAwayProbability;
    private SoldierInfo info;
    private Texture texture;
    private Texture simpleTexture;
    private boolean isFront = true;
    private boolean isDetail = false;

    public Soldier(
        String ID,
        SoldierInfo info,
        Rank rank,
        Branch branch,
        List<Short> preferenceTerrain,
        boolean isUncommonRange,
        short speed,
        byte rangeX,
        byte rangeY,
        byte runAwayProbability
    ) {
        this.ID = ID;
        this.name = info.getName();
        this.description = info.getDescription();
        this.texturePath = ID;
        this.rank = rank;
        this.branch = branch;
        this.talent = info.getTalent();
        this.empower = info.getEmpower();
        this.weaken = info.getWeaken();
        this.initialPreferenceTerrain = preferenceTerrain;
        this.initialIsUncommonRange = isUncommonRange;
        this.initialSpeed = speed;
        this.initialRangeX = rangeX;
        this.initialRangeY = rangeY;
        this.initialRunAwayProbability = runAwayProbability;
        initValue();
    }

    public void initValue() {
        this.preferenceTerrain = this.initialPreferenceTerrain;
        this.isUncommonRange = this.initialIsUncommonRange;
        this.speed = this.initialSpeed;
        this.rangeX = this.initialRangeX;
        this.rangeY = this.initialRangeY;
        this.runAwayProbability = this.initialRunAwayProbability;
        empowerLevel(EmpowerLevel.NEUTRAL);
    }

    private void renderSimple() {
//        batch.draw(AssetFinder.getTexture(texturePath + "DogTag"), offsetX, offsetY, width, height);
//        batch.draw(TextureFinder.rank(rank), offsetX/* + 위치조정 */, offsetY, width / 3.0F, height);
//        font.draw(batch, rank.name(), offsetX + width * 2 / 3.0F, offsetY + height / 2.0F);
//        font.draw(batch, name, offsetX + width * 2 / 3.0F, offsetY + height);
//        this.box.render();
    }

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
//        TextureData rank = TextureFinder.rank(this.rank).getTextureData();

        backgroundFrame.prepare();
        portrait.prepare();
//        rank.prepare();

        Pixmap backgroundFramePix = backgroundFrame.consumePixmap();
        Pixmap portraitPix = portrait.consumePixmap();
//        Pixmap rankPix = rank.consumePixmap();

        Label rankNameLabel = new Label(this.rank.name(), skin);
        Label nameLabel = new Label(this.getName(), skin);
        Group group = new Group();

        backgroundFramePix.drawPixmap(portraitPix, 50, 60);
//        backgroundFramePix.drawPixmap(rankPix, 0, 0);
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

    public void empowerLevel(EmpowerLevel level) {
        if (level != EmpowerLevel.NEUTRAL) logger.info("empowerLevel | " + this.name + " is empowered as " + level);
        this.empowerLevel = level;
        switch (level) {
            case WEAKEN -> this.weaken();
            case EMPOWERED -> this.empowered();
            case NEUTRAL -> this.neutralized();
        }
    }

    public abstract void talent(List<Soldier> soldiers, Map<Command, List<Soldier>> commands, Level level, Player player);

    protected abstract void empowered();

    protected abstract void neutralized();

    protected abstract void weaken();

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Soldier o) {
        return this.rank.getValue() - o.rank.getValue();
    }

}
