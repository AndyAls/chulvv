package com.qlckh.chunlvv.utils;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;

/**
 * @author Andy
 * @date 2018/10/8 11:46
 * Desc:
 */
public class HexUtils {


    /*
     * 16进制数字字符集
     */
    private static String hexString = "0123456789ABCDEF";

    /*
     * 将字符串编码成16进制数字,适用于所有字符（包括中文）
     */
    public static String encode(String str) {
        // 根据默认编码获取字节数组
        byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }

    /*
     * 将16进制数字解码成字符串,适用于所有字符（包括中文）
     */
    public static String decode(String bytes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(
                bytes.length() / 2);
        // 将每2位16进制整数组装成一个字节
        for (int i = 0; i < bytes.length(); i += 2) {
            baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
                    .indexOf(bytes.charAt(i + 1))));
        }
        return new String(baos.toByteArray());
    }

    public static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
            // if (n<b.length-1) hs=hs+":";
        }
        return hs.toUpperCase();
    }

    public static String byteHexStr(byte b) {
        String hs = "";
        String stmp = "";
        // /for (int n = 0; n < b.length; n++) {
        stmp = (Integer.toHexString(b & 0XFF));
        if (stmp.length() == 1) {
            hs = "0" + stmp;
        } else {
            hs = stmp;
        }
        // if (n<b.length-1) hs=hs+":";
        // }
        return hs.toUpperCase();
    }
    public static byte[] hexStringtoByteArray(String hexString) {
        if (TextUtils.isEmpty(hexString)) {
            throw new IllegalArgumentException("this hexString must not be empty");
        }

        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {
            //因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }
    public static String byte2HexStrByi(byte[] b, int start, int end) {
        String hs = "";
        String stmp = "";
        for (int n = start; n < end; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + decode("0" + stmp);
            } else {
                hs = hs + decode(stmp);
            }
            // if (n<b.length-1) hs=hs+":";
        }
        return hs.toUpperCase();
    }
}
