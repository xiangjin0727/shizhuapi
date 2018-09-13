package com.hz.app.api;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.hz.core.util.ClassLoaderUtil;
import com.hz.util.common.ResMsgUtils;
import com.hz.util.common.SendDataToUrl;

public class NoTransServer {
	
	private static String ServiceId = "ServiceId";
	
	private static Logger logger = LoggerFactory.getLogger(NoTransServer.class);
	/**
	 * 
	 * <b>Description:</b><br> 对客户端请求的参数进行验证其合法性
	 * @param serviceId    业务编号
	 * @param reqMsg       客户端数据
	 * @param request
	 * @param version      版本号
	 * @return
	 * @Note
	 * <b>Author:</b>
	 * <br><b>Date:</b> 2017年4月7日 下午4:58:27
	 * <br><b>Version:</b> 2.0
	 */
	public  static String reqHttpData(Object serviceId,String  reqMsg,HttpServletRequest request,String version){
			  String serviceUrl = "";
			  int type = 0;
				try {
					serviceUrl = ServletEnum.valueOf((String)serviceId).getUrl();										
				} catch (Exception e) {
					e.printStackTrace();
					return  ResMsgUtils.resErrorMsg((String)serviceId,"网络超时，请稍后再试");
				}
				try {
					return  DataHandler((String)serviceId,serviceUrl,reqMsg,request,version);
				} catch (Exception e) {
					e.printStackTrace();
					return  ResMsgUtils.resErrorMsg((String)serviceId,"系统网络异常");
				}
	} 
	
	/**
	 * <b>Description:</b><br> 映射URL和数据，进行业务处理
	 * @param serviceId           业务编码
	 * @param postBussinessUrl    映射url
	 * @param reqMsg              接收客户端的数据
	 * @param request
	 * @param version             版本号
	 * @return
	 * @throws Exception
	 * @Note
	 * <b>Author:</b>
	 * <br><b>Date:</b> 2017年4月7日 下午4:56:17
	 * <br><b>Version:</b> 2.0
	 */
	public static String DataHandler(String serviceId,String postBussinessUrl,String reqMsg,HttpServletRequest request,String version) throws Exception{
		String preUrl = "";
		logger.info("serviceId: " + serviceId + "postBussinessUrl: " + postBussinessUrl + "reqMsg: " + reqMsg + "version: " + version);

		Properties property = ClassLoaderUtil.getProperties("app_system.properties");
		preUrl = property.getProperty("ShiZuConstant.URL")+request.getContextPath()+"/";

		logger.info("请求数据=====>"+reqMsg);
		logger.info("跳转的URL路径=====>"+preUrl+postBussinessUrl);
		String resDecrypt = SendDataToUrl.getDataToUrl(preUrl+postBussinessUrl,reqMsg,"POST");//原文
		logger.info("返回给客户端的数据=====>"+resDecrypt);
		//net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(resDecrypt);
		//jsonObject.put(ServiceId, serviceId);
		return  resDecrypt;
	}
	
	
}
