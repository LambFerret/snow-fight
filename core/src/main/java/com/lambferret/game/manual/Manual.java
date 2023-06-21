package com.lambferret.game.manual;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lambferret.game.component.CustomButton;
import com.lambferret.game.constant.Rarity;
import com.lambferret.game.text.dto.ManualInfo;
import com.lambferret.game.util.AssetFinder;
import com.lambferret.game.util.GlobalUtil;
import com.lambferret.game.util.Input;
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
        // TODO vanilla
        this.texturePath = "DisciplineAndPunish";
        this.description = info.getDescription();
        this.rarity = rarity;
        this.price = price;
        atlas = AssetFinder.getAtlas("manual");
    }

    public TextureRegionDrawable renderIcon() {
        return new TextureRegionDrawable(atlas.findRegion(this.texturePath));
    }

    public Image renderSideCover() {
        Image image = new Image(new TextureRegionDrawable(atlas.findRegion(this.texturePath + "_side")));
        image.addListener(Input.soundWhenHover("button_book"));
        return image;
    }

    public CustomButton renderInfo() {
        String sb = this.name + "\n" + this.effectDescription + "\n" + this.description + "\n";
        return GlobalUtil.simpleButton("button/button_up", sb);
    }

    public abstract void effect(ManualTiming timing);

    @Override
    public int compareTo(Manual o) {
        return this.ID.compareTo(o.ID);
    }

    public enum ManualTiming {
        START_STAGE, ACTION_START, ACTION_END
    }

}
