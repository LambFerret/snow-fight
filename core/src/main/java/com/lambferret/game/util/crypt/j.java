package com.lambferret.game.util.crypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;

public final class j {
    private static String a = "6E766865FEF58690102062C6A73EC6E4";
    private static String b = "B028DD0DFB89304F08B8203381FC2BA549D7BC3A58E6D87F6FF8A4FA25622FCD";
    private static String c = "B506CCCBE892554C59C37C4A36056FACB2DEC4537BCFBF2354FFD7B17FE2C34E";
    private static String d = "DF8E58C056165203731154443DD32543";
    private static String e;
    private static String f;
    private static String g;
    private static String h;

    public j() {
    }

    private static void a() {
        k var0;
        e = (var0 = new k("")).a(a);
        f = var0.a(b);
        g = var0.a(c);
        var0.a(c);
        h = var0.a(d);

    }

    static String a(String var0) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        a();
        SecureRandom var2 = new SecureRandom();
        byte[] var3 = new byte[20];
        var2.nextBytes(var3);
        byte[] var7 = var3;
        byte[] var5 = d.getBytes();
        SecretKeySpec var6 = new SecretKeySpec(var5, e);
        Cipher var8;
        (var8 = Cipher.getInstance(f)).init(1, var6);
        var5 = var8.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
        byte[] var4 = var8.doFinal(var0.getBytes(h));
        var3 = new byte[20 + var5.length + var4.length];
        System.arraycopy(var7, 0, var3, 0, 20);
        System.arraycopy(var5, 0, var3, 20, var5.length);
        System.arraycopy(var4, 0, var3, 20 + var5.length, var4.length);
        return Base64.getEncoder().encodeToString(var3);
    }

    static String b(String var0) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        a();
        Cipher var2 = Cipher.getInstance(f);
        ByteBuffer var5 = ByteBuffer.wrap(Base64.getDecoder().decode(var0));
        byte[] var3 = new byte[20];
        var5.get(var3, 0, 20);
        var3 = new byte[var2.getBlockSize()];
        var5.get(var3, 0, var3.length);
        byte[] var4 = new byte[var5.capacity() - 20 - var3.length];
        var5.get(var4);
        byte[] var6 = d.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec var7 = new SecretKeySpec(var6, e);
        var2.init(2, var7, new IvParameterSpec(var3));
        var6 = var2.doFinal(var4);
        return new String(var6);
    }
}
