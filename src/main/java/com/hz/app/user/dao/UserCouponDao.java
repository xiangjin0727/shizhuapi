package com.hz.app.user.dao;

import java.util.List;
import java.util.Map;


public interface UserCouponDao {
	/**
	 * 获取积分兑换列表 S0018
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getFractionalTransactionList();
	/**
	 * 积分兑换操作 S0019
	 * @param map
	 * @return
	 */
	public int convertibilityOperation(Map<String, String> map);
	/**
	 * 获取积分兑换记录 S0020
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getConvertibilityRecord(Map<String, String> map);
	
	/**
	 * 更新用户积分S0019
	 * @param map
	 * @return
	 */
	public int updateUserCore(Map<String, String> map);
	
	/**
	 * 查询用户积分S0019
	 * @param map
	 * @return
	 */
	public Map<String, String> searchUserCore(Map<String, String> map);
	/**
	 * 获取生日特权房租抵用券 S0052
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getBirthdayVoucher(Map<String, String> map);
	
	
}
