package com.hz.app.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hz.app.house.dao.HouseDao;
import com.hz.app.order.controller.service.PayMentService;
import com.hz.app.order.dao.OrderDao;
import com.hz.app.order.dao.PaymentDao;
import com.hz.app.servlet.BaseController;
import com.hz.app.user.dao.UserDao;
import com.hz.app.user.vo.UserVO;
import com.hz.core.util.StringUtil;
import com.ibm.icu.math.BigDecimal;

/**
 * TODO 智能看房 S0039
 * TODO 智能看房 S0040  支付
 * TODO 立即签约 S0041 
 * TODO 立即签约 S0042  支付
 * 支付账单金额 S0057
 * 房源详情中的付款方式 S0065
 * 退押金(看房) S0053
 * 
 * @author XJin
 *
 */
@Controller
@RequestMapping("payment")
public class PaymentController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	PaymentDao paymentDao;
	@Autowired
	OrderDao orderDao;
	@Autowired
	HouseDao houseDao;
	@Autowired
	UserDao userDao;
	@Autowired
	PayMentService payMentService;
	
	/**
	 * 智能看房 S0039
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value="intelligentHouseLoad",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> intelligentHouseLoad(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------智能看房 S0039 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			String amount = "0"; //看房押金
			//判断用户信用分是否大于等于65
			UserVO u = userDao.searchUserById(data);
			Integer xinyongCore = u.getUser_xinyong_score();
			if(xinyongCore<65){
				resultMap.put("result_code", "0001");
				resultMap.put("result_message", "用户信用不足");
				return resultMap;
			}
			Map<String, String> m = paymentDao.iskanfang(data);
			if(m!=null){
				resultMap.put("result_code", "0001");
				resultMap.put("result_message", "当前房源已被约看");
				return resultMap;
			}
			List<Map<String, String>> shixinNum = paymentDao.searchshixinNum(data);
			if(shixinNum.size()>2){
				resultMap.put("result_code", "0001");
				resultMap.put("result_message", "失信次数过高");
				return resultMap;
			}else if(shixinNum.size()==2){
				amount = (399.00 - u.getUser_yajin_amount())+"";	
			}else if(shixinNum.size()==1){
				amount = (199.00 - u.getUser_yajin_amount())+"";	
			}else{
				if(u.getUser_yajin_amount()>0){
					amount = "0";
				}else{
					amount="99";
				}
			}

			
			data.put("smart_view_order_price", amount);
			
			UUID code = UUID.randomUUID();
			String c = code.toString().replaceAll("-", "");
			data.put("order_code", c);
			paymentDao.saveSmartView(data);
			System.err.println(String.valueOf(data.get("smart_view_id")));
			paymentDao.saveSmartViewOrder(data);
			
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "智能看房成功");
			Map<String, String> mapL = new HashMap<>();
			mapL.put("smart_view_order_code", c);
			mapL.put("amount", amount);
			resultMap.put("data", mapL);
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "智能看房失败");
		}
		return resultMap;
	
	}
/*	public static void main(String[] args) {
		UUID code = UUID.randomUUID();
		System.out.println(code.toString().replaceAll("-", ""));
	}*/
	
	/**
	 * 智能看房 S0040  支付
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value="intelligentHousePay",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> intelligentHousePay(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, String> map = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------智能看房 S0040  支付 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");			
			Map<String, String> oMap = paymentDao.searchViewOrder(data);
			if(oMap==null){
				resultMap.put("result_code", "0001");
				resultMap.put("result_message", "未查询到相关订单");
				return resultMap;
			}
			Map<String, String> m = paymentDao.iskanfang(oMap);
			if(m!=null){
				resultMap.put("result_code", "0001");
				resultMap.put("result_message", "当前房源已被约看");
				return resultMap;
			}
			String code = String.valueOf(data.get("smart_view_order_code"));
			
			if((new BigDecimal("0.00")).compareTo(new BigDecimal(String.valueOf(data.get("amount").trim())))==0){
				data.put("status", "0");
				paymentDao.updateSmartViewOrder(data);	
				map.put("is_payment", "1");			
			}else{
				
			
				//TODO  支付成功后增加积分
				//-----------------支付相关--------------------------
				int payType = Integer.valueOf(String.valueOf(data.get("payment_operation_way")));
				Map<String, String> paramMap = new HashMap<>();
				paramMap.put("body", "时租看房");
				paramMap.put("subject", "时租看房");
				paramMap.put("code", code);
				paramMap.put("amount", String.valueOf(data.get("amount")));			
				
				String resultStr = payMentService.doPay(payType, paramMap);
				String payCode = 	"";		
				//创建支付订单
				Map<String, String> paymentM = new HashMap<>();
				paymentM.put("trade_no", payCode);
				paymentM.put("payment_operation_order_code", code);
				paymentM.put("user_id", String.valueOf(data.get("user_id")));
				paymentM.put("payment_operation_way", payType+"");
				paymentM.put("payment_operation_type", "0");
				paymentM.put("payment_operation_pay_way", "0");
				paymentM.put("payment_amount", String.valueOf(data.get("amount")));		
				paymentM.put("status", "2");
				paymentDao.createPaymentOperation(paymentM);			
				//预下单订单信息（返回给客户端）
				map.put("payment_content", resultStr);
				map.put("is_payment", "0");			
				//-----------------支付相关--------------------------
				
/*				
				BigDecimal amount = new BigDecimal(String.valueOf(data.get("amount")));
				BigDecimal core = amount.divide(new BigDecimal("100"), 0, BigDecimal.ROUND_UP);
				data.put("core", core.toString());
				orderDao.updateUserCore(data);
				
				
				//增加账户流水
				data.put("user_bill_flow_info", "看房押金付款");
				data.put("user_bill_flow_type", "0");
				data.put("user_bill_pay_num", String.valueOf(data.get("amount")));
				data.put("user_bill_pay_type",String.valueOf(data.get("payment_operation_way")));
				data.put("user_bill_id", String.valueOf(data.get("smart_view_order_code")));
				paymentDao.createUserBillFlow(data);
				
				//修改用户押金金额
				
				paymentDao.updateUserYaJin(data);*/
			}		
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "智能看房支付成功");
		
			map.put("result_code", "0000");
			map.put("pay_code", "0000");
			resultMap.put("data", map);
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "智能看房支付失败");
		}
		return resultMap;
	
	}

	
	/**
	 * 立即签约 S0041 
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value="contractImmediatelyLoad",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> contractImmediatelyLoad(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------立即签约 S0041  入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			
			if(!StringUtil.isEmpty(String.valueOf(data.get("understand_sign_redeem_id")))){
				//判断优惠券是否已使用
				Map<String, String> redeem = paymentDao.judgeRedeemStatus(data);
				if(redeem==null||"0".equals(String.valueOf(redeem.get("user_redeem_status")))){	
					resultMap.put("result_code", "0001");
					resultMap.put("result_message", "优惠券已被使用");
					return resultMap;
				}
			}
			
			UUID code = UUID.randomUUID();
			String c = code.toString().replaceAll("-", "");
			data.put("order_code", c);
			paymentDao.contractImmediatelyLoad(data);
			
			
			
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "立即签约成功");
			Map<String, String> map = new HashMap<>();
			map.put("understand_sign_order_code", c);
			resultMap.put("data", map);
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "立即签约失败");
		}
		return resultMap;
	
	}

	
	/**
	 * 立即签约 S0042  支付
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value="contractImmediatelyPay",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> contractImmediatelyPay(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, String> map = new HashMap<>();
		
	/*	String code = UUID.randomUUID().toString().replace("-", "").toUpperCase();*/
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------立即签约 S0042  支付 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			
			String code = String.valueOf(data.get("understand_sign_order_code"));
			
			data.put("amount", "0.01"); //测试使用
			
			Map<String, String> m = paymentDao.searchOrder(data);			
			if(m==null){
				resultMap.put("result_code", "0001");
				resultMap.put("result_message", "订单不存在或订单已支付");
				return resultMap;
			}
			
			//-----------------支付相关--------------------------
			int payType = Integer.valueOf(String.valueOf(data.get("payment_operation_way")));
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("body", "时租租房");
			paramMap.put("subject", "时租租房");
			paramMap.put("code", code);
			paramMap.put("amount", String.valueOf(data.get("amount")));			
			
			String resultStr = payMentService.doPay(payType, paramMap);
			String payCode = 	"";		
			//创建支付订单
			Map<String, String> paymentM = new HashMap<>();
			paymentM.put("trade_no", payCode);
			paymentM.put("payment_operation_order_code", code);
			paymentM.put("user_id", String.valueOf(data.get("user_id")));
			paymentM.put("payment_operation_way", payType+"");
			paymentM.put("payment_operation_type", "0");
			paymentM.put("payment_operation_pay_way", "2");
			paymentM.put("payment_amount", String.valueOf(data.get("amount")));
			paymentM.put("status", "2");
			paymentDao.createPaymentOperation(paymentM);			
			//预下单订单信息（返回给客户端）
			map.put("payment_content", resultStr);
			//-----------------支付相关--------------------------
		
/*			

			//支付成功后增加积分
			BigDecimal amount = new BigDecimal(String.valueOf(data.get("amount")));
			BigDecimal core = amount.divide(new BigDecimal("100"), 0, BigDecimal.ROUND_UP);
			data.put("core", core.toString());
			orderDao.updateUserCore(data);
			
			//生成账单
			Map<String, String> orderM = paymentDao.getUnderstandSign(data);  
			int nums = Integer.valueOf(String.valueOf(orderM.get("timeLong")));
			int month = Integer.valueOf(String.valueOf(orderM.get("month")));
			SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Long time = formate.parse(String.valueOf(orderM.get("understand_sign_time"))).getTime();			
			DateTime makeALoanDate = new DateTime(time);
			
			DateTime makeALoanDate_ = makeALoanDate.minusDays(1);
			DateTime oldT = makeALoanDate;
			List<Map<String, String>> mL= new ArrayList<>();
			
			//生成合同信息
			Map<String, String> con = paymentDao.searchOrderDetail(data);
			con.put("payment_operation_way", String.valueOf(data.get("payment_operation_way")));
			con.put("nums", nums+"");
			paymentDao.createContractInformation(con);
			
			//如果使用了优惠券，修改优惠券状态
			paymentDao.updateUserRedeem(orderM);
			
			
			String user_id = data.get("user_id");
			for(int i=1;i<=nums;i++){
				BigDecimal zujin = (new BigDecimal(String.valueOf(con.get("room_info_price")))).multiply(new BigDecimal(month));
				Map<String, String> dataM = new HashMap<>();
				dataM.put("user_id", user_id);
				makeALoanDate_ = makeALoanDate_.plusMonths(month);
				dataM.put("user_bill_type", "1");//0在线预订1在线签约2在线看房
				dataM.put("user_bill_info", "房租");//消费账单信息
				dataM.put("user_bill_method", "1");//0已缴费1待缴费
				dataM.put("user_bill_pay_day", makeALoanDate_.toString("yyyy-MM-dd HH:mm:ss"));//待缴费日期
				dataM.put("user_bill_pay_num",zujin.toString());//代缴费金额
				dataM.put("user_bill_pay_type", String.valueOf(data.get("payment_operation_way")));//0支付宝1微信
				dataM.put("user_bill_pay_code", String.valueOf(data.get("understand_sign_order_code")));//订单号
				dataM.put("user_bill_num", i+"");//账单期数
				String cycle = oldT.getMonthOfYear()+"月"+oldT.getDayOfMonth()+"日"+"-"+ makeALoanDate_.getMonthOfYear()+"月"+makeALoanDate_.getDayOfMonth()+"日";
				
				dataM.put("user_ht_id",String.valueOf(con.get("ht_id")));//合同号
				oldT = makeALoanDate_.plusDays(1);
				dataM.put("user_bill_cycle", cycle);//账单周期
				
				if(i==1){
					dataM.put("user_bill_method", "0");//0已缴费1待缴费
					dataM.put("user_bill_pay_num", String.valueOf(orderM.get("understand_sign_order_price")));//代缴费金额
				}
				dataM.put("user_bill_code", code);
				code = UUID.randomUUID().toString().replace("-", "").toUpperCase();
				mL.add(dataM);
			}
			paymentDao.createBillList(mL);
			
			
			//增加账户流水
			data.put("user_bill_flow_info", "签约付款");
			data.put("user_bill_flow_type", "0");
			data.put("user_bill_pay_num", String.valueOf(data.get("amount")));
			data.put("user_bill_pay_type",String.valueOf(data.get("payment_operation_way")));
			data.put("user_bill_id", String.valueOf(data.get("understand_sign_order_code")));
			paymentDao.createUserBillFlow(data);	

			
			//修改房间的状态
			paymentDao.updateRoomStatus(orderM);
		
			
			
*/
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "立即签约支付成功");
			map.put("result_code", "0000");
			map.put("pay_code", "0000");
			resultMap.put("data", map);
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "立即签约支付失败");
		}
		return resultMap;
	
	}
	
	/**
	 * 支付账单金额 S0057
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value="doPaymentOfBill",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> doPaymentOfBill(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, String> map = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------支付账单金额 S0057 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			Map<String, String> billInfo = paymentDao.searchUserBillInfo(data);
			if(billInfo==null){
				resultMap.put("result_code", "0002");
				resultMap.put("result_message", "该帐单不存在或已支付");
				return resultMap;
			}
			String code = String.valueOf(billInfo.get("user_bill_code"));
	
			
			//-----------------支付相关--------------------------
			int payType = Integer.valueOf(String.valueOf(data.get("user_bill_pay_type")));
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("body", "时租租房");
			paramMap.put("subject", "时租租房");
			paramMap.put("code", code);
			paramMap.put("amount", String.valueOf(data.get("user_bill_pay_num")));			
			
			String resultStr = payMentService.doPay(payType, paramMap);
			String payCode = 	"";		
			//创建支付订单
			Map<String, String> paymentM = new HashMap<>();
			paymentM.put("trade_no", payCode);
			paymentM.put("payment_operation_order_code", code);
			paymentM.put("user_id", String.valueOf(data.get("user_id")));
			paymentM.put("payment_operation_way", payType+"");
			paymentM.put("payment_operation_type", "0");
			paymentM.put("payment_operation_pay_way", "0");
			paymentM.put("payment_amount", String.valueOf(data.get("user_bill_pay_num")));
			paymentM.put("status", "2");
			paymentDao.createPaymentOperation(paymentM);			
			//预下单订单信息（返回给客户端）
			map.put("payment_content", resultStr);
			//-----------------支付相关--------------------------

/*			
			//支付成功后增加积分
			BigDecimal amount = new BigDecimal(String.valueOf(data.get("user_bill_pay_num")));
			BigDecimal core = amount.divide(new BigDecimal("100"), 0, BigDecimal.ROUND_UP);
			data.put("core", core.toString());
			orderDao.updateUserCore(data);
			
			paymentDao.doPaymentOfBill(data);	
			data.put("user_bill_flow_info", "支付账单");
			data.put("user_bill_flow_type", "0");
			paymentDao.createUserBillFlow(data);	*/
			

			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "支付账单金额成功");
			map.put("result_code", "0000");
			map.put("pay_code", "0000");
			resultMap.put("data", map);
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "支付账单金额失败");
		}
		return resultMap;
	
	}
	
	
	/**
	 * 房源详情中的付款方式 S0065
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value="getPaymentMethodList",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getPaymentMethodList(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------房源详情中的付款方式 S0065 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
	
			List<Map<String, String>> methodList = paymentDao.getPaymentMethodList();	
			Map<String, String> core = paymentDao.getUserCore(data);	
			Map<String, String> pirce = paymentDao.gethosePirce(data);	
			Integer userC = 0;
			BigDecimal houseP = new BigDecimal(String.valueOf(pirce.get("room_info_price")));						
			if(!StringUtil.isEmpty(String.valueOf(data.get("user_id"))))
			{
				userC = Integer.valueOf(String.valueOf(core.get("user_xinyong_score")));	
			}
			for(Map<String, String> m:methodList){
				BigDecimal payment_method_deposit = new BigDecimal(String.valueOf(m.get("payment_method_deposit")));//押金
				BigDecimal payment_method_service = new BigDecimal(String.valueOf(m.get("payment_method_service")));//服务费
				BigDecimal payment_deposit = new BigDecimal(0);
				BigDecimal payment_service = new BigDecimal(0);
				BigDecimal amountP = houseP.multiply(payment_method_deposit);
		        if (userC<65) {
		        	payment_deposit = amountP;
		        	payment_service = (amountP.multiply(new BigDecimal(12)).multiply(new BigDecimal("0.07"))).divide(new BigDecimal("1"),2,BigDecimal.ROUND_HALF_UP);
		        } else if(userC>=65 && userC<70) {
		        	payment_deposit = (amountP.multiply(new BigDecimal("0.8"))).divide(new BigDecimal("1"),2,BigDecimal.ROUND_HALF_UP);
		        	payment_service = (amountP.multiply(new BigDecimal(12)).multiply(new BigDecimal("0.07"))).divide(new BigDecimal("1"),2,BigDecimal.ROUND_HALF_UP);
		        } else if (userC>=70 && userC<80) {
		        	payment_deposit = (amountP.multiply(new BigDecimal("0.6"))).divide(new BigDecimal("1"),2,BigDecimal.ROUND_HALF_UP);
		        	payment_service = (amountP.multiply(new BigDecimal(12)).multiply(new BigDecimal("0.06"))).divide(new BigDecimal("1"),2,BigDecimal.ROUND_HALF_UP);

		        } else if (userC>=80 && userC<100) {
		        	payment_deposit = (amountP.multiply(new BigDecimal("0.4"))).divide(new BigDecimal("1"),2,BigDecimal.ROUND_HALF_UP);
		        	payment_service = (amountP.multiply(new BigDecimal(12)).multiply(new BigDecimal("0.05"))).divide(new BigDecimal("1"),2,BigDecimal.ROUND_HALF_UP);

		        } else if (userC>=100) {
		        	payment_deposit = new BigDecimal(0);
		        	payment_service = (amountP.multiply(new BigDecimal(12)).multiply(new BigDecimal("0.05"))).divide(new BigDecimal("1"),2,BigDecimal.ROUND_HALF_UP);

		        }

				
				
				m.put("payment_deposit", payment_deposit.toString());//押金
				m.put("payment_service", payment_service.toString());//服务费
				m.put("room_info_price", houseP.toString());// 月房租
			}			
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "房源详情中的付款方式成功");
			resultMap.put("data", methodList);
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "房源详情中的付款方式失败");
		}
		return resultMap;
	
	}
	/**
	 * 退押金(看房) S0053
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping(value="ReturnTheDepositMoney",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> ReturnTheDepositMoney(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, String> map = new HashMap<>();
		boolean status = false; 
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------退押金(看房) 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String, String>> ma = paymentDao.searchSmartViewOrder(data);
			if(ma!=null&&ma.size()>0){
				resultMap.put("result_code", "0001");
				resultMap.put("result_message", "当前用户有未完成看房订单，不能退押金");
				return resultMap;
			}
			List<Map<String, String>> mapOpration = paymentDao.searchPaymentOperationByuser(data);
			
			for(Map<String, String> m:mapOpration){
				String code = UUID.randomUUID().toString().replace("-", "").toUpperCase();
				int payType = Integer.valueOf(String.valueOf(m.get("payment_operation_way")));
				String amount = String.valueOf(m.get("payment_amount"));
				String order_code = String.valueOf(m.get("payment_operation_order_code"));
				Map<String, String> paramMap = new HashMap<>();
				paramMap.put("tuiCode", code);
				paramMap.put("code", order_code);
				paramMap.put("amount", amount);		
			//-----------------支付相关--------------------------
				status = payMentService.refund(payType, paramMap);
				
				String payCode = "";
				//创建支付订单
				Map<String, String> paymentM = new HashMap<>();
				paymentM.put("trade_no", payCode);
				paymentM.put("payment_operation_order_code", code);
				paymentM.put("user_id", String.valueOf(data.get("user_id")));
				paymentM.put("payment_operation_way", payType+"");
				paymentM.put("payment_operation_type", "1");
				paymentM.put("payment_operation_pay_way", "0");
				paymentM.put("payment_amount", amount);
				paymentM.put("status", (status?0:1)+"");
				paymentDao.createPaymentOperation(paymentM);			
			//-----------------支付相关--------------------------
			
			}
			if(status){
				data.put("status", "4");
				paymentDao.updateSmartViewOrder(data);			
				data.put("user_bill_flow_info", "退押金(看房)");
				data.put("user_bill_flow_type", "1");
				data.put("user_bill_pay_num", String.valueOf(data.get("amount")));
				data.put("user_bill_pay_type","0");
				data.put("user_bill_id", String.valueOf(data.get("payment_operation_order_code")));
				paymentDao.createUserBillFlow(data);	
				logger.debug("===========退款成功=============:{}",String.valueOf(data.get("payment_operation_order_code")));
			}else{
				logger.debug("===========退款失败=============:{}",String.valueOf(data.get("payment_operation_order_code")));
			}
			
			if(!status){
				resultMap.put("result_code", "0001");
				resultMap.put("result_message", "退押金(看房)失败");
			}else{	
				resultMap.put("result_code", "0000");
				resultMap.put("result_message", "退押金(看房)成功");
				}

			map.put("result_code", "0000");
			resultMap.put("data", map);
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "退押金(看房)失败");
		}
		return resultMap;
	
	}	
	
}
