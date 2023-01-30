package com.lambferret.game.screen.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.SnowFight;
import com.lambferret.game.screen.AbstractScreen;
import com.lambferret.game.util.MainScreenInputProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainMenuScreen extends AbstractScreen {


    SnowFight game;
    MainScreenInputProcessor inputProcessor;

    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;


    private static final Logger logger = LogManager.getLogger(MainMenuScreen.class.getName());

    TextButton startButton;
    TextButton loadButton;
    TextButton optionButton;
    TextButton exitButton;
    BitmapFont font;
    TextButton.TextButtonStyle textButtonStyle;

    public MainMenuScreen(final SnowFight game) {
        this.game = game;
        WINDOW_WIDTH = game.getWidth();
        WINDOW_HEIGHT = game.getHeight();
        font = new BitmapFont();
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
//        background = new Texture("./asset1.jpeg");
    }

    @Override
    public void create() {
        logger.info("show | IN ACTION");
        startButton = new TextButton("start", textButtonStyle);
        loadButton = new TextButton("load", textButtonStyle);
        optionButton = new TextButton("option", textButtonStyle);
        exitButton = new TextButton("exit", textButtonStyle);

        startButton.setPosition(game.getWidth()/2, 50);
        loadButton.setPosition(game.getWidth()/2, 150);
        optionButton.setPosition(game.getWidth()/2, 200);
        exitButton.setPosition(game.getWidth()/2, 100);
    }


    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) switchFullScreen();


        update(delta);

    }

    protected void update(float delta) {
        addInputProcessor(new MainScreenInputProcessor());

    }

    // 전체화면 관련
    private void switchFullScreen() {
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);
        } else {
            Graphics.DisplayMode mode = Gdx.graphics.getDisplayMode();
            Gdx.graphics.setFullscreenMode(mode);
        }
    }

}
