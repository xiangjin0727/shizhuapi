package com.hz.app.servlet;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hz.app.api.util.JsonUtil;
import com.hz.constant.CodeEnum;
import com.hz.core.util.ClassLoaderUtil;
import com.hz.util.DESede;
import com.hz.util.common.ServletReqUtils;

/**
 * <b>Description:</b><br> 基类Controller
 * @author <a href="http://www.hzdjr.com" target="_blank">yansf</a>
 * @version 2.0
 * @Note
 * <b>ProjectName:</b> hzdjr-mobile
 * <br><b>PackageName:</b> com.hz.hzdjr.base.controller
 * <br><b>ClassName:</b> BaseController
 * <br><b>Date:</b> 2017年4月7日 下午4:25:23
 */
public abstract class BaseController{
	/**
	 * 将查询结果的json数据响应到客户端
	 * 
	 */
	private static String  ContentType = "text/json;charset=utf-8";
	private static String  CharacterEncoding = "UTF-8";
	private static Properties properties = ClassLoaderUtil.getProperties("app_system.properties");
	private static Logger logger = LoggerFactory.getLogger(BaseController.class);

	private static String  InvitationCode_Key="07e02fc9035e4b06b624f30f";
	protected static final String REDIS_VI_K = "verification_code_";
	
	/**
	 * 
	 * <b>Description:</b><br> 获取请求参数
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @Note
	 * <b>Author:</b> <a href="http://www.hzdjr.com" target="_blank">yansf</a>
	 * <br><b>Date:</b> 2017年4月7日 下午4:30:56
	 * <br><b>Version:</b> 2.0
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getParameters(HttpServletRequest request) throws UnsupportedEncodingException {
		String reqMsg = ServletReqUtils.getRequestStr(request);
		Map<String, Object> reqMapObject = JsonUtil.convJsonToMapO(reqMsg);
		
		return reqMapObject;
	}
	
	/**
	 * <b>Description:</b><br> 构造返回数据
	 * @param response
	 * @param msg         信息描述
	 * @param jsonObject  返回数据
	 * @Note
	 * <b>Author:</b> <a href="http://www.hzdjr.com" target="_blank">yansf</a>
	 * <br><b>Date:</b> 2017年4月7日 下午4:26:02
	 * <br><b>Version:</b> 2.0
	 */
	public  void write(HttpServletResponse response , String  msg ,  Object jsonObject){
		PrintWriter outObject = null;
		try {
			response.setCharacterEncoding(ContentType);
			response.setCharacterEncoding(CharacterEncoding);
			outObject = response.getWriter();
			if(msg==null||"".equals(msg)){
		       msg = "Fail";
			}
			HashMap<String,Object>  objectHashMap = new HashMap<String,Object>();
			if(jsonObject instanceof List){//集合
				objectHashMap.put("list", jsonObject);
				outObject.print(getResult(msg, objectHashMap));
			}else if(jsonObject instanceof String){//字符串
				objectHashMap.put("name", jsonObject);
				outObject.print(getResult(msg, objectHashMap));
			}else{//HashMap
				outObject.print(getResult(msg, jsonObject));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			msg = "Fail";
			outObject.print(getResult(msg, jsonObject));
		}
		
	}
	/**
	 * <b>Description:</b><br> 构造错误信息
	 * @param response
	 * @param msg       信息描述
	 * @Note
	 * <b>Author:</b> <a href="http://www.hzdjr.com" target="_blank">yansf</a>
	 * <br><b>Date:</b> 2017年4月7日 下午4:26:27
	 * <br><b>Version:</b> 2.0
	 */
	public  void write(HttpServletResponse response , String  msg){
		PrintWriter outObject = null;
		try {
			response.setCharacterEncoding(ContentType);
			response.setCharacterEncoding(CharacterEncoding);
			outObject = response.getWriter();
			outObject.print(getResult(msg));
		} catch (Exception e) {
			e.printStackTrace();
			outObject.print(msg);
		}
	}
	
	/**
	 * <b>Description:</b><br> 对结果进行封装
	 * @param alias        信息描述
	 * @param resObject    返回数据
	 * @return
	 * @Note
	 * <b>Author:</b> <a href="http://www.hzdjr.com" target="_blank">yansf</a>
	 * <br><b>Date:</b> 2017年4月7日 下午4:26:54
	 * <br><b>Version:</b> 2.0
	 */
	private String getResult(String alias, Object resObject){
    	Map<String, Object> mapResult = new HashMap<String,Object>();
        mapResult.put("Code",CodeEnum.valueOf(alias).getIndex());
        mapResult.put("Message", CodeEnum.valueOf(alias).getName());
        mapResult.put("Body", resObject);
        logger.info(com.alibaba.fastjson.JSONObject.toJSONString(mapResult));
        return  com.alibaba.fastjson.JSONObject.toJSONString(mapResult);
    }
	/**
	 * <b>Description:</b><br> 对结果进行封装
	 * @param alias 信息描述
	 * @return
	 * @Note
	 * <b>Author:</b> <a href="http://www.hzdjr.com" target="_blank">yansf</a>
	 * <br><b>Date:</b> 2017年4月7日 下午4:27:14
	 * <br><b>Version:</b> 2.0
	 */
	private String getResult(String alias){
    	Map<String, Object> mapResult = new HashMap<String,Object>();
        mapResult.put("Code", "0000");
        mapResult.put("Message", alias);
        logger.info(com.alibaba.fastjson.JSONObject.toJSONString(mapResult));
        return  com.alibaba.fastjson.JSONObject.toJSONString(mapResult);
    }
	
	/**
	 * <b>Description:</b><br> 获取短信验证码开关
	 * @return
	 * @Note
	 * <b>Author:</b> <a href="http://www.hzdjr.com" target="_blank">yansf</a>
	 * <br><b>Date:</b> 2017年4月7日 下午4:27:29
	 * <br><b>Version:</b> 2.0
	 */
	public Boolean getSmsProperties(){
		
		return properties.getProperty("sms.flag.value", "true").equals("true");
	}

	/**
	 * 分享文案加密
	 * @throws Exception
	 */
	public String ShareInformationEncryption(String str) throws Exception{

		return str2HexStr(DESede.encryptCBC(InvitationCode_Key, str));
	}

	/**
	 * 分享文案解密
	 * @throws Exception
	 */
	public String ShareInformationDcryption(String str) throws Exception{
		String information=hexStr2Str(str);
		information=DESede.decryptCBC(InvitationCode_Key,information);
		return information;
	}

	/**   
	  * 字符串 转换 十六进制
	  * @param String str Byte字符串(Byte之间无分隔符 如:[616C6B])  
	  * @return String 对应的字符串  
	  */
	public static String str2HexStr(String str)
	{
		char[] chars ="0123456789ABCDEF".toCharArray();
		StringBuilder sb=new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length;i++)
		{
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString().trim();
	}

	/**   
	  * 十六进制转换字符串  
	  * @param String str Byte字符串(Byte之间无分隔符 如:[616C6B])  
	  * @return String 对应的字符串  
	  */
	public static String hexStr2Str(String hexStr)
	{
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes=new byte[hexStr.length()/2];
		int n;
		for (int i = 0; i < bytes.length;i++)
		{
			n = str.indexOf(hexs[2 * i]) *16;
			n += str.indexOf(hexs[2 * i +1]);
			bytes[i] =(byte)(n&0xff);
		}
		return new String(bytes);
	}

}