package com.dazhi.libroot.util;

import android.util.Base64;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 功能描述：加密解密工具
 * <p>
 * 作者：WangZezhi
 * 邮箱：wangzezhi528@163.com
 * 创建日期：17-11-22 上午11:15
 * 修改日期：17-11-22 上午11:15
 */
@SuppressWarnings("unused")
public class UtCode {
    //秘钥
    private static final String SECRET_KEY = "&*u!^%$#";
    //初始化向量，随意填充,单只能是8个字节
    private static byte[] iv = {'a', 'n', 'j', 'd', 'p', 13, 22, '*'};


    /**
     * =======================================
     * 作者：WangZezhi  (2018/6/8  17:23)
     * 功能： 获得加密的字符串
     * 描述：
     * =======================================
     */
    public static String getEncryptStr(String plaintext) {
        return getEncryptStr(plaintext, SECRET_KEY);
    }

    public static String getEncryptStr(String plaintext, String strKey) {
        try {
            // 实例化IvParameterSpec对象，使用指定的初始化向量
            IvParameterSpec spec = new IvParameterSpec(iv);
            // 实例化SecretKeySpec类,根据字节数组来构造SecretKeySpec
            SecretKeySpec key = new SecretKeySpec(strKey.getBytes(), "DES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            // 用密码初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);
            // 执行加密操作
            byte[] encryptData = cipher.doFinal(plaintext.getBytes());
            // 返回加密后的数据
            return base64Encode(encryptData);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * =======================================
     * 作者：WangZezhi  (2018/6/8  17:23)
     * 功能：获得解密的字符串
     * 描述：
     * =======================================
     */
    public static String getDecryptStr(String ciphertext) {
        return getDecryptStr(ciphertext, SECRET_KEY);
    }

    public static String getDecryptStr(String decryptString, String decryptKey) {
        try {
            // 先使用Base64解密
            byte[] base64byte = base64Decode(decryptString);
            // 实例化IvParameterSpec对象，使用指定的初始化向量
            IvParameterSpec spec = new IvParameterSpec(iv);
            // 实例化SecretKeySpec类,根据字节数组来构造SecretKeySpec
            SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            // 用密码初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            // 获取解密后的数据
            byte[] decryptedData = cipher.doFinal(base64byte);
            // 将解密后数据转换为字符串输出
            return new String(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    /**
     * =======================================
     * 作者：WangZezhi  (2019-05-15  16:08)
     * 功能：Base64 编码/解码
     * 描述：
     * =======================================
     */
    // 编码
    public static String base64Encode(final String input) {
        return base64Encode(input.getBytes());
    }

    public static String base64Encode(final byte[] input) {
        if (input == null || input.length == 0) return "";
        return Base64.encodeToString(input, Base64.NO_WRAP);
    }

    // 解码
    public static byte[] base64Decode(final String input) {
        if (input == null || input.length() == 0) return new byte[0];
        return Base64.decode(input, Base64.NO_WRAP);
    }

    public static byte[] base64Decode(final byte[] input) {
        if (input == null || input.length == 0) return new byte[0];
        return Base64.decode(input, Base64.NO_WRAP);
    }


    /**
     * =======================================
     * 作者：WangZezhi  (2019-05-15  16:23)
     * 功能：MD5
     * 描述：
     * =======================================
     */
    public static String md5(String strText) {
        return md5(strText, "I1j0l9n4DF3Aac|spP03DGqd4msbb");
    }

    @SuppressWarnings("CharsetObjectCanBeUsed")
    public static String md5(String strText, String strSecret) {
        try {
            strText = strText + strSecret;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(strText.getBytes("UTF8"));
            byte[] s = m.digest();
            StringBuilder result = new StringBuilder();
            for (byte b : s) {
                result.append(Integer.toHexString((0x000000FF & b) | 0xFFFFFF00).substring(6));
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /*=======================================
     * 作者：WangZezhi  (2020/8/6  14:24)
     * 功能：bcd与10进制互转
     * 描述：BIN、OCT、HEX、DEC(decimal)分别代表二、八、十六、十进制
     *=======================================*/
    public static byte[] decToBcd(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        str = str.replace(" ", "")
                .replace(",", "");
        int len = str.length();
        if (len % 2 == 1) {
            // 奇数需补零
            ++len;
            str = "0" + str;
        }
        len /= 2;
        byte[] result = new byte[len];
        String temp;
        for (int i = 0, j = 0; j < len; i += 2, j++) {
            temp = str.substring(i, i + 2);
            try {
                // 10进制数直接拿来当16进制数
                result[j] = Integer.valueOf(temp, 16).byteValue();
            } catch (Exception ignored) {
                return null;
            }
        }
        return result;
    }

    // 十进制转为BCD (注意：10进制最大为99，不可能出现负数)
    public static byte decToBcd(byte bt) {
        return (byte) (bt / 10 * 6 + bt);
    }

    // BCD转为十进制 (注意：BCD最大为0x99，可能为负，需&0xFF转int)
    public static byte bcdToDec(byte bt) {
        return (byte) (bt & 0xFF - (bt >>> 4) * 6);
    }

    public static String bcdToDec(byte[] btArr) {
        return bcdToDec(btArr, "");
    }

    public static String bcdToDec(byte[] btArr, String separator) {
        if (btArr == null || btArr.length == 0) {
            return null;
        }
        final int len = btArr.length;
        StringBuilder sb = new StringBuilder(len * 2);
        separator = separator == null ? "" : separator;
        for (byte bt : btArr) {
            // BCD最大为0x99，可能为负，需&转int，拆分后最大为9，因此可直接追加
            sb.append((byte) ((bt & 0xF0) >>> 4));
            sb.append((byte) (bt & 0x0F));
            sb.append(separator);
        }
//        return sb.toString().substring(0, 1)
//                .equalsIgnoreCase("0") ? sb.toString()
//                .substring(1) : sb.toString();
        return sb.toString();
    }

    /*=======================================
     * 作者：WangZezhi  (2020/8/6  15:26)
     * 功能：时间戳与BCD互转
     * 描述：
     *=======================================*/
    public static byte[] timestampToBcd(long timestamp) {
        return timestampToBcd(timestamp, "yyMMddHHmmss");
    }

    public static byte[] timestampToBcd(long timestamp, String format) {
        String dateTime;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
            dateTime = sdf.format(timestamp);
        } catch (Exception ignored) {
            return null;
        }
        return decToBcd(dateTime);
    }

    public static long bcdToTimestamp(byte[] bcd) {
        return bcdToTimestamp(bcd, "yyMMddHHmmss");
    }

    @SuppressWarnings("ConstantConditions")
    public static long bcdToTimestamp(byte[] bcd, String format) {
        String dec = bcdToDec(bcd, null);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
            return sdf.parse(dec).getTime();
        } catch (Exception ignored) {
            return 0;
        }
    }

    /*=======================================
     * 作者：WangZezhi  (2020/8/6  14:49)
     * 功能：10进制与16进制互转
     * 描述：
     *=======================================*/
    public static String decToHex(byte[] btArr, String separator) {
        if (btArr == null || btArr.length == 0) {
            return null;
        }
        return decToHex(btArr, 0, btArr.length, separator);
    }

    public static String decToHex(byte[] btArr, int offset, int len, String separator) {
        if (btArr == null || offset < 0 || len < 1 || btArr.length < offset + len) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        separator = separator == null ? "" : separator;
        for (int i = 0; i < len; i++) {
            sb.append(String.format("%02x", btArr[offset + i]));
            sb.append(separator);
        }
        return sb.toString().toUpperCase();
    }

    public static byte[] hexToDec(String hexStr) {
        if (hexStr == null || hexStr.isEmpty()) {
            return null;
        }
        hexStr = hexStr.replace(" ", "").replace(",", "");
        if (hexStr.isEmpty()) {
            return null;
        }
        int len = hexStr.length();
        if ((len & 0x1) == 1) { //是奇数
            ++len;
            hexStr = "0" + hexStr; // 是奇数，首位加个0
        }
        byte[] result = new byte[len / 2]; //两个数作为一组，放到一个字节里
        for (int i = 0, j = 0; i < len; i += 2, j++) {
            try {
                result[j] = (byte) Integer.parseInt(hexStr.substring(i, i + 2), 16);
            } catch (Exception ignored) {
                return null;
            }
        }
        return result;
    }

}


/*
 * ===============================================
 * 作者：WangZezhi  (17-5-26  上午11:36)
 * 功能：Base64编码/解码部分
 * 详情：合法的（legal）
 * =======================================================================================
 * 作者：WangZezhi  (2018/6/8  17:22)
 * 功能：base64Decode
 * 描述：
 * =======================================
 */
//    private static final char[] CARR_LEGAL_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
//    /**
//     * data[]进行编码
//     * @param data
//     * @return
//     */
//    public static String base64Encode(byte[] data) {
//        if(data==null){
//            return "";
//        }
//        int start = 0;
//        int len = data.length;
//        StringBuffer buf = new StringBuffer(data.length * 3 / 2);
//        int end = len - 3;
//        int i = start;
//        int n = 0;
//        while (i <= end) {
//            int d = ((((int) data[i]) & 0x0ff) << 16)
//                    | ((((int) data[i + 1]) & 0x0ff) << 8)
//                    | (((int) data[i + 2]) & 0x0ff);
//            buf.append(CARR_LEGAL_CHAR[(d >> 18) & 63]);
//            buf.append(CARR_LEGAL_CHAR[(d >> 12) & 63]);
//            buf.append(CARR_LEGAL_CHAR[(d >> 6) & 63]);
//            buf.append(CARR_LEGAL_CHAR[d & 63]);
//            i += 3;
//            if (n++ >= 14) {
//                n = 0;
//                buf.append(" ");
//            }
//        }
//        if (i == start + len - 2) {
//            int d = ((((int) data[i]) & 0x0ff) << 16)
//                    | ((((int) data[i + 1]) & 255) << 8);
//            buf.append(CARR_LEGAL_CHAR[(d >> 18) & 63]);
//            buf.append(CARR_LEGAL_CHAR[(d >> 12) & 63]);
//            buf.append(CARR_LEGAL_CHAR[(d >> 6) & 63]);
//            buf.append("=");
//        } else if (i == start + len - 1) {
//            int d = (((int) data[i]) & 0x0ff) << 16;
//            buf.append(CARR_LEGAL_CHAR[(d >> 18) & 63]);
//            buf.append(CARR_LEGAL_CHAR[(d >> 12) & 63]);
//            buf.append("==");
//        }
//        return buf.toString();
//    }


/*=======================================
 * 作者：WangZezhi  (2018/6/8  17:22)
 * 功能：base64Decode
 * 描述：
 *=======================================*/
//    public static byte[] base64Decode(String s) throws Exception {
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        base64Decode(s, bos);
//        byte[] decodedBytes = bos.toByteArray();
//        bos.close();
//        bos = null;
//        return decodedBytes;
//    }
//    //
//    private static void base64Decode(String s, OutputStream os) throws Exception {
//        int i = 0;
//        int len = s.length();
//        while (true) {
//            while (i < len && s.charAt(i) <= ' ') {
//                i++;
//            }
//            if (i == len) {
//                break;
//            }
//            int tri = (base64Decode(s.charAt(i)) << 18)
//                    + (base64Decode(s.charAt(i + 1)) << 12)
//                    + (base64Decode(s.charAt(i + 2)) << 6)
//                    + (base64Decode(s.charAt(i + 3)));
//            os.write((tri >> 16) & 255);
//            if (s.charAt(i + 2) == '=') {
//                break;
//            }
//            os.write((tri >> 8) & 255);
//            if (s.charAt(i + 3) == '=') {
//                break;
//            }
//            os.write(tri & 255);
//            i += 4;
//        }
//    }
//    //
//    private static int base64Decode(char c) {
//        if (c >= 'A' && c <= 'Z') {
//            return ((int) c) - 65;
//        } else if (c >= 'a' && c <= 'z') {
//            return ((int) c) - 97 + 26;
//        } else if (c >= '0' && c <= '9') {
//            return ((int) c) - 48 + 26 + 26;
//        } else {
//            switch (c) {
//                case '+':
//                    return 62;
//                case '/':
//                    return 63;
//                case '=':
//                    return 0;
//                default:
//                    throw new RuntimeException("unexpected code: " + c);
//            }
//        }
//    }
