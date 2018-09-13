package com.hz.app.evaluate.controller;

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

import com.hz.app.evaluate.dao.EveluateDao;
import com.hz.app.servlet.BaseController;
import com.hz.app.user.dao.UserMyInformationDao;

/**
 * 投诉建议 S0028
 * 投诉建议列表 S0044
 * 可维修项目  S0068
 * 
 * @author XJin
 *
 */
@Controller
@RequestMapping("evaluate")
public class EveluateController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(EveluateController.class);
	
	@Autowired
	UserMyInformationDao userMyInformationDao;
	@Autowired
	EveluateDao eveluateDao;
	
	
	/**
	 * 投诉建议 S0028
	 * @return
	 */
	@RequestMapping(value = "doComplaintProposal",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> doComplaintProposal(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------投诉建议 S0028 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			eveluateDao.doComplaintProposal(data);
			
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "投诉建议成功");
			Map<String, String> da = new HashMap<>();
			da.put("result_code", "0000");
			resultMap.put("data", da);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "投诉建议失败");
		}
		return resultMap;
	}

	
	/**
	 * 投诉建议列表 S0044
	 * @return
	 */
	@RequestMapping(value = "doComplaintProposalList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> doComplaintProposalList(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------投诉建议列表  S0044 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String,String>> mapL=eveluateDao.doComplaintProposalList(data);		
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "投诉建议列表成功");
			resultMap.put("data", mapL);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "投诉建议列表失败");
		}
		return resultMap;
	}
	
	/**
	 * 可维修项目  S0068
	 * @return
	 */
	@RequestMapping(value = "getRepairableProject",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getRepairableProject(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------可维修项目  S0068 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String,String>> mapL=eveluateDao.getRepairableProject(data);		
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "可维修项目成功");
			resultMap.put("data", mapL);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "可维修项目失败");
		}
		return resultMap;
	}
	
}
