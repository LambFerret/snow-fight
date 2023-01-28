package com.lambferret.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;

public abstract class BaseScreen implements Screen {
    protected static final String TAG = "BaseScreen";

    @Override
    public void show() {
        Gdx.app.log(TAG, "show: CREATED");
        Gdx.app.setLogLevel(Application.LOG_NONE);

    }

    public void debug() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.F12)) {
            if (Gdx.app.getLogLevel() == Application.LOG_NONE) {
                Gdx.app.setLogLevel(Application.LOG_DEBUG);
            } else {
                Gdx.app.setLogLevel(Application.LOG_NONE);
            }
        }
    }

    @Override
    public void hide() {

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "dispose: ");
    }

}
