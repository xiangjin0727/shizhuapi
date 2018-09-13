package com.hz.app.user.controller;

import java.util.ArrayList;
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
import com.hz.app.user.dao.UserMyInformationDao;
import com.hz.app.user.service.UserService;

/**
 * TODO 6.8我的收藏  S0008
 * TODO 6.9我的约看  S0009
 * TODO 6.10我的预订  S0010
 * TODO 6.11我的订单-租赁订单 S0011
 * TODO 6.12我的订单-生活订单  S0012
 * TODO 6.13我的评价-房屋评价  S0013
 * TODO 6.14我的评价-保洁评价  S0014
 * TODO 6.15我的评价-维修评价  S0015
 * TODO 6.16我的评价-管家评价  S0016
 * TODO 6.17我的会员  S0017
 * TODO 6.21 我的钱包  S0021
 * TODO 6.22 我的优惠券 S0022
 * TODO 6.24我的合同  S0024
 * TODO 6.26获取我的管家列表  S0026
 * TODO 6.31获取个人信息  S0031
 * TODO 6.45房屋评价列表  S0045
 * TODO 6.46保洁评价列表  S0046
 * TODO 6.47维修评价列表  S0047
 *  获取合同详情  S0054
 * @author XJin
 *
 */

@Controller
@RequestMapping("userMyInformation")
public class UserMyInformationController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(UserMyInformationController.class);
	
	@Autowired
	UserService userService;
	@Autowired
	UserMyInformationDao userMyInformationDao;
	
	/**
	 * 我的收藏  S0008
	 * @return
	 */
	@RequestMapping(value = "myCollection",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> myCollection(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------我的收藏  S0008 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String, Object>> mapL = userMyInformationDao.myCollection_01(data);
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "我的收藏成功");

			resultMap.put("data", mapL);
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "我的收藏失败");
		}
		return resultMap;
	}
	/**
	 * 我的约看  S0009
	 * @return
	 */
	@RequestMapping(value = "myAppointment",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> myAppointment(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------我的约看  S0009 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String, Object>> mapL = userMyInformationDao.myAppointment(data);
			for(Map m:mapL){
				String keeps = String.valueOf(m.get("house_keeper"));
				String[] kk = keeps.split(",");
				List<Map<String, String>> ke = new ArrayList<>();
				for(String k:kk){
					Map<String, String> mm = new HashMap<>();
					mm.put("house_keeper_id", k);
					Map<String, String> keep = userMyInformationDao.myHousekeeperList(mm);
					ke.add(keep);
				}
				m.put("keeps",ke);
			}
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "我的约看成功");
			resultMap.put("data", mapL);

			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "我的约看失败");
		}
		return resultMap;
	}
	
	
	/**
	 * 我的预订  S0010
	 * @return
	 */
	@RequestMapping(value = "myReservation",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> myReservation(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------我的预订  S0010 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String, Object>> mapL = userMyInformationDao.myReservation(data);
			
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "我的预定成功");
			resultMap.put("data", mapL);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "我的预订失败");
		}
		return resultMap;
	}

	
	/**
	 * 租赁订单 S0011
	 * @return
	 */
	@RequestMapping(value = "myLeaseOrder",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> myLeaseOrder(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------租赁订单 S0011 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");

			List<Map<String, Object>> mapL = userMyInformationDao.myLeaseOrder(data);
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "租赁订单成功");
			resultMap.put("data", mapL);

			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "租赁订单失败");
		}
		return resultMap;
	}

	
	/**
	 * 生活订单  S0012
	 * @return
	 */
	@RequestMapping(value = "myLifeOrder",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> myLifeOrder(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------生活订单  S0012 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");

			List<Map<String, String>> mapL = userMyInformationDao.myLifeOrder(data);
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "-生活订单获取成功");
			resultMap.put("data", mapL);
			
			
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "生活订单失败");
		}
		return resultMap;
	}

	
	/**
	 * 房屋评价  S0013
	 * @return
	 */
	@RequestMapping(value = "myHousingEvaluation",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> myHousingEvaluation(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------房屋评价  S0013 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");

			userMyInformationDao.myHousingEvaluation(data);
			
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "房屋评价成功");
			Map<String, String> da = new HashMap<>();
			da.put("result_code", "0000");
			resultMap.put("data", da);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "房屋评价失败");
		}
		return resultMap;
	}

	
	/**
	 * 保洁评价  S0014
	 * @return
	 */
	@RequestMapping(value = "myCleaningEvaluation",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> myCleaningEvaluation(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------保洁评价  S0014 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			userMyInformationDao.myCleaningEvaluation(data);
			
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "保洁评价成功");
			Map<String, String> da = new HashMap<>();
			da.put("result_code", "0000");
			resultMap.put("data", da);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "保洁评价失败");
		}
		return resultMap;
	}

	
	/**
	 * 维修评价  S0015
	 * @return
	 */
	@RequestMapping(value = "myMaintenanceEvaluation",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> myMaintenanceEvaluation(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------维修评价  S0015 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");

			userMyInformationDao.myMaintenanceEvaluation(data);
			
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "维修评价成功");
			Map<String, String> da = new HashMap<>();
			da.put("result_code", "0000");
			resultMap.put("data", da);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "维修评价失败");
		}
		return resultMap;
	}

	
	/**
	 * 管家评价  S0016
	 * @return
	 */
	@RequestMapping(value = "myHousekeeperEvaluation",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> myHousekeeperEvaluation(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------管家评价  S0016 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");

			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "管家评价失败");
		}
		return resultMap;
	}

	
	/**
	 * 我的会员  S0017
	 * @return
	 */
	@RequestMapping(value = "myMembers",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> myMembers(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------我的会员  S0017 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			Map<String, String> map = userMyInformationDao.myMembers(data);
			
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "我的会员成功");
			//Map<String, String> da = new HashMap<>();
			int core = Integer.valueOf(String.valueOf(map.get("user_total_core")));
			/**
			 * 普通会员（0-269积分数）level = 1 ⽩白银会员（270-619积分数）level = 2 ⻩
			 * 黄⾦金金会员（620-939积分数）level = 3 铂⾦金金会员（940-1299积分数）level = 4 
			 * 钻⽯石会员（1300以上积分数）level = 5 
			 */
			if(0<=core&&core<=269){
				map.put("user_core_level", "1");				
			}
			if(270<=core&&core<=619){
				map.put("user_core_level", "2");				
			}
			if(620<=core&&core<=939){
				map.put("user_core_level", "3");				
			}
			if(940<=core&&core<=1299){
				map.put("user_core_level", "4");				
			}
			if(1300<=core){
				map.put("user_core_level", "5");				
			}
			resultMap.put("data", map);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "我的会员 失败");
		}
		return resultMap;
	}

	
	
	/**
	 * 我的钱包  S0021
	 * @return
	 */
	@RequestMapping(value = "myWallet",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> myWallet(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------我的钱包  S0021入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			Map<String, String> map = userMyInformationDao.myWallet(data);
			if(map==null){
				map = new HashMap<>();
				map.put("user_account", "0.00");
				map.put("is_view_order", "0");
				map.put("smart_view_order_price", "0.0");
			}
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "我的钱包成功");
			resultMap.put("data", map);
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "我的钱包失败");
		}
		return resultMap;
	}
	
	
	/**
	 * 我的优惠券 S0022
	 * @return
	 */
	@RequestMapping(value = "myCoupon",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> myCoupon(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------我的优惠券 S0022 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String,String>> mapL=userMyInformationDao.myCouponList(data);		
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "我的优惠券成功");
			resultMap.put("data", mapL);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "我的优惠券失败");
		}
		return resultMap;
	}
	
	
	/**
	 * 我的合同  S0024
	 * @return
	 */
	@RequestMapping(value = "myContract",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> myContract(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------我的合同  S0024 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String,String>> mapL=userMyInformationDao.myContract(data);		
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "我的合同成功");
			resultMap.put("data", mapL);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "我的合同失败");
		}
		return resultMap;
	}
	
	/**
	 * 获取我的管家列表  S0026
	 * @return
	 */
	@RequestMapping(value = "myHousekeeperList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> myHousekeeperList(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------获取我的管家列表  S0026 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<String> houseI = userMyInformationDao.myHouseInfo(data);
			List<Map<String, String>> mapL = new ArrayList<>();
			for(String ks:houseI){
				String[] kk = ks.split(",");
				for(String k:kk){
					Map<String, String> m = new HashMap<>();
					m.put("house_keeper_id", k);
					Map<String, String> keep = userMyInformationDao.myHousekeeperList(m);
					mapL.add(keep);
				}
			}
			
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "获取我的管家列表成功");
			resultMap.put("data", mapL);
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "获取我的管家列表失败");
		}
		return resultMap;
	}
	
	/**
	 * 获取个人信息  S0031
	 * @return
	 */
	@RequestMapping(value = "getPersonalInformation",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getPersonalInformation(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------获取个人信息  S0031 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			Map<String, String> map = userMyInformationDao.getPersonalInformation(data);
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "获取个人信息成功");
			resultMap.put("data", map);
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "获取个人信息失败");
		}
		return resultMap;
	}

	
	/**
	 * 房屋评价列表  S0045
	 * @return
	 */
	@RequestMapping(value = "housingEvaluationList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> housingEvaluationList(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------房屋评价列表  S0045 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String, String>> dataMapL = new ArrayList<>();
			List<Map<String, String>> mapL = userMyInformationDao.housingEvaluationList(data);
			
			for(Map<String, String> map:mapL){
				
				List<Map<String, String>> m = userMyInformationDao.searchHousEvaluation(map);
				if(Integer.valueOf(String.valueOf(data.get("status")))==1 && m!=null && m.size()>0){
					dataMapL.add(map);
				}else if(Integer.valueOf(String.valueOf(data.get("status")))==2 && (m==null || m.size()==0)){
					dataMapL.add(map);
				}

			}
			
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "房屋评价列表成功");
			resultMap.put("data", dataMapL);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "房屋评价列表失败");
		}
		return resultMap;
	}


	
	/**
	 * 保洁评价列表  S0046
	 * @return
	 */
	@RequestMapping(value = "cleanlinessEvaluationList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> cleanlinessEvaluationList(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------保洁评价列表  S0046 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String, String>> mapL = userMyInformationDao.cleanlinessEvaluationList(data);
			if("0".equals(String.valueOf(data.get("status")))){
				for(Map<String, String> map:mapL){
					data.put("order_id", String.valueOf(map.get("clean_order_id")));
					Map<String, String> m = userMyInformationDao.huodebaojieliebiaopingjia(data);
					map.put("clean_evaluation_contents",String.valueOf(m.get("clean_evaluation_contents")));
					map.put("clean_evaluation_level", String.valueOf(m.get("clean_evaluation_level")));
					
				}
			}
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "保洁评价列表成功");
			resultMap.put("data", mapL);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "保洁评价列表失败");
		}
		return resultMap;
	}

	/**
	 * 维修评价列表  S0047
	 * @return
	 */
	@RequestMapping(value = "maintenanceEvaluationList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> maintenanceEvaluationList(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------维修评价列表  S0047 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String, String>> mapL = userMyInformationDao.maintenanceEvaluationList(data);
			if("0".equals(String.valueOf(data.get("status")))){
				for(Map<String, String> map:mapL){
					data.put("order_id", String.valueOf(map.get("repair_order_id")));
					Map<String, String> m = userMyInformationDao.weixiujieliebiaopingjia(data);
					map.put("repair_appraise_contents",String.valueOf(m.get("repair_appraise_contents")));
					map.put("repair_appraise_level", String.valueOf(m.get("repair_appraise_level")));
					
				}
			}

			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "维修评价列表成功");
			resultMap.put("data", mapL);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "维修评价列表失败");
		}
		return resultMap;
	}

	/**
	 * 获取合同详情  S0054
	 * @return
	 */
	@RequestMapping(value = "myContractDetail",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> myContractDetail(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------获取合同详情  S0054入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			Map<String,String> map=userMyInformationDao.myContractDetail(data);		
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "获取合同详情成功");
			resultMap.put("data", map);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "获取合同详情失败");
		}
		return resultMap;
	}
	

	
}
