package com.hz.app.order.dao;

import java.util.List;
import java.util.Map;


public interface OrderDao {
	
	/**
	 * 智享维修-报修 S0027
	 * @param map
	 * @return
	 */
	public int doApplyForRepair(Map<String, String> map);

	/**
	 * 增加用户积分
	 * @param map
	 * @return
	 */
	public int updateUserCore(Map<String, String> map);
	
	/**
	 * 用户信用分变更
	 * @param map
	 * @return
	 */
	public int updateUserXinYongCore(Map<String, String> map);
	
	/**
	 * 待缴账单 S0025
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> searchUnpaidUserBill(Map<String, String> map);
	
	/**
	 * 账单明细 S0023
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getBillingDetails(Map<String, String> map);
	
	/**
	 *获取账单详情 S0056
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getDetailsOfTheBill(Map<String, String> map);
	/**
	 * 获取合同下房租账单列表 S0055
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getBillList(Map<String, String> map);
	
	
}
