package com.lambferret.game.soldier;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.lambferret.game.command.Command;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.constant.Branch;
import com.lambferret.game.constant.EmpowerLevel;
import com.lambferret.game.constant.Rank;
import com.lambferret.game.constant.Terrain;
import com.lambferret.game.level.Level;
import com.lambferret.game.player.Player;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.dto.SoldierInfo;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;
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
    private List<Terrain> preferenceTerrain;

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

    String initialName;
    List<Terrain> initialPreferenceTerrain;
    boolean initialIsUncommonRange;
    short initialSpeed;
    byte initialRangeX;
    byte initialRangeY;
    byte initialRunAwayProbability;
    private SoldierInfo info;
    private boolean isFront;
    TextureAtlas atlas;

    public Soldier(
        String ID,
        SoldierInfo info,
        Rank rank,
        Branch branch,
        List<Terrain> preferenceTerrain,
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
        this.initialName = name;
        this.initialPreferenceTerrain = preferenceTerrain;
        this.initialIsUncommonRange = isUncommonRange;
        this.initialSpeed = speed;
        this.initialRangeX = rangeX;
        this.initialRangeY = rangeY;
        this.initialRunAwayProbability = runAwayProbability;
        empowerLevel(EmpowerLevel.NEUTRAL);
        atlas = AssetFinder.getAtlas("Soldiers");
        initValue();
    }

    public Animation<TextureRegion> getAnimation() {
        float frameDuration = 0.125F;
        Array<TextureAtlas.AtlasRegion> animationFrames = atlas.findRegions(name);
        return new Animation<>(frameDuration, animationFrames);
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

    public Container<Group> card() {
        Container<Group> plate = new Container<>();
        Group front = renderFrontPlate();
        Group back = renderBackPlate();
        plate.setActor(front);
        isFront = true;
        plate.fill();
        plate.addListener(Input.click(() -> {
            if (isFront) {
                isFront = false;
                plate.setActor(back);
            } else {
                isFront = true;
                plate.setActor(front);
            }
        }));
        return plate;
    }

    private Group renderFrontPlate() {
        Group frontPlate = new Group();

        CustomButton soldierButton = GlobalUtil.simpleButton(frontPlateTexture(), "");
        Label soldierName = new Label(this.name, GlobalSettings.skin);

        frontPlate.setSize(soldierButton.getWidth(), soldierButton.getHeight());

        soldierName.setPosition(frontPlate.getWidth() / 3, 0);
        soldierName.setSize(frontPlate.getWidth() * 2 / 3, frontPlate.getHeight() / 3);
        soldierName.setAlignment(Align.center);
        soldierName.setTouchable(Touchable.disabled);

        frontPlate.addActor(soldierButton);
        frontPlate.addActor(soldierName);
        return frontPlate;
    }

    private TextureRegionDrawable frontPlateTexture() {
        Pixmap framePix = GlobalUtil.readyPixmap(AssetFinder.getTexture("soldierFront"));
        Pixmap portraitPix = GlobalUtil.regionToPixmap(atlas.findRegion(name + "Portrait"));
        Pixmap rankPix = GlobalUtil.readyPixmap(TextureFinder.rank(this.rank));

        framePix.drawPixmap(portraitPix, 0, 0, portraitPix.getWidth(), portraitPix.getHeight(),
            0, 0, framePix.getWidth(), framePix.getWidth()
        );
        framePix.drawPixmap(rankPix, 0, 0, rankPix.getWidth(), rankPix.getHeight(),
            0, framePix.getHeight() * 2 / 3, framePix.getWidth() / 3, framePix.getHeight() / 3
        );

        Texture texture = new Texture(framePix);

        portraitPix.dispose();
        rankPix.dispose();
        framePix.dispose();

        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    private Group renderBackPlate() {
        Group backPlate = new Group();

        CustomButton soldierButton = GlobalUtil.simpleButton(backPlateTexture(), "");
        Label soldierName = new Label(this.description, GlobalSettings.skin);

        backPlate.setSize(soldierButton.getWidth(), soldierButton.getHeight());

        soldierName.setPosition(backPlate.getWidth() / 3, 0);
        soldierName.setSize(backPlate.getWidth() * 2 / 3, backPlate.getHeight() / 3);
        soldierName.setAlignment(Align.center);
        soldierName.setTouchable(Touchable.disabled);

        backPlate.addActor(soldierButton);
        backPlate.addActor(soldierName);
        return backPlate;
    }

    private TextureRegionDrawable backPlateTexture() {
        Pixmap framePix = GlobalUtil.readyPixmap(AssetFinder.getTexture("soldierFront"));
        Pixmap portraitPix = GlobalUtil.readyPixmap(AssetFinder.getTexture(this.texturePath));
        Pixmap rankPix = GlobalUtil.readyPixmap(TextureFinder.rank(this.rank));

        framePix.drawPixmap(portraitPix, 0, 0, portraitPix.getWidth(), portraitPix.getHeight(),
            0, 0, framePix.getWidth(), framePix.getWidth()
        );
        framePix.drawPixmap(rankPix, 0, 0, rankPix.getWidth(), rankPix.getHeight(),
            0, framePix.getHeight() * 2 / 3, framePix.getWidth() / 3, framePix.getHeight() / 3
        );

        Texture texture = new Texture(framePix);

        framePix.dispose();
        portraitPix.dispose();
        rankPix.dispose();

        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public void empowerLevel(EmpowerLevel level) {
        if (level != EmpowerLevel.NEUTRAL) logger.info("empowerLevel | " + this.name + " is empowered as " + level);
        this.empowerLevel = level;
        switch (level) {
            case WEAKEN -> {
                this.weaken();
                this.name = this.initialName + " - ";
            }
            case EMPOWERED -> {
                this.empowered();
                this.name = this.initialName + " + ";
            }
            case NEUTRAL -> {
                this.neutralized();
                this.name = this.initialName;
            }
        }
    }

    public abstract void talent(List<Soldier> soldiers, Map<Command, List<Soldier>> commands, Level level, Player player);

    protected abstract void empowered();

    protected abstract void neutralized();

    protected abstract void weaken();

    public void setRangeX(byte rangeX) {
        if (rangeX < 0) {
            logger.fatal("setRangeX | " + this.name + " rangeX < 0");
            throw new IllegalArgumentException("rangeX < 0");
        } else if (rangeX > 100) {
            logger.warn("setRangeX | " + this.name + " rangeX > 10");
        } else {
            this.rangeX = rangeX;
        }
    }

    public void setRangeY(byte rangeY) {
        if (rangeY < 0) {
            logger.fatal("setRangeY | " + this.name + " rangeY < 0");
            throw new IllegalArgumentException("rangeY < 0");
        } else if (rangeY > 100) {
            logger.warn("setRangeY | " + this.name + " rangeY > 10");
        } else {
            this.rangeY = rangeY;
        }
    }

    public void setSpeed(short speed) {
        if (speed < 0) {
            logger.warn("setSpeed | " + this.name + " speed < 0");
            this.speed = 0;
        } else {
            this.speed = speed;
        }
    }

    public void setRunAwayProbability(byte runAwayProbability) {
        if (runAwayProbability < 0) {
            logger.fatal("setRunAwayProbability | " + this.name + " runAwayProbability < 0");
            this.runAwayProbability = 0;
        } else if (runAwayProbability > 100) {
            logger.fatal("setRunAwayProbability | " + this.name + " runAwayProbability > 100");
            this.runAwayProbability = 100;
        } else {
            this.runAwayProbability = runAwayProbability;
        }
    }

    @Override
    public int compareTo(Soldier o) {
        return this.rank.ordinal() - o.rank.ordinal();
    }

}
