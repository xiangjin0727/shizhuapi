package com.hz.core.util.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.hz.app.api.HttpProxyClientPost;
import com.hz.core.framework.redis.RedisCacheDao;
import com.hz.core.util.ClassLoaderUtil;
import com.hz.core.util.UUIDUtil;

/**
 * 发送短信公共类
*/

public class MobileUtil {
	
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(UUIDUtil.class);
	
	/**
	 * addr:手机号
	 * content：发送内容
	 * sendType:实时：1,批量2(暂时只支持实时发送)
	 *返回值为json字符串
	 * @throws Exception 
	*/
	@SuppressWarnings("rawtypes")
	public static String sendMobileMsg(String addr,String content,String sendType) throws Exception{
		
		HttpProxyClientPost httpProxy = new HttpProxyClientPost();
		
		/*if(RedisCacheDao.read(addr)==null||"".equals(RedisCacheDao.read(addr))){
			RedisCacheDao.set(addr, content, 60*10);//
		}else{
			return null;
		}*/
		
		///拼接传入参数
		Map<String,Object> message= new HashMap<String,Object>();
		String bizId = UUIDUtil.genUUIDString();
		//log.info("bizId="+bizId);
		message.put("bizId", bizId);
		Properties property = ClassLoaderUtil.getProperties("app_system.properties");
		message.put("systemSourceId",property.get("SYSTEM_SOURCE_ID"));
		message.put("sendType", sendType);
		List<Map> list = new ArrayList<Map>();
		Map<String,Object> map= new HashMap<String,Object>();
		StringBuffer sb = new StringBuffer();
		String bizId1 = UUIDUtil.genUUIDString();
		map.put("addr", addr);
		map.put("bizId", bizId1);
		sb.append("{\'content\':\'").append(content).append("\'}");
		map.put("content", sb.toString());
		list.add(map);
		message.put("sendBillList", list);
		log.info("message="+message);
		log.info("将要发送手机验证码到目标手机号...");
		log.info("手机号为："+addr);
		//调用发短信接口，发送短信，传message给他
		//String retJson = httpProxy.sendBill(message);
		
		return httpProxy.sendBill(message);
	}
	
	/**
	 *传入参数：map内容包含：addr:手机号， content：发送内容 ， sendType:实时：1,批量2(暂时只支持实时发送)
	 *返回值为json字符串
	 * @throws Exception 
	*/
	@SuppressWarnings("rawtypes")
	public static String sendMobileMsgByMap(Map<String,Object> map) throws Exception{
		
		HttpProxyClientPost httpProxy = new HttpProxyClientPost();
		
		String addr = (String)map.get("addr");
		String content = (String)map.get("content");
		String sendType = (String)map.get("sendType");
		
		///拼接传入参数
		Map<String,Object> message= new HashMap<String,Object>();
		String bizId = UUIDUtil.genUUIDString();
		//log.info("bizId="+bizId);
		message.put("bizId", bizId);
		Properties property = ClassLoaderUtil.getProperties("app_system.properties");
		message.put("systemSourceId",property.get("SYSTEM_SOURCE_ID"));
		message.put("sendType", sendType);
		List<Map> list = new ArrayList<Map>();
		Map<String,Object> mapTT= new HashMap<String,Object>();
		StringBuffer sb = new StringBuffer();
		String bizIdAnother = UUIDUtil.genUUIDString();
		mapTT.put("addr", addr);
		mapTT.put("bizId", bizIdAnother);
		sb.append("{\'content\':\'").append(content).append("\'}");
		mapTT.put("content", sb.toString());
		list.add(mapTT);
		message.put("sendBillList", list);
		log.info("message="+message);
		log.info("将要发送手机验证码到目标手机号...");
		log.info("手机号为："+addr);
		//调用发短信接口，发送短信，传message给他
		//String retJson = httpProxy.sendBill(message);
		
		return httpProxy.sendBill(message);
	}
	
	
	

}
