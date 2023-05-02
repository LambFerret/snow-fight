package com.lambferret.game.command;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.constant.Rarity;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.soldier.Soldier;
import com.lambferret.game.text.dto.CommandInfo;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.GlobalUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public abstract class Command implements Comparable<Command> {
    private static final Logger logger = LogManager.getLogger(Command.class.getName());

    /**
     * ID String
     */
    private final String ID;
    /**
     * 이름
     */
    private final String name;
    /**
     * 텍스쳐 경로
     */
    private final String texturePath;
    /**
     * 설명
     */
    private final String description;
    /**
     * 간단 설명
     */
    private final String shortDescription;
    /**
     * 효과 설명
     */
    private final String effectText;
    /**
     * 종류
     */
    private final Type type;
    /**
     * 사용 비용
     */
    private int cost;
    /**
     * 대상 : 플레이어, 병사
     */
    private final Target target;
    /**
     * 희귀도
     */
    private final Rarity rarity;
    /**
     * 가격
     */
    private int price;
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

    private int initialCost;
    private int initialPrice;
    private int initialAffectToUp;
    private int initialAffectToMiddle;
    private int initialAffectToDown;

    private int itemCount = 0;

    // TODO 여기 호감도 시스템 어케할건지 확인요함
    public Command(
        String ID,
        CommandInfo info,
        Type type,
        int cost,
        Target target,
        Rarity rarity,
        int price,

        int affectToUp,
        int affectToMiddle,
        int affectToDown,
        boolean isPersistentEffect,
        boolean isReusable
    ) {
        this.ID = ID;
        this.texturePath = ID;
        this.name = info.getName();
        this.description = info.getDescription();
        this.effectText = info.getEffect();
        this.shortDescription = info.getShortDescription();
        this.type = type;
        this.cost = cost;
        this.target = target;
        this.rarity = rarity;
        this.price = price;

        this.affectToUp = affectToUp;
        this.affectToMiddle = affectToMiddle;
        this.affectToDown = affectToDown;
        this.isPersistentEffect = isPersistentEffect;
        this.isReusable = isReusable;

        this.initialCost = cost;
        this.initialPrice = price;
        this.initialAffectToUp = affectToUp;
        this.initialAffectToMiddle = affectToMiddle;
        this.initialAffectToDown = affectToDown;
    }

    public void initValue() {
        this.cost = this.initialCost;
        this.price = this.initialPrice;
        this.affectToUp = this.initialAffectToUp;
        this.affectToMiddle = this.initialAffectToMiddle;
        this.affectToDown = this.initialAffectToDown;
    }

    public abstract void execute(List<Soldier> soldiers);

    public void execute() {
        execute(new ArrayList<>());
    }

    public TextureRegionDrawable renderIcon() {
        return new TextureRegionDrawable(new TextureRegion(AssetFinder.getTexture(this.texturePath)));
    }

    public Group renderSimple() {
        // 이름 아이콘(코스트) 간단설명
        Skin skin = GlobalSettings.skin;

        Label nameLabel = new Label(this.ID, skin);
        Label descriptionLabel = new Label(this.shortDescription, skin);
        Label costLabel = new Label("cost: " + this.cost, skin);

        Group group = new Group() {
            @Override
            protected void sizeChanged() {
                super.sizeChanged();

                nameLabel.setSize(getWidth() / 3F, getHeight() / 3F);
                descriptionLabel.setSize(getWidth() / 3F, getHeight() / 3F);
                costLabel.setSize(getWidth() / 5F, getHeight() / 5F);

                nameLabel.setPosition((getWidth() * 4 / 4F) - nameLabel.getWidth(), (getHeight() * 3 / 4F) - nameLabel.getHeight());
                descriptionLabel.setPosition((getWidth() * 3 / 4F) - descriptionLabel.getWidth(), (getHeight() / 4F) - descriptionLabel.getHeight());
                costLabel.setPosition((getWidth() / 4F) - costLabel.getWidth(), (getHeight() / 4F) - costLabel.getHeight());

            }
        };
        Pixmap plate = GlobalUtil.readyPixmap(AssetFinder.getTexture("itemUI_plate"));
        Pixmap icon = GlobalUtil.readyPixmap(AssetFinder.getTexture(this.texturePath));

        plate.drawPixmap(icon, 0, 0, icon.getWidth(), icon.getHeight(), 0, 0, plate.getWidth() / 2, plate.getHeight());
        Image background = new Image(new Texture(plate));
        background.setFillParent(true);
        group.addActor(background);
        group.addActor(nameLabel);
        group.addActor(descriptionLabel);
        group.addActor(costLabel);

        descriptionLabel.setTouchable(Touchable.disabled);
        nameLabel.setTouchable(Touchable.disabled);
        costLabel.setTouchable(Touchable.disabled);

        icon.dispose();
        plate.dispose();

        return group;
    }

    public CustomButton renderInfo() {
        // 이름 자세히설명 종류 코스트 타겟 한마디
        String sb = this.effectText + "\n" +
            "cost: " + this.cost + "\n" +
            "target: " + this.target.toString() + "\n" +
            "type: " + this.type.toString() + "\n" +
            this.description;

        NinePatchDrawable ninePatchDrawable = GlobalUtil.getNinePatchDrawable("itemUI_description", 5);

        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = ninePatchDrawable;
        style.font = GlobalSettings.font;
        return new CustomButton(sb, style);
    }

    @Override
    public int compareTo(Command o) {
        return this.ID.compareTo(o.ID);
    }

    enum Target {
        PLAYER, SOLDIER, UI, ENEMY
    }

    public enum Type {
        /**
         * 작전
         */
        OPERATION,
        /**
         * 포상
         */
        REWARD,
        /**
         * 내통
         */
        BETRAYAL,
    }

}
