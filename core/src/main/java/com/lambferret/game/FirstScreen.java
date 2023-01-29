package com.lambferret.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FirstScreen extends AbstractScreen implements InputProcessor {

    SnowFight game;


    private static final Logger logger = LogManager.getLogger(FirstScreen.class.getName());

    private static final int BUTTON_WIDTH = 330;
    private static final int BUTTON_HEIGHT = 660;

    Texture newGameButtonActive;
    Texture loadGameButtonActive;
    Texture newGameButtonInactive;
    Texture loadGameButtonInactive;
    Texture background;

    public FirstScreen(final SnowFight game) {
        this.game = game;
        background = new Texture("./asset1.jpeg");
        newGameButtonActive = new Texture("./sprite/start.png");
        loadGameButtonActive = new Texture("./sprite/load.png");
        newGameButtonInactive = new Texture("./sprite/load.png");
        loadGameButtonInactive = new Texture("./sprite/start.png");
    }


    @Override
    public void render(float delta) {


        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) switchFullScreen();

        game.batch.begin();

        game.batch.draw(background, 0, 0);
        game.batch.draw(newGameButtonActive, 5, game.width/2);
        game.batch.draw(loadGameButtonActive, 5, game.height/2 + 100);

        game.batch.end();
    }

    // 전체화면 관련
    private void switchFullScreen() {
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setWindowedMode(game.width, game.height);
        } else {
            Graphics.DisplayMode mode = Gdx.graphics.getDisplayMode();
            Gdx.graphics.setFullscreenMode(mode);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}
