package com.hz.app.homePage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hz.app.servlet.BaseController;

/**
 *
 * 
 * @author XJin
 *
 */
@Controller
@RequestMapping("homePage")
public class HomePageController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(HomePageController.class);

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


			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "获取积分兑换列表成功");
			resultMap.put("data", null);
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "获取积分兑换列表失败");
		}
		return resultMap;
	}

}
