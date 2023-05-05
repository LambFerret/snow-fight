package com.lambferret.game.soldier;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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

    List<Terrain> initialPreferenceTerrain;
    boolean initialIsUncommonRange;
    short initialSpeed;
    byte initialRangeX;
    byte initialRangeY;
    byte initialRunAwayProbability;
    private SoldierInfo info;
    private boolean isFront;
    TextureAtlas atlas;
    Animation<TextureRegion> animation;

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
        this.initialPreferenceTerrain = preferenceTerrain;
        this.initialIsUncommonRange = isUncommonRange;
        this.initialSpeed = speed;
        this.initialRangeX = rangeX;
        this.initialRangeY = rangeY;
        this.initialRunAwayProbability = runAwayProbability;
        empowerLevel(EmpowerLevel.NEUTRAL);
        atlas = AssetFinder.getAtlas(texturePath);
        setAnimation();
    }

    private void setAnimation() {
        float frameDuration = 1;
        Array<TextureAtlas.AtlasRegion> animationFrames = atlas.findRegions("animation");
        animation = new Animation<>(frameDuration, animationFrames);
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
        plate.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isFront) {
                    isFront = false;
                    plate.setActor(back);
                } else {
                    isFront = true;
                    plate.setActor(front);
                }
            }
        });
        return plate;
    }

    private Group renderFrontPlate() {
        Group frontPlate = new Group();

        CustomButton soldierButton = new CustomButton("", getFrontPlateStyle());
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

    private ImageTextButton.ImageTextButtonStyle getFrontPlateStyle() {
        var style = new ImageTextButton.ImageTextButtonStyle();

        Pixmap framePix = GlobalUtil.readyPixmap(AssetFinder.getTexture("soldierFront"));
        Pixmap portraitPix = GlobalUtil.regionToPixmap(atlas.findRegion("portrait"));
        Pixmap rankPix = GlobalUtil.readyPixmap(TextureFinder.rank(this.rank));

        framePix.drawPixmap(portraitPix, 0, 0, portraitPix.getWidth(), portraitPix.getHeight(),
            0, 0, framePix.getWidth(), framePix.getWidth()
        );
        framePix.drawPixmap(rankPix, 0, 0, rankPix.getWidth(), rankPix.getHeight(),
            0, framePix.getHeight() * 2 / 3, framePix.getWidth() / 3, framePix.getHeight() / 3
        );

        style.font = GlobalSettings.font;
        style.up = new TextureRegionDrawable(new TextureRegion(new Texture(framePix)));

        portraitPix.dispose();
        rankPix.dispose();

        return style;
    }

    private Group renderBackPlate() {
        Group backPlate = new Group();

        CustomButton soldierButton = new CustomButton("", getBackPlateStyle());
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

    private ImageTextButton.ImageTextButtonStyle getBackPlateStyle() {
        var style = new ImageTextButton.ImageTextButtonStyle();

        Pixmap framePix = GlobalUtil.readyPixmap(AssetFinder.getTexture("soldierFront"));
        Pixmap portraitPix = GlobalUtil.readyPixmap(AssetFinder.getTexture(this.texturePath));
        Pixmap rankPix = GlobalUtil.readyPixmap(TextureFinder.rank(this.rank));

        framePix.drawPixmap(portraitPix, 0, 0, portraitPix.getWidth(), portraitPix.getHeight(),
            0, 0, framePix.getWidth(), framePix.getWidth()
        );
        framePix.drawPixmap(rankPix, 0, 0, rankPix.getWidth(), rankPix.getHeight(),
            0, framePix.getHeight() * 2 / 3, framePix.getWidth() / 3, framePix.getHeight() / 3
        );

        style.font = GlobalSettings.font;
        style.up = new TextureRegionDrawable(new TextureRegion(new Texture(framePix)));

        return style;
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
        return this.rank.ordinal() - o.rank.ordinal();
    }

}
