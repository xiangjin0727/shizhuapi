package com.hz.app.house.dao;

import java.util.List;
import java.util.Map;


public interface HouseDao {
	/**
	 * 获取房源列表 S0033
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> searchHouseList(Map<String, String> map);
	/**
	 * 房间配置图  S0034
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getRootSet(Map<String, String> map);
	
	
	/**
	 * 房源缩略图  S0034
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getHouseThumbnail(Map<String, String> map);
	
	/**
	 * 管家列表  S0034
	 * @param map
	 * @return
	 */
	public Map<String, String> getHouseKeeper(Map<String, String> map);

	/**
	 * 房源详情  S0034
	 * @param map
	 * @return
	 */
	public Map<String, Object> getHouseInfo(Map<String, String> map);
	/**
	 * 是否收藏过 S0034
	 * @param map
	 * @return
	 */
	public String isCollection(Map<String, String> map);
	
	/**
	 * 房源下房间情况  S0034
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getPeopleInRoom(Map<String, String> map);
	
	/**
	 * 获取看房违约记录  S0050
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getHouseDefaultRecords(Map<String, String> map);
	
	/**
	 * 房源详情，收藏房源  S0059
	 * @param map
	 * @return
	 */
	public int doCollectionOfHouses(Map<String, String> map);
	
	/**
	 * 房源详情，取消收藏房源 S0060
	 * @param map
	 * @return
	 */
	public int doCancelCollectionOfHouses(Map<String, String> map);
	
	/**
	 * 取消自主看房（取消约看）  S0061
	 * @param map
	 * @return
	 */
	public int doCancelSeeHouse(Map<String, String> map);
	/**
	 * 自主看房看房时间列表  S0064
	 * @param map
	 * @return
	 */
	public List<String> getAvailableTime(Map<String, String> map);
	
	/**
	 * 获取租住违约记录  S0051
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getRecordOfBreachOfContract(Map<String, String> map);
	/**
	 * 获取首页图片 S0063
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getBanner(Map<String, String> map);
	
	/**
	 * 完成看房接口 S0067
	 * @param map
	 * @return
	 */
	public int finishTheHouse(Map<String, String> map);
	
	
}
