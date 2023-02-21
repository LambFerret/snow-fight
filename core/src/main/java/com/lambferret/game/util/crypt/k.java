package com.lambferret.game.util.crypt;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

final class k {
    private final int[][] a = new int[][]{{99, 124, 119, 123, 242, 107, 111, 197, 48, 1, 103, 43, 254, 215, 171, 118, 202, 130, 201, 125, 250, 89, 71, 240, 173, 212, 162, 175, 156, 164, 114, 192, 183, 253, 147, 38, 54, 63, 247, 204, 52, 165, 229, 241, 113, 216, 49, 21, 4, 199, 35, 195, 24, 150, 5, 154, 7, 18, 128, 226, 235, 39, 178, 117, 9, 131, 44, 26, 27, 110, 90, 160, 82, 59, 214, 179, 41, 227, 47, 132, 83, 209, 0, 237, 32, 252, 177, 91, 106, 203, 190, 57, 74, 76, 88, 207, 208, 239, 170, 251, 67, 77, 51, 133, 69, 249, 2, 127, 80, 60, 159, 168, 81, 163, 64, 143, 146, 157, 56, 245, 188, 182, 218, 33, 16, 255, 243, 210, 205, 12, 19, 236, 95, 151, 68, 23, 196, 167, 126, 61, 100, 93, 25, 115, 96, 129, 79, 220, 34, 42, 144, 136, 70, 238, 184, 20, 222, 94, 11, 219, 224, 50, 58, 10, 73, 6, 36, 92, 194, 211, 172, 98, 145, 149, 228, 121, 231, 200, 55, 109, 141, 213, 78, 169, 108, 86, 244, 234, 101, 122, 174, 8, 186, 120, 37, 46, 28, 166, 180, 198, 232, 221, 116, 31, 75, 189, 139, 138, 112, 62, 181, 102, 72, 3, 246, 14, 97, 53, 87, 185, 134, 193, 29, 158, 225, 248, 152, 17, 105, 217, 142, 148, 155, 30, 135, 233, 206, 85, 40, 223, 140, 161, 137, 13, 191, 230, 66, 104, 65, 153, 45, 15, 176, 84, 187, 22}, {226, 78, 84, 252, 148, 194, 74, 204, 98, 13, 106, 70, 60, 77, 139, 209, 94, 250, 100, 203, 180, 151, 190, 43, 188, 119, 46, 3, 211, 25, 89, 193, 29, 6, 65, 107, 85, 240, 153, 105, 234, 156, 24, 174, 99, 223, 231, 187, 0, 115, 102, 251, 150, 76, 133, 228, 58, 9, 69, 170, 15, 238, 16, 235, 45, 127, 244, 41, 172, 207, 173, 145, 141, 120, 200, 149, 249, 47, 206, 205, 8, 122, 136, 56, 92, 131, 42, 40, 71, 219, 184, 199, 147, 164, 18, 83, 255, 135, 14, 49, 54, 33, 88, 72, 1, 142, 55, 116, 50, 202, 233, 177, 183, 171, 12, 215, 196, 86, 66, 38, 7, 152, 96, 217, 182, 185, 17, 64, 236, 32, 140, 189, 160, 201, 132, 4, 73, 35, 241, 79, 80, 31, 19, 220, 216, 192, 158, 87, 227, 195, 123, 101, 59, 2, 143, 62, 232, 37, 146, 229, 21, 221, 253, 23, 169, 191, 212, 154, 126, 197, 57, 103, 254, 118, 157, 67, 167, 225, 208, 245, 104, 242, 27, 52, 112, 5, 163, 138, 213, 121, 134, 168, 48, 198, 81, 75, 30, 166, 39, 246, 53, 210, 110, 36, 22, 130, 95, 218, 230, 117, 162, 239, 44, 178, 28, 159, 93, 111, 128, 10, 114, 68, 155, 108, 144, 11, 91, 51, 125, 90, 82, 243, 97, 161, 247, 176, 214, 63, 124, 109, 237, 20, 224, 165, 61, 34, 179, 248, 137, 222, 113, 26, 175, 186, 181, 129}, {82, 9, 106, 213, 48, 54, 165, 56, 191, 64, 163, 158, 129, 243, 215, 251, 124, 227, 57, 130, 155, 47, 255, 135, 52, 142, 67, 68, 196, 222, 233, 203, 84, 123, 148, 50, 166, 194, 35, 61, 238, 76, 149, 11, 66, 250, 195, 78, 8, 46, 161, 102, 40, 217, 36, 178, 118, 91, 162, 73, 109, 139, 209, 37, 114, 248, 246, 100, 134, 104, 152, 22, 212, 164, 92, 204, 93, 101, 182, 146, 108, 112, 72, 80, 253, 237, 185, 218, 94, 21, 70, 87, 167, 141, 157, 132, 144, 216, 171, 0, 140, 188, 211, 10, 247, 228, 88, 5, 184, 179, 69, 6, 208, 44, 30, 143, 202, 63, 15, 2, 193, 175, 189, 3, 1, 19, 138, 107, 58, 145, 17, 65, 79, 103, 220, 234, 151, 242, 207, 206, 240, 180, 230, 115, 150, 172, 116, 34, 231, 173, 53, 133, 226, 249, 55, 232, 28, 117, 223, 110, 71, 241, 26, 113, 29, 41, 197, 137, 111, 183, 98, 14, 170, 24, 190, 27, 252, 86, 62, 75, 198, 210, 121, 32, 154, 219, 192, 254, 120, 205, 90, 244, 31, 221, 168, 51, 136, 7, 199, 49, 177, 18, 16, 89, 39, 128, 236, 95, 96, 81, 127, 169, 25, 181, 74, 13, 45, 229, 122, 159, 147, 201, 156, 239, 160, 224, 59, 77, 174, 42, 245, 176, 200, 235, 187, 60, 131, 83, 153, 97, 23, 43, 4, 126, 186, 119, 214, 38, 225, 105, 20, 99, 85, 33, 12, 125}, {48, 104, 153, 27, 135, 185, 33, 120, 80, 57, 219, 225, 114, 9, 98, 60, 62, 126, 94, 142, 241, 160, 204, 163, 42, 29, 251, 182, 214, 32, 196, 141, 129, 101, 245, 137, 203, 157, 119, 198, 87, 67, 86, 23, 212, 64, 26, 77, 192, 99, 108, 227, 183, 200, 100, 106, 83, 170, 56, 152, 12, 244, 155, 237, 127, 34, 118, 175, 221, 58, 11, 88, 103, 136, 6, 195, 53, 13, 1, 139, 140, 194, 230, 95, 2, 36, 117, 147, 102, 30, 229, 226, 84, 216, 16, 206, 122, 232, 8, 44, 18, 151, 50, 171, 180, 39, 10, 35, 223, 239, 202, 217, 184, 250, 220, 49, 107, 209, 173, 25, 73, 189, 81, 150, 238, 228, 168, 65, 218, 255, 205, 85, 134, 54, 190, 97, 82, 248, 187, 14, 130, 72, 105, 154, 224, 71, 158, 92, 4, 75, 52, 21, 121, 38, 167, 222, 41, 174, 146, 215, 132, 233, 210, 186, 93, 243, 197, 176, 191, 164, 59, 113, 68, 70, 43, 252, 235, 111, 213, 246, 20, 254, 124, 112, 90, 125, 253, 47, 24, 131, 22, 165, 145, 31, 5, 149, 116, 169, 193, 91, 74, 133, 109, 19, 7, 79, 78, 69, 178, 15, 201, 28, 166, 188, 236, 115, 144, 123, 207, 89, 143, 161, 249, 45, 242, 177, 0, 148, 55, 159, 208, 46, 156, 110, 40, 63, 128, 240, 61, 211, 37, 138, 181, 231, 66, 179, 199, 234, 247, 76, 17, 51, 3, 162, 172, 96}};
    private final int[][] b = new int[][]{{81, 124, 193, 183, 39, 34, 10, 148, 254, 19, 171, 232, 250, 154, 110, 224}, {109, 177, 74, 204, 158, 33, 200, 32, 255, 40, 177, 213, 239, 93, 226, 176}, {219, 146, 55, 29, 33, 38, 233, 112, 3, 36, 151, 117, 4, 232, 201, 14}};
    private final int[] c = new int[32];

    public k(String var1) {
        this.c(var1);
    }

    private static void a(int[] var0, int[] var1) {
        int var2 = var0[3] ^ var0[4] ^ var0[9] ^ var0[14];
        var1[0] = var0[6] ^ var0[8] ^ var0[13] ^ var2;
        var1[5] = var0[1] ^ var0[10] ^ var0[15] ^ var2;
        var1[11] = var0[2] ^ var0[7] ^ var0[12] ^ var2;
        var1[14] = var0[0] ^ var0[5] ^ var0[11] ^ var2;
        var2 = var0[2] ^ var0[5] ^ var0[8] ^ var0[15];
        var1[1] = var0[7] ^ var0[9] ^ var0[12] ^ var2;
        var1[4] = var0[0] ^ var0[11] ^ var0[14] ^ var2;
        var1[10] = var0[3] ^ var0[6] ^ var0[13] ^ var2;
        var1[15] = var0[1] ^ var0[4] ^ var0[10] ^ var2;
        var2 = var0[1] ^ var0[6] ^ var0[11] ^ var0[12];
        var1[2] = var0[4] ^ var0[10] ^ var0[15] ^ var2;
        var1[7] = var0[3] ^ var0[8] ^ var0[13] ^ var2;
        var1[9] = var0[0] ^ var0[5] ^ var0[14] ^ var2;
        var1[12] = var0[2] ^ var0[7] ^ var0[9] ^ var2;
        var2 = var0[0] ^ var0[7] ^ var0[10] ^ var0[13];
        var1[3] = var0[5] ^ var0[11] ^ var0[14] ^ var2;
        var1[6] = var0[2] ^ var0[9] ^ var0[12] ^ var2;
        var1[8] = var0[1] ^ var0[4] ^ var0[15] ^ var2;
        var1[13] = var0[3] ^ var0[6] ^ var0[8] ^ var2;
    }

    private static void a(int[] var0, int var1, int[] var2, int var3) {
        int var4 = var0[var1 + 3] ^ var0[var1 + 4] ^ var0[var1 + 9] ^ var0[var1 + 14];
        var2[var3] = var0[var1 + 6] ^ var0[var1 + 8] ^ var0[var1 + 13] ^ var4;
        var2[var3 + 5] = var0[var1 + 1] ^ var0[var1 + 10] ^ var0[var1 + 15] ^ var4;
        var2[var3 + 11] = var0[var1 + 2] ^ var0[var1 + 7] ^ var0[var1 + 12] ^ var4;
        var2[var3 + 14] = var0[var1] ^ var0[var1 + 5] ^ var0[var1 + 11] ^ var4;
        var4 = var0[var1 + 2] ^ var0[var1 + 5] ^ var0[var1 + 8] ^ var0[var1 + 15];
        var2[var3 + 1] = var0[var1 + 7] ^ var0[var1 + 9] ^ var0[var1 + 12] ^ var4;
        var2[var3 + 4] = var0[var1] ^ var0[var1 + 11] ^ var0[var1 + 14] ^ var4;
        var2[var3 + 10] = var0[var1 + 3] ^ var0[var1 + 6] ^ var0[var1 + 13] ^ var4;
        var2[var3 + 15] = var0[var1 + 1] ^ var0[var1 + 4] ^ var0[var1 + 10] ^ var4;
        var4 = var0[var1 + 1] ^ var0[var1 + 6] ^ var0[var1 + 11] ^ var0[var1 + 12];
        var2[var3 + 2] = var0[var1 + 4] ^ var0[var1 + 10] ^ var0[var1 + 15] ^ var4;
        var2[var3 + 7] = var0[var1 + 3] ^ var0[var1 + 8] ^ var0[var1 + 13] ^ var4;
        var2[var3 + 9] = var0[var1] ^ var0[var1 + 5] ^ var0[var1 + 14] ^ var4;
        var2[var3 + 12] = var0[var1 + 2] ^ var0[var1 + 7] ^ var0[var1 + 9] ^ var4;
        var4 = var0[var1] ^ var0[var1 + 7] ^ var0[var1 + 10] ^ var0[var1 + 13];
        var2[var3 + 3] = var0[var1 + 5] ^ var0[var1 + 11] ^ var0[var1 + 14] ^ var4;
        var2[var3 + 6] = var0[var1 + 2] ^ var0[var1 + 9] ^ var0[var1 + 12] ^ var4;
        var2[var3 + 8] = var0[var1 + 1] ^ var0[var1 + 4] ^ var0[var1 + 15] ^ var4;
        var2[var3 + 13] = var0[var1 + 3] ^ var0[var1 + 6] ^ var0[var1 + 8] ^ var4;
    }

    private static void b(int[] var0, int var1, int[] var2, int var3) {
        int var5 = var1 / 8;
        var1 %= 8;

        for (int var4 = 0; var4 < 16; ++var4) {
            var2[var3 + (var5 + var4) % 16] ^= (byte) (var0[var4] >> var1);
            if (var1 != 0) {
                var2[var3 + (var5 + var4 + 1) % 16] ^= (byte) (var0[var4] << 8 - var1);
            }
        }

    }

    private static void a(byte[] var0, int[] var1) {
        for (int var2 = 0; var2 < var0.length; ++var2) {
            if (var0[var2] < 0) {
                var1[var2] = var0[var2] + 256;
            } else {
                var1[var2] = var0[var2];
            }
        }

    }

    private static void a(int[] var0) {
        for (int var1 = 0; var1 < 272; ++var1) {
            if (var0[var1] < 0) {
                var0[var1] += 256;
            } else {
                var0[var1] = var0[var1];
            }
        }

    }

    private static void a(int[] var0, byte[] var1) {
        for (int var2 = 0; var2 < 16; ++var2) {
            var1[var2] = (byte) var0[var2];
        }

    }

    private int a(int[] var1, int[] var2, int var3) {
        int var4 = (var3 + 256) / 32;
        int[] var6 = new int[16];
        int[] var7 = new int[16];
        int[] var8 = new int[16];
        int[] var9 = new int[16];
        int var5 = (var3 - 128) / 64;

        for (var3 = 0; var3 < 16; ++var3) {
            var6[var3] = this.a[var3 % 4][this.b[var5][var3] ^ var1[var3]];
        }

        a(var6, var7);
        if (var4 == 14) {
            for (var3 = 0; var3 < 8; ++var3) {
                var7[var3] ^= var1[var3 + 16];
            }
        } else if (var4 == 16) {
            for (var3 = 0; var3 < 16; ++var3) {
                var7[var3] ^= var1[var3 + 16];
            }
        }

        var5 = var5 == 2 ? 0 : var5 + 1;

        for (var3 = 0; var3 < 16; ++var3) {
            var6[var3] = this.a[(var3 + 2) % 4][this.b[var5][var3] ^ var7[var3]];
        }

        a(var6, var8);

        for (var3 = 0; var3 < 16; ++var3) {
            var8[var3] ^= var1[var3];
        }

        var5 = var5 == 2 ? 0 : var5 + 1;

        for (var3 = 0; var3 < 16; ++var3) {
            var6[var3] = this.a[var3 % 4][this.b[var5][var3] ^ var8[var3]];
        }

        a(var6, var9);

        for (var3 = 0; var3 < 16; ++var3) {
            var9[var3] ^= var7[var3];
        }

        for (var3 = 0; var3 < 16 * (var4 + 1); ++var3) {
            var2[var3] = 0;
        }

        b(var1, 0, var2, 0);
        b(var7, 19, var2, 0);
        b(var7, 0, var2, 16);
        b(var8, 19, var2, 16);
        b(var8, 0, var2, 32);
        b(var9, 19, var2, 32);
        b(var9, 0, var2, 48);
        b(var1, 19, var2, 48);
        b(var1, 0, var2, 64);
        b(var7, 31, var2, 64);
        b(var7, 0, var2, 80);
        b(var8, 31, var2, 80);
        b(var8, 0, var2, 96);
        b(var9, 31, var2, 96);
        b(var9, 0, var2, 112);
        b(var1, 31, var2, 112);
        b(var1, 0, var2, 128);
        b(var7, 67, var2, 128);
        b(var7, 0, var2, 144);
        b(var8, 67, var2, 144);
        b(var8, 0, var2, 160);
        b(var9, 67, var2, 160);
        b(var9, 0, var2, 176);
        b(var1, 67, var2, 176);
        b(var1, 0, var2, 192);
        b(var7, 97, var2, 192);
        if (var4 > 12) {
            b(var7, 0, var2, 208);
            b(var8, 97, var2, 208);
            b(var8, 0, var2, 224);
            b(var9, 97, var2, 224);
        }

        if (var4 > 14) {
            b(var9, 0, var2, 240);
            b(var1, 97, var2, 240);
            b(var1, 0, var2, 256);
            b(var7, 109, var2, 256);
        }

        return var4;
    }

    private int b(int[] var1, int[] var2, int var3) {
        int[] var5 = new int[16];
        int var4 = this.a(var1, var2, 256);

        for (var3 = 0; var3 < 16; ++var3) {
            var5[var3] = var2[var3];
            var2[var3] = var2[var4 * 16 + var3];
            var2[var4 * 16 + var3] = var5[var3];
        }

        for (int var6 = 1; var6 <= var4 / 2; ++var6) {
            a(var2, var6 << 4, var5, 0);
            a(var2, var4 - var6 << 4, var2, var6 << 4);

            for (var3 = 0; var3 < 16; ++var3) {
                var2[(var4 - var6 << 4) + var3] = var5[var3];
            }
        }

        return var4;
    }

    private void a(int[] var1, int var2, int[] var3, int[] var4) {
        int var6 = 0;
        int[] var7 = new int[16];

        int var5;
        for (var5 = 0; var5 < 16; ++var5) {
            var4[var5] = var1[var5];
        }

        for (int var8 = 0; var8 < var2 / 2; ++var8) {
            for (var5 = 0; var5 < 16; ++var5) {
                var7[var5] = this.a[var5 % 4][var3[var6 + var5] ^ var4[var5]];
            }

            a(var7, var4);
            var6 += 16;

            for (var5 = 0; var5 < 16; ++var5) {
                var7[var5] = this.a[(var5 + 2) % 4][var3[var6 + var5] ^ var4[var5]];
            }

            a(var7, var4);
            var6 += 16;
        }

        a(var4, var7);

        for (var5 = 0; var5 < 16; ++var5) {
            var4[var5] = (byte) (var3[var5] ^ var7[var5]);
        }

    }

    private void a(int[] var1, int var2, int var3, int[] var4, int[] var5) {
        int var7 = 0;
        int[] var8 = new int[16];

        int var6;
        for (var6 = 0; var6 < 16; ++var6) {
            var5[var6] = var1[var2 + var6];
        }

        for (int var9 = 0; var9 < var3 / 2; ++var9) {
            for (var6 = 0; var6 < 16; ++var6) {
                var8[var6] = this.a[var6 % 4][var4[var7 + var6] ^ var5[var6]];
            }

            a(var8, var5);
            var7 += 16;

            for (var6 = 0; var6 < 16; ++var6) {
                var8[var6] = this.a[(var6 + 2) % 4][var4[var7 + var6] ^ var5[var6]];
            }

            a(var8, var5);
            var7 += 16;
        }

        a(var5, var8);

        for (var6 = 0; var6 < 16; ++var6) {
            var5[var6] = var4[var7 + var6] ^ var8[var6];
        }

    }

    private static String a(byte[] var0) {
        StringBuilder var1 = new StringBuilder();

        for (int var2 = 0; var2 < 16; ++var2) {
            var1.append(String.format("%02X", var0[var2]));
        }

        return var1.toString();
    }

    private static byte[] b(String var0) {
        if (var0 != null && var0.length() != 0) {
            byte[] var1 = new byte[var0.length() / 2];

            for (int var2 = 0; var2 < var1.length; ++var2) {
                var1[var2] = (byte) Integer.parseInt(var0.substring(2 * var2, 2 * var2 + 2), 16);
            }

            return var1;
        } else {
            return null;
        }
    }

    private Boolean c(String var1) {
        if (var1.length() > 32) {
            return Boolean.FALSE;
        } else {
            try {
                a(var1.getBytes("EUC-KR"), this.c);
            } catch (UnsupportedEncodingException var3) {
                var3.printStackTrace();
            }

            return Boolean.TRUE;
        }
    }

    private String d(String var1) {
        String var2 = "";
        int[] var3 = new int[272];
        int[] var4 = new int[16];
        int[] var5 = new int[256];
        int var6 = this.a(this.c, var3, 256);
        a(var3);
        int var7 = 0;

        try {
            byte[] var10;
            int[] var8 = new int[(var10 = var1.getBytes("EUC-KR")).length];
            a(var10, var8);
            System.arraycopy(var8, 0, var5, 0, var8.length);
            var7 = (int) Math.ceil((double) var8.length / 16.0);
        } catch (UnsupportedEncodingException var9) {
            var9.printStackTrace();
        }

        for (int var11 = 0; var11 < var7; ++var11) {
            Arrays.fill(var4, 0);
            this.a(var5, var11 << 4, var6, var3, var4);
            byte[] var12 = new byte[16];
            a(var4, var12);
            var2 = var2 + a(var12);
        }

        return var2;
    }

    public String a(String var1) {
        int[] var2 = new int[272];
        int[] var3 = new int[16];
        byte[] var4 = new byte[16];
        String var5 = "";
        String var6 = var1;
        int var8;
        byte[] var10000;
        if (var1 != null && var1.length() != 0) {
            byte[] var9 = new byte[var1.length() / 2];

            for (var8 = 0; var8 < var9.length; ++var8) {
                var9[var8] = (byte) Integer.parseInt(var6.substring(2 * var8, 2 * var8 + 2), 16);
            }

            var10000 = var9;
        } else {
            var10000 = null;
        }

        byte[] var14 = var10000;
        int[] var7;
        Arrays.fill(var7 = new int[var10000.length], 0);
        a(var14, var7);
        boolean var16 = true;
        int[] var18 = var2;
        int[] var17 = this.c;
        int[] var11 = new int[16];
        int var10 = this.a(var17, var2, 256);

        for (var8 = 0; var8 < 16; ++var8) {
            var11[var8] = var18[var8];
            var18[var8] = var18[var10 * 16 + var8];
            var18[var10 * 16 + var8] = var11[var8];
        }

        int var15;
        for (var15 = 1; var15 <= var10 / 2; ++var15) {
            a(var18, var15 << 4, var11, 0);
            a(var18, var10 - var15 << 4, var18, var15 << 4);

            for (var8 = 0; var8 < 16; ++var8) {
                var18[(var10 - var15 << 4) + var8] = var11[var8];
            }
        }

        var15 = var10;
        a(var2);
        int var13;
        ByteBuffer var20 = ByteBuffer.allocate((var13 = (int) Math.ceil((double) var1.length() / 32.0)) << 4);

        for (int var19 = 0; var19 < var13; ++var19) {
            Arrays.fill(var3, 0);
            Arrays.fill(var4, (byte) 0);
            this.a(var7, var19 << 4, var15, var2, var3);
            a(var3, var4);
            var20.put(var4);
        }

        try {
            var5 = (new String(var20.array(), "EUC-KR")).replace("\u0000", "");
        } catch (UnsupportedEncodingException var12) {
            var12.printStackTrace();
        }

        return var5;
    }
}
