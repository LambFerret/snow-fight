package com.lambferret.game.util;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainScreenInputProcessor implements InputProcessor {
    TextButton textButton;

    private static final Logger logger = LogManager.getLogger(MainScreenInputProcessor.class.getName());
    public MainScreenInputProcessor(TextButton textButton) {
        this.textButton = textButton;
    }

    @Override
    public boolean keyDown(int keycode) {
        logger.info("keyDown | " + keycode + " key pressed");
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 screenPos = new Vector2(screenX, screenY);
        Vector2 buttonPos = textButton.localToStageCoordinates(new Vector2(0, 0));
        Rectangle buttonBounds = new Rectangle(buttonPos.x, buttonPos.y, textButton.getWidth(), textButton.getHeight());
        if (buttonBounds.contains(screenPos)) {
            // Perform desired action
            logger.info("touchDown | !!!!");
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
