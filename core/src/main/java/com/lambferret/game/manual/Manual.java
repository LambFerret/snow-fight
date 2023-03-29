package com.lambferret.game.manual;

import com.lambferret.game.constant.Rarity;
import com.lambferret.game.text.dto.ManualInfo;
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

    private byte stack;

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
    }

    public abstract void effect();

    @Override
    public int compareTo(Manual o) {
        return this.ID.compareTo(o.ID);
    }
}
