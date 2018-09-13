package com.hz.app.order.dao;

import java.util.List;
import java.util.Map;

public interface PaymentDao {
	/**
	 * 保存看房信息 S0039
	 * @param map
	 * @return
	 */
	public int saveSmartView(Map<String, String> map);
	/**
	 * 保存看房订单 S0039
	 * @param map
	 * @return
	 */
	public int saveSmartViewOrder(Map<String, String> map);
	/**
	 * 修改看房订单 S0040
	 * @param map
	 * @return
	 */
	public int updateSmartViewOrder(Map<String, String> map);
	/**
	 * 查询房间信息 S0040
	 * @param map
	 * @return
	 */
	public Map<String, Object> searchHouse(Map<String, String> map);
	/**
	 * 立即签约 S0041 
	 * @param map
	 * @return
	 */
	public int contractImmediatelyLoad(Map<String, String> map);
	/**
	 * 立即签约 S0042  支付
	 * @param map
	 * @return
	 */
	public int contractImmediatelyPay(Map<String, String> map);
	/**
	 * 支付账单金额 S0057
	 * @param map
	 * @return
	 */
	public int doPaymentOfBill(Map<String, String> map);
	
	/**
	 * 增加账户流水
	 * @param map
	 * @return
	 */
	public int createUserBillFlow(Map<String, String> map);
	
	/**
	 * 查询付款方式S0065
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getPaymentMethodList();
	
	/**
	 * 查询信用分S0065
	 * @param map
	 * @return
	 */
	public Map<String, String> getUserCore(Map<String, String> map);
	/**
	 * 查询房费S0065
	 * @param map
	 * @return
	 */
	public  Map<String, String> gethosePirce(Map<String, String> map);

	/**
	 * 订单支付成功后 生成对应的账单
	 * @param mapL
	 * @return
	 */
	public int createBillList(List<Map<String, String>> mapL);
	/**
	 * 查询签约订单信息
	 * @param map
	 * @return
	 */
	public Map<String, String> getUnderstandSign(Map<String, String> map);
	/**
	 * 查询用户看房信息
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> searchSmartViewOrder(Map<String, String> map);
	/**
	 * 修改用户押金（看房）
	 * @param map
	 * @return
	 */
	public int updateUserYaJin(Map<String, String> map);
	
	/**
	 * 查询用户失信次数
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> searchshixinNum(Map<String, String> map);
	
	/**
	 * 添加合同相关信息
	 * @param map
	 * @return
	 */
	public int createContractInformation(Map<String, String> map);
	/**
	 * 查询订单相关信息（合同使用）
	 * @param map
	 * @return
	 */
	public Map<String, String> searchOrderDetail(Map<String, String> map);
	/**
	 * 修改用户优惠券的状态
	 * @param map
	 * @return
	 */
	public int updateUserRedeem(Map<String, String> map);
	/**
	 * 判断优惠券状态
	 * @param map
	 * @return
	 */
	public Map<String, String> judgeRedeemStatus(Map<String, String> map);
	
	/**
	 * 修改房间状态
	 * @return
	 */
	public int updateRoomStatus(Map<String, String> map);
	
	/**
	 * 增加支付单
	 * @param map
	 * @return
	 */
	public int createPaymentOperation(Map<String, String> map);
	
	/**
	 * 修改支付单
	 * @param map
	 * @return
	 */
	public int updatePaymentOperation(Map<String, String> map);
	
	
	/**
	 * 查询 待查寻支付订单 
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> searchPaymentOperation(Map<String, String> map);
	
	
	
	/**
	 * 根据ID查询指定账单
	 * @param map
	 * @return
	 */
	public Map<String, String> searchUserBillInfo(Map<String, String> map);
	
	/**
	 * 查询指定用户的订单信息
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> searchPaymentOperationByuser(Map<String, String> map);
	
	
	/**
	 * 查询指定用户的订单信息
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> searchPaymentOperationByuser_(Map<String, Object> map);
	
	
	public Map<String, String> searchSmart_view_order(Map<String, Object> map);
		
	public Map<String, String> searchBill(Map<String, Object> map);
	/**
	 * 查看用户是否可约看当前房源
	 * @param map
	 * @return
	 */
	public Map<String, String> iskanfang(Map<String, String> map);
	/**
	 * 根据看房订单编号 查询看房订单
	 * @param map
	 * @return
	 */
	public Map<String, String> searchViewOrder(Map<String,String> map);
	
	/**
	 * 查询签约订单是否存在
	 * @param map
	 * @return
	 */
	public Map<String, String> searchOrder(Map<String,String> map);
}
