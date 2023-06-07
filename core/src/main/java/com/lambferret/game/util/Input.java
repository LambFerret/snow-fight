package com.lambferret.game.util;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Input {

    public static InputListener hover(Runnable runOnEnter, Runnable runOnExit) {
        return new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) {
                    runOnEnter.run();
                }
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    runOnExit.run();
                }
            }
        };
    }

    public static InputListener visibleWhenHover(Actor actor) {
        return hover(
            () -> actor.setVisible(true),
            () -> actor.setVisible(false)
        );
    }

    public static InputListener soundWhenClick(Sound sound) {
        return new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (pointer == -1) sound.play();
            }
        };
    }

    public static InputListener setScrollFocusWhenHover(Stage stage, Actor actor) {
        return new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                stage.setScrollFocus(actor);
                super.enter(event, x, y, pointer, fromActor);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (pointer == -1) {
                    stage.setScrollFocus(null);
                }
                super.exit(event, x, y, pointer, toActor);
            }
        };
    }

    public static ClickListener click(Runnable runnable) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                runnable.run();
            }
        };
    }


}
