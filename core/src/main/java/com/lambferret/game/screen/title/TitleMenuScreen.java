package com.lambferret.game.screen.title;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.AssetPath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class TitleMenuScreen extends AbstractScreen {


    private static final Logger logger = LogManager.getLogger(TitleMenuScreen.class.getName());

    ArrayList<TitleMenuButton> buttons;

    public TitleMenuScreen() {
        this.buttons = new ArrayList<>();
        addTitleMenuButtons();
    }

    private void addTitleMenuButtons() {
        int index = 1;
        this.buttons.clear();
        this.buttons.add(new TitleMenuButton(TitleMenuButton.TitleMenuButtonAction.EXIT, index++));
        this.buttons.add(new TitleMenuButton(TitleMenuButton.TitleMenuButtonAction.CREDIT, index++));
        this.buttons.add(new TitleMenuButton(TitleMenuButton.TitleMenuButtonAction.OPTION, index++));
        this.buttons.add(new TitleMenuButton(TitleMenuButton.TitleMenuButtonAction.LOAD, index++));
        this.buttons.add(new TitleMenuButton(TitleMenuButton.TitleMenuButtonAction.CONTINUE, index++));
        this.buttons.add(new TitleMenuButton(TitleMenuButton.TitleMenuButtonAction.NEW, index++));
    }

    @Override
    public void create() {
    }


    public void render(SpriteBatch batch) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) switchFullScreen();
        batch.setColor(1, 1, 1, 1);
        batch.draw(AssetPath.getTexture("titleBackground"),
            0, 0, GlobalSettings.WIDTH, GlobalSettings.HEIGHT);
        for (TitleMenuButton button : buttons) {
            button.render(batch);
        }
    }

    public void update(float delta) {
        for (TitleMenuButton b : buttons) {
            b.update(delta);
        }
    }

    // 전체화면 관련
    private void switchFullScreen() {
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setWindowedMode(GlobalSettings.WIDTH, GlobalSettings.HEIGHT);
        } else {
            Graphics.DisplayMode mode = Gdx.graphics.getDisplayMode();
            Gdx.graphics.setFullscreenMode(mode);
        }
    }

}
