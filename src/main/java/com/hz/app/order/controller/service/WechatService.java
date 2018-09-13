package com.hz.app.order.controller.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.hz.app.api.util.JsonUtil;
import com.hz.app.payUtil.alipay.AlipayTrade;
import com.hz.app.payUtil.wechat.WechatClient;
import com.hz.app.payUtil.wechat.WechatConfig;
import com.hz.app.payUtil.wechat.entity.WechatRefund;
import com.hz.app.payUtil.wechat.entity.WechatUnifiedOrder;

@Service
public class WechatService {
	private Logger logger = LoggerFactory.getLogger(WechatService.class);
	/**
	 * 获取微信预订单
	 * @param request_
	 * @param reponse
	 * @return
	 */
	public String getBill(Map<String, String> paramMap){
        WechatUnifiedOrder request = new WechatUnifiedOrder();
        request.setBody(paramMap.get("body"));
        request.setDetail(paramMap.get("body"));
        request.setGoods_tag(paramMap.get("subject"));
        request.setOut_trade_no(paramMap.get("code"));
        request.setFee_type("CNY");
        request.setTotal_fee(Integer.valueOf((new BigDecimal(String.valueOf(paramMap.get("amount"))).multiply(new BigDecimal("100")).divide(new BigDecimal("1"),0,BigDecimal.ROUND_DOWN)).toString()));//单位分
        request.setSpbill_create_ip("127.0.0.1");
        request.setTime_start(System.currentTimeMillis()+"");
        WechatClient client = WechatConfig.getInstance();
        WechatUnifiedOrder.Response response = client.unifiedOrder(request);
        System.err.println(JSON.toJSONString(response));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appid", response.getAppid());
        map.put("partnerid", response.getMch_id());
        map.put("prepayid", response.getPrepay_id());
        map.put("package", "Sign=WXPay");
        map.put("noncestr", response.getNonce_str());
        map.put("timestamp", (int)(System.currentTimeMillis()/1000));
        TreeMap<String, Object> treem = new TreeMap<>(map);
        String sign =client.sign(treem).toUpperCase();      
        map.put("sign", sign);
        System.err.println(JSON.toJSONString(map));
		return JSON.toJSONString(map);
	}
    /**
	 * 退款操作
	 * @param request
	 * @param reponse
	 * @return
	 * @throws AlipayApiException 
	 */
	public boolean doRefund(Map<String, String> paramMap) {
        WechatRefund refund = new WechatRefund();
        refund.setOut_trade_no(paramMap.get("code"));//交易订单号
        //refund.setTransaction_id("4006602001201610318291951971");
        refund.setOut_refund_no(paramMap.get("tuiCode"));
        refund.setTotal_fee(Integer.valueOf((new BigDecimal(String.valueOf(paramMap.get("amount"))).multiply(new BigDecimal("100")).divide(new BigDecimal("1"),0,BigDecimal.ROUND_DOWN)).toString()));
        refund.setRefund_fee(Integer.valueOf((new BigDecimal(String.valueOf(paramMap.get("amount"))).multiply(new BigDecimal("100")).divide(new BigDecimal("1"),0,BigDecimal.ROUND_DOWN)).toString()));
        //refund.setOp_user_id("NIULI");
       // refund.setRefund_account("REFUND_SOURCE_UNSETTLED_FUNDS");
        WechatRefund.Response response = WechatConfig.getInstance().refund(refund);
        //System.out.println(JSON.toJSONString(response));
        Map<String, Object> map = JsonUtil.convJsonToMapO(JSON.toJSONString(response));
        if((WechatConfig.SUCCESS_REQUEST).equals(map.get("result_code"))){
        	return true;
        }
        return false;
	}
	
	/**
	 * 用户交易订单查询
	 * @return
	 * @throws AlipayApiException
	 */
	public Map<String, String> orderSearch() throws AlipayApiException{
   	 AlipayTrade alipayTrade = new AlipayTrade();
   	 Map<String,String> paraMap = new HashMap<>();
   	 paraMap.put("out_trade_no", "");
   	 paraMap.put("trade_no", "");
   	 alipayTrade.searchBill(paraMap);
   	 return null;
	}

}
