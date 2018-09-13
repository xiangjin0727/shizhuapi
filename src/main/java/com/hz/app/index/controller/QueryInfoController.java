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
import com.hz.app.index.service.QueryService;
import com.hz.app.index.service.ModelService;
import com.hz.app.index.vo.ItemFirm;
import com.hz.app.index.vo.ItemMoney;
import com.hz.app.index.vo.ItemOther;
import com.hz.app.index.vo.ItemProject;
import com.hz.app.index.vo.User;
import com.hz.core.framework.base.BaseController;
import com.hz.core.framework.redis.RedisCacheDao;

@Controller
@RequestMapping(value = "/queryInfo")
public class QueryInfoController extends BaseController {

	private static Logger log = Logger.getLogger(QueryInfoController.class);

	@Autowired
	QueryService queryService;
	@Autowired
	PublishService publishService;
	@Autowired
	ModelService userService;
	
	/**
	 * 首页推荐
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/indexPage", method = RequestMethod.POST)
	public void zjzcInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
				Map<String, Object> params = getParameters(request);
				String userToken = (String)params.get("userToken");
				User userObject = (User)RedisCacheDao.getObjByKey(userToken);
				String mobile = userObject.getMobile();
				params.put("mobile", mobile);
				params.put("type", "0");
				HashMap<String,Object> objectHashMap = userService.getUserInfo(params);
				int numInfoYet = Integer.parseInt((String)objectHashMap.get("numInfoYet"));
				String level = (String)objectHashMap.get("level");
				
				if(!"2".equals(level)){
					int count = 0;
					if("0".equals(level)){
						count = 6;
					}else if("1".equals(level)){
						count  =21;
					}
					log.info("次数====11111===="+numInfoYet);
					if(userService.getLogById(params)==0){
						if(numInfoYet<=0){
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
				String id = String.valueOf(params.get("id"));
				log.info("接收的===========》"+id);
				//公用参数
				String browserCount = "0";
				String categorydb = "0"; 
				String time = "";
				HashMap<String,Object> resEndHashMap = new HashMap<String,Object>();
				HashMap<String,Object> idMap = new HashMap<String,Object>();
				idMap.put("id", id);
				String userId = "";
				if(id.indexOf("zj")>-1){//资金0
					categorydb = "1";
					HashMap<String,Object> zjMap = queryService.getZjMap(idMap);
					String comId = String.valueOf(zjMap.get("comId"));
					resEndHashMap.put("itemFirm", getItemFirmInfo(comId,publishService));
					//资金
					time = String.valueOf(zjMap.get("createtime")).substring(0,16);
					resEndHashMap.put("itemMoney", getItemMoney(zjMap));
					userId = (String)zjMap.get("userId");
				}else if(id.indexOf("zc")>-1){//资产
					categorydb = "2";
					HashMap<String,Object> zcMap = queryService.getZcMap(idMap);
					String comId = String.valueOf(zcMap.get("comId"));
					resEndHashMap.put("itemFirm", getItemFirmInfo(comId,publishService));
					//资产
					time = String.valueOf(zcMap.get("createtime")).substring(0,16);
					resEndHashMap.put("itemProject", getItemProject(zcMap));
					userId = (String)zcMap.get("userId");
				}else if(id.indexOf("ot")>-1){//其它
					categorydb = "3";
					HashMap<String,Object> otherMap = queryService.getOtherMap(idMap);
					String comId = String.valueOf(otherMap.get("comId"));
					resEndHashMap.put("itemFirm", getItemFirmInfo(comId,publishService));
					//其它
					time = String.valueOf(otherMap.get("createtime")).substring(0,16);
					resEndHashMap.put("itemOther", getItemOther(otherMap));
					userId = (String)otherMap.get("userId");
				}
				resEndHashMap.put("id", id);
				resEndHashMap.put("category", categorydb);
				resEndHashMap.put("level", level);
				resEndHashMap.put("browserCount", browserCount);
				resEndHashMap.put("favorite", "1");
				resEndHashMap.put("userId", userId);
				resEndHashMap.put("time", time);
				write(response, "Success", resEndHashMap);// 登录成功后返回相关的信息
		} catch (Exception e) {
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	
	/**
	 * 构造公司信息
	 * @param comId
	 * @param publishService
	 * @return
	 */
	public static ItemFirm getItemFirmInfo(String comId ,PublishService publishService){
		HashMap<String,Object> comIdMap = new HashMap<String,Object>();
		comIdMap.put("id", comId);
		HashMap<String,Object> companyMap = publishService.getCompanyInfoById(comIdMap);
		//构造公司信息
		ItemFirm  itemFirm = new ItemFirm();
		//itemFirm.setAddress((String)companyMap.get("address"));
		itemFirm.setArea((String)companyMap.get("area"));
		//itemFirm.setFirm((String)companyMap.get("firm"));
		//itemFirm.setFirmFull((String)companyMap.get("firmFull"));
		//itemFirm.setName((String)companyMap.get("name"));
		//itemFirm.setPhone((String)companyMap.get("phone"));
		//itemFirm.setWechat((String)companyMap.get("wechat"));
		return itemFirm;
	}
	
	/**
	 * 资金对象
	 * @param zcMap
	 * @return
	 */
	public static ItemMoney getItemMoney(HashMap<String,Object> zjMap){
		ItemMoney itemMoney = new ItemMoney();
		itemMoney.setFavorite((String)zjMap.get("favorite"));
		itemMoney.setMoneyHigh((String)zjMap.get("moneyHigh"));
		itemMoney.setMoneyLow((String)zjMap.get("moneyLow"));
		itemMoney.setName((String)zjMap.get("name"));
		//itemMoney.setRemark((String)zjMap.get("remark"));
		itemMoney.setScale((String)zjMap.get("scale"));
		itemMoney.setType((String)zjMap.get("type"));
		itemMoney.setValidity((String)zjMap.get("validity"));
		return itemMoney;
		
	}
	
	/**
	 * 资产对象
	 * @param zcMap
	 * @return
	 */
	public static ItemProject getItemProject(HashMap<String,Object> zcMap){
		ItemProject itemProject = new ItemProject();
		itemProject.setProjectName((String)zcMap.get("projectName"));
		itemProject.setPerTime((String)zcMap.get("perTime"));
		itemProject.setMoneyHigh((String)zcMap.get("moneyHigh"));
		itemProject.setMoneyLow((String)zcMap.get("moneyLow"));
		itemProject.setName((String)zcMap.get("name"));
		//itemProject.setRemark((String)zcMap.get("remark"));
		itemProject.setScale((String)zcMap.get("scale"));
		itemProject.setType((String)zcMap.get("type"));
		itemProject.setValidity((String)zcMap.get("validity"));
		itemProject.setPerMoney((String)zcMap.get("perMoney"));
		itemProject.setMonthMoney((String)zcMap.get("monthMoney"));
		itemProject.setDateStart((String)zcMap.get("dateStart"));
		itemProject.setPerMoneyHigh((String)zcMap.get("perMoneyHigh"));
		return itemProject;
		
	}
	/**
	 * 其它对象
	 * @param zcMap
	 * @return
	 */
	public static ItemOther getItemOther(HashMap<String,Object> otherMap){
		ItemOther itemOther = new ItemOther();
		itemOther.setName((String)otherMap.get("name"));
		itemOther.setRemark((String)otherMap.get("remark"));
		itemOther.setType((String)otherMap.get("type"));
		itemOther.setValidity((String)otherMap.get("validity"));
		return itemOther;
		
	}
}
