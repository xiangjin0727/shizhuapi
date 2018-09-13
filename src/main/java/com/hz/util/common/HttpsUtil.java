package com.hz.util.common;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.HttpClient;



public class HttpsUtil{
	public static String post(String url,String json) throws Exception{  
		String strResult = "";
		// HttpPost连接对象

		HttpClient httpclient = new HttpClient();
	    Protocol.registerProtocol("https", new Protocol("https", new MySSLProtocolFactory(), 443));
		PostMethod postMethod = new PostMethod(url);
		RequestEntity entity = new StringRequestEntity(json, "text/xml;charset=UTF-8", null);
		postMethod.setRequestEntity(entity);
		int result = httpclient.executeMethod(postMethod);

		System.out.println("http status:" + result);

		 strResult = postMethod.getResponseBodyAsString();
	     return strResult;
	
	} 
	
	public static void main(String[] args) {
    	try {
			String url = "https://app.hzdjr.com/hzcfmobile/mobiledata";
			
	        String jsonData = "{\"ServiceId\":\"S0008\",\"PageIndex\":\"1\",\"PageSize\":\"10\",\"Type\":\"2\"}";
			System.out.println(post(url, jsonData));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
   
}