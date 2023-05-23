package com.lambferret.game.component;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.StringBuilder;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.AssetFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TypewriterLabel extends Label {
    private static final Logger logger = LogManager.getLogger(TypewriterLabel.class.getName());

    private static final float KR_SPEED = 0.3F;
    private static final float JP_SPEED = 0.5F;
    private static final float EN_SPEED = 0.1F;
    private static final float RU_SPEED = 0.2F;
    private final StringBuilder fullText;
    private float timePerCharacter;
    private float elapsedTime;
    private int oldVisibleChars = 0;
    Sound sound;

    public TypewriterLabel(CharSequence text, Skin skin, float timePerCharacter) {
        super(text, skin);
        sound = AssetFinder.getSound("button_typewriter");
        this.fullText = new StringBuilder(text);
        this.timePerCharacter = timePerCharacter;
        this.elapsedTime = 0;

        setText(null);
    }

    public TypewriterLabel(CharSequence text, float timePerCharacter) {
        this(text, GlobalSettings.skin, timePerCharacter);
    }

    public TypewriterLabel(CharSequence text) {
        this(text, GlobalSettings.skin, 0.1F);
        float time = switch (GlobalSettings.language) {
            case KR -> KR_SPEED;
            case JP -> JP_SPEED;
            case EN -> EN_SPEED;
            case RU -> RU_SPEED;
        };
        this.setTime(time);
    }

    public void setTime(float timePerCharacter) {
        this.timePerCharacter = timePerCharacter;
    }

    public void startTyping() {
        elapsedTime = 0;
        setText(null);
    }

    public boolean isEnd() {
        return getText().length == fullText.length;
    }

    public void instantShow() {
        this.elapsedTime = fullText.length * this.timePerCharacter;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedTime += delta;
        int visibleChars = (int) (elapsedTime / timePerCharacter);
        if (visibleChars > fullText.length) {
            visibleChars = fullText.length;
        }
        if (visibleChars != oldVisibleChars) {
            sound.play();
        }
        oldVisibleChars = visibleChars;
        setText(fullText.substring(0, visibleChars));
    }

    @Override
    public void setText(CharSequence newText) {
//        sound.play(GlobalSettings.effectVolume);
        super.setText(newText);
    }
}
