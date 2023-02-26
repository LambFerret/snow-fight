package com.lambferret.game.screen.title;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.setting.ScreenConfig;
import com.lambferret.game.component.Hitbox;
import com.lambferret.game.save.SaveLoader;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.text.LocalizeConfig;
import com.lambferret.game.text.dto.TitleMenuText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TitleMenuButton {

    private static final Logger logger = LogManager.getLogger(TitleMenuButton.class.getName());
    private static TitleMenuText text;
    private Hitbox box;
    private String name;
    private TitleMenuButtonAction action;
    private static final float s = GlobalSettings.scale;

    public TitleMenuButton(TitleMenuButtonAction action, int index) {
        text = LocalizeConfig.uiText.getTitleMenuText();
        this.action = action;
        this.name = setName(action);
        this.box = new Hitbox(100 * s, 50.0F * s * (index + 1), 50 * s, 25 * s);
    }

    public void render(SpriteBatch batch) {
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.setColor(Color.BLACK);
        this.box.render(batch);
    }

    public void update(float delta) {
        this.box.update(delta);
        setAction();
    }

    private void setAction() {
        if (this.box.isClicked) switch (this.action) {
            case NEW -> {
                SaveLoader.makeNewSave(0);
                ScreenConfig.changeScreen = ScreenConfig.AddedScreen.TRAINING_GROUND_SCREEN;
            }
            case CONTINUE -> {
                SaveLoader.load(0);
                ScreenConfig.changeScreen = ScreenConfig.AddedScreen.SHOP_SCREEN;
            }
            case LOAD -> {
                ScreenConfig.changeScreen = ScreenConfig.AddedScreen.RECRUIT_SCREEN;
            }
            case OPTION -> {
                ScreenConfig.changeScreen = ScreenConfig.AddedScreen.READY_SCREEN;
            }
            case CREDIT -> {
                logger.info("update | CREDIT");
            }
            case EXIT -> {
                Gdx.app.exit();
            }
        }
    }

    private String setName(TitleMenuButtonAction action) {
        return switch (action) {
            case NEW -> text.getNewGame();
            case CONTINUE -> text.getContinueGame();
            case LOAD -> text.getLoadGame();
            case OPTION -> text.getOption();
            case CREDIT -> text.getCredit();
            case EXIT -> text.getExit();
        };
    }

    enum TitleMenuButtonAction {
        NEW,
        CONTINUE,
        LOAD,
        OPTION,
        CREDIT,
        EXIT
    }
}
