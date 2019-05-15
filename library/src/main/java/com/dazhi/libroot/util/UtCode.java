package com.dazhi.libroot.util;

import android.util.Base64;
import java.security.MessageDigest;
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
public class UtCode {
    //秘钥
    private static final String SECRET_KEY = "&*u!^%$#";
    //初始化向量，随意填充,单只能是8个字节
    private static byte[] iv = { 'a', 'n', 'j', 'd', 'p', 13, 22, '*'};


    /**=======================================
     * 作者：WangZezhi  (2018/6/8  17:23)
     * 功能： 获得加密的字符串
     * 描述：
     *=======================================*/
    public static String getEncryptStr(String plaintext){
        return getEncryptStr(plaintext, SECRET_KEY);
    }

    public static String getEncryptStr(String plaintext, String strKey) {
        try{
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
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }


    /**=======================================
     * 作者：WangZezhi  (2018/6/8  17:23)
     * 功能：获得解密的字符串
     * 描述：
     *=======================================*/
    public static String getDecryptStr(String ciphertext){
        return getDecryptStr(ciphertext, SECRET_KEY);
    }

    public static String getDecryptStr(String decryptString, String decryptKey) {
        try{
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
            byte decryptedData[] = cipher.doFinal(base64byte);
            // 将解密后数据转换为字符串输出
            return new String(decryptedData);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }


    /**=======================================
     * 作者：WangZezhi  (2019-05-15  16:08)
     * 功能：Base64 编码/解码
     * 描述：
     *=======================================*/
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


    /**=======================================
     * 作者：WangZezhi  (2019-05-15  16:23)
     * 功能：MD5
     * 描述：
     *=======================================*/
    public static String md5(String strText) {
        return md5(strText, "I1j0l9n4DF3Aac|spP03DGqd4msbb");
    }
    public static String md5(String strText, String strSecret) {
        try {
            strText = strText + strSecret;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(strText.getBytes("UTF8"));
            byte s[] = m.digest();
            String result = "";
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}



/**===============================================
 * 作者：WangZezhi  (17-5-26  上午11:36)
 * 功能：Base64编码/解码部分
 * 详情：合法的（legal）
 ================================================*/
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


/**=======================================
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
