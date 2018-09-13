package com.hz.app.order.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hz.app.api.util.JsonUtil;
import com.hz.app.house.dao.HouseDao;
import com.hz.app.order.controller.service.PayMentService;
import com.hz.app.order.dao.OrderDao;
import com.hz.app.order.dao.PaymentDao;
import com.hz.app.payUtil.SignUtil;
import com.hz.app.payUtil.XmlUtil;
import com.hz.app.payUtil.wechat.WechatConfig;
import com.hz.app.payUtil.wechat.entity.WechatPayNotify;
import com.hz.app.user.dao.UserDao;
import com.ibm.icu.math.BigDecimal;

@Controller
@RequestMapping("callBack")
public class CallBackController {
	private Logger logger = LoggerFactory.getLogger(CallBackController.class);
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
	 * 微信异步回调
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("callback_wechat")
	@ResponseBody
    public String verifyNotify(HttpServletRequest request) throws ParseException{
		
        try {
            InputStream inputStream = request.getInputStream();
            WechatPayNotify notice = XmlUtil.xmlToBean(inputStream, WechatPayNotify.class);
            if (notice == null) return "";
            logger.debug("微信回调参数:"+ JSON.toJSONString(notice));
            Map<String, Object> map = JsonUtil.convJsonToMapO(JSON.toJSONString(notice));
            
            if("SUCCESS".equals(map.get("return_code"))){
            	if("SUCCESS".equals(map.get("result_code"))){
            		map.put("payment_operation_order_code", map.get("out_trade_no"));
            		map.put("payment_operation_way", "1");           		
            		List<Map<String, String>> dmapL = paymentDao.searchPaymentOperationByuser_(map);
            		Map<String, String> dmap = dmapL.get(0);
            		logger.debug("订单数据：{}",dmap);
            		if("0".equals(String.valueOf(dmap.get("payment_operation_pay_way")).trim())){//看房订单
            			logger.debug("===========看房订单回调==========");
	            		Map<String, String> dmapOrder = paymentDao.searchSmart_view_order(map);
	            		dmapOrder.put("user_id", String.valueOf(dmapOrder.get("smart_view_order_user_id")));
	            		dmapOrder.put("amount", String.valueOf(dmapOrder.get("smart_view_order_price")));      		
	            		dmapOrder.put("payment_operation_way", "1");      		
	            		dokanfang(dmapOrder);   
            		}else if("2".equals(String.valueOf(dmap.get("payment_operation_pay_way")).trim())){// 签约订单
            			logger.debug("===========签约订单回调==========");
            			Map<String, String> dmapOrder = paymentDao.searchBill(map);

            			if(dmapOrder==null){ //订单第一次支付 没有账单
            				logger.debug("===========订单第一次支付 没有账单==========");
            				dmapOrder = new HashMap<>();
            				dmapOrder.put("user_id", String.valueOf(dmap.get("payment_operation_user_id")));
                			dmapOrder.put("payment_operation_way", "1");
                			dmapOrder.put("understand_sign_order_code", String.valueOf(map.get("out_trade_no")));
                			dmapOrder.put("amount", String.valueOf(dmap.get("payment_amount")));     
            				doqianyue(dmapOrder);
            			}else{ // 有账单的情况
            				logger.debug("===========有账单的情况==========");
            				//支付成功后增加积分
            				BigDecimal amount = new BigDecimal(String.valueOf(dmapOrder.get("user_bill_pay_num")));
            				BigDecimal core = amount.divide(new BigDecimal("100"), 0, BigDecimal.ROUND_UP);
            				dmapOrder.put("core", core.toString());
            				orderDao.updateUserCore(dmapOrder);
            				dmapOrder.put("user_bill_pay_type", "1"); //微信支付       				
            				paymentDao.doPaymentOfBill(dmapOrder);	
            				dmapOrder.put("user_bill_flow_info", "支付账单");
            				dmapOrder.put("user_bill_flow_type", "0");
            				paymentDao.createUserBillFlow(dmapOrder);		
            			}         
            		}
            		//将多余的支付单变为已作废
            		for(Map<String, String> m:dmapL){
            			m.put("status", "3");
            			m.put("payment_operation_info", "已作废");
            			paymentDao.updatePaymentOperation(m);
            		}
            		//修改支付单
            		dmap.put("status", "0");
            		dmap.put("payment_operation_info", JSON.toJSONString(notice));
            		paymentDao.updatePaymentOperation(dmap);
            		
            		
            	}
            }            
         
            String sign = WechatConfig.getInstance().sign(SignUtil.bean2TreeMap(notice));
            boolean ischeck = sign.equals(notice.getSign());
            logger.debug("微信验签结果:"+ischeck);
            return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "<xml><return_code><![CDATA[FILE]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
 

	}
	
	/**
	 * 支付宝异步回调
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("callback_zhifubao")
	@ResponseBody
	public String doCallback(HttpServletRequest request, HttpServletResponse reponse){
		   //获取支付宝POST过来反馈信息
		String status = "";
	    Map<String,String> params = new HashMap<String,String>();
	    System.out.println("异步通知参数：");
	    Map<String,String[]> requestParams  = request.getParameterMap();
	    for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
	        String name = (String) iter.next();
	        String[] values = (String[]) requestParams.get(name);
	        String valueStr = "";
	        for (int i = 0; i < values.length; i++) {
	            valueStr = (i == values.length - 1) ? valueStr + values[i]
	                    : valueStr + values[i] + ",";
	        }
	        if(name.equals("trade_status")){
	        	status= valueStr;
	        	System.out.println("交易状态为："+valueStr);

	        }
	        //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
	        //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
	        params.put(name, valueStr);
	    }
	    System.out.println("params"+params);
	    String pk = "自己的支付宝公钥";
	  
	    //获取支付宝的通知返回参数
		try {
/*	        boolean verify_result;

			verify_result = AlipaySignature.rsaCheckV1(params,pk, "UTF-8", "RSA2");*/
			//这里可以做处理修改订单状态
			Map<String, Object> map = new HashMap<>();
			map.put("out_trade_no", String.valueOf(params.get("out_trade_no")));
            	if("TRADE_SUCCESS".equals(status)){
            		map.put("payment_operation_order_code", map.get("out_trade_no"));
            		map.put("payment_operation_way", "2");           		
               		List<Map<String, String>> dmapL = paymentDao.searchPaymentOperationByuser_(map);
            		Map<String, String> dmap = dmapL.get(0);
            		logger.debug("订单数据：{}",dmap);
            		if("0".equals(String.valueOf(dmap.get("payment_operation_pay_way")).trim())){//看房订单
            			logger.debug("===========看房订单回调==========");
	            		Map<String, String> dmapOrder = paymentDao.searchSmart_view_order(map);
	            		dmapOrder.put("user_id", String.valueOf(dmapOrder.get("smart_view_order_user_id")));
	            		dmapOrder.put("amount", String.valueOf(dmapOrder.get("smart_view_order_price")));      		
	            		dmapOrder.put("payment_operation_way", "2");      		
	            		dokanfang(dmapOrder);   
            		}else if("2".equals(String.valueOf(dmap.get("payment_operation_pay_way")).trim())){// 签约订单
            			logger.debug("===========签约订单回调==========");
            			Map<String, String> dmapOrder = paymentDao.searchBill(map);

            			if(dmapOrder==null){ //订单第一次支付 没有账单
            				logger.debug("===========订单第一次支付 没有账单==========");
            				dmapOrder = new HashMap<>();
            				dmapOrder.put("user_id", String.valueOf(dmap.get("payment_operation_user_id")));
                			dmapOrder.put("payment_operation_way", "2");
                			dmapOrder.put("understand_sign_order_code", String.valueOf(map.get("out_trade_no")));
                			dmapOrder.put("amount", String.valueOf(dmap.get("payment_amount")));     
            				doqianyue(dmapOrder);
            			}else{ // 有账单的情况
            				logger.debug("===========有账单的情况==========");
            				//支付成功后增加积分
            				BigDecimal amount = new BigDecimal(String.valueOf(dmapOrder.get("user_bill_pay_num")));
            				BigDecimal core = amount.divide(new BigDecimal("100"), 0, BigDecimal.ROUND_UP);
            				dmapOrder.put("core", core.toString());
            				orderDao.updateUserCore(dmapOrder);
            				dmapOrder.put("user_bill_pay_type", "2"); // 支付宝支付    				
            				paymentDao.doPaymentOfBill(dmapOrder);	
            				dmapOrder.put("user_bill_flow_info", "支付账单");
            				dmapOrder.put("user_bill_flow_type", "0");
            				paymentDao.createUserBillFlow(dmapOrder);		
            			}         
            		}
            		//将多余的支付单变为已作废
            		for(Map<String, String> m:dmapL){
            			m.put("status", "3");
            			m.put("payment_operation_info", "已作废");
            			paymentDao.updatePaymentOperation(m);
            		}
            		//修改支付单
            		dmap.put("status", "0");
            		dmap.put("payment_operation_info", JSON.toJSONString(params));
            		paymentDao.updatePaymentOperation(dmap);
            	}   
/*			
			if(verify_result){//验证成功
				//支付成功只需要返回success
				System.out.println("success");
				
			}else{//验证失败
				System.out.println("fail");
			}*/
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	  return "fail";
	}
	
	
	
	
	public String dokanfang(Map<String, String> data){
		
		Map<String, String> map = new HashMap<>();
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
		
		paymentDao.updateUserYaJin(data);	
		
		data.put("status", "0");
		paymentDao.updateSmartViewOrder(data);
		return null;
	}
	
	
	public String doqianyue(Map<String, String> data) throws ParseException{
		String code = String.valueOf(data.get("understand_sign_order_code"));
		
		int t = paymentDao.contractImmediatelyPay(data);
		
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

		return null;
	}

 }
