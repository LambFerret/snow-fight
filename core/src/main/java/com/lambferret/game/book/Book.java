package com.lambferret.game.book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
}
