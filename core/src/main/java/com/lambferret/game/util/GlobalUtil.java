package com.lambferret.game.util;

import com.lambferret.game.util.crypt.j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GlobalUtil {
    private static final Logger logger = LogManager.getLogger(GlobalUtil.class.getName());

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
