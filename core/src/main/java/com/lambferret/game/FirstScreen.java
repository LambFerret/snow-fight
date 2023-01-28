package com.lambferret.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;

public class FirstScreen extends BaseScreen implements  InputProcessor {

    final SnowFight game;

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
        debug();

        game.batch.begin();

        game.batch.draw(background, 0, 0);
        game.batch.draw(newGameButtonActive, 5, 50);
        game.batch.draw(loadGameButtonActive, 5, 100);

        game.batch.end();
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
