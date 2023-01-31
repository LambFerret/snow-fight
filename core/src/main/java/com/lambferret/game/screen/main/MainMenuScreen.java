package com.lambferret.game.screen.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.lambferret.game.Hitbox;
import com.lambferret.game.SnowFight;
import com.lambferret.game.screen.AbstractScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class MainMenuScreen extends AbstractScreen {


    SnowFight game;

    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;


    private static final Logger logger = LogManager.getLogger(MainMenuScreen.class.getName());

    TextButton startButton;
    TextButton loadButton;
    TextButton optionButton;
    TextButton exitButton;
    BitmapFont font;
    TextButton.TextButtonStyle textButtonStyle;
    Hitbox startHitbox;
    Hitbox exitHitbox;
    SpriteBatch batch;
    ArrayList<MainMenuButton> buttons;

    public MainMenuScreen(final SnowFight game) {
        batch = new SpriteBatch();
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
        startButton = new TextButton("start", textButtonStyle);
        loadButton = new TextButton("load", textButtonStyle);
        optionButton = new TextButton("option", textButtonStyle);
        exitButton = new TextButton("exit", textButtonStyle);

        startButton.setPosition(400, 50);
        startButton.setSize(50, 50);
        startHitbox = new Hitbox(400, 50, 50 ,50);
        loadButton.setPosition(400, 150);
        optionButton.setPosition(400, 200);
        exitButton.setPosition(400, 100);
        exitButton.setSize(50, 50);
        exitHitbox = new Hitbox(400, 100, 50 ,50);


    }


    public void render(float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) switchFullScreen();
        batch.begin();
        startButton.draw(batch, 1);
        exitButton.draw(batch, 1);
        batch.end();


        update(delta);

    }

    protected void update(float delta) {
        startHitbox.update();
        exitHitbox.update();




        if (startHitbox.isHovered)
        logger.info("update | starthovered??" + startHitbox.isHovered);
        if (startHitbox.isClicked)
        logger.info("update | startHitted?" + startHitbox.isClicked);

        if (exitHitbox.isClicked) Gdx.app.exit();


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
