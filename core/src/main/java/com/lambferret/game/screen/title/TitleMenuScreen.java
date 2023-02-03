package com.lambferret.game.screen.title;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.SnowFight;
import com.lambferret.game.screen.AbstractScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class TitleMenuScreen extends AbstractScreen {


    private static final Logger logger = LogManager.getLogger(TitleMenuScreen.class.getName());

    SpriteBatch batch;
    ArrayList<TitleMenuButton> buttons;

    public TitleMenuScreen() {
        this.buttons = new ArrayList<>();
        batch = new SpriteBatch();
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


    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) switchFullScreen();
        batch.begin();
        batch.setProjectionMatrix(SnowFight.camera.combined);
        for (TitleMenuButton button : buttons) {
            button.render(batch);
        }

        batch.end();

        this.update();
    }

    public void update() {
        for (TitleMenuButton b : buttons) {
            b.update();
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
