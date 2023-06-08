package com.lambferret.game.component;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.TitleMenuText;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WindowDialog extends Dialog {
    private static final Logger logger = LogManager.getLogger(WindowDialog.class.getName());
    protected static final TitleMenuText text = LocalizeConfig.uiText.getTitleMenuText();

    public static final float WIDTH = 400;
    public static final float HEIGHT = 300;
    private int ID = 999;


    public WindowDialog(String title, int ID) {
        this("", WarnLevel.INFO, title);
        this.ID = ID;
    }

    public WindowDialog(String title, WarnLevel warnLevel, String context) {
        super(title, GlobalSettings.skin);
        this.setSize(WIDTH, HEIGHT);
        setMovable(false);
        setResizable(false);

        Skin skin = GlobalSettings.skin;
        WindowStyle style = skin.get(WindowStyle.class);
        style.background = new NinePatchDrawable(new NinePatch(new TextureRegion(AssetFinder.getTexture("windowDialog" + ".9")), 3, 3, 27, 3));

        setStyle(style);
        this.getContentTable().add(new Image(AssetFinder.getTexture(warnLevel.name().toLowerCase()))).size(50);

        switch (warnLevel) {
            case INFO -> {
            }
            case ERROR -> {
                this.button(text.getYes(), true);
                this.button(text.getNo(), false);
            }
            case FATAL -> {
                this.button(text.getOk());
            }
        }
        this.text(context);
        this.getContentTable().padRight(5).padLeft(5);
    }

    @Override
    public Dialog button(Button button, Object object) {
        this.getButtonTable().add(button).size(100, 25).pad(5);
        this.setObject(button, object);
        return this;
    }

    public int getID() {
        return ID;
    }

    public enum WarnLevel {
        INFO, ERROR, FATAL
    }

}
