package com.hz.util;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.Security;
import java.util.Random;

public class DESede {
    private static Logger logger = Logger.getLogger(DESede.class);
    public static final String KEY_IV = "jI6E3d72";
    public static final String key2 = "8d7bie20ciD7G2cy0P4b23Ma";
    public static final String KEY_ALGORITHM = "DESede";
    public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS7Padding";
    public static final String PROVIDER_BOUNCY_CASTOLE = "BC";

    public DESede() {
    }

    public static void test() {
        byte[] dataKey = new byte[]{70, -17, 124, 11, 26, 115, -92, 31, -95, 127, -34, 109, 8, -23, 94, 70, -80, -80, 59, -10, 104, -102, 31, -43};
        String textPlain = "aaabbbccc天涯海角aaabbbccc";
        String keyPlain = "01234567890123456789gfhy";
        logger.info("DES Key = " + keyPlain);
        logger.info("DES IV = jI6E3d72");
        byte[] dataPlain = null;
        Object var4 = null;

        try {
            new String(dataKey, "UTF-8");
             dataPlain = textPlain.getBytes("UTF-8");
            String strEncrypt = encryptCBC(textPlain, keyPlain, "jI6E3d72");
            System.out.println("1:" + strEncrypt);
            String dataDecrypt = decryptCBC(strEncrypt, keyPlain, "jI6E3d72");
            System.out.println("2:" + dataDecrypt);
        } catch (Exception var8) {
            var8.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        test();
    }

    public static String encryptCBC(String key, String data) {
        Security.addProvider(new BouncyCastleProvider());
        String strEncrypt = "";

        try {
            strEncrypt = encryptCBC(data, key, "jI6E3d72");
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return strEncrypt;
    }

    public static String decryptCBC(String key, String data) {
        Security.addProvider(new BouncyCastleProvider());
        String strDec = "";

        try {
            strDec = decryptCBC(data, key, "jI6E3d72");
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return strDec;
    }

    public static byte[] convertByte(int[] keyInt) {
        byte[] keyByte = new byte[keyInt.length];

        for(int i = 0; i < keyByte.length; ++i) {
            if (keyInt[i] > 127) {
                keyByte[i] = (byte)(keyInt[i] - 256);
            } else {
                keyByte[i] = (byte)keyInt[i];
            }
        }

        return keyByte;
    }

    public static byte[] initKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance("DESede", "BC");
        kg.init(192);
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }

    private static final Key toKey(byte[] key) throws Exception {
        DESedeKeySpec dks = new DESedeKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        return keyFactory.generateSecret(dks);
    }

    public static String encryptCBC(String dataInfo, String keyInfo, String ivInfo) throws Exception {
        String textEncrypt = null;
        byte[] key = keyInfo.getBytes("UTF-8");
        byte[] keyiv = ivInfo.getBytes("UTF-8");
        byte[] data = dataInfo.getBytes("UTF-8");
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS7Padding", "BC");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(1, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
        textEncrypt = Base64.encode(bOut);
        return textEncrypt;
    }

    public static String decryptCBC(String dataInfo, String keyInfo, String ivInfo) throws Exception {
        byte[] key = keyInfo.getBytes("UTF-8");
        byte[] keyiv = ivInfo.getBytes("UTF-8");
        byte[] data = Base64.decode(dataInfo);
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS7Padding", "BC");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(2, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
        String text = new String(bOut, "UTF-8");
        return text;
    }

    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        if (key.length < 24) {
            key = convertKey(key);
        }

        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance("DESede", "BC");
        cipher.init(1, k);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        if (key.length < 24) {
            key = convertKey(key);
        }

        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance("DESede", "BC");
        cipher.init(2, k);
        return cipher.doFinal(data);
    }

    public static String encrypt(String data, String key) throws Exception {
        Key k = toKey(key.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance("DESede", "BC");
        cipher.init(1, k);
        byte[] dataEncrypt = cipher.doFinal(data.getBytes("UTF-8"));
        String textEncrypt = Base64.encode(dataEncrypt);
        return textEncrypt;
    }

    public static String decrypt(String data, String key) throws Exception {
        Key k = toKey(key.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance("DESede", "BC");
        cipher.init(2, k);
        byte[] dataEncrypt = Base64.decode(data);
        byte[] dataText = cipher.doFinal(dataEncrypt);
        String text = new String(dataText, "UTF-8");
        return text;
    }

    public static byte[] convertKey(byte[] srcKey) {
        byte[] destKey = null;
        byte[] keyFirst = new byte[8];
        ByteBuffer buffer = ByteBuffer.wrap(srcKey);
        buffer.get(keyFirst);
        buffer.clear();
        buffer = ByteBuffer.allocate(24);
        buffer.put(srcKey);
        buffer.put(keyFirst);
        buffer.flip();
         destKey = buffer.array();
        buffer.clear();
        return destKey;
    }

    public static final byte[] insertBytes(byte[] srcArray) {
        int[] positionArray = new int[]{3, 7, 10, 17, 19, 23};
        int destLength = srcArray.length + positionArray.length;
        ByteBuffer buffer = ByteBuffer.allocate(destLength);
        buffer.put(srcArray, 0, positionArray[0]);
        byte temp = getRandomByte();
        buffer.put(temp);

        byte tempByte;
        for(int i = 1; i < positionArray.length; ++i) {
            tempByte = getRandomByte();
            buffer.put(srcArray, positionArray[i - 1], positionArray[i] - positionArray[i - 1]);
            buffer.put(tempByte);
        }

        buffer.put(srcArray, positionArray[positionArray.length - 1], srcArray.length - positionArray[positionArray.length - 1]);
        buffer.flip();
        byte[] destArray = buffer.array();
        buffer.clear();
        tempByte = destArray[2];
        destArray[2] = destArray[destArray.length - 1];
        destArray[destArray.length - 1] = tempByte;
        tempByte = destArray[8];
        destArray[8] = destArray[destArray.length - 3];
        destArray[destArray.length - 3] = tempByte;
        return destArray;
    }

    public static final byte[] clearInsertBytes(byte[] srcArray) {
        byte tempByte = srcArray[2];
        srcArray[2] = srcArray[srcArray.length - 1];
        srcArray[srcArray.length - 1] = tempByte;
        tempByte = srcArray[8];
        srcArray[8] = srcArray[srcArray.length - 3];
        srcArray[srcArray.length - 3] = tempByte;
        int[] positionArray = new int[]{3, 7, 10, 17, 19, 23};
        int destLength = srcArray.length - positionArray.length;
        ByteBuffer buffer = ByteBuffer.allocate(destLength);

        for(int i = 0; i < positionArray.length; ++i) {
            if (i == 0) {
                buffer.put(srcArray, 0, positionArray[i]);
            } else {
                buffer.put(srcArray, positionArray[i - 1] + i, positionArray[i] - positionArray[i - 1]);
            }

            if (i == positionArray.length - 1) {
                buffer.put(srcArray, positionArray[positionArray.length - 1] + i + 1, srcArray.length - i - 1 - positionArray[positionArray.length - 1]);
            }
        }

        byte[] destArray = buffer.array();
        buffer.clear();
        return destArray;
    }

    public static final byte getRandomByte() {
        Random ran = new Random();
        int min = 0;
        int max = 256;
        int ranInt = min + (ran.nextInt() >>> 1) % max;
        byte ranByte = (byte)(ranInt - 128);
        return ranByte;
    }

    public static final String stringArray(byte[] srcArray) {
        StringBuffer buffer = new StringBuffer();

        for(int i = 0; i < srcArray.length; ++i) {
            if (i == 0) {
                buffer.append("{" + srcArray[i] + ", ");
            } else if (i == srcArray.length - 1) {
                buffer.append(srcArray[i] + "}");
            } else {
                buffer.append(srcArray[i] + ", ");
            }
        }

        return buffer.toString();
    }
}
