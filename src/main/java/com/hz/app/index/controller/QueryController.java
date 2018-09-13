package com.hz.app.index.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
import com.hz.app.index.vo.ItemFirm;
import com.hz.app.index.vo.ItemMoney;
import com.hz.app.index.vo.ItemOther;
import com.hz.app.index.vo.ItemProject;
import com.hz.app.index.vo.User;
import com.hz.core.framework.base.BaseController;
import com.hz.core.framework.redis.RedisCacheDao;

@Controller
@RequestMapping(value = "/query")
public class QueryController extends BaseController{

	private static Logger log = Logger.getLogger(QueryController.class);

	@Autowired
	QueryService queryService;
	@Autowired
	PublishService publishService;
	
	/**
	 * 关键词列表
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/keyList", method = RequestMethod.POST)
	public void keyList(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = getParameters(request);
		try {
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
			
			List<HashMap<String,Object>> list = queryService.getKeyWordsList(params);
			HashMap<String,Object> hashMapobject = new HashMap<String,Object>();
			int resCount = queryService.countKeyWords(params);
			hashMapobject.put("pageCount", String.valueOf(resCount / pageSize + (resCount % pageSize == 0 ? 0 : 1)));
			hashMapobject.put("itemCount", String.valueOf(resCount));
			
			hashMapobject.put("listKeyword",list);
			write(response, "Success", hashMapobject);// 登录成功后返回相关的信息
			
		
		} catch (Exception e) {
			
			e.printStackTrace();
			write(response, "sorryUser", null);
		}
	}
	
	/**
	 * 首页推荐
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/indexPage", method = RequestMethod.POST)
	public void zjzcInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> params = getParameters(request);
			Object object= params.get("data");
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(object);
			String type = (String)jsonObject.get("type");
			String category = (String)jsonObject.get("category");
			String keyword = (String)jsonObject.get("keyword");
			String time = "";
			String idParam = String.valueOf(jsonObject.get("id"));
			int    pageIndex = Integer.parseInt(String.valueOf(jsonObject.get("pageIndex")));
			Object pageSizeObject = jsonObject.get("pageSize");
			if(pageSizeObject==null)  pageSizeObject=10;
			int    pageSize = Integer.parseInt(String.valueOf(pageSizeObject));
			params.put("pageSize", pageSize);
			params.put("pageIndex", pageIndex);
			//公用参数
			String level = "0";
			String browserCount = "0";
			//=================================以下为首页推荐=========================================
			//String category = (String)params.get("category");
			if("0".equals(type)){//首页推荐
				List<HashMap<String,Object>> tjList = queryService.getTjList(params);
				List<HashMap<String,Object>> ResList = new ArrayList<HashMap<String,Object>>();
				if(tjList!=null){
					for(int i =0 ; i < tjList.size() ;  i++){
						HashMap<String,Object> resEndHashMap = new HashMap<String,Object>();
						HashMap<String,Object> tjMap = tjList.get(i);
						String id = (String)tjMap.get("id");
						String categorydb = (String)tjMap.get("type");
						
						//构造查询对象，传ID查询
						HashMap<String,Object> idMap = new HashMap<String,Object>();
						idMap.put("id", id);
						
						if("1".equals(categorydb)){//资金
							HashMap<String,Object> zjMap = queryService.getZjMap(idMap);
							String comId = String.valueOf(zjMap.get("comId"));
							resEndHashMap.put("itemFirm", getItemFirmInfo(comId,publishService));
							//资金
							resEndHashMap.put("itemMoney", getItemMoney(zjMap));
							time = String.valueOf(zjMap.get("createtime")).substring(0,16);
						}else if("2".equals(categorydb)){//资产
							HashMap<String,Object> zcMap = queryService.getZcMap(idMap);
							String comId = String.valueOf(zcMap.get("comId"));
							resEndHashMap.put("itemFirm", getItemFirmInfo(comId,publishService));
							//资产
							resEndHashMap.put("itemProject", getItemProject(zcMap));
							time = String.valueOf(zcMap.get("createtime")).substring(0,16);
						}else if("3".equals(categorydb)){//其它
							HashMap<String,Object> otherMap = queryService.getOtherMap(idMap);
							String comId = String.valueOf(otherMap.get("comId"));
							resEndHashMap.put("itemFirm", getItemFirmInfo(comId,publishService));
							//其它
							resEndHashMap.put("itemOther", getItemOther(otherMap));
							time = String.valueOf(otherMap.get("createtime")).substring(0,16);
						}
						resEndHashMap.put("id", id);
						resEndHashMap.put("category", categorydb);
						resEndHashMap.put("level", level);
						resEndHashMap.put("browserCount", browserCount);
						resEndHashMap.put("time", time);
						ResList.add(resEndHashMap);
					}
				}
				sort(ResList);
				HashMap<String,Object> hashMapobject = new HashMap<String,Object>();
				hashMapobject.put("listProject",ResList);
				hashMapobject.put("pageCount", String.valueOf(tjList.size() / pageSize + (tjList.size() % pageSize == 0 ? 0 : 1)));
				hashMapobject.put("itemCount", String.valueOf(tjList.size()));
				write(response, "Success", hashMapobject);// 登录成功后返回相关的信息
			//======================================以下为搜索==============================================	
			}else if("1".equals(type)){//搜索
				String categorydb = "0";
				List<HashMap<String,Object>> ResList = new ArrayList<HashMap<String,Object>>();
				HashMap<String,Object> keyMap = new HashMap<String,Object>();
				keyMap.put("keyword", keyword);
				queryService.insertKeyWords(keyMap);
				//资金
				List<HashMap<String,Object>> zjMapList = queryService.getZjListByKey(keyMap);
				if(zjMapList!=null && zjMapList.size()>0){//
					categorydb = "1";	
					for (int i = 0; i < zjMapList.size(); i++) {
						HashMap<String,Object> zjMap = zjMapList.get(i);
						HashMap<String,Object> resEndHashMap = new HashMap<String,Object>();
						String comId = String.valueOf(zjMap.get("comId"));
						resEndHashMap.put("itemFirm", getItemFirmInfo(comId,publishService));
						//资金
						time = String.valueOf(zjMap.get("createtime")).substring(0,16);
						resEndHashMap.put("itemMoney", getItemMoney(zjMap));
						resEndHashMap.put("id", zjMap.get("id"));
						resEndHashMap.put("category", categorydb);
						resEndHashMap.put("level", level);
						resEndHashMap.put("browserCount", browserCount);
						resEndHashMap.put("time", time);
						ResList.add(resEndHashMap);
					}
				}
				
				//资产
				List<HashMap<String,Object>> zcMapList = queryService.getZcListByKey(keyMap);
				if(zcMapList!=null && zcMapList.size()>0){//
					categorydb = "2";	
					for (int i = 0; i < zcMapList.size(); i++) {
						HashMap<String,Object> zcMap = zcMapList.get(i);
						HashMap<String,Object> resEndHashMap = new HashMap<String,Object>();
						String comId = String.valueOf(zcMap.get("comId"));
						resEndHashMap.put("itemFirm", getItemFirmInfo(comId,publishService));
						//资产
						time = String.valueOf(zcMap.get("createtime")).substring(0,16);
						resEndHashMap.put("itemProject", getItemProject(zcMap));
						resEndHashMap.put("id", zcMap.get("id"));
						resEndHashMap.put("category", categorydb);
						resEndHashMap.put("level", level);
						resEndHashMap.put("browserCount", browserCount);
						resEndHashMap.put("time", time);
						ResList.add(resEndHashMap);
					}
				}
				//其它				
				List<HashMap<String,Object>> otherMapList = queryService.getOtherListByKey(keyMap);
				if(otherMapList!=null && otherMapList.size()>0){//
					categorydb = "3";	
					for (int i = 0; i < otherMapList.size(); i++) {
						HashMap<String,Object> otherMap = otherMapList.get(i);
						HashMap<String,Object> resEndHashMap = new HashMap<String,Object>();
						String comId = String.valueOf(otherMap.get("comId"));
						resEndHashMap.put("itemFirm", getItemFirmInfo(comId,publishService));
						//其它
						time = String.valueOf(otherMap.get("createtime")).substring(0,16);
						resEndHashMap.put("itemOther", getItemOther(otherMap));
						resEndHashMap.put("id", otherMap.get("id"));
						resEndHashMap.put("category", categorydb);
						resEndHashMap.put("level", level);
						resEndHashMap.put("browserCount", browserCount);
						resEndHashMap.put("time", time);
						ResList.add(resEndHashMap);
					}
				}
				sort(ResList);
				HashMap<String,Object> hashMapobject = new HashMap<String,Object>();
				List<HashMap<String,Object>> tmpList = new ArrayList<HashMap<String,Object>>();
				if(ResList!=null){
					for (int i = (pageIndex-1) * pageSize; i<pageIndex*pageSize && i<ResList.size(); i++) {
						tmpList.add(ResList.get(i));
					}
				}
				hashMapobject.put("listProject",tmpList);
				hashMapobject.put("pageCount", String.valueOf(ResList.size() / pageSize + (ResList.size() % pageSize == 0 ? 0 : 1)));
				hashMapobject.put("itemCount", String.valueOf(ResList.size()));
				write(response, "Success", hashMapobject);// 登录成功后返回相关的信息
		    //========================================以下为分类查询==========================================================
			}else if("2".equals(type)){//分类查询
				String categorydb = "0";
				
				if(category==null||"".equals(category)){
					category = "0"; 
				}
				
				List<HashMap<String,Object>> ResList = new ArrayList<HashMap<String,Object>>();
				HashMap<String,Object> keyMap = new HashMap<String,Object>();
			
				if("1".equals(category) || "0".equals(category)){
					List<HashMap<String,Object>> zjMapList = queryService.getZjList(keyMap);
					if(zjMapList!=null && zjMapList.size()>0){//
						categorydb = "1";
						for (int i = 0; i < zjMapList.size(); i++) {
							HashMap<String,Object> zjMap = zjMapList.get(i);
							HashMap<String,Object> resEndHashMap = new HashMap<String,Object>();
							String comId = String.valueOf(zjMap.get("comId"));
							resEndHashMap.put("itemFirm", getItemFirmInfo(comId,publishService));
							//资金
							time = String.valueOf(zjMap.get("createtime")).substring(0,16);
							resEndHashMap.put("itemMoney",getItemMoney(zjMap));
							resEndHashMap.put("id", zjMap.get("id"));
							resEndHashMap.put("category", categorydb);
							resEndHashMap.put("level", level);
							resEndHashMap.put("browserCount", browserCount);
							resEndHashMap.put("time", time);
							ResList.add(resEndHashMap);
						}
					}
				}
				if("2".equals(category) || "0".equals(category)){
					List<HashMap<String,Object>> zcMapList = queryService.getZcList(keyMap);
					if(zcMapList!=null && zcMapList.size()>0){//
						categorydb = "2";	
						for (int i = 0; i < zcMapList.size(); i++) {
							HashMap<String,Object> zcMap = zcMapList.get(i);
							HashMap<String,Object> resEndHashMap = new HashMap<String,Object>();
							String comId = String.valueOf(zcMap.get("comId"));
							resEndHashMap.put("itemFirm", getItemFirmInfo(comId,publishService));
							//资产
							time = String.valueOf(zcMap.get("createtime")).substring(0,16);
							resEndHashMap.put("itemProject", getItemProject(zcMap));
							resEndHashMap.put("id", zcMap.get("id"));
							resEndHashMap.put("category", categorydb);
							resEndHashMap.put("level", level);
							resEndHashMap.put("browserCount", browserCount);
							resEndHashMap.put("time", time);
							ResList.add(resEndHashMap);
						}
					}
				}
				if("3".equals(category) || "0".equals(category)){
					List<HashMap<String,Object>> otherMapList = queryService.getOtherList(keyMap);
					if(otherMapList!=null && otherMapList.size()>0){//
						categorydb = "3";	
						for (int i = 0; i < otherMapList.size(); i++) {
							HashMap<String,Object> otherMap = otherMapList.get(i);
							HashMap<String,Object> resEndHashMap = new HashMap<String,Object>();
							String comId = String.valueOf(otherMap.get("comId"));
							resEndHashMap.put("itemFirm", getItemFirmInfo(comId,publishService));
							//其它
							time = String.valueOf(otherMap.get("createtime")).substring(0,16);
							resEndHashMap.put("itemOther", getItemOther(otherMap));
							resEndHashMap.put("id", otherMap.get("id"));
							resEndHashMap.put("category", categorydb);
							resEndHashMap.put("level", level);
							resEndHashMap.put("browserCount", browserCount);
							resEndHashMap.put("time", time);
							ResList.add(resEndHashMap);
						}
					}
				}
				sort(ResList);
				HashMap<String,Object> hashMapobject = new HashMap<String,Object>();
				List<HashMap<String,Object>> tmpList = new ArrayList<HashMap<String,Object>>();
				if(ResList!=null){
					for (int i = (pageIndex-1) * pageSize; i<pageIndex*pageSize && i<ResList.size(); i++) {
						tmpList.add(ResList.get(i));
					}
				}
				hashMapobject.put("listProject",tmpList);
				hashMapobject.put("pageCount", String.valueOf(ResList.size() / pageSize + (ResList.size() % pageSize == 0 ? 0 : 1)));
				hashMapobject.put("itemCount", String.valueOf(ResList.size()));
				write(response, "Success", hashMapobject);// 登录成功后返回相关的信息
		//===========================================以下为我的发布====================================================
			}else if("3".equals(type)){//我的发布
				String userToken = (String)params.get("userToken");
				User userObject = (User)RedisCacheDao.getObjByKey(userToken);
				params.put("userId", userObject.getId());
				if(category==null||"".equals(category)){
					category = "0"; 
				}
				String categorydb = "0";
				List<HashMap<String,Object>> ResList = new ArrayList<HashMap<String,Object>>();
				
				if("1".equals(category) || "0".equals(category)){
					List<HashMap<String,Object>> zjMapList = queryService.getZjList(params);
					if(zjMapList!=null && zjMapList.size()>0){//
						categorydb = "1";
						for (int i = 0; i < zjMapList.size(); i++) {
							HashMap<String,Object> zjMap = zjMapList.get(i);
							HashMap<String,Object> resEndHashMap = new HashMap<String,Object>();
							String comId = String.valueOf(zjMap.get("comId"));
							resEndHashMap.put("itemFirm", getItemFirmInfo(comId,publishService));
							//资金
							time = String.valueOf(zjMap.get("createtime")).substring(0,16);
							resEndHashMap.put("itemMoney",getItemMoney(zjMap));
							resEndHashMap.put("id", zjMap.get("id"));
							resEndHashMap.put("category", categorydb);
							resEndHashMap.put("level", level);
							resEndHashMap.put("browserCount", browserCount);
							resEndHashMap.put("time", time);
							ResList.add(resEndHashMap);
						}
					}
				} 
				if("2".equals(category) || "0".equals(category)){
					List<HashMap<String,Object>> zcMapList = queryService.getZcList(params);
					if(zcMapList!=null && zcMapList.size()>0){//
						categorydb = "2";	
						for (int i = 0; i < zcMapList.size(); i++) {
							HashMap<String,Object> zcMap = zcMapList.get(i);
							HashMap<String,Object> resEndHashMap = new HashMap<String,Object>();
							String comId = String.valueOf(zcMap.get("comId"));
							resEndHashMap.put("itemFirm", getItemFirmInfo(comId,publishService));
							//资产
							time = String.valueOf(zcMap.get("createtime")).substring(0,16);
							resEndHashMap.put("itemProject", getItemProject(zcMap));
							resEndHashMap.put("id", zcMap.get("id"));
							resEndHashMap.put("category", categorydb);
							resEndHashMap.put("level", level);
							resEndHashMap.put("browserCount", browserCount);
							resEndHashMap.put("time", time);
							ResList.add(resEndHashMap);
						}
					}
				}
				if("3".equals(category) || "0".equals(category)){
					List<HashMap<String,Object>> otherMapList = queryService.getOtherList(params);
					if(otherMapList!=null && otherMapList.size()>0){//
						categorydb = "3";	
						for (int i = 0; i < otherMapList.size(); i++) {
							HashMap<String,Object> otherMap = otherMapList.get(i);
							HashMap<String,Object> resEndHashMap = new HashMap<String,Object>();
							String comId = String.valueOf(otherMap.get("comId"));
							resEndHashMap.put("itemFirm", getItemFirmInfo(comId,publishService));
							//其它
							time = String.valueOf(otherMap.get("createtime")).substring(0,16);
							resEndHashMap.put("itemOther", getItemOther(otherMap));
							resEndHashMap.put("id", otherMap.get("id"));
							resEndHashMap.put("category", categorydb);
							resEndHashMap.put("level", level);
							resEndHashMap.put("browserCount", browserCount);
							resEndHashMap.put("time", time);
							ResList.add(resEndHashMap);
						}
					}
				}
				sort(ResList);
				HashMap<String,Object> hashMapobject = new HashMap<String,Object>();
				List<HashMap<String,Object>> tmpList = new ArrayList<HashMap<String,Object>>();
				if(ResList!=null){
					for (int i = (pageIndex-1) * pageSize; i<pageIndex*pageSize && i<ResList.size(); i++) {
						tmpList.add(ResList.get(i));
					}
				}
				hashMapobject.put("listProject",tmpList);
				hashMapobject.put("pageCount", String.valueOf(ResList.size() / pageSize + (ResList.size() % pageSize == 0 ? 0 : 1)));
				hashMapobject.put("itemCount", String.valueOf(ResList.size()));
				write(response, "Success", hashMapobject);// 登录成功后返回相关的信息
			//======================================以下为发布者============================================	
			}else if("4".equals(type)){//发布者
				
				params.put("userId", idParam);
				
				if(category==null||"".equals(category)){
					category = "0"; 
				}
				
				String categorydb = "0";
				List<HashMap<String,Object>> ResList = new ArrayList<HashMap<String,Object>>();
				
				if("1".equals(category) || "0".equals(category)){
					List<HashMap<String,Object>> zjMapList = queryService.getZjList(params);
					if(zjMapList!=null && zjMapList.size()>0){//
						categorydb = "1";
						for (int i = 0; i < zjMapList.size(); i++) {
							HashMap<String,Object> zjMap = zjMapList.get(i);
							HashMap<String,Object> resEndHashMap = new HashMap<String,Object>();
							String comId = String.valueOf(zjMap.get("comId"));
							resEndHashMap.put("itemFirm", getItemFirmInfo(comId,publishService));
							//资金
							time = String.valueOf(zjMap.get("createtime")).substring(0,16);
							resEndHashMap.put("itemMoney",getItemMoney(zjMap));
							resEndHashMap.put("id", zjMap.get("id"));
							resEndHashMap.put("category", categorydb);
							resEndHashMap.put("level", level);
							resEndHashMap.put("browserCount", browserCount);
							resEndHashMap.put("time", time);
							ResList.add(resEndHashMap);
						}
					}
				}else if("2".equals(category) || "0".equals(category)){
					List<HashMap<String,Object>> zcMapList = queryService.getZcList(params);
					if(zcMapList!=null && zcMapList.size()>0){//
						categorydb = "2";	
						for (int i = 0; i < zcMapList.size(); i++) {
							HashMap<String,Object> zcMap = zcMapList.get(i);
							HashMap<String,Object> resEndHashMap = new HashMap<String,Object>();
							String comId = String.valueOf(zcMap.get("comId"));
							resEndHashMap.put("itemFirm", getItemFirmInfo(comId,publishService));
							//资产
							time = String.valueOf(zcMap.get("createtime")).substring(0,16);
							resEndHashMap.put("itemProject", getItemProject(zcMap));
							resEndHashMap.put("id", zcMap.get("id"));
							resEndHashMap.put("category", categorydb);
							resEndHashMap.put("level", level);
							resEndHashMap.put("browserCount", browserCount);
							resEndHashMap.put("time", time);
							ResList.add(resEndHashMap);
						}
					}
				}else if("3".equals(category) || "0".equals(category)){
					List<HashMap<String,Object>> otherMapList = queryService.getOtherList(params);
					if(otherMapList!=null && otherMapList.size()>0){//
						categorydb = "3";	
						for (int i = 0; i < otherMapList.size(); i++) {
							HashMap<String,Object> otherMap = otherMapList.get(i);
							HashMap<String,Object> resEndHashMap = new HashMap<String,Object>();
							String comId = String.valueOf(otherMap.get("comId"));
							resEndHashMap.put("itemFirm", getItemFirmInfo(comId,publishService));
							//其它
							time = String.valueOf(otherMap.get("createtime")).substring(0,16);
							resEndHashMap.put("itemOther", getItemOther(otherMap));
							resEndHashMap.put("id", otherMap.get("id"));
							resEndHashMap.put("category", categorydb);
							resEndHashMap.put("level", level);
							resEndHashMap.put("browserCount", browserCount);
							resEndHashMap.put("time", time);
							ResList.add(resEndHashMap);
						}
					}
				}
				sort(ResList);
				HashMap<String,Object> hashMapobject = new HashMap<String,Object>();
				List<HashMap<String,Object>> tmpList = new ArrayList<HashMap<String,Object>>();
				if(ResList!=null){
					for (int i = (pageIndex-1) * pageSize; i<pageIndex*pageSize && i<ResList.size(); i++) {
						tmpList.add(ResList.get(i));
					}
				}
				hashMapobject.put("listProject",tmpList);
				hashMapobject.put("pageCount", String.valueOf(ResList.size() / pageSize + (ResList.size() % pageSize == 0 ? 0 : 1)));
				hashMapobject.put("itemCount", String.valueOf(ResList.size()));
				write(response, "Success", hashMapobject);// 登录成功后返回相关的信息
				
			}else if("5".equals(type)){//我的收藏
				//待定
			}
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

	private static void sort(List<HashMap<String, Object>> data) {
        Collections.sort(data, new Comparator<HashMap>() {
        public int compare(HashMap o1, HashMap o2) {
                String a = (String) o1.get("time");
                String b = (String) o2.get("time");
                //asc
                //return a.compareTo(b);
                //desc
                return b.compareTo(a);
            }
                // 升序
        });
                //return a.compareTo(b);
      }
	
}
