package com.lambferret.game.book;

import com.lambferret.game.constant.Rarity;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Getter
@Setter
public abstract class Book implements Comparable<Book> {
    private static final Logger logger = LogManager.getLogger(Book.class.getName());

    /**
     * ID String
     */
    private String ID;
    /**
     * 텍스쳐 경로
     */
    private String texturePath;
    /**
     * 설명
     */
    private String description;
    /**
     * 희귀도
     */
    private Rarity rarity;
    /**
     * 가격
     */
    private int price;

    private byte stack;

    public Book(
        String ID,
        String texturePath,
        String description,
        Rarity rarity,
        int price,
        byte stack
    ) {
        this.ID = ID;
        this.texturePath = texturePath;
        this.description = description;
        this.rarity = rarity;
        this.price = price;
        this.stack = stack;
    }

    @Override
    public int compareTo(Book o) {
        return this.ID.compareTo(o.ID);
    }
}
