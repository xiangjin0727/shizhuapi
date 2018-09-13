package com.hz.app.payUtil.alipay;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.hz.app.payUtil.SignUtil;

/**
 * 支付宝交易类
 * @author Niu Li
 * @date 2016/10/29
 */
public class AlipayTrade {

    private Logger logger = LoggerFactory.getLogger(AlipayTrade.class);

    /**
     * web支付下单并支付(web支付在安卓中是可以直接唤醒支付宝APP的)
     * url https://doc.open.alipay.com/doc2/detail.htm?treeId=203&articleId=105463&docType=1#s1
     * @return web支付的表单
     */
    public String TradeWapPayRequest(AlipayTradeAppPayModel model){
    	AlipayTradeAppPayRequest  alipayRequest = new AlipayTradeAppPayRequest ();
        alipayRequest.setReturnUrl(AliPayConfig.RETURN_URL);
        alipayRequest.setNotifyUrl(AliPayConfig.PAY_NOTIFY);
        alipayRequest.setBizModel(model);
		String form = "";
        try {
            form = AliPayConfig.getInstance().sdkExecute(alipayRequest).getBody();
            System.err.println(form);
        } catch (AlipayApiException e) {
            logger.error("支付宝构造表单失败",e);
        }
        logger.debug("支付宝支付表单构造:"+form);
        return form;
    }

    /**
     * 申请退款
     * @param sParaTemp 退款参数
     * @return true成功,回调中处理
     * 备注:https://doc.open.alipay.com/docs/api.htm?spm=a219a.7629065.0.0.3RjsEZ&apiId=759&docType=4
     */
    public AlipayTradeRefundResponse tradeRefundRequest(Map<String, ?> sParaTemp) throws AlipayApiException {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setReturnUrl(AliPayConfig.RETURN_URL);
        request.setNotifyUrl(AliPayConfig.REFUND_NOTIFY);
        // 待请求参数数组
        request.setBizContent(JSON.toJSONString(sParaTemp));
        AlipayTradeRefundResponse response = AliPayConfig.getInstance().execute(request);
        System.err.println(response.getBody());
        return response;
    }

    /**
     * 支付宝回调验签
     * @param request 回调请求
     * @return true成功
     * 备注:验签成功后，按照支付结果异步通知中的描述(二次验签接口,貌似称为历史接口了)
     */
    public boolean verifyNotify(HttpServletRequest request) throws AlipayApiException {
        Map<String,String> paranMap = SignUtil.request2Map(request);
        logger.debug("支付宝回调参数:"+paranMap.toString());
        boolean isVerify = false;
        if (AliPayConfig.SUCCESS_REQUEST.equals(paranMap.get("trade_status")) || AliPayConfig.TRADE_CLOSED.equals(paranMap.get("trade_status"))) {
            isVerify = AlipaySignature.rsaCheckV1(paranMap, AliPayConfig.ALIPAY_PUBLIC_KEY, AliPayConfig.CHARSET); //调用SDK验证签名
        }
        logger.debug("支付宝验签结果"+isVerify);
        return isVerify;
    }
    
    /**
     * 查询订单支付状态
     * @param sParaTemp
     * @return
     * @throws AlipayApiException
     */
    public Map<String, String> searchBill(Map<String, String> sParaTemp) throws AlipayApiException{
     	AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
    	request.setBizContent(JSON.toJSONString(sParaTemp));
    	AlipayTradeQueryResponse response = AliPayConfig.getInstance().execute(request);
    	System.err.println(response.isSuccess());
    	System.err.println(response.getBody());
    	if(response.isSuccess()){
    	System.out.println("调用成功");
    	} else {
    	System.out.println("调用失败");
    	}
    	return  null;
    }
    
}
