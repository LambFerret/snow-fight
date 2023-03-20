package com.lambferret.game.screen.title;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OptionWindow extends Window {
    private static final Logger logger = LogManager.getLogger(OptionWindow.class.getName());

    public static boolean isTitle;

    public OptionWindow(String title, Skin skin) {
        super(title, skin);
    }

    public void create() {
    }

}
