package com.lambferret.game.util;

import com.badlogic.gdx.math.Interpolation;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.crypt.j;

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

    public static String encrypt(String var0) {
        String var2 = null;

        try {
            var2 = j.a(var0);
        } catch (Exception var3) {
            var3.printStackTrace();
            System.out.println("$ Encrypt Error Occurred!");
        }

        return var2;
    }

    public static String decrypt(String var0) {
        String var2 = null;

        try {
            var2 = j.b(var0);
        } catch (Exception var3) {
            var3.printStackTrace();
            System.out.println("$ Decrypt Error Occurred");
        }

        return var2;
    }
}
