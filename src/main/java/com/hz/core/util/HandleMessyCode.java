package com.hz.core.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理请求参数Map对象的中文乱码问题
 * 
 * @author 卢伟
 *
 */
public class HandleMessyCode {

	/**
	 * 处理乱码
	 * 将所有null参数转化成 ""
	 * @param params
	 * @return
	 */
	public static Map<String, Object> handle(Map<String, Object> params) {
		Map<String, Object> doParams = new HashMap<String, Object>();
		try {
			for (String key : params.keySet()) {
				Object value = params.get(key);
				doParams.put(key, new String(value.toString().getBytes("ISO-8859-1"), "UTF-8").equals("null") ? ""
						: new String(value.toString().getBytes("ISO-8859-1"),
								"UTF-8"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return doParams;
	}
	
	/**
	 * 处理乱码
	 * @param param
	 * @return
	 */
	public static String handle(String param) {
		if( org.springframework.util.StringUtils.isEmpty(param) ){
			return null;
		}
		try {
			return new String(param.toString().getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
