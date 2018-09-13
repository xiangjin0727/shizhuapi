package com.hz.app.payUtil.alipay;


import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

/**
 * 配置公共参数
 * @author Niu Li
 * @date 2016/10/29
 */
public final class AliPayConfig {
    /**
     * 应用号
     */
    public static String APP_ID = "2018080360956028";
    /**
     * 商户的私钥
     */
    public static String APP_PRIVATE_KEY = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQDDcH5SLT3zIrBkUHdfRxu85cOQV6YC4xMJreHlmxQNqoG2LXG/+zdCezZCiNC7VXLbQssQSU5NEYEWof378Ntztf7rU6OgqXDbOiUxhXhVYDRecVvIBK34uh4TEOJ8rr2iRuMVPfpxKHW5B51n5xbxrPa3sDzXrhzzPdKcOUTwmVtZ/LdCmtQDHhCITZyWohuDOsGAtZ9woDLjtwLv6FQJkA0Vckz6yK//HruYzdzjSqKPSY1mM/iJiDBA5fjOTUrOoAf+5/vDHcOgLfQeoLvSPCyiYaIc3nQXmOrKOBO8XW8t99wukQb2du3/svalv47HjNTkn/7y424/ulE2bErVAgMBAAECggEBAL+MOcKA5P6vOQKYeua+4si4yHtn7CVYOQh7i7Nl0rmyK39J+vqWVQqrh/qd7TYuAWw0gqFrt3qBdWuhSTTsU6mM7lPmnbCRYTL8QgfPMKk8qRcHM1VOgNQOVfuJyPE6slGh+F6RaCwP45F9jSCQtUQYqZZO/NLGq0kbhpfm6VKEwvigiVZcDFXS62mAxQAngHD0vMfV+bG4OYT2PhXnhHXiWiWMWpeMvpfzqvwT6sst3joVdyd8ng2Daybfa/Q6huudONP4Gh9i6wBjj64CNfVM7dPOLtfAqxxdWyL5nBj2yaydYyu3xTCMje4+j3+TOnLPLJCn6zFT2BkW5x26LOECgYEA93nqd1a13l1DmqhEb8j2eDFU+tdw9ChI6j3NeOg8wWdexUO8wg4yIX8k996srM0MsS+RI8bvaxqZ1KdXLYX2/rs/6hTLYt3boVNFKmoJ2lBqm1ZvMECuMBceQ6hvZ1mKq0/Pp7+jGFE5AhTkw9NO1rLIOoIsgS0nyQU69f3o7PcCgYEAyivAPDIExPwaiw+G5re/d7tqEtCaRcuyddbraIC+sareqvro6Gz1GfGSIOT0bTaBoL0IBeZa4aGvalJBBEcBBpM3KNiafArMT8EiPkzs8/had3/OmJuf4a6hRFIiNWIUFfTk9iBzPMLS3Bdk1FseMbI07zKjdkZLeGlGohTUT5MCgYEA6wuTIpdrkvAXtntczKMdXJjWMoyOzoa4gioCznQcyEBKpacpT1I2UNQ2o2mNrW3vQRaBjm5ylNImCvCI/0a+icAE+2mWpqc646V4Csm94+j+59yXLEgyaKB8cRw3+vvzlbPu8U81M3JpZ826XTeEKKyTOebLKEFgCS/7iQoIwY8CgYEApd/tsrXrWd/qu0v7CBOc4dUAO/2xhjCR9MNolG6Fh2pFlY2WSRUUqLuehKS1s2G0S7is+4I78MJjFV31gFftjIJ/EGFfpwSJC6rPuMQO6kFLoJ5ZWLbfICDSqialcJ1PE5DBxvtq8WB79x6umNlF1cp8SgGHQoR8aYge7t8eL0cCgYEA1ivPvQSOVx6sL3OkIHjyhw7rp7dX9+0pxMaFJRv/DEPRgaqHizVhvdvATjsyBc2tML5QpCgmIGmIINgqsyApIeSs/LJkJ4Bzee/+w1J2yj8Bo6gFQ3Zp5Uczgw5AEviPOQtVtYpLxu9BF/MQeagqhN13n7YnlFOtMlTSdnux35U=";  
    /**
     * 
     * 编码
     */
    public static String CHARSET = "UTF-8";
    /**
     * 支付宝公钥
     */
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAghOyvkLGCayBdjWO3hLJRZhJNwMvEePdhH5rM4GLTDwADAWFRlJRlpqb/cTlYLd+JhCbz/XMiCqUmF35fwOeOCygcjfoT+mvdAVC2fH8Q3lBw2baRSsoZoeOWZxg74aphXXHtPtJAJJKKGlK108aHczFFjqI/te1Lm4EgXLjUTg57wQRPKGwMo8sCknqD9GxClPQcAl6fFFoMqOwQHRSfs00C4LZXy55sH6MfczE0sG/pdQtqAXmQQyblumDCXX2Z9X7wMY/ZhX4mWsiYQzcCF3VRn0MxupZCON7v++2CqHBuId+VctEOgD/6FsivnXmaVfKMyDFiEqarTfRZhm3CQIDAQAB/cTlYLd+JhCbz/XMiCqUmF35fwOeOCygcjfoT+mvdAVC2fH8Q3lBw2baRSsoZoeOWZxg74aphXXHtPtJAJJKKGlK108aHczFFjqI/te1Lm4EgXLjUTg57wQRPKGwMo8sCknqD9GxClPQcAl6fFFoMqOwQHRSfs00C4LZXy55sH6MfczE0sG/pdQtqAXmQQyblumDCXX2Z9X7wMY/ZhX4mWsiYQzcCF3VRn0MxupZCON7v++2CqHBuId+VctEOgD/6FsivnXmaVfKMyDFiEqarTfRZhm3CQIDAQAB";
    /**
     * 支付宝网关地址
     */
    private static String GATEWAY = "https://openapi.alipay.com/gateway.do";
    /**
     * 成功付款回调
     */
    public static String PAY_NOTIFY = "http://47.104.229.248:8080/szhz/callBack/callback_zhifubao";
    /**
     * 支付成功回调
     */
    public static String REFUND_NOTIFY = "http://niuli.tunnel.qydev.com/notify/alipay_refund";
    /**
     * 前台通知地址
     */
    public static String RETURN_URL = "http://niuli.tunnel.qydev.com/notify/alipay_pay";
    /**
     * 参数类型
     */
    public static String PARAM_TYPE = "json";
    /**
     * 成功标识
     */
    public static final String SUCCESS_REQUEST = "TRADE_SUCCESS";
    /**
     * 交易关闭回调(当该笔订单全部退款完毕,则交易关闭)
     */
    public static final String TRADE_CLOSED = "TRADE_CLOSED";
    /**
     * 收款方账号
     */
    public static final String SELLER_ID = "bjzntq2018@163.com";   
    /**
     *  加密类型
     */
    public static String SIGNTYPE = "RSA2";
    /**
     * 支付宝请求客户端入口
     */
    private volatile static AlipayClient alipayClient = null;

    /**
     * 不可实例化
     */
    private AliPayConfig(){};

    /**
     * 双重锁单例
     * @return 支付宝请求客户端实例
     */
    public static AlipayClient getInstance(){
        if (alipayClient == null){
            synchronized (AliPayConfig.class){
                if (alipayClient == null){
                    alipayClient = new DefaultAlipayClient(GATEWAY,APP_ID,APP_PRIVATE_KEY,PARAM_TYPE,CHARSET,ALIPAY_PUBLIC_KEY,SIGNTYPE);
                }
            }
        }
        return alipayClient;
    }

}
