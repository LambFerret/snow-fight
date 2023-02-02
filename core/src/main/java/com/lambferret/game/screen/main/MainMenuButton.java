package com.lambferret.game.screen.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.GlobalSettings;
import com.lambferret.game.Hitbox;
import com.lambferret.game.SnowFight;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.MainMenuText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainMenuButton {

    private static final Logger logger = LogManager.getLogger(MainMenuButton.class.getName());
    public static MainMenuText text;
    public Hitbox box;
    public String name;
    public ButtonAction action;
    private static final float s = GlobalSettings.scale;

    public MainMenuButton(ButtonAction action, int index) {
        text = LocalizeConfig.uiText.getMainMenuText();
        this.action = action;
        this.name = setName(action);
        this.box = new Hitbox(100 * s, 50.0F * s * (index + 1), 50 * s, 25 * s);
    }

    public void render(SpriteBatch batch) {
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.setColor(Color.BLACK);
        this.box.render(batch);
    }

    public void update() {
        this.box.update();
        setAction();
    }

    private void setAction() {
        if (this.box.isClicked) switch (this.action) {
            case NEW -> {
                logger.info("update | NEW");
                SnowFight.changeScreen = SnowFight.AddedScreen.SUB_SCREEN;
            }
            case LOAD -> {
                logger.info("update | LOAD");
            }
            case OPTION -> {
                logger.info("update | OPTION");
            }
            case CREDIT -> {
                logger.info("update | CREDIT");
            }
            case EXIT -> {
                logger.info("update | exit game!");
                Gdx.app.exit();
            }
        }
    }

    private String setName(ButtonAction action) {
        return switch (action) {
            case NEW -> text.getNEW_GAME();
            case CONTINUE -> text.getCONTINUE();
            case LOAD -> text.getLOAD_GAME();
            case OPTION -> text.getOPTION();
            case CREDIT -> text.getCREDIT();
            case EXIT -> text.getEXIT();
        };
    }

    enum ButtonAction {
        NEW,
        CONTINUE,
        LOAD,
        OPTION,
        CREDIT,
        EXIT
    }
}
