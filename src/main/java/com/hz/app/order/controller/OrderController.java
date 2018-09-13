package com.hz.app.order.controller;

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

import com.hz.app.order.dao.OrderDao;
import com.hz.app.servlet.BaseController;
import com.hz.app.user.dao.UserMyInformationDao;

/**
 * TODO  6.23账单明细 S0023
 * TODO  6.25待缴账单 S0025
 * TODO  6.27智享维修-报修 S0027
 * 获取账单详情 S0056
 * 获取合同下房租账单列表 S0055
 * @author XJin
 *
 */
@Controller
@RequestMapping("order")
public class OrderController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	UserMyInformationDao userMyInformationDao;
	@Autowired
	OrderDao orderDao;
	

	/**
	 * 账单明细 S0023
	 * @return
	 */
	@RequestMapping(value = "getBillingDetails",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getBillingDetails(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------账单明细 S0023 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String, String>> ml = orderDao.getBillingDetails(data);
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "账单明细 成功");
			resultMap.put("data", ml);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "账单明细失败");
		}
		return resultMap;
	}


	
	/**
	 * 待缴账单 S0025
	 * @return
	 */
	@RequestMapping(value = "getPendingBill",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getPendingBill(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------待缴账单 S0025  入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String, String>> ml = orderDao.searchUnpaidUserBill(data);
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "待缴账单成功");
			resultMap.put("data", ml);
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "待缴账单失败");
		}
		return resultMap;
	}


	
	/**
	 * 智享维修-报修 S0027
	 * @return
	 */
	@RequestMapping(value = "doApplyForRepair",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> doApplyForRepair(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------智享维修-报修 S0027 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			orderDao.doApplyForRepair(data);
			
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "智享维修-报修成功");
			Map<String, String> da = new HashMap<>();
			da.put("result_code", "0000");
			da.put("repair_order_id",String.valueOf(data.get("repair_order_id")));
			
			resultMap.put("data", da);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "智享维修-报修失败");
		}
		return resultMap;
	}
	/**
	 * 获取账单详情 S0056
	 * @return
	 */
	@RequestMapping(value = "getDetailsOfTheBill",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getDetailsOfTheBill(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------获取账单详情 S0056 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String, String>> ml = orderDao.getDetailsOfTheBill(data);
			
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "获取账单详情成功");
			resultMap.put("data", ml);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "获取账单详情失败");
		}
		return resultMap;
	}
	/**
	 * 获取合同下房租账单列表 S0055
	 * @return
	 */
	@RequestMapping(value = "getBillList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getBillList(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------获取合同下房租账单列表 S0055 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String, String>> ml = orderDao.getBillList(data);
			if(ml==null){
				resultMap.put("result_code", "0001");
				resultMap.put("result_message", "合同下账单数据为空");
				return resultMap;
			}
			if(ml.get(0)!=null&&"2".equals(String.valueOf(ml.get(0).get("user_bill_method")))){				
				ml.get(0).put("user_bill_method", "1");
			}
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "获取合同下房租账单列表成功");
			resultMap.put("data", ml);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "获取合同下房租账单列表失败");
		}
		return resultMap;
	}

	
}
