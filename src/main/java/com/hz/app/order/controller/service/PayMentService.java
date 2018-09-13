package com.hz.app.order.controller.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;

@Service
public class PayMentService {
	private Logger logger = LoggerFactory.getLogger(PayMentService.class);
	@Autowired
	WechatService wechatService;
	@Autowired
	ZhifuService zhifuService;
	
	
	public String doPay(int payType,Map<String,String> paramMap){
		logger.info("==========================调用支付预下单==========================");
		String result = "";
		if(payType==1){//微信
			result = wechatService.getBill(paramMap);
		}else if(payType==2){//支付宝
			result = zhifuService.getBill(paramMap);
		}		
		return result;
	}
	public boolean refund(int payType,Map<String,String> paramMap) throws AlipayApiException{
		logger.info("==========================调用支付退款==========================");
		boolean result = false;
		if(payType==1){//微信
			result = wechatService.doRefund(paramMap);
		}else if(payType==2){//支付宝
			result = zhifuService.doRefund(paramMap);
		}	
		
		return result;
	}

}
