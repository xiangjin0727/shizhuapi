package com.hz.constant;

import java.util.Properties;

import com.hz.core.util.ClassLoaderUtil;
import com.hz.util.common.SendDataToUrl;
import com.hzwealth.app.dispatch.core.util.AESUtil;

public class HzcfConstant {
	
	//正式环境=======================================================================================
	
	/*//线上正式环境内网地址及端口
	public static final String URL="http://127.0.0.1:80";//"http://127.0.0.1:80"
	//Web签章服务URL  http://www.hzdjr.com/seal/createpdf/
	//public static final String WebUrl = "http://10.20.30.14:80/hzcf/seal/createpdf/";
	public static final String WebUrl = "http://10.0.1.81:8081/seal/createpdf/";
	//public static final String WebUrl = "http://www.hzdjr.com/seal/createpdf/";
	//扫描注册提供给手机端的地址前缀
	//public static final String H5Url = "https://115.182.42.150:25005";
	
	public static final String H5Url = "https://app.hzdjr.com";
	
	public static final String HttpH5Url = "http://app.hzdjr.com";
	//返回签章的地址，即PDF的地址
	//public static final String resUrlPre = "http://test.corp1.hzdjr.com/hzcf/seal/download/";
	public static final String resUrlPre = "http://chujie.hzdjr.com/seal/download/";
	//密钥
	public static final String qzKey = "hzcf@2016";*/
	
	
	
	//测试环境=======================================================================================
	
	/*//线上正式环境内网地址及端口
	public static final String URL="http://127.0.0.1:8081";//"http://127.0.0.1:80"
	//Web签章服务URL  http://www.hzdjr.com/seal/createpdf/
	public static final String WebUrl = "http://127.0.0.1:8080/hzcf/seal/createpdf/";
	//public static final String WebUrl = "http://www.hzdjr.com/seal/createpdf/";
	//扫描注册提供给手机端的地址前缀
	public static final String H5Url = "https://211.103.153.135:8081";
	
	//public static final String H5Url = "https://app.hzdjr.com";
	
	public static final String HttpH5Url = "http://211.103.153.135:8081";
	
	//public static final String HttpH5Url = "http://app.hzdjr.com";
	//返回签章的地址，即PDF的地址
	
	public static final String resUrlPre = "http://test2.hzdjr.com:8080/hzcf/seal/download/";
	//public static final String resUrlPre = "http://www.hzdjr.com/seal/download/";
	
	//密钥
	public static final String qzKey = "hzcf@2016";*/
	
	
	
	
	
	//发送签章请求
	public static void  reqQz(final String userId,final String sign){
		System.out.println(userId+"===执行签章操作====="+sign+"==");
		Properties property = ClassLoaderUtil.getProperties("app_system.properties");
		try {
			 final String paramsJson = "{\"referenceId\":\""+userId+"\",\"contractType\":\""+sign+"\"}";
			 final String encryptParams = AESUtil.enCrypt(paramsJson, property.getProperty("HzcfConstant.qzKey"));
			 System.out.println(encryptParams);
			 new Thread() {
		            public void run() {
		            	Properties property = ClassLoaderUtil.getProperties("app_system.properties");
	            		String WebUrl =property.getProperty("HzcfConstant.WebUrl");
		            	try {
		            		SendDataToUrl.getDataToUrl(WebUrl,"params="+encryptParams,"POST");
		            		System.out.println(userId+"---签章成功====="+ WebUrl);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							System.out.println(userId+"---签章失败====="+WebUrl);
						}
		            };  
		        }.start(); 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public static void main (String [] args){
		reqQz("412614c0950a4a3881296ca5d0f56129","FYZHXY");
	}
	
}
