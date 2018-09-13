package com.hz.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImgBase64Util {

	/**
	 * @Title: GetImageStrFromUrl
	 * @Description: TODO(将一张网络图片转化成Base64字符串)
	 * @param imgUrl 网络资源位置
	 * @return Base64字符串
	 */
	public static String GetImageStrFromUrl(String imgUrl){
		byte data[] = null;
		try {
			URL url = new URL(imgUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(30*1000);
			InputStream inStream = conn.getInputStream();
			data = new byte[inStream.available()];
			inStream.read(data);
			inStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		BASE64Encoder encoder = new BASE64Encoder();	
		return encoder.encode(data);
	}
	
    /**
     * 将一张网络图片转化成Base64字符串
    * @param imgURL
    * @return
    */
   public static String GetImageStrFromUrl_(String imgURL) {  
    	ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // 创建URL
            URL url = new URL(imgURL);
            byte[] by = new byte[1024];
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            InputStream is = conn.getInputStream();
            // 将内容读取内存中
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            // 关闭流
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
		//return encoder.encode(data.toByteArray());
        
        byte[] ddd = data.toByteArray();
        System.err.println("-----------byte:"+ddd.length);
        
        return org.apache.commons.codec.binary.Base64.encodeBase64String(ddd);
    }  
    

	
	
	/**
	 * @Title: GetImageStrFromPath
	 * @Description: TODO(将一张本地图片转化成Base64字符串)
	 * @param path
	 * @return
	 */
	public static String GetImageStrFromPath(String path){
		InputStream inputS = null;
		byte[] data = null;
		try {
			inputS = new FileInputStream(path);
			data = new byte[inputS.available()];
			inputS.read(data);
			inputS.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("本地图片大小："+data.length);
		return Base64.encode(data);
	}
	/**
	 * @Title: GenerateImage
	 * @Description: TODO(base64字符串转化成图片)
	 * @param imageSoure  图片内容
	 * @param fileUrl  图片存放地址
	 * @return
	 */
	public static boolean GenerateImage(String imageSoure,String fileUrl){

		boolean isTru = true;
		if(imageSoure == null){
			isTru = false;
			return isTru;
		}
		try {
			
			BASE64Decoder d =new BASE64Decoder();
			byte[] data = org.apache.commons.codec.binary.Base64.decodeBase64(imageSoure);   //d.decodeBuffer(imageSoure);
			for(int i=0;i<data.length;i++){
				if(data[i]<0){
					data[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(fileUrl);

			out.write(data);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			isTru = false;
		}
		
		return isTru;
	}
	
	
	public static void main(String[] args) {
		
		//String base = ImgBase64Util.GetImageStrFromUrl_("http://47.104.229.248:8080/szhz/image/test.jpeg");
		String base = ImgBase64Util.GetImageStrFromPath("C:\\Users\\hzcf\\Desktop\\test004.png");

		System.out.println(base);
		System.out.println("String长度："+base.length());

		ImgBase64Util.GenerateImage(base, "C:\\Users\\hzcf\\Desktop\\时租\\test.png");
		
	}

}
