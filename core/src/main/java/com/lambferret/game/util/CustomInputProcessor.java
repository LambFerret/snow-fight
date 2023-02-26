package com.lambferret.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class CustomInputProcessor implements InputProcessor {
    private static final Logger logger = LogManager.getLogger(CustomInputProcessor.class.getName());
    private static final Marker command = MarkerManager.getMarker("command");
    private static boolean isMouseDown = false;
    private static boolean isGrabbed = false;
    private static boolean isMouseUp = false;

    private static int keyDown;
    private static int keyUp;
    private static float mouseDownX;
    private static float mouseDownY;
    private static float mouseUpX;
    private static float mouseUpY;
    private static float mouseLocationX;
    private static float mouseLocationY;
    private static float mouseDragStartX;
    private static float mouseDragStartY;
    private static float mouseDragEndX;
    private static float mouseDragEndY;
    private static float scrolledAmount;


    /**
     * 왼쪽 : 0, 오른쪽 : 1, 스크롤 : 2, ...마우스버튼
     * {@link Input.Buttons}
     */
    private static int mouseButton;
    /**
     * 사용할지도 모름 멀티터치시 1 단일터치시 0
     */
    private static int mousePointer;

    public static boolean isGrabbed() {
        return isGrabbed;
    }

    public static boolean isMouseDown() {
        if (isMouseDown) {
            isMouseDown = false;
            return true;
        }
        return false;
    }

    public static boolean isMouseUp() {
        if (isMouseUp) {
            isMouseUp = false;
            return true;
        }
        return false;
    }

    public static float getMouseDownX() {
        return mouseDownX;
    }

    public static float getMouseDownY() {
        return mouseDownY;
    }

    public static float getMouseUpX() {
        return mouseUpX;
    }

    public static float getMouseUpY() {
        return mouseUpY;
    }

    public static float getMouseLocationX() {
        return mouseLocationX;
    }

    public static float getMouseLocationY() {
        return mouseLocationY;
    }

    public static float getScrolledAmount() {
        float current = scrolledAmount;
        scrolledAmount = 0;
        return current;
    }

    public static boolean pressedKey(int keycode) {
        if (keyDown == keycode) {
            keyDown = Input.Keys.ANY_KEY;
            return true;
        }
        return false;
    }

    public static boolean pressedKeyUp(int keycode) {
        if (keyUp == keycode) {
            keyUp = Input.Keys.ANY_KEY;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        keyDown = keycode;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keyUp = keycode;
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!(button == Input.Buttons.LEFT || button == Input.Buttons.RIGHT)) return false;

        isGrabbed = true;
        isMouseDown = true;

        mouseDownX = screenX;
        mouseDownY = Gdx.graphics.getHeight() - screenY;
        mousePointer = pointer;
        mouseButton = button;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!(button == Input.Buttons.LEFT || button == Input.Buttons.RIGHT)) return false;

        isGrabbed = false;
        isMouseUp = true;

        mouseUpX = screenX;
        mouseUpY = Gdx.graphics.getHeight() - screenY;
        mousePointer = pointer;
        mouseButton = button;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isMouseDown) {
            mouseDragStartX = screenX;
            mouseDragStartY = Gdx.graphics.getHeight() - screenY;
        }

        if (isMouseUp) {
            mouseDragEndX = screenX;
            mouseDragEndY = Gdx.graphics.getHeight() - screenY;

        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseLocationX = screenX;
        mouseLocationY = Gdx.graphics.getHeight() - screenY;
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        scrolledAmount = amountY;
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }
}
