package com.lambferret.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomInputProcessor implements InputProcessor {
    private static final Logger logger = LogManager.getLogger(CustomInputProcessor.class.getName());
    private static boolean isTouched = false;
    private static float x;
    private static float y;

    public static boolean isTouched() {
        return isTouched;
    }

    public static float getX() {
        return x;
    }

    public static float getY() {
        return y;
    }

    public static int getPressedKey() {
        return pressedKey;
    }

    public static int getPressedKeyUp() {
        return pressedKeyUp;
    }

    public static int getMouseButton() {
        return mouseButton;
    }

    public static int getMousePointer() {
        return mousePointer;
    }

    public static float getScrolledAmount() {
        return scrolledAmount;
    }

    public static boolean isESCPressed() {
        return isESCPressed;
    }

    private static int pressedKey;
    private static int pressedKeyUp;
    /**
     * 왼쪽 : 0, 오른쪽 : 1, 스크롤 : 2, ...마우스버튼
     * {@link Input.Buttons}
     */
    private static int mouseButton;
    /**
     * 사용할지도 모름
     */
    private static int mousePointer;
    private static float scrolledAmount;
    private static boolean isESCPressed = false;

    @Override
    public boolean keyDown(int keycode) {
        pressedKey = keycode;
        if (pressedKey == Input.Keys.ESCAPE) {
            isESCPressed = true;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        pressedKeyUp = keycode;
        if (pressedKey == pressedKeyUp) {
            pressedKey = Input.Keys.ANY_KEY;
        }

        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT) return false;

        isTouched = true;
        x = screenX;
        y = Gdx.graphics.getHeight() - screenY;

        mousePointer = pointer;
        mouseButton = button;

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isTouched = false;
        x = screenX;
        y = Gdx.graphics.getHeight() - screenY;
        mousePointer = pointer;
        mouseButton = button;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        isTouched = true;
        x = screenX;
        y = Gdx.graphics.getHeight() - screenY;
        mousePointer = pointer;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        x = screenX;
        y = Gdx.graphics.getHeight() - screenY;
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        scrolledAmount = amountX;
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }
}
