package com.lambferret.game.manual;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.constant.Rarity;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.dto.ManualInfo;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.GlobalUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Getter
@Setter
public abstract class Manual implements Comparable<Manual> {
    private static final Logger logger = LogManager.getLogger(Manual.class.getName());
    /**
     * ID String
     */
    private String ID;
    /**
     * 이름
     */
    private String name;
    /**
     * 텍스쳐 경로
     */
    private String texturePath;
    /**
     * 설명
     */
    private String description;
    /**
     * 효과 설명
     */
    private String effectDescription;
    /**
     * 희귀도
     */
    private Rarity rarity;
    /**
     * 가격
     */
    private int price;

    protected boolean isDisable;
    TextureAtlas atlas;

    public Manual(
        String ID,
        ManualInfo info,
        Rarity rarity,
        int price
    ) {
        this.ID = ID;
        this.name = info.getName();
        this.texturePath = ID;
        this.description = info.getDescription();
        this.rarity = rarity;
        this.price = price;

        atlas = AssetFinder.getAtlas("manual");
    }

    public TextureRegionDrawable renderIcon() {
        return new TextureRegionDrawable(atlas.findRegion(this.texturePath));
    }

    public CustomButton renderFrontCover() {
        CustomButton button = GlobalUtil.simpleButton("button/button_up", this.name);
        button.padBottom(30);
        return button;
    }

    public Group renderSideCover() {
        Label label = new Label(this.name, GlobalSettings.skin);
        Image plate = new Image(AssetFinder.getTexture("book_sidecover_plate"));

        Group group = new Group() {
            @Override
            protected void sizeChanged() {
                super.sizeChanged();
                label.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
                plate.setPosition(0, 0);
                plate.setSize(getWidth(), getHeight());
            }
        };
        label.setOrigin(Align.center);
        label.setRotation(90);

        group.addActor(plate);
        group.addActor(label);

        return group;
    }

    public CustomButton renderInfo() {
        String sb = this.name + "\n" + this.effectDescription + "\n" + this.description + "\n";
        return GlobalUtil.simpleButton("button/button_up", sb);
    }

    public abstract void effect();

    @Override
    public int compareTo(Manual o) {
        return this.ID.compareTo(o.ID);
    }

}
