package com.hz.app.index.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hz.app.index.service.ModelService;
import com.hz.app.index.vo.AllInfo;
import com.hz.app.index.vo.AvailableInfo;
import com.hz.app.index.vo.User;
import com.hz.app.index.vo.WinXinEntity;
import com.hz.core.framework.base.BaseController;
import com.hz.core.framework.redis.RedisCacheDao;
import com.hz.core.util.SDKTestSendTemplateSMS;
import com.hz.core.util.UUIDUtil;
import com.hz.util.common.SendDataToUrl;

@Controller
@RequestMapping(value = "/model")
public class ModelController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(ModelController.class);

	@Autowired
	ModelService userService;

	/**
	 * 登录接口,根据用户名密码判断是否正确
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void loginByUserInfo(HttpServletRequest request, HttpServletResponse response) {
		String uuid = UUIDUtil.genUUIDString();// UUID
		String keyCode = uuid.substring(16) + "9iK64Rd8";// TOKEN
		try {
			Map<String, Object> params = getParameters(request);
			HashMap<String,Object> resHashMap = new HashMap<String,Object>();
			String uuidByUser = UUIDUtil.genUUIDString();
			String mobile = (String)params.get("mobile");
			String redisUUID = uuidByUser.substring(11) + mobile;
			resHashMap.put("userToken", redisUUID);
			resHashMap.put("keyCode", uuid);
			resHashMap.put("hasRealName", "0");
			resHashMap.put("userLevel", "0");
			resHashMap.put("invitationCode", "");
			resHashMap.put("invitationCodeUrl", "");
			String reqWxUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxaedcd88802a81fc7&redirect_uri=http://www.zjzclink.com/zwchat/userInfo?mobile="+mobile+"&response_type=code&scope=snsapi_userinfo&state=3&#wechat_redirect";
			resHashMap.put("wechatCodeUrl", reqWxUrl);
			resHashMap.put("openid", userService.getOpenId(params));
			Object userObject = userService.loginByUser(params);
			if(userObject!=null){
				User user = (User)userObject;
				user.setToken(keyCode);//加密key
				user.setUserToken(redisUUID);
				user.setKeyCode(uuid);
				getKey(uuidByUser.substring(11), mobile, user);
				new Thread(new RequestWx(reqWxUrl)).start();
				logger.debug("存入的keycode::"+uuid+":::usertoken:::"+redisUUID+"::::token::::"+keyCode);
				write(response, "Success", resHashMap);
			}else{
				write(response, "UPLoginError", null);// 登录成功后返回相关的信息
			}
			
			//log.debug(userService.loginByUser(null));
						
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	
	
	
	
	/**
	 * 固定登录
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/wechatlogin", method = RequestMethod.POST)
	public void wechatlogin(HttpServletRequest request, HttpServletResponse response) {
		//String uuid = UUIDUtil.genUUIDString();// UUID
		//String keyCode = uuid.substring(16) + "9iK64Rd8";// TOKEN
		try {
			Map<String, Object> params = getParameters(request);
			HashMap<String,Object> resHashMap = new HashMap<String,Object>();
			//String uuidByUser = UUIDUtil.genUUIDString();
			Object openidObject = params.get("openid");
			String mobile = userService.getMobile(params);
			//String redisUUID = uuidByUser.substring(11) + mobile;
			//resHashMap.put("userToken", redisUUID);
			logger.info("==========>手机号"+mobile);
			resHashMap.put("hasRealName", "0");
			resHashMap.put("userLevel", "0");
			resHashMap.put("invitationCode", "");
			resHashMap.put("invitationCodeUrl", "");
			resHashMap.put("mobile", mobile);
			params.put("mobile", mobile);
			Object userToken = RedisCacheDao.read(mobile);
			Object userObject  = RedisCacheDao.getObjByKey((String)userToken);
			//Object userObject = userService.wchatUser(params);
			if(openidObject!=null && !"".equals(openidObject)){
				User user = (User)userObject;
				//user.setToken(user.getToken());//加密key
				//user.setUserToken(user.getUserToken());//加密key
				resHashMap.put("userToken", user.getUserToken());
				resHashMap.put("keyCode", user.getKeyCode());
				logger.debug("取出的keycode::"+user.getKeyCode()+":::usertoken:::"+user.getUserToken()+"::::token::::"+user.getToken());
				//getKey(user.getUserToken().substring(11), mobile, user);
				write(response, "Success", resHashMap);
			}else{
				write(response, "UPLoginError", null);// 登录成功后返回相关的信息
			}
			
			//log.debug(userService.loginByUser(null));
			
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	
	
	/**
	 * 发送短信验证码
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/sendSms", method = RequestMethod.POST)
	public void sendSms(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> params = getParameters(request);
			HashMap<String,Object> resHashMap = new HashMap<String,Object>();
			String mobile = String.valueOf(params.get("mobile"));
			String action = String.valueOf(params.get("action"));
			Object redisVale = RedisCacheDao.read(mobile+"_"+action);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = new Date();
			if(redisVale==null){
				int rd = (int)((Math.random()*9+1)*100000);
				String currentTime = sdf.format(d);
				System.out.println("当前时间：" + currentTime+"=======>短信验证码为:"+rd);
				RedisCacheDao.set(mobile+"_"+action, rd+"_"+currentTime,60);
				new Thread(new SendSms(mobile,action,String.valueOf(rd))).start();
				write(response, "sms_sendSucess", resHashMap);
			}else{
				String fromTime = String.valueOf(redisVale).split("_")[1];
				String toTime = sdf.format(d);  
				long from = sdf.parse(fromTime).getTime();  
				long to = sdf.parse(toTime).getTime();  
				int seconds = (int) ((to - from)/(1000));
				System.out.println("时间差：========"+seconds+"==>"+fromTime+"=====>"+toTime);
				if(seconds<60){
					write(response, "time_short", null);
				}else{
					int rd = (int)((Math.random()*9+1)*100000);
					String currentTime = sdf.format(d);
					System.out.println("当前时间：" + currentTime);
					RedisCacheDao.set(mobile+"_"+action, rd+"_"+currentTime,60);
					new Thread(new SendSms(mobile,action,String.valueOf(rd))).start();
					write(response, "sms_sendSucess", null);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	
	
	/**
	 * 获取用户微信昵称和头像
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/getchatinfo", method = RequestMethod.POST)
	public void getchatinfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> params = getParameters(request);
//			String mobile = (String)params.get("mobile");
//			String code = (String)params.get("code");
			Map<String,Object> userObject = userService.getChatInfo(params);
			write(response, "Success", userObject);
		} catch (Exception e) {
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	
	
	/**
	 * http://www.zjzclink.com/zwchat/cs
	 * 获取用户微信昵称和头像
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/signShare", method = RequestMethod.POST)
	public void signShare(HttpServletRequest request, HttpServletResponse response) {
		String url = "http://www.zjzclink.com/zwchat/shareSign";
		try {
			Map<String, Object> params = getParameters(request);
//			String urlParam = (String)params.get("url");
//			String mobile = (String)params.get("mobile");
//			String code = (String)params.get("code");
			//Map<String,Object> userObject = userService.getChatInfo(params);
			String resDecrypt = SendDataToUrl.getDataToUrl(url,com.alibaba.fastjson.JSONObject.toJSONString(params),"POST");//原文
			WinXinEntity winXinEntity=com.alibaba.fastjson.JSON.parseObject(resDecrypt,WinXinEntity.class);
			System.out.println("============================>"+winXinEntity);
			
			String title="瑞金互联—资金资产金融信息平台等你来！";
			String subtitle="还在翻阅微信群里海量的信息吗？瑞金互联-资金资产金融信息平台，让金融对接更高效、更简单！";
			String icon="http://www.zjzclink.com/web/image/logo.png";
			String resUrl="http://www.zjzclink.com/web";
			HashMap<String,Object> hash = new HashMap<String,Object>();
			hash.put("title", title);
			hash.put("subtitle", subtitle);
			hash.put("icon", icon);
			hash.put("url", resUrl);


			HashMap<String,Object> hash1 = new HashMap<String,Object>();
			
			hash1.put("appid", "wxaedcd88802a81fc7");
			hash1.put("timestamp", winXinEntity.getTimestamp());
			hash1.put("nonce", winXinEntity.getNoncestr());
			hash1.put("signature", winXinEntity.getSignature());
			
			hash.put("wechatData", hash1);
			
			write(response, "Success", hash);
		} catch (Exception e) {
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}

	
	/**
	 * 消息列表
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/messageList", method = RequestMethod.POST)
	public void messageList(HttpServletRequest request, HttpServletResponse response) {
		
		try {
		Map<String, Object> params = getParameters(request);
		List<HashMap<String,Object>> list = userService.messageList(params);
		HashMap<String,Object> hashMapobject = new HashMap<String,Object>();
		hashMapobject.put("listMessage",list);
		write(response, "Success", hashMapobject);// 登录成功后返回相关的信息
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}

	/**
	 * banner列表
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/bannerList", method = RequestMethod.POST)
	public void bannerList(HttpServletRequest request, HttpServletResponse response) {
		
		try {
		Map<String, Object> params = getParameters(request);
		List<HashMap<String,Object>> list = userService.bannerList(params);
		HashMap<String,Object> hashMapobject = new HashMap<String,Object>();
		hashMapobject.put("listBanner",list);
		write(response, "Success", hashMapobject);// 登录成功后返回相关的信息
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	/**
	 * 资产列表
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/zjzcList", method = RequestMethod.POST)
	public void zjzcList(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			Map<String, Object> params = getParameters(request);
			List<HashMap<String,Object>> list = userService.zjzcList(params);
			HashMap<String,Object> hashMapobject = new HashMap<String,Object>();
			hashMapobject.put("listProject",list);
			write(response, "Success", hashMapobject);// 登录成功后返回相关的信息
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	
	/**
	 * 行业报告列表
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/hyreportList", method = RequestMethod.POST)
	public void hyreportList(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			Map<String, Object> params = getParameters(request);
			Object object= params.get("data");
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(object);
			
			int    pageIndex = 0;
			Object pageIndexObject = jsonObject.get("pageIndex");
			if(pageIndexObject!=null){
				pageIndex = Integer.parseInt(String.valueOf(jsonObject.get("pageIndex")));
			}
			Object pageSizeObject = jsonObject.get("pageSize");
			if(pageSizeObject==null)  pageSizeObject=10;
			int    pageSize = Integer.parseInt(String.valueOf(pageSizeObject));
			params.put("pageSize", pageSize);
			params.put("pageIndex", pageIndex);
			
			
			
			List<HashMap<String,Object>> list = userService.hyreportList(params);
			HashMap<String,Object> hashMapobject = new HashMap<String,Object>();
			int resCount = userService.hyreportCount(params);
			hashMapobject.put("pageCount", String.valueOf(resCount / pageSize + (resCount % pageSize == 0 ? 0 : 1)));
			hashMapobject.put("itemCount", String.valueOf(resCount));
			
			hashMapobject.put("listReport",list);
			write(response, "Success", hashMapobject);// 登录成功后返回相关的信息
		} catch (Exception e) {
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	
	
	
	
	
	/**
	 * 行业资讯列表
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/hyzxList", method = RequestMethod.POST)
	public void hyzxList(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			Map<String, Object> params = getParameters(request);
			Object object= params.get("data");
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(object);
			
			int    pageIndex = 0;
			Object pageIndexObject = jsonObject.get("pageIndex");
			if(pageIndexObject!=null){
				pageIndex = Integer.parseInt(String.valueOf(jsonObject.get("pageIndex")));
			}
			Object pageSizeObject = jsonObject.get("pageSize");
			if(pageSizeObject==null)  pageSizeObject=10;
			int    pageSize = Integer.parseInt(String.valueOf(pageSizeObject));
			params.put("pageSize", pageSize);
			params.put("pageIndex", pageIndex);
			
			
			
			List<HashMap<String,Object>> list = userService.hyzxList(params);
			HashMap<String,Object> hashMapobject = new HashMap<String,Object>();
			int resCount = userService.hyreportCount(params);
			hashMapobject.put("pageCount", String.valueOf(resCount / pageSize + (resCount % pageSize == 0 ? 0 : 1)));
			hashMapobject.put("itemCount", String.valueOf(resCount));
			
			hashMapobject.put("listInfo",list);
			write(response, "Success", hashMapobject);// 登录成功后返回相关的信息
		} catch (Exception e) {
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	/**
	 * 关注列表
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/focusList", method = RequestMethod.POST)
	public void focusList(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			Map<String, Object> params = getParameters(request);
			String userToken = (String)params.get("userToken");
			User userObject = (User)RedisCacheDao.getObjByKey(userToken);
			params.put("userId", userObject.getId());
			int    pageIndex = 0;
			Object pageIndexObject = params.get("pageIndex");
			if(pageIndexObject!=null){
				pageIndex = Integer.parseInt(String.valueOf(params.get("pageIndex")));
			}
			Object pageSizeObject = params.get("pageSize");
			if(pageSizeObject==null)  pageSizeObject=10;
			int    pageSize = Integer.parseInt(String.valueOf(pageSizeObject));
			params.put("pageSize", pageSize);
			params.put("pageIndex", pageIndex);
			
			List<HashMap<String,Object>> list = userService.focusList(params);
			HashMap<String,Object> hashMapobject = new HashMap<String,Object>();
			hashMapobject.put("listFocus",list);
			hashMapobject.put("pageCount", String.valueOf(list.size() / pageSize + (list.size() % pageSize == 0 ? 0 : 1)));
			hashMapobject.put("itemCount", String.valueOf(list.size()));
			
			write(response, "Success", hashMapobject);// 登录成功后返回相关的信息
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	/**
	 * 行业报告详情
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/hyreportInfo", method = RequestMethod.POST)
	public void hyreportInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getParameters(request);
		try {
			
			String userToken = (String)params.get("userToken");
			User userObject = (User)RedisCacheDao.getObjByKey(userToken);
			String mobile = userObject.getMobile();
			params.put("mobile", mobile);
			params.put("type", "1");
			HashMap<String,Object> objectHashMap = userService.getUserInfo(params);
			int numReport = Integer.parseInt((String)objectHashMap.get("numReportYet"));
			String level = (String)objectHashMap.get("level");
			int count = 0;
			if(!"2".equals(level)){
			if("0".equals(level)){
				count = 2;
			}else if("1".equals(level)){
				count  = 6;
			}
			logger.info("共多少次："+numReport);
			if("0".equals(level)){//普通用户
				int reportCount  = userService.getLogById(params);
				logger.info("存在这条记录吗?"+reportCount);
				if(reportCount==0){
					if(numReport<=0){
						write(response, "qx_time", null);
	                    return;					
					}else{
						int res = userService.queryUserInfo(params);
						logger.info("已存入多少条"+res+"=====>小于"+count+"次吗?");
						if(res<count){
							logger.info("小于执行:=====>"+res+"====="+count);
							userService.updateUserInfo(params);
						}
					}
				 }
			}
			
			if("1".equals(level)){
				if(userService.getLogById(params)==0){
					if(numReport<=0){
						write(response, "qx_time", null);
	                    return;					
					}else{
						int res = userService.queryUserInfo(params);
						logger.info("次数===222====="+res);
						if(res<count){
							logger.info("次数====3333===="+res);
							userService.updateUserInfo(params);
						}
					}
				 }
			}
			
			}
			HashMap<String,Object> objectHashMaps = userService.hyreportInfo(params);
			objectHashMaps.put("urlDownload", "http://www.zjzclink.com:81/zjzc/files"+objectHashMaps.get("urlDownload"));
			write(response, "Success", objectHashMaps);
		} catch (Exception e) {
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	/**
	 * 获取隐私策略
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/getYscl", method = RequestMethod.POST)
	public void getYscl(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> params = getParameters(request);
		try {
			String userToken = (String)params.get("userToken");
			User userObject = (User)RedisCacheDao.getObjByKey(userToken);
			params.put("mobile", userObject.getMobile());
			HashMap<String,Object> objectHashMap = userService.getYscl(params);
			write(response, "Success", objectHashMap);
		} catch (Exception e) {
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	
	/**
	 * 获取用户信息
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
	public void getUserInfo(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> params = getParameters(request);
		try {
			String userToken = (String)params.get("userToken");
			User userObject = (User)RedisCacheDao.getObjByKey(userToken);
			params.put("mobile", userObject.getMobile());
			HashMap<String,Object> objectHashMap = userService.getUserInfo(params);
			String level = (String)objectHashMap.get("level");
			String numInfo = (String)objectHashMap.get("numInfo");
			String numReport = (String)objectHashMap.get("numReport");
			String numPublish = (String)objectHashMap.get("numPublish");
			String numGroup = (String)objectHashMap.get("numGroup");
			String numInfoYet = (String)objectHashMap.get("numInfoYet");
			String numReportYet = (String)objectHashMap.get("numReportYet");
			String numPublishYet = (String)objectHashMap.get("numPublishYet");
			String numGroupYet = (String)objectHashMap.get("numGroupYet");
			
			AllInfo  allInfo = new AllInfo();
			allInfo.setNumGroup(numGroup);
			allInfo.setNumInfo(numInfo);
			allInfo.setNumPublish(numPublish);
			allInfo.setNumReport(numReport);
			
			AvailableInfo  availableInfo = new AvailableInfo();
			availableInfo.setNumGroup(numGroupYet);
			availableInfo.setNumInfo(numInfoYet);
			availableInfo.setNumPublish(numPublishYet);
			availableInfo.setNumReport(numReportYet);
			
			objectHashMap.put("level", level);
			objectHashMap.put("allInfo", allInfo);
			objectHashMap.put("availableInfo", availableInfo);
			
			write(response, "Success", objectHashMap);
		} catch (Exception e) {
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	/**
	 * 设置隐私策略
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/setYscl", method = RequestMethod.POST)
	public void setYscl(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> params = getParameters(request);
		try {
			String userToken = (String)params.get("userToken");
			User userObject = (User)RedisCacheDao.getObjByKey(userToken);
			params.put("mobile", userObject.getMobile());
			userService.setYscl(params);
			write(response, "Success", null);
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	/**
	 * 用户信息
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/userInfo", method = RequestMethod.POST)
	public void userInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getParameters(request);
		try {
			String userToken = (String)params.get("userToken");
			User userObject = (User)RedisCacheDao.getObjByKey(userToken);
			params.put("userId", userObject.getId());
			HashMap<String,Object> objectHashMap = userService.userInfo(params);
			write(response, "Success", objectHashMap);
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	/**
	 * 消息详情
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/messageInfo", method = RequestMethod.POST)
	public void messageInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getParameters(request);
		try {
			HashMap<String,Object> objectHashMap = userService.messageInfo(params);
			write(response, "Success", objectHashMap);
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	/**
	 * 发布者主页信息 
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/fbUserInfo", method = RequestMethod.POST)
	public void fbUserInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> params = getParameters(request);
			String userToken = (String)params.get("userToken");
			User userObject = (User)RedisCacheDao.getObjByKey(userToken);
			String userId = userObject.getId();//我的USERID
			HashMap<String,String> objMap = new HashMap<String,String>();
			objMap.put("myUserId", userId);
			objMap.put("ToUserId", (String)params.get("userId"));
			boolean existFocus = userService.existFocus(objMap);
			String focus = "1";
			if(existFocus) focus = "0";
			
			HashMap<String,Object> objectHashMap = userService.userInfo(params);
			HashMap<String,Object> so = new HashMap<String,Object>();
			so.put("userId", objectHashMap.get("userId"));
			so.put("avatarUrl", objectHashMap.get("avatarUrl"));
			so.put("level", "0");
			so.put("nickname", objectHashMap.get("nickname"));
			so.put("focus","0");
			so.put("fans", "0");
			
			so.put("hasFocus", focus);
			write(response, "Success", so);// 登录成功后返回相关的信息
		} catch (Exception e) {
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	
	/**
	 * 资产详情
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/zjzcInfo", method = RequestMethod.POST)
	public void zjzcInfo(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			Map<String, Object> params = getParameters(request);
			HashMap<String,Object> objectHashMap = userService.zjzcInfo(params);
			write(response, "Success", objectHashMap);
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	
	/**
	 * 圈子列表
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/qList", method = RequestMethod.POST)
	public void qList(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			Map<String, Object> params = getParameters(request);
			Object object= params.get("data");
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(object);
			
			int    pageIndex = 0;
			Object pageIndexObject = jsonObject.get("pageIndex");
			if(pageIndexObject!=null){
				pageIndex = Integer.parseInt(String.valueOf(jsonObject.get("pageIndex")));
			}
			Object pageSizeObject = jsonObject.get("pageSize");
			if(pageSizeObject==null)  pageSizeObject=10;
			int    pageSize = Integer.parseInt(String.valueOf(pageSizeObject));
			params.put("pageSize", pageSize);
			params.put("pageIndex", pageIndex);
			
			List<HashMap<String,Object>> list = userService.qList(params);
			HashMap<String,Object> hashMapobject = new HashMap<String,Object>();
			hashMapobject.put("listCircle",list);
			int resCount = userService.qListCount(params);
			hashMapobject.put("pageCount", String.valueOf(resCount / pageSize + (resCount % pageSize == 0 ? 0 : 1)));
			hashMapobject.put("itemCount", String.valueOf(resCount));
			
			write(response, "Success", hashMapobject);// 登录成功后返回相关的信息
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	
	/**
	 * 注册接口
	 * @return
	 */
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void register(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getParameters(request);
		try {
		  String smsCode = (String)params.get("smsCode");	
		  String mobile = (String)params.get("mobile");	
		  Object redisVale = RedisCacheDao.read(mobile+"_1");
		  if(redisVale!=null){
			  String code = String.valueOf(redisVale).split("_")[0];
			  if(!smsCode.equals(code)){//不相等
				  System.out.println("验证码有问题================");
				  write(response, "Reg_failure", null);
			  }else{//相等逻辑判断
				  int res = userService.register(params);
				  if(res==1){
					  write(response, "Success", null); 	  
				  }else  if(res==2){
					  write(response, "Reg_existuser", null);
				  }else{
					  write(response, "Reg_failure", null);
				  }
			  }
		  }else{
			  write(response, "sorryUser", null);
		  }
		  
		} catch (Exception e) {
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	
	/**
	 * 关注接口
	 * @return
	 */
	
	@RequestMapping(value = "/focus", method = RequestMethod.POST)
	public void focus(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getParameters(request);
		try {
			String userToken = (String)params.get("userToken");
			User userObject = (User)RedisCacheDao.getObjByKey(userToken);
			params.put("touserId", params.get("userId"));
			params.put("userId", userObject.getId());
            userService.focus(params);
		    write(response, "Success", null);
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	
	/**
	 * 重置密码
	 * @return
	 */
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public void resetPassword(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getParameters(request);
		try {
			int res = userService.resetPassword(params);
			if(res>0){
				write(response, "Success", null);
			}else{
				write(response, "CzLoginPwdFail", null);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public void updatePassword(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getParameters(request);
		try {
			String userToken = (String)params.get("userToken");
			User userObject = (User)RedisCacheDao.getObjByKey(userToken);
			params.put("mobile", userObject.getMobile());
			int res = userService.updatePassword(params);
			if(res>0){
				write(response, "Success", null);
			}else{
				write(response, "UpLoginPwdFail", null);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	/**
	 * 登出、注销
	 * 
	 * @param request
	 *            response
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		String mobile = "";
		try {
			Map<String, Object> params = getParameters(request);
			String userToken = (String) params.get("UserToken");
			mobile = userToken.substring(21);
			RedisCacheDao.delete(userToken);
			RedisCacheDao.delete(mobile);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		logger.debug("<<<<<<<<<<>>>>>>>>>>>注销成功-:"+mobile);
		write(response, "Success", "注销成功");
	}
	
	private void getKey(String preuid, String mobile, User userObject) {
		Object newUserToken = RedisCacheDao.read(mobile);
		if(newUserToken!=null && !"".equals(newUserToken)){
		    Object  objectNewUserToken  = RedisCacheDao.read((String)newUserToken);
			if(objectNewUserToken!=null && !"".equals(objectNewUserToken)){
			     RedisCacheDao.set(String.valueOf(newUserToken),"",60*60*24*200);
			     RedisCacheDao.set(mobile,preuid+mobile,60*60*24*200);
			     RedisCacheDao.set(preuid+mobile,userObject,60*60*24*200);
			}
		}else{
			 RedisCacheDao.set(mobile,preuid+mobile,60*60*24*200);
			 RedisCacheDao.set(preuid+mobile,userObject,60*60*24*200);
		}
	}
	
	private class RequestWx implements Runnable{
		private String reqWxUrl;
		public RequestWx(String reqWxUrl) {
			// TODO Auto-generated constructor stub
			this.reqWxUrl = reqWxUrl;
		}
		@Override
		public void run() {
			try {
				SendDataToUrl.doHttpsGetJson(reqWxUrl);
				System.out.println("=================>"+reqWxUrl);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		@Override
		public String toString() {
			return "reqWxUrl [reqWxUrl=" + reqWxUrl +"]";
		}
	}
	
	
	private class SendSms implements Runnable{
		private String mobile;
		private String type;
		private String content;
		public SendSms(String mobile,String type,String content) {
			// TODO Auto-generated constructor stub
			this.mobile = mobile;
			this.type = type;
			this.content = content;
		}
		@Override
		public void run() {
			try {
				SDKTestSendTemplateSMS sdkSms = new SDKTestSendTemplateSMS();
				sdkSms.sendSms(mobile, type, content);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		@Override
		public String toString() {
			return "SendSms [mobile=" + mobile +",+"+type+"+"+content+"]";
		}
	}

}
