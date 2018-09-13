package com.hz.core.framework.cons;

/**
 *整个应用通用的常量 
 *<br><b>类描述:</b>
 *<pre>|</pre>
 *@see
 *@since
 */
public class CommonConstant
{
   /**
    * 用户对象放到Session中的键名称
    */
   public static final String USER_CONTEXT = "USER_CONTEXT";
   
   /**
    * 将登录前的URL放到Session中的键名称
    */
   public static final String LOGIN_TO_URL = "toUrl";
   
   /**
    * 每页的记录数
    */
   public static final int PAGE_SIZE = 3;
   
   public static final String PRE_SMS = "hzcf_sms_";
   
   //注册验证码
   public static final String REG_PRE_SMS = "reg_hzcf_sms_";
   
   //重置登录密码验证码
   public static final String RESET_PWD_SMS = "reset_hzcf_sms_";
   
   //修改交易密码验证码
   public static final String TRADE_PWD_SMS = "trade_hzcf_sms_";
   
   //绑定邮箱验证码
   public static final String BIND_EMAIL_SMS = "bindEmail_hzcf_sms_";
   
   //借款人申请验证码
   public static final String BORROWER_APPLY_SMS = "borrower_hzcf_sms_";
   
   public static final String PRE_LOGIN = "hzcf_pclogin_";
}
