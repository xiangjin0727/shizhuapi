package com.hz.app.index.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hz.app.index.service.PublishService;
import com.hz.app.index.service.ModelService;
import com.hz.app.index.vo.User;
import com.hz.core.framework.base.BaseController;
import com.hz.core.framework.redis.RedisCacheDao;
import com.hz.core.util.UUIDUtil;

@Controller
@RequestMapping(value = "/publish")
public class PublishController extends BaseController {

	private static Logger log = Logger.getLogger(PublishController.class);

	@Autowired
	PublishService publishService;
	@Autowired
	ModelService userService;

	/**
	 * 资金
	 * @return
	 */
	
	@RequestMapping(value = "/zj", method = RequestMethod.POST)
	public void publishZj(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getParameters(request);
		try {
			String userToken = (String)params.get("userToken");
			User userObject = (User)RedisCacheDao.getObjByKey(userToken);
			params.put("userId", userObject.getId());
			params.put("mobile", userObject.getMobile());
			params.put("id", "zj"+UUIDUtil.genUUIDString());
			params.put("type", "2");
			HashMap<String,Object> objectHashMap = userService.getUserInfo(params);
			int numPublish = Integer.parseInt((String)objectHashMap.get("numPublish"));
			String level = (String)objectHashMap.get("level");
			int count = 0;
			if(!"2".equals(level)){
			if("0".equals(level)){
				count = 6;
			}else if("1".equals(level)){
				count  = 11;
			}
			log.info("次数====11111===="+numPublish);
			
			if(userService.getLogById(params)==0){
				if(numPublish<=0){
					write(response, "qx_time", null);
                    return;					
				}else{
					int res = userService.queryUserInfo(params);
					log.info("次数===222====="+res);
					if(res<count){
						log.info("次数====3333===="+res);
						userService.updateUserInfo(params);
					}
				}
			}
		}
	        publishService.insertZj(params);
			write(response, "Success", null);
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	
	/**
	 * 资产
	 * @return
	 */
	
	@RequestMapping(value = "/zc", method = RequestMethod.POST)
	public void publishZc(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getParameters(request);
		try {
			String userToken = (String)params.get("userToken");
			User userObject = (User)RedisCacheDao.getObjByKey(userToken);
			params.put("userId", userObject.getId());
            params.put("mobile", userObject.getMobile());
            params.put("id", "zc"+UUIDUtil.genUUIDString());
			params.put("type", "2");
			HashMap<String,Object> objectHashMap = userService.getUserInfo(params);
			int numPublish = Integer.parseInt((String)objectHashMap.get("numPublishYet"));
			String level = (String)objectHashMap.get("level");
			int count = 0;
			if(!"2".equals(level)){
			if("0".equals(level)){
				count = 6;
			}else if("1".equals(level)){
				count  = 11;
			}
			log.info("次数====11111===="+numPublish);
			
			if(userService.getLogByIdNoDay(params)==0){
				if(numPublish<=0){
					write(response, "qx_time", null);
                    return;					
				}else{
					int res = userService.queryUserInfoNoDay(params);
					log.info("次数===222====="+res);
					if(res<count){
						log.info("次数====3333===="+res);
						userService.updateUserInfoNoDay(params);
					}
				}
			}
		}
			publishService.insertZc(params);
			write(response, "Success", null);
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	/**
	 * 上传公司信息
	 * @return
	 */
	
	@RequestMapping(value = "/uploadCompanyInfo", method = RequestMethod.POST)
	public void uploadCompanyInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getParameters(request);
		try {
			String userToken = (String)params.get("userToken");
			User userObject = (User)RedisCacheDao.getObjByKey(userToken);
			params.put("userId", userObject.getId());
			publishService.uploadCompanyInfo(params);
			write(response, "Success", null);
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	
	/**
	 * 投诉
	 * @return
	 */
	
	@RequestMapping(value = "/uploadComplain", method = RequestMethod.POST)
	public void uploadComplain(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getParameters(request);
		try {
			String userToken = (String)params.get("userToken");
			User userObject = (User)RedisCacheDao.getObjByKey(userToken);
			params.put("userId", userObject.getId());
			publishService.uploadComplain(params);
			write(response, "Success", null);
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	/**
	 * 其它
	 * @return
	 */
	
	@RequestMapping(value = "/other", method = RequestMethod.POST)
	public void publishOther(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getParameters(request);
		try {
			String userToken = (String)params.get("userToken");
			User userObject = (User)RedisCacheDao.getObjByKey(userToken);
			params.put("userId", userObject.getId());
            params.put("mobile", userObject.getMobile());
            params.put("id", "ot"+UUIDUtil.genUUIDString());
			params.put("type", "2");
			HashMap<String,Object> objectHashMap = userService.getUserInfo(params);
			int numPublish = Integer.parseInt((String)objectHashMap.get("numPublish"));
			String level = (String)objectHashMap.get("level");
			int count = 0;
			if(!"2".equals(level)){
			if("0".equals(level)){
				count = 6;
			}else if("1".equals(level)){
				count  = 11;
			}
			log.info("次数====11111===="+numPublish);
			
			if(userService.getLogByIdNoDay(params)==0){
				if(numPublish<=0){
					write(response, "qx_time", null);
                    return;					
				}else{
					int res = userService.queryUserInfoNoDay(params);
					log.info("次数===222====="+res);
					if(res<count){
						log.info("次数====3333===="+res);
						userService.updateUserInfoNoDay(params);
					}
				}
			}
		}
			publishService.insertOther(params);
			write(response, "Success", null);
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	
	
	/**
	 * 获取公司信息
	 * @return
	 */
	
	@RequestMapping(value = "/getCompanyInfo", method = RequestMethod.POST)
	public void getCompanyInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getParameters(request);
		try {
			String userToken = (String)params.get("userToken");
			User userObject = (User)RedisCacheDao.getObjByKey(userToken);
			params.put("userId", userObject.getId());
			HashMap<String,Object>  publishObject = publishService.getCompanyInfoByUserId(params);
			write(response, "Success", publishObject);
		} catch (Exception e) {
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	
	
}
