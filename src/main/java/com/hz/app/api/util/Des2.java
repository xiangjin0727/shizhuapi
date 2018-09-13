package com.hz.app.api.util;

import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * Created by IntelliJ IDEA.
 * Date: 2008-6-18 11:48:56
 */
public class Des2
{
    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
    private static Log log = LogFactory.getLog(Des2.class);

    /**
     * DES�㷨������
     *
     * @param data �������ַ���
     * @param key  ����˽Կ�����Ȳ��ܹ�С��8λ
     * @return ���ܺ���ֽ����飬һ����Base64����ʹ��
     * @throws CryptException �쳣
     */
    public static String encode(String key,String data) throws Exception
    {
        return encode(key, data.getBytes("GB18030"));
    }
    /**
     * DES�㷨������
     *
     * @param data �������ַ���
     * @param key  ����˽Կ�����Ȳ��ܹ�С��8λ
     * @return ���ܺ���ֽ����飬һ����Base64����ʹ��
     * @throws CryptException �쳣
     */
    public static String encode(String key,byte[] data) throws Exception
    {
        try
        {
	    	DESKeySpec dks = new DESKeySpec(key.getBytes());
	    	
	    	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key�ĳ��Ȳ��ܹ�С��8λ�ֽ�
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,paramSpec);
            
            byte[] bytes = cipher.doFinal(data);
            return Base64.encode(bytes);
        } catch (Exception e)
        {
            throw new Exception(e);
        }
    }

    /**
     * DES�㷨������
     *
     * @param data �������ַ���
     * @param key  ����˽Կ�����Ȳ��ܹ�С��8λ
     * @return ���ܺ���ֽ�����
     * @throws Exception �쳣
     */
    public static byte[] decode(String key,byte[] data) throws Exception
    {
        try
        {
        	SecureRandom sr = new SecureRandom();
	    	DESKeySpec dks = new DESKeySpec(key.getBytes());
	    	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            //key�ĳ��Ȳ��ܹ�С��8λ�ֽ�
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey,paramSpec);
            return cipher.doFinal(data);
        } catch (Exception e)
        {
            throw new Exception(e);
        }
    }
    
    /**
     * ��ȡ������ֵ
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static String decodeValue(String key,String data) 
    {
    	byte[] datas;
    	String value = null;
		try {
			if(System.getProperty("os.name") != null && (System.getProperty("os.name").equalsIgnoreCase("sunos") || System.getProperty("os.name").equalsIgnoreCase("linux")))
	        {
	    		log.debug("os.name(true)=" + System.getProperty("os.name"));
	    		datas = decode(key, Base64.decode(data));
	    		log.debug("ddd=" + new String(datas));
	        }
	    	else
	    	{
	    		log.debug("os.name(false)=" + System.getProperty("os.name"));
	    		datas = decode(key, Base64.decode(data));
	    		log.debug("ddd=" + new String(datas,"GB18030"));
	    	}
			
			value = new String(datas,"GB18030");
		} catch (Exception e) {
			e.printStackTrace();
			log.warn("����ʧ��");
			value = "";
		}
    	return value;
    }

    public static void main(String[] args) throws Exception
    {
      
      //System.out.println("����abc ���ܣ�" + Des2.encode("12345678","abc"));
  	 // System.out.println("����ABC ���ܣ�" + Des2.encode("12345678","ABC"));
  	 // //System.out.println("�����й��� ���ܣ�" + Des2.encode("12345678","�й���"));
  	//  System.out.println("g3ME4dIH/5dpd1M mHqZ2eWk8pxuq8V5=" + Des2.decodeValue("88888888", "g3ME4dIH/5dpd1MmHqZ2eWk8pxuq8V5"));
//  	  System.out.println("ss=" + Des2.decodeValue("88888888", "rc+uwHsk8I4SGFElQ4NMQbZgdrPwMSNrfv0Pt5RQ15/2q+i7jd6JFOgahlPhhGDuohv5MS7K8UZiVxP7Zp796Y0FQPJu58wx5LhlHUEZCUXcHAKnjX0EsQSrpfmIF/JZxMfcGCNWCABDTRQG8oDs2CMYGI/g2cmL9nU6EpG4RGH9zhx9Gsovv/mnxKKqLI9fySUfAaMxReEZoUWpEFPKk7xvV7mxQsUNNaaCekCt0D+FP65bQBweZA=="));
  	 
  	 // System.out.println("��������ңԶ�ĵط� ���ܣ�" + Des2.encode("12345678","g3ME4dIH/5dpd1M mHqZ2eWk8pxuq8V5"));
  	  //System.out.println(new String(Des2.decode("wwwid5cn", "ABB0E340805D367C438E24FC4005C121F60247F6EE4430B5".toLowerCase().getBytes())));
  	  System.out.println("dd=" + Des2.encode("12345678", new String("idtagpckhd")));
//  	 System.out.println("dd=" + Des2.encode("88888888", new String("�ܻ�".getBytes("GBK"),"GBK")));
  	System.out.println("dd=" + Des2.decodeValue("12345678", "yPJEpR5wlVn35Ag+YyRsSw=="));
    }
}
