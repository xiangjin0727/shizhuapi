package com.hz.util.common;

import java.net.URLEncoder;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * 釆用3DES标准以模式为ECB、填充方式为PKCS7加密数据 
 * @author Administrator
 *
 */
public class DESecbpkcs7 {
	
	private Cipher cipher = null; 
	// base64编码  
    private BASE64Encoder base64Encode = new BASE64Encoder();  
    private BASE64Decoder base64Decode = new BASE64Decoder();  
    // 密钥  
    private  String key = "";  
    // 过滤换行  
    private boolean filter = true;  
  
    public String getKey(){  
        return key;  
    }  
  
    public boolean getFilter(){  
        return filter;  
    }  
      
    /** 
     * 设置密钥 
     * @param key 
     */  
    public void setKey(String key){  
        this.key = key;  
    }  
  
    public void setFilter(boolean filter){  
        this.filter = filter;  
    }  
  
    private final Cipher initCipher(int mode){  
        try{  
            // 添加新安全算法:PKCS7  
            Security.addProvider(new BouncyCastleProvider());  
            String algorithm = "DESede/ECB/PKCS7Padding";  
            SecretKey desKey = new SecretKeySpec(key.getBytes("UTF-8"), algorithm);  
            Cipher tcipher = Cipher.getInstance(algorithm);  
            tcipher.init(mode, desKey);  
            return tcipher;  
        } catch (Exception e){  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** 
     * 加密以charset编码做为密文 
     *  
     * @param src 
     *            明文 
     * @param charset 
     *            编码,例：UTF8、BASE64 
     * @return 
     */  
    public String encrypt(String src, String charset){  
        try{  
            return URLEncoder.encode(encrypt(src), charset);  
        } catch (Exception e){  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** 
     * 解密 
     * @param src 二进制数组 
     * @return 
     * @throws Exception 
     */  
    private byte[] decrypt(byte[] src) throws Exception{  
        cipher = initCipher(Cipher.DECRYPT_MODE);  
        return cipher.doFinal(src);  
    }  
    /** 
     * 解密 
     * @param src 密文 
     * @return 
     * @throws Exception 
     */  
    public  String decrypt(String src) throws Exception{  
        byte[] bt=base64Decode.decodeBuffer(src);  
        byte[] sbt=decrypt(bt);  
        return new String(sbt,"UTF-8");  
    }  
    /** 
     * 加密以base64做为密文 
     *  
     * @param src 
     *            明文 
     * @return 密文 
     */  
    public String encrypt(String src) throws Exception{  
        cipher = initCipher(Cipher.ENCRYPT_MODE);  
        byte[] dd = encrypt(src.getBytes("UTF-8"));  
        String str = base64Encode.encode(dd);  
        return str;  
    }  
  
    /** 
     *  
     * @param src 
     * @return 
     */  
    public byte[] encrypt(byte[] src){  
        try{  
            return cipher.doFinal(src);  
        } catch (Exception e){  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    public static void main(String[] args) throws Exception{
    	DESecbpkcs7 des7Object = new DESecbpkcs7();
    	//base64解密
//    	String key = new String(des7Object.base64Decode.decodeBuffer("aWxzZTI4Mzc0dWhqd2VIWldETU9CSUxF"),"UTF-8");
//    	System.out.println(getEncrypt("P9i8d7gli73673UJDD6D23ro", "1lsdjfo天涯trngfj,xvkdfjg"));
    	System.out.println(getDecrypt("P9i8d7gli73673UJDD6D23ro", "1AcoSZ2fjYWup6R3VUn1Yuy076R3zud1KBiQzmFdoZ0ZL429S9fDa7gPcp1y3rFlLJotmRh0Rf0z+FKDduJEidZTNQ6QayczOUx2ghsQfJcOGeqMpa8NyQx3TyE+3JJdnGserl8w0lusy4Dzh3JX8MgPO4te3ySJ6M/6g9sNiR1LOH6RylfIyA=="));
    }
    
    public static String transKey(String key){
    	try {
    		DESecbpkcs7 des7Object = new DESecbpkcs7();
        	return  new String(des7Object.base64Decode.decodeBuffer(key),"UTF-8");
		} catch (Exception e) {
			// TODO: handle exceptione
			e.printStackTrace();
		}
    	return "";
    }
    
    /**
     * 加密操作
     * key 密钥  encrypt 加密前的原文
     * @param key
     * @param encrypt
     * @return
     * @throws Exception
     */
    public static String getEncrypt(String key,String encrypt) throws Exception{
    	DESecbpkcs7 des7Object = new DESecbpkcs7();  
        Security.addProvider(new com.sun.crypto.provider.SunJCE()); 
        des7Object.key = key;
        return  des7Object.encrypt(encrypt); 
    }
    
    /**
     * 解密操作
     * key 密钥   decrypt base64加密后
     * @param key
     * @param encrypt
     * @return
     * @throws Exception
     */
    public static String getDecrypt(String key,String decrypt) throws Exception{
    	DESecbpkcs7 des7Object = new DESecbpkcs7();  
        Security.addProvider(new com.sun.crypto.provider.SunJCE()); 
        des7Object.key = key;
        //String desBase64 = new String(des7Object.base64Decode.decodeBuffer(decrypt),"UTF-8");
        return  des7Object.decrypt(decrypt); 
    }
    
    
}
