package com.hz.app.order.controller.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.hz.app.payUtil.alipay.AliPayConfig;
import com.hz.app.payUtil.alipay.AlipayTrade;

@Service
public class ZhifuService {
	private Logger logger = LoggerFactory.getLogger(ZhifuService.class);

	/**
	 * 支付宝预下单
	 * @param request_
	 * @param reponse
	 * @return
	 */
	public String getBill(Map<String, String> paramMap){
		Map<String, String> map = new HashMap<>();
        AlipayTrade alipayTrade = new AlipayTrade();
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody(paramMap.get("body"));
		model.setSubject(paramMap.get("subject"));
		model.setOutTradeNo(paramMap.get("code"));//更换为自己的订单编号
		model.setTimeoutExpress("30m");
		model.setTotalAmount(paramMap.get("amount"));
		model.setProductCode("QUICK_MSECURITY_PAY");
		model.setSellerId(AliPayConfig.SELLER_ID);
        String orderStr = alipayTrade.TradeWapPayRequest(model);
		return orderStr;
	}
	/**
	 * 退款操作
	 * @param request
	 * @param reponse
	 * @return
	 * @throws AlipayApiException 
	 */
	@RequestMapping("doRefund")
	public boolean doRefund(Map<String, String> paramMap) throws AlipayApiException{
        AlipayTrade alipayTrade = new AlipayTrade();
        Map<String,String> paraMap = new HashMap<>();
       // paraMap.put("trade_no","123321");
        paraMap.put("refund_amount",paramMap.get("amount")); //退款金额
        paraMap.put("refund_reason","正常退款");// 
        paraMap.put("out_trade_no",paramMap.get("code")); //交易单号
       // paraMap.put("operator_id","OP001");
        //paraMap.put("store_id","NJ_S_001");
       // paraMap.put("terminal_id","NJ_T_001");
        AlipayTradeRefundResponse response = alipayTrade.tradeRefundRequest(paraMap);
        System.err.println(response.getBody());
        logger.debug("支付宝退货结果:"+response.isSuccess());
		return response.isSuccess();
	}
	
	/**
	 * 用户交易订单查询
	 * @return
	 * @throws AlipayApiException
	 */
	public Map<String, String> orderSearch(Map<String, String> paramMap) throws AlipayApiException{
   	 AlipayTrade alipayTrade = new AlipayTrade();
   	 Map<String,String> paraMap = new HashMap<>();
   	 paraMap.put("out_trade_no", "");
   	 paraMap.put("trade_no", "");
   	 alipayTrade.searchBill(paraMap);
   	 return null;
	}

}
