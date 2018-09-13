package com.hz.app.user.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.hz.app.servlet.BaseController;
import com.hz.app.user.dao.UserCouponDao;

/**
 * 获取积分兑换列表 S0018
 * 积分兑换操作 S0019
 * 获取积分兑换记录 S0020
 * 获取生日特权房租抵用券 S0052
 * @author XJin
 *
 */
@Controller
@RequestMapping("userCoupon")
public class UserCouponController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(UserCouponController.class);
	
	@Autowired
	UserCouponDao userCouponDao;
	/**
	 * 获取积分兑换列表 S0018
	 * @return
	 */
	@RequestMapping(value = "getFractionalTransactionList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getFractionalTransactionList(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------获取积分兑换列表 S0018 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String, String>> mapL = userCouponDao.getFractionalTransactionList();
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "获取积分兑换列表成功");
			resultMap.put("data", mapL);
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "获取积分兑换列表失败");
		}
		return resultMap;
	}

	/**
	 * 积分兑换操作 S0019
	 * @return
	 */
	@RequestMapping(value = "convertibilityOperation",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> convertibilityOperation(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------积分兑换操作 S0019 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			int sultI = userCouponDao.convertibilityOperation(data);
			userCouponDao.updateUserCore(data);
			Map<String, String> ma = userCouponDao.searchUserCore(data);
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "积分兑换表成功");
			Map<String, String> map = new HashMap<>();
			map.put("result_code", "0000");
			map.put("user_core", String.valueOf(ma.get("user_core")));
			resultMap.put("data", map);
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "积分兑换失败");
		}
		return resultMap;
	}

	
	
	/**
	 * 获取积分兑换记录 S0020
	 * @return
	 */
	@RequestMapping(value = "getConvertibilityRecord",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getConvertibilityRecord(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------获取积分兑换记录 S0020 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");

			List<Map<String,String>> mapL=userCouponDao.getConvertibilityRecord(data);		
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "获取积分兑换记录成功");
			resultMap.put("data", mapL);
			
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "获取积分兑换记录失败");
		}
		return resultMap;
	}

	/**
	 * 获取生日特权房租抵用券 S0052
	 * @return
	 */
	@RequestMapping(value = "getBirthdayVoucher",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getBirthdayVoucher(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------获取生日特权房租抵用券 S0052 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");

			List<Map<String,String>> mapL=userCouponDao.getBirthdayVoucher(data);		
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "获取积分兑换记录成功");
			resultMap.put("data", mapL);
			
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "获取积分兑换记录失败");
		}
		return resultMap;
	}



}
