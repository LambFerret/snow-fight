package com.lambferret.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.lambferret.game.setting.GlobalSettings;
import com.lambferret.game.util.crypt.j;

public class GlobalUtil {

    /**
     * lerp
     *
     * @param from  움직이는 값을 넣어줘야함
     * @param to    목표치, 고정값
     * @param speed
     * @return from 값
     */
    public static float lerp(float from, float to, float speed) {
        if (from != to) {
            from = Interpolation.circleOut.apply(from, to, Gdx.graphics.getDeltaTime() * speed);
            if (Math.abs(from - to) < GlobalSettings.scale) {
                from = to;
            }
        }
        return from;
    }

    public static float smallLerp(float from, float to, float speed) {
        float divide = 10.0F;
        from *= divide;
        to *= divide;
        if (from != to) {
            from = Interpolation.circleOut.apply(from, to, Gdx.graphics.getDeltaTime() * speed);
            if (Math.abs(from - to) < GlobalSettings.scale) {
                from = to;
            }
        }
        return Math.round(from * divide) / (divide * divide);

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
