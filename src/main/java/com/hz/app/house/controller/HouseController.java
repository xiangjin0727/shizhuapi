package com.hz.app.house.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.hz.app.house.dao.HouseDao;
import com.hz.app.servlet.BaseController;
import com.hz.app.user.dao.UserMyInformationDao;
import com.hz.core.util.StringUtil;
import com.hz.util.IdcardInfoExtractor;
import com.hz.util.LocationUtil;
import com.ibm.icu.math.BigDecimal;

/**
 * 获取房源列表 S0033
 * 获取房源详情  S0034
 * 获取房源付款方式  S0035
 * 获取看房违约记录  S0050
 * 房源详情，收藏房源  S0059
 * 房源详情，取消收藏房源 S0060
 * 取消自主看房（取消约看）  S0061
 * 自主看房看房时间列表  S0064
 * 获取租住违约记录  S0051
 * 首页图片获取 S0063
 * 完成看房接口 S0067
 * @author XJin
 *
 */
@Controller
@RequestMapping("house")
public class HouseController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(HouseController.class);
	
	@Autowired
	UserMyInformationDao userMyInformationDao;
	@Autowired
	HouseDao houseDao;

	/**
	 * 获取房源列表 S0033
	 * @return
	 */
	@RequestMapping(value = "searchHouseList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> searchHouseList(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------获取房源列表 S0033 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			
			String longitude = String.valueOf(data.get("longitude"));//经度
			String latitude = String.valueOf(data.get("latitude"));//纬度
			
			List<Map<String, Object>> mapL = houseDao.searchHouseList(data);
			
			for(Map<String, Object> m:mapL){
				//装入缩略图
/*				List<String> list_thumbnailS = userMyInformationDao.myCollection_02(m);
				if(list_thumbnailS!=null){
					m.put("house_thumbnail_urls", list_thumbnailS);
				}else{
					m.put("house_thumbnail_urls", "");
				}*/
				
				//计算并装入距离
				String house_latitude_longitude = String.valueOf(m.get("house_latitude_longitude"));
				String []  lat_lon = house_latitude_longitude.split(",");				
				double distance = LocationUtil.getDistance(Double.valueOf(latitude), Double.valueOf(longitude),
						Double.valueOf(lat_lon[0]), Double.valueOf(lat_lon[1]));
				if(distance>=0){
					BigDecimal b = BigDecimal.valueOf(distance);
					BigDecimal di = b.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_DOWN);
					m.put("distance", di);
				}else{
					m.put("distance", 0);
				}
				
				//装入标签
				
				String house_partnerS = String.valueOf(m.get("house_partner"));
/*				String[] house_labelS = String.valueOf(m.get("house_label")).split(",");
				List<Map<String, String>> phList = new ArrayList<>();
				Map<String, String> ph = new HashMap<>();
				for(String p:house_partnerS){*/
					Map<String, String> map = userMyInformationDao.myCollection_04(house_partnerS);
					//if(map!=null){
					m.put("label_name", String.valueOf(map.get("house_partner_name")));
						//ph.put("label_ colour",  String.valueOf(map.get("house_partner_color")));
					m.put("house_partner_logo_url",  String.valueOf(map.get("house_partner_logo_url")!=null?map.get("house_partner_logo_url"):""));					
				/*		phList.add(ph);
					}
				}
*//*				for(String h:house_labelS){
					Map<String, String> map = userMyInformationDao.myCollection_03(h);
					if(map!=null){
						ph.put("label_name", String.valueOf(map.get("label_name")));
						ph.put("label_ colour",  String.valueOf(map.get("label_color")));
						phList.add(ph);
					}
				}*/
				//m.put("label", phList);
				
			}
			
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "获取房源列表成功");
			resultMap.put("data", mapL);
			
			
		} catch (Exception e) {
			logger.error("获取房源列表失败  {}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "获取房源列表失败");
		}
		return resultMap;
	}
/*	public static void main(String[] args) {
		String house_latitude_longitude = "116.469914,40.007505";
		String []  lat_lon = house_latitude_longitude.split(",");				
		double distance = LocationUtil.getDistance(Double.valueOf(0), Double.valueOf(0),
				Double.valueOf(lat_lon[0]), Double.valueOf(lat_lon[1]));
		System.err.println(distance);
	}*/
	
	/**
	 * 获取房源详情  S0034
	 * @return
	 */
	@RequestMapping(value = "getRoomDetails",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getRoomDetails(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------获取房源详情  S0034 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			String longitude = String.valueOf(data.get("longitude")==null?"0":String.valueOf(data.get("longitude")));//经度
			String latitude = String.valueOf(data.get("latitude")==null?"0":String.valueOf(data.get("latitude")));//纬度
			Map<String, Object> mapL = houseDao.getHouseInfo(data);
			//房源详情缩略图
			List<Map<String, String>> houseThumbnail = houseDao.getHouseThumbnail(data);
			mapL.put("house_thumbnail_urls", houseThumbnail);
			
			//房间信息
			List<Map<String, String>> peopleInRoom = houseDao.getPeopleInRoom(data);
			for(Map<String, String> m: peopleInRoom){
				String idCard =String.valueOf(m.get("user_id_card"));
				IdcardInfoExtractor idcardInfo=new IdcardInfoExtractor(idCard);
				String constellation = idcardInfo.getConstellation(idcardInfo.getMonth(), idcardInfo.getDay());
				m.put("user_sex", idcardInfo.getGender());
				m.put("user_constellation", constellation);
				if(String.valueOf(data.get("room_info_id")).equals(String.valueOf(m.get("room_info_id")))){
					m.put("is_current", "0");
				}else{
					m.put("is_current", "1");
				}
			}
			mapL.put("people_in_room", peopleInRoom);
			
			//管家列表
			String[] keeperS = String.valueOf(mapL.get("house_keeper")).split(",");
			List<Map<String, String>> m = new ArrayList<>();
			for(String k:keeperS){
				Map<String, String> map  = new HashMap<>();
				map.put("house_keeper_id", k);
				Map<String, String> houseKeeper = houseDao.getHouseKeeper(map);
				m.add(houseKeeper);
			}
			mapL.put("keepers", m);
			//房间标配图片
			List<Map<String, String>> rootSet = houseDao.getRootSet(data);
			mapL.put("room_marking_url", rootSet);
			
			if(StringUtil.isEmpty(String.valueOf(data.get("user_id")))){
				mapL.put("is_collection", 1);
			}else{
				//是否收藏			
				String collection = houseDao.isCollection(data);
				if(StringUtil.isEmpty(collection)){
					mapL.put("is_collection", 1);
				}else{
					mapL.put("is_collection", 0);
				}
			}	
			
			
			
			String house_partnerS = String.valueOf(mapL.get("house_partner"));
			Map<String, String> map = userMyInformationDao.myCollection_04(house_partnerS);			
			mapL.put("label_name", String.valueOf(map.get("house_partner_name")));
			mapL.put("house_partner_logo_url",  String.valueOf(map.get("house_partner_logo_url")));					

			//计算并装入距离
			String house_latitude_longitude = String.valueOf(mapL.get("house_latitude_longitude"));
			String []  lat_lon = house_latitude_longitude.split(",");				
			double distance = LocationUtil.getDistance(Double.valueOf(latitude), Double.valueOf(longitude),
					Double.valueOf(lat_lon[0]), Double.valueOf(lat_lon[1]));
			if(distance>=0){
				BigDecimal b = BigDecimal.valueOf(distance);
				BigDecimal di = b.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_DOWN);
				mapL.put("distance", di);
			}else{
				mapL.put("distance", 0);
			}
			
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "获取房源列表成功");
			resultMap.put("data", mapL);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "获取房源详情 失败");
		}
		return resultMap;
	}

public static void main(String[] args) {




//341225199007088999
//410203198906120612
IdcardInfoExtractor idcardInfo=new IdcardInfoExtractor("410203198001019954");
String constellation = idcardInfo.getConstellation(idcardInfo.getMonth(), idcardInfo.getDay());
System.err.println(constellation);
}
	
	/**
	 * 获取房源付款方式  S0035
	 * @return
	 */
	@RequestMapping(value = "getAccessToHousePayment",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getAccessToHousePayment(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------获取房源付款方式  S0035 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");

			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "获取房源付款方式失败");
		}
		return resultMap;
	}

	/**
	 * 获取看房违约记录  S0050
	 * @return
	 */
	@RequestMapping(value = "getHouseDefaultRecords",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getHouseDefaultRecords(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------获取看房违约记录  S0050 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String, String>> listM = houseDao.getHouseDefaultRecords(data);
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "获取房源列表成功");
			resultMap.put("data", listM);
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "获取看房违约记录失败");
		}
		return resultMap;
	}

	/**
	 * 房源详情，收藏房源  S0059
	 * @return
	 */
	@RequestMapping(value = "doCollectionOfHouses",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> doCollectionOfHouses(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------房源详情，收藏房源  S0059 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			houseDao.doCollectionOfHouses(data);
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "房源详情，收藏房源成功");
			Map<String, String> m = new HashMap<>();
			m.put("result_code", "0000");
			resultMap.put("data", m);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "房源详情，收藏房源失败");
		}
		return resultMap;
	}
	/**
	 * 房源详情，取消收藏房源 S0060
	 * @return
	 */
	@RequestMapping(value = "doCancelCollectionOfHouses",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> doCancelCollectionOfHouses(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------房源详情，取消收藏房源 S0060 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			houseDao.doCancelCollectionOfHouses(data);
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "房源详情，取消收藏房源成功");
			Map<String, String> m = new HashMap<>();
			m.put("result_code", "0000");
			resultMap.put("data", m);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "房源详情，取消收藏房源失败");
		}
		return resultMap;
	}

	
	/**
	 * 取消自主看房（取消约看）  S0061
	 * @return
	 */
	@RequestMapping(value = "doCancelSeeHouse",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> doCancelSeeHouse(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------取消自主看房（取消约看）  S0061入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			houseDao.doCancelSeeHouse(data);
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "取消自主看房（取消约看） 成功");
			Map<String, String> m = new HashMap<>();
			m.put("result_code", "0000");
			resultMap.put("data", m);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "取消自主看房（取消约看） 失败");
		}
		return resultMap;
	}

	
	/**
	 * 自主看房看房时间列表  S0064
	 * @return
	 */
	@RequestMapping(value = "getAvailableTime",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getAvailableTime(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------自主看房看房时间列表  S0064入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String, String>> listM = new ArrayList<>();
			String [] t = {"08:00","08:20","09:00","09:20","10:00","10:20","11:00","11:20","12:00","12:20"
					,"13:00","13:20","14:00","14:20","15:00","15:20","16:00","16:20","17:00","17:20","18:00"
					,"18:20","19:00","19:20","20:00","20:20"};
			List<String> li = Arrays.asList(t);
		
			List<String> liTime = houseDao.getAvailableTime(data);
			String dt = String.valueOf(data.get("time"));
			SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formate2 = new SimpleDateFormat("HH:mm");
			String systime = formate.format(new Date());
			String sysday = formate2.format(new Date());
			if(dt.compareTo(systime)<0){
				logger.error("自主看房看房时间列表  S0064  Time参数错误");
				 throw new Exception();
			}
			for(String ti:li){
				Map<String, String> m = new HashMap<>();
				if(dt.compareTo(systime)==0){
					if(ti.compareTo(sysday)<=0){
						m.put("time", ti);
						m.put("is_it_available", "1");
					}else{
						boolean isk = true;
						for(String ts:liTime){
							if(ti.compareTo(ts)==0){
								isk=false;
							}
						}
						if(isk){
							m.put("time", ti);
							m.put("is_it_available", "0");
						}else{
							m.put("time", ti);
							m.put("is_it_available", "1");
						}
					}
				}else{
					boolean isk = true;
					for(String ts:liTime){
						if(ti.compareTo(ts)==0){
							isk=false;
						}
					}
					if(isk){
						m.put("time", ti);
						m.put("is_it_available", "0");
					}else{
						m.put("time", ti);
						m.put("is_it_available", "1");
					}
				}
				listM.add(m);
			}

			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "自主看房看房时间列表成功");
			resultMap.put("data", listM);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "自主看房看房时间列表失败");
		}
		return resultMap;
	}
	
	
	
	
	
	/**
	 * 获取租住违约记录  S0051
	 * @return
	 */
	@RequestMapping(value = "getRecordOfBreachOfContract",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getRecordOfBreachOfContract(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------获取租住违约记录  S0051入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String, String>> map = houseDao.getRecordOfBreachOfContract(data);
			
			
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "获取租住违约记录成功");
			resultMap.put("data", map);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "获取租住违约记录失败");
		}
		return resultMap;
	}

	/**
	 * 首页图片获取 S0063
	 * @return
	 */
	@RequestMapping(value = "banner",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> banner(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------首页图片获取 S0063入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String, String>> map = houseDao.getBanner(data);
			
			
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "首页图片获取成功");
			resultMap.put("data", map);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "首页图片获取失败");
		}
		return resultMap;
	}

	/**
	 * 完成看房接口 S0067
	 * @return
	 */
	@RequestMapping(value = "finishTheHouse",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> finishTheHouse(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------完成看房接口 S0067入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			houseDao.finishTheHouse(data);
			
			
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "完成看房接口成功");
			Map<String, String> map = new HashMap<>();
			map.put("result_code", "0000");
			resultMap.put("data", map);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "完成看房接口失败");
		}
		return resultMap;
	}

}
