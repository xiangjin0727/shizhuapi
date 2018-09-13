package com.hz.util.common;

public class ResMsgUtils {
	
	/**
	 * 错误信息标准JSON串
	 */
	public static String resErrorMsg(String serviceId,String message){
		if(serviceId==null||"".equals(serviceId)){
			serviceId = "S0000";
		}
		return  "{\"fid\":\""+serviceId+"\",\"message\":\""+message+"\",\"code\":\"0001\"}";
	}
	
	/**
	 * 错误信息标准JSON串
	 */
	public static String resErrorMsgLog(String serviceId,String message){
		return  "{\"fid\":\""+serviceId+"\",\"message\":\""+message+"\",\"code\":\"9999\"}";
	}
	
	
	/**
	 * 错误信息标准JSON串
	 */
	public static String resErrorMsgLogout(String serviceId,String message){
		return  "{\"fid\":\""+serviceId+"\",\"message\":\""+message+"\",\"code\":\"0001\"}";
	}

}
