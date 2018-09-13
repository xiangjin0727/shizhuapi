package com.hz.app.error.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hz.core.util.StringUtil;



/**
 * 错误消息提取帮助类
 * @ClassName ErrorService
 * @Description TODO(错误消息提取帮助类)
 * @author ysj
 * @Date 2017年9月9日 下午3:21:55
 * @version 1.0.0
 */
public abstract class ErrorService{

	/**
	 * 1.0 所有错误消息内容
	 */
	public static JSONObject ERROR = new JSONObject();
	
	
	/**
	 * 刷新错误码和消息
	 * @Description (TODO 刷新错误码和消息)
	 * @param ERROR
	 * @return
	 */
	public static boolean FLUSH_DATA(JSONObject ERROR){
		ERROR = ErrorService.ERROR;
		return true;
	}
	/**
	 * 取错误消息
	 * @Description (TODO 取错误消息)
	 * @param index 错误序列
	 * @return 返回错误对象
	 */
	public static JSONObject GET_CODE(String code){
		JSONObject r= new JSONObject();
		String val = ERROR.getString(code);
		if(StringUtil.isEmpty(val))return null;
		r.put(code, val);
		return r;
	}
}
