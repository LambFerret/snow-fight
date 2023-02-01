package com.lambferret.game.screen.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lambferret.game.GlobalSettings;
import com.lambferret.game.screen.AbstractScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MainMenuScreen extends AbstractScreen {


    private static final Logger logger = LogManager.getLogger(MainMenuScreen.class.getName());

    SpriteBatch batch;
    ArrayList<MainMenuButton> buttons;

    public MainMenuScreen() {
        this.buttons = new ArrayList<>();
        batch = new SpriteBatch();
        addMainMenuButtons();
    }

    private void addMainMenuButtons() {
        int index = 1;
        this.buttons.clear();
        this.buttons.add(new MainMenuButton(MainMenuButton.ButtonAction.EXIT, index++));
        this.buttons.add(new MainMenuButton(MainMenuButton.ButtonAction.CREDIT, index++));
        this.buttons.add(new MainMenuButton(MainMenuButton.ButtonAction.OPTION, index++));
        this.buttons.add(new MainMenuButton(MainMenuButton.ButtonAction.LOAD, index++));
        this.buttons.add(new MainMenuButton(MainMenuButton.ButtonAction.NEW, index++));
    }

    @Override
    public void create() {
    }


    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) switchFullScreen();
        batch.begin();
        for (MainMenuButton button : buttons) {
            button.render(batch);
        }

        batch.end();

        update(delta);

    }

    protected void update(float delta) {
        for (MainMenuButton b : buttons) {
            b.update();
        }
    }

    // 전체화면 관련
    private void switchFullScreen() {
        logger.info("switchFullScreen |  🐳 screensize | " + Gdx.graphics.getHeight());
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setWindowedMode(GlobalSettings.WIDTH, GlobalSettings.HEIGHT);
        } else {
            Graphics.DisplayMode mode = Gdx.graphics.getDisplayMode();
            Gdx.graphics.setFullscreenMode(mode);
        }
    }

}
