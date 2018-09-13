package com.hz.util.common;

import java.nio.ByteBuffer;
import java.security.Key;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import com.hz.core.util.UUIDUtil;

public class DESede {
	
	/**
	 * 对称加密DESede密钥算法
	 * JAVA 6 支持长度为112和168位
	 * Bouncy Castle支持密钥长度为128位和192位
	 */
	public static final String KEY_ALGORITHM = "DESede";
	
	/**
	 * 加解密算法/工作模式/填充方式
	 * JAVA 6 支持PKCS5Padding填充方式
	 * Bouncy Castle支持PKCS7Padding填充方式
	 */
	public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS7Padding";
	
	//Bouncy Castle算法提供者
	public static final String PROVIDER_BOUNCY_CASTOLE = "BC";
	
//	public void test(){
//		byte[] dataKey = {70,-17,124,11,26,115,-92,31,-95,127,-34,109,8,-23,94,70,-80,-80,59,-10,104,-102,31,-43};
//		String keyBase64 = "RpF8Cxpz3B/ff6JtCJdeRtDQO4po5h+r";
//		
//		dataKey = Base64.decode(keyBase64);
//		
//		String textPlain = "aaabbbccc___aaabbbccc";
////    	byte[] dataKey = convertKey(keyInt);
//    	Log.d("Test", "DESede Key = " + Base64.encode(dataKey));
//    	byte[] dataPlain = null;
//    	byte[] dataEncrypt = null;
//		try {
//			dataPlain = textPlain.getBytes("UTF-8");
//			dataEncrypt = DESede.encrypt(dataPlain, dataKey);
//			Log.d("Test", "dataEncrypt = " + Base64.encode(dataEncrypt));
//			byte[] dataDecrypt = DESede.decrypt(dataEncrypt, dataKey);
//	    	String text = new String(dataDecrypt, "UTF-8");
//	    	Log.d("Test", "textPlain = " + text);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	//将C#的字节数组转换成JAVA字节数组
	public static byte[] convertByte(int[] keyInt){
		byte[] keyByte = new byte[keyInt.length];
		for (int i = 0; i < keyByte.length; i++) {
			if(keyInt[i]>127){
				keyByte[i] = (byte) (keyInt[i]-256);
			}else{
				keyByte[i] = (byte) keyInt[i];
			}
		}
		return keyByte;
	}
	
	//生成密钥
	public static byte[] initKey() throws Exception{
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM, PROVIDER_BOUNCY_CASTOLE);
//		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		kg.init(192);
		SecretKey secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}
	
	//转换密钥
	private static final Key toKey(byte[] key) throws Exception{
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		return keyFactory.generateSecret(dks);
	}
	
	//加密数据
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception{
		if(key.length<24){
			key = convertKey(key);
		}
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM, PROVIDER_BOUNCY_CASTOLE);
//		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(data);
	}
	
	//解密数据
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception{
		if(key.length<24){
			key = convertKey(key);
		}
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM, PROVIDER_BOUNCY_CASTOLE);
//		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		return cipher.doFinal(data);
	}
	
	//将16字节的密钥转换成24字节
	public static byte[] convertKey(byte[] srcKey){
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
	
	/**
	 * 在字节数组里插入随机字节数组
	 * 
	 * @param srcArray
	 *            源字节数组
	 * @return 目标字节数组
	 */
	public static final byte[] insertBytes(byte[] srcArray) {
		// 位置字节数组
		int[] positionArray = { 3, 7, 10, 17, 19, 23 };
		// 目标字节数组长度
		int destLength = srcArray.length + positionArray.length;

		// 字节缓冲流
		ByteBuffer buffer = ByteBuffer.allocate(destLength);

		buffer.put(srcArray, 0, positionArray[0]);
		byte temp = getRandomByte();
		buffer.put(temp);

		// 初始化待插入的随机字节数组
		for (int i = 1; i < positionArray.length; i++) {
			byte tempByte = getRandomByte();
			buffer.put(srcArray, positionArray[i - 1], positionArray[i]
					- positionArray[i - 1]);
			buffer.put(tempByte);
		}

		buffer.put(srcArray, positionArray[positionArray.length - 1],
				srcArray.length - positionArray[positionArray.length - 1]);

		buffer.flip();
		// 目标字节数组
		byte[] destArray = buffer.array();
		buffer.clear();

		// 交换字节
		byte tempByte = destArray[2];
		destArray[2] = destArray[destArray.length - 1];
		destArray[destArray.length - 1] = tempByte;

		tempByte = destArray[8];
		destArray[8] = destArray[destArray.length - 3];
		destArray[destArray.length - 3] = tempByte;

		return destArray;
	}

	/**
	 * 从字节数组里清除之前插入的随机字节数组
	 * 
	 * @param srcArray
	 *            源字节数组
	 * @return 目标字节数组
	 */
	public static final byte[] clearInsertBytes(byte[] srcArray) {
		// 交换字节
		byte tempByte = srcArray[2];
		srcArray[2] = srcArray[srcArray.length - 1];
		srcArray[srcArray.length - 1] = tempByte;

		tempByte = srcArray[8];
		srcArray[8] = srcArray[srcArray.length - 3];
		srcArray[srcArray.length - 3] = tempByte;

		// 位置字节数组
		int[] positionArray = { 3, 7, 10, 17, 19, 23 };
		// 目标字节数组长度
		int destLength = srcArray.length - positionArray.length;

		// 字节缓冲流
		ByteBuffer buffer = ByteBuffer.allocate(destLength);

		for (int i = 0; i < positionArray.length; i++) {
			if (i == 0) {
				buffer.put(srcArray, 0, positionArray[i]);
			} else {
				buffer.put(srcArray, positionArray[i - 1] + i, positionArray[i]
						- positionArray[i - 1]);
			}

			if (i == positionArray.length - 1) {
				buffer.put(srcArray, positionArray[positionArray.length - 1]
						+ i + 1, srcArray.length - i - 1
						- positionArray[positionArray.length - 1]);
			}

		}

		// 目标字节数组
		byte[] destArray = buffer.array();
		buffer.clear();

		return destArray;
	}

	// 获取随机字节
	public static final byte getRandomByte() {
		Random ran = new Random();
		int min = 0;
		int max = 256;
		// try {
		// Thread.sleep(1);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		int ranInt = min + ((ran.nextInt() >>> 1) % max);
		byte ranByte = (byte) (ranInt - 128);
		return ranByte;
		// return min + Math.abs(ran.nextInt() % max);
	}

	// 打印数组
	public static final String stringArray(byte[] srcArray) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < srcArray.length; i++) {
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
	
	public static void main(String[] args) {
		String str ="abcdefg";
		String uuid = UUIDUtil.genUUIDString().substring(18);
		String endStr = uuid+"HZWDMOBILE";
		System.out.println(endStr.getBytes());
		try {
		  byte[]  res = encrypt(str.getBytes("utf-8"),endStr.getBytes("utf-8"));
		  System.out.println(new String(res));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
