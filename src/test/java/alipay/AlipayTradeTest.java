package alipay;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.hz.app.payUtil.alipay.AliPayConfig;
import com.hz.app.payUtil.alipay.AlipayTrade;

/**
 * @author Niu Li
 * @date 2016/10/29
 */
public class AlipayTradeTest {

   @Test
    public void testTradeWapPayRequest(){
        AlipayTrade alipayTrade = new AlipayTrade();
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("我是测试数据");
		model.setSubject("App支付测试Java");
		model.setOutTradeNo("test001");//更换为自己的订单编号
		model.setTimeoutExpress("30m");
		model.setTotalAmount("0.01");
		model.setProductCode("QUICK_MSECURITY_PAY");
		model.setSellerId(AliPayConfig.SELLER_ID);
        alipayTrade.TradeWapPayRequest(model);
    }
    //@Test
    public void testRefund() throws AlipayApiException {
        AlipayTrade alipayTrade = new AlipayTrade();
        Map<String,String> paraMap = new HashMap<>();
        paraMap.put("trade_no","123321");
        paraMap.put("refund_amount","0.01");
        paraMap.put("refund_reason","测试退款");
        paraMap.put("out_request_no","test001");
        paraMap.put("operator_id","OP001");
        paraMap.put("store_id","NJ_S_001");
        paraMap.put("terminal_id","NJ_T_001");
        alipayTrade.tradeRefundRequest(paraMap);
    }
    //@Test
    public void testSearchBill() throws AlipayApiException{
    	 AlipayTrade alipayTrade = new AlipayTrade();
    	 Map<String,String> paraMap = new HashMap<>();
    	 paraMap.put("out_trade_no", "test001");
    	 alipayTrade.searchBill(paraMap);
    }
}
