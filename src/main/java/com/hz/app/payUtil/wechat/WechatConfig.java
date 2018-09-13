package com.hz.app.payUtil.wechat;

/**
 * 微信支付配置
 * @author Niu Li
 * @date 2016/10/29
 */
public final class WechatConfig {
    /**
     * 用户的id
     */
    public static final String APP_ID = "wxd71c7825d6c2ecdd";
    /**
     * 身份密钥
     */
    public static final String APP_SECRET = "zhinuotianqi2017zhinuotianqi2017";
    /**
     * 商户id
     */
    public static final String MCH_ID = "1502726901";
    /**
     * 统一下单地址
     */
    public static final String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 交易退款地址
     */
    public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    /**
     * 交易退款查询地址
     */
    public static final String REFUND_QUERY = "https://api.mch.weixin.qq.com/pay/refundquery";
    /**
     * 支付成功回调
     */
    public static final String NOTIFY_PAY = "http://47.104.229.248:8080/szhz/callBack/callback_wechat";
    /**
     * 支付类型
     */
    public static final String TRADE_TYPE = "APP";
    /**
     * 证书地址
     */
    public static final String CERT_PATH = "C:\\Users\\hzcf\\Desktop\\微信\\apiclient_cert.p12";
    /**
     * 成功标识
     */
    public static final String SUCCESS_REQUEST = "SUCCUSS";

    /**
     * 不可实例化
     */
    private WechatConfig(){}

    private volatile static WechatClient wechatClient = null;

    /**
     * 双重锁单例
     * @return WechatClient实例
     */
    public static WechatClient getInstance(){
        if (wechatClient == null){
            synchronized (WechatConfig.class){
                if (wechatClient == null){
                    return new WechatClient(APP_ID,MCH_ID,APP_SECRET,TRADE_TYPE);
                }
            }
        }
        return wechatClient;
    }

}
