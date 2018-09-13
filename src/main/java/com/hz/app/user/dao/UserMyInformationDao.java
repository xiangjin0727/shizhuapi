package com.hz.app.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import flexjson.factories.MapObjectFactory;


public interface UserMyInformationDao {

	public List<Map<String, Object>> myCollection_01(Map<String, String> map);
	/**
	 * 查询缩略图
	 * @param map
	 * @return
	 */
	public List<String> myCollection_02(Map<String, Object> map);
	/**
	 * 查询标签
	 * @param map
	 * @return
	 */
	public Map<String, String> myCollection_03(@Param("label_id")String label_id);
	
	/**
	 * 查询合作商
	 * @param map
	 * @return
	 */
	public Map<String, String> myCollection_04(@Param("house_partner_id")String house_partner_id);
	
	/**
	 * 我的约看
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> myAppointment(Map<String, String> map);
	
	
	/**
	 * 我的预定
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> myReservation(Map<String, String> map);
	
	/**
	 * 租赁订单
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> myLeaseOrder(Map<String, String> map);
	
	/**
	 * 房屋评价
	 * @param map
	 * @return
	 */
	public int myHousingEvaluation(Map<String, String> map);
	
	
	/**
	 * 保洁评价
	 * @param map
	 * @return
	 */
	public int myCleaningEvaluation(Map<String, String> map);
	
	/**
	 * 获取我的管家列表
	 * @param map
	 * @return
	 */
	public Map<String, String> myHousekeeperList(Map<String, String> map);
	
	public List<String> myHouseInfo(Map<String, String> map);
	
	
	/**
	 * 生活订单  S0012
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> myLifeOrder(Map<String, String> map);
	
	
	/**
	 * 获取个人信息  S0031
	 * @param map
	 * @return
	 */
	public Map<String, String> getPersonalInformation(Map<String, String> map);
	

	/**
	 * 维修评价  S0015
	 * @param map
	 * @return
	 */
	public int myMaintenanceEvaluation(Map<String, String> map);
	
	
	/**
	 * 我的优惠券 S0022
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> myCouponList(Map<String, String> map);
	
	
	/**
	 * 房屋评价列表  S0045
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> housingEvaluationList(Map<String, String> map);
	/**
	 * 保洁评价列表  S0046
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> cleanlinessEvaluationList(Map<String, String> map);
	/**
	 * 维修评价列表  S0047
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> maintenanceEvaluationList(Map<String, String> map);

	/**
	 * 保洁评价信息
	 * @param map
	 * @return
	 */
	public Map<String,String> huodebaojieliebiaopingjia(Map<String, String> map);
	/**
	 * 维修评价信息
	 * @param map
	 * @return
	 */
	public Map<String,String> weixiujieliebiaopingjia(Map<String, String> map);
	
	/**
	 *  我的会员  S0017
	 * @param map
	 * @return
	 */
	public Map<String,String> myMembers(Map<String, String> map);

	/**
	 *  我的钱包  S0021
	 * @param map
	 * @return
	 */
	public Map<String,String> myWallet(Map<String, String> map);

	/**
	 * 我的合同  S0024
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> myContract(Map<String, String> map);
	/**
	 * 获取合同详情  S0054
	 * @param map
	 * @return
	 */
	public Map<String, String> myContractDetail(Map<String, String> map);

	/**
	 * 查询用户房屋评价内容
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> searchHousEvaluation(Map<String, String> map);
}
