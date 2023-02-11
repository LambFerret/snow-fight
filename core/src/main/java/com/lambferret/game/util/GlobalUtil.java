package com.lambferret.game.util;

import com.badlogic.gdx.math.Interpolation;
import com.lambferret.game.setting.GlobalSettings;

public class GlobalUtil {
    public static float lerp(float from, float to, float speed, float delta) {
        if (from != to) {
            from = Interpolation.circleOut.apply(from, to, delta * speed);
            if (Math.abs(from - to) < GlobalSettings.scale) {
                from = to;
            }
        }
        return from;
    }

    public static String encode() {
        return "";
    }

    public static String decode() {
        return "";
    }
}
