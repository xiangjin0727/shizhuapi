package com.hz.util.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by liuwei on 2015/7/23.
 * 
 * 投资工具类
 * @author lch
 * @version 1.1
 * @since 2015-09-24
 */
public class InvestHelper {
	/**
	* 生成回款计划
	* @param investId 投资序号
	* @param investAmount 投资额
	* @param yearRate 年利率
	* @param closeTime 封闭期限
	* @param repayStyle 回款方式(2-每月还息到期返还本金; 3-一次性还款)
	* @param arriTradeTime 到账日期
	* @param interestAccrualDate 计息日期
	* 
	* 以到账日期为准，到账日期<=当月19号，下月一号开始付息，到账日期>当月19号下下月一号开始付息
	* 所以到账日期<=当月19号，应该是n+1次给客户转账，到账日期>当月19号应该是n次转账
	* 
	* 投资示例：
	* 
	* 投资10000元，封闭期限3个月，年利率 12%
	* 一次性还款   总利息 = 10000*12%*3/12
	* 
	* ====== 每月还息到期返还本金 ==========
	* 注解：
	* 1. 计息日期不满整月的，首月利息按照天利息计算(计息日至月底天数)
	* 2. 计息日期不满整月的，末月利息按照天利息计算(计息日 - 1)
	* 3. 到期日期 = 计息日期 + 封闭期限 - 1
	* 4. 到期还款日期 = 计息日期 + 封闭期限
	* 
	* *** 投资日期(到账日期) <= 19号 *** 
	* 投资8月1号，计息日期8月2号，到期日期 11.1
	* 1) 首月利息 30天利息
	* 2) 末月还款日期 11.2，利息2天利息
	* 3) 4次给客户转账 9.1 10.1 11.1 11.2
	* 
	* *** 投资日期(到账日期) > 19号 ***
	* 投资8月25号，计息日期8月26号，到期日期 11.25
	* 1) 首月利息 6天利息 + 9月份整月利息
	* 2) 末月还款日期 11.26，利息25天利息
	* 3) 3次给客户转账 10.1 11.1 11.26
	* 
	* *** 整月计息情景 ***
	* 投资8月1号，计息日期8月1号，到期日期 10.31
	* 1) 4次给客户转账 9.1 10.1 11.1 11.1
	* 
	* 投资7月31号，计息日期8月1号，到期日期 10.31
	* 1) 首月利息 8月整月利息
	* 2) 末月还款日期 11.1，整月利息
	* 3) 3次给客户转账 9.1 10.1 11.1
	*/
	public static List<Map<String, Object>> produceInvestBillPlan(String investId, BigDecimal investAmount, BigDecimal yearRate, int closeTime, 
			int repayStyle, Date arriTradeTime, Date interestAccrualDate) {
		
		// 应收利息 还款日期
		BigDecimal sinte;
		Map<String, Object> invBillplanParams = new HashMap<String, Object>();
		List<Map<String, Object>> invBillplanList = new ArrayList<Map<String, Object>>();
		
		Calendar calendar = Calendar.getInstance();
		
		// 一次性还款
		if (3 == repayStyle) {
			invBillplanParams.put("investId", investId);
			invBillplanParams.put("billNum", 1);
			
			calendar.setTime(interestAccrualDate);
			calendar.add(Calendar.MONTH, closeTime);
			
			sinte = yearRate.multiply(investAmount).multiply(new BigDecimal(closeTime)).divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
			// 应收日期 应收利息
			invBillplanParams.put("sDate", calendar.getTime());
			invBillplanParams.put("sinte", sinte);
			invBillplanParams.put("stcapi", investAmount);
			invBillplanParams.put("capiYear", null);
			invBillplanParams.put("capiMonth", null);
		
			invBillplanList.add(invBillplanParams);
		} else if (2 == repayStyle) {
			// 每月还息到期返还本金
			Calendar interestCalendar = Calendar.getInstance();
			// 到账日[几号]
			calendar.setTime(arriTradeTime);
			int arriTradeDay = calendar.get(Calendar.DAY_OF_MONTH);
			
			interestCalendar.setTime(interestAccrualDate);
			// 计息日[几号]
			int interestDay = interestCalendar.get(Calendar.DAY_OF_MONTH);
			// 非满月当月计息天数
			int curMonthInterestDays = interestCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - interestDay + 1;
			
			int totalBillNums = arriTradeDay <= 19 ? closeTime + 1 : closeTime;
			
			// 应还日期月份
			if (arriTradeDay > 19) {
				calendar.add(Calendar.MONTH, 1);
			}
			
			for (int index = 1; index <=totalBillNums; index++) {
				invBillplanParams = new HashMap<String, Object>();
				invBillplanParams.put("investId", investId);
				invBillplanParams.put("billNum", index);
				invBillplanParams.put("capiYear", calendar.get(Calendar.YEAR));
				invBillplanParams.put("capiMonth", calendar.get(Calendar.MONTH) + 1);
				
				calendar.set(Calendar.DATE, 1);
				calendar.add(Calendar.MONTH, 1);
				
				if (arriTradeDay <= 19) {
					if (index == 1) {
						if (interestDay == 1) {
							sinte = yearRate.multiply(investAmount).divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_UP);
						} else {
							sinte = yearRate.multiply(investAmount).multiply(new BigDecimal(curMonthInterestDays)).divide(new BigDecimal(365), 2, BigDecimal.ROUND_HALF_UP);
						}
					} else if (index == totalBillNums) {
						sinte = interestDay == 1 ? new BigDecimal(0) : yearRate.multiply(investAmount).multiply(new BigDecimal(interestDay - 1)).divide(new BigDecimal(365), 2, BigDecimal.ROUND_HALF_UP);
					} else {
						sinte = yearRate.multiply(investAmount).divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_UP);
					}
				} else {
					if (index == 1) {
						if (interestDay == 1) {
							sinte = yearRate.multiply(investAmount).divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_UP);
						} else {
							sinte = (yearRate.multiply(investAmount).divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_UP)).add(
									yearRate.multiply(investAmount).multiply(new BigDecimal(curMonthInterestDays)).divide(new BigDecimal(365), 2, BigDecimal.ROUND_HALF_UP));
						}
					} else if (index == totalBillNums) {
						if (interestDay == 1) {
							sinte = yearRate.multiply(investAmount).divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_UP);
						} else {
							sinte = yearRate.multiply(investAmount).multiply(new BigDecimal(interestDay - 1)).divide(new BigDecimal(365), 2, BigDecimal.ROUND_HALF_UP);
						}
					} else {
						sinte = yearRate.multiply(investAmount).divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_UP);
					}
				}
				
				
				//应收利息 应收本金
				invBillplanParams.put("sinte", sinte);
				invBillplanParams.put("stcapi", index == totalBillNums ? investAmount : 0);
				
				if (index == totalBillNums) {
					interestCalendar.add(Calendar.MONTH, closeTime);
					invBillplanParams.put("sDate", interestCalendar.getTime());
				} else {
					invBillplanParams.put("sDate", calendar.getTime());
				}
				
				invBillplanList.add(invBillplanParams);
			}
		}
		
		return invBillplanList;
	}
	/**
	 * 计算投资应收利息总额
	 * @param investAmount 投资额
	 * @param yearRate 年利率
	 * @param closeTime 封闭期限
	 * @param repayStyle 回款方式(2-每月还息到期返还本金; 3-一次性还款)
	 * @param interestAccrualDate 计息日期
	 * @return
	 */
	public static BigDecimal calculReceivedInterest(BigDecimal investAmount, BigDecimal yearRate, int closeTime, int repayStyle, Date interestAccrualDate) {
		BigDecimal receivedInterest = new BigDecimal(0);
		if (3 == repayStyle) {
			receivedInterest = yearRate.multiply(investAmount).multiply(new BigDecimal(closeTime)).divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
		} else if (2 == repayStyle) {
			Calendar interestCalendar = Calendar.getInstance();
			interestCalendar.setTime(interestAccrualDate);
			
			// 计息日[几号]
			int interestDay = interestCalendar.get(Calendar.DAY_OF_MONTH);
			int curMonthInterestDays = interestCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			if (interestDay == 1) {
				receivedInterest = (yearRate.multiply(investAmount).divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(closeTime));
			} else {
				receivedInterest = (yearRate.multiply(investAmount).divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(closeTime - 1)).add(
						yearRate.multiply(investAmount).multiply(new BigDecimal(curMonthInterestDays - interestDay + 1)).divide(new BigDecimal(365), 2, BigDecimal.ROUND_HALF_UP)).add(
								yearRate.multiply(investAmount).multiply(new BigDecimal(interestDay - 1)).divide(new BigDecimal(365), 2, BigDecimal.ROUND_HALF_UP));
			}
		}
		
		return receivedInterest;
	}
	/**
	 * 计算投资回款账单总期数
	 * @param closeTime 封闭期限
	 * @param repayStyle 回款方式(2-每月还息到期返还本金; 3-一次性还款)
	 * @param arriTradeTime 到账日期
	 * @throws
	 */
	public static int calculInvestBillNums(int closeTime, int repayStyle, Date arriTradeTime) {
		if (3 == repayStyle) {
			return 1;
		} else {
			Calendar calendar = Calendar.getInstance();
	
			// 到账日[几号]
			calendar.setTime(arriTradeTime);
			int arriTradeDay = calendar.get(Calendar.DAY_OF_MONTH);
			
			return arriTradeDay <= 19 ? closeTime + 1 : closeTime;
		}
	}
	/**
	 * 第三方接口返回值检测[单结果]
	 * @param returnValue 第三方接口返回值
	 * @return true-调用成功;false-调用失败;
	 */
	public static Boolean checkThirdpartyInterfaceSingleReturnValue(String returnValue) {
		Boolean checkedResult = true;
		String checkAttributeName = "retCode";
		if (StringUtils.isBlank(returnValue) || !JSONObject.parseObject(returnValue).get("retCode").equals("0000")) {
			checkedResult = false;
		} else {
			try {
				JSONObject returnJsonValue = JSONObject.parseObject(returnValue);
				if (!returnJsonValue.containsKey(checkAttributeName)) {
					checkedResult = false; 
				} else {
					if (!returnJsonValue.getString(checkAttributeName).equals("0000")) {
						checkedResult = false;
					}
				}
				
			} catch (Exception ex) {
				checkedResult = false;
			}
		}
		
		return checkedResult;
	}
}
