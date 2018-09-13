package com.hz.core.framework.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hz.app.index.vo.User;
import com.hz.constant.CodeEnum;
import com.hz.core.util.ClassLoaderUtil;
import com.hz.util.IsObjectNullUtils;
import com.hz.util.common.DESecbpkcs7;
import com.hz.util.common.SendDataToUrl;
import com.hz.util.common.ServletReqUtils;
import com.hz.util.common.StatusValue;

/**
 * 基类Controller
 * 
 * @author luwei
 *
 */
public abstract class BaseController {
	/**
	 * 将查询结果的json数据响应到客户端
	 * 
	 */
	private static String InvitationCode_Key = "07e02fc9035e4b06b624f30f";
	private static String ContentType = "text/json;charset=utf-8";
	private static String CharacterEncoding = "UTF-8";
	private static Logger logger = LoggerFactory.getLogger(BaseController.class);

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getParameters(HttpServletRequest request) {
		String reqMsg = ServletReqUtils.getReaMsg(request);
		net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(reqMsg);
		Map<String, Object> reqMapObject = (Map<String, Object>) net.sf.json.JSONObject.toBean(jsonObject, Map.class);
		return reqMapObject;
	}

	// /**
	// * 输出到客户端
	// * @Create Date: 2015年8月4日下午6:40:13
	// * @Version: V1.00 （版本号）
	// * @Parameters: BaseController
	// * @Return: void
	// */
	// public void resptoandroid(String jsonResult){
	// this.response.setContentType("text/json;charset=utf-8");
	// this.response.setCharacterEncoding("UTF-8");
	// byte[] jsonBytes;
	// try {
	// jsonBytes = jsonResult.toString().getBytes("utf-8");
	// response.setContentLength(jsonBytes.length);
	// response.getOutputStream().write(jsonBytes);
	// response.getOutputStream().flush();
	// response.getOutputStream().close();
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// }catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }

	/**
	 * 构造返回数据
	 * 
	 * @param res
	 * @param msg
	 * @param jsonObject
	 */
	public void write(HttpServletResponse response, String msg, Object jsonObject) {
		PrintWriter outObject = null;
		try {
			response.setCharacterEncoding(ContentType);
			response.setCharacterEncoding(CharacterEncoding);
			outObject = response.getWriter();
			if (msg == null || "".equals(msg)) {
				msg = "Fail";
			}
			HashMap<String, Object> objectHashMap = new HashMap<String, Object>();
			if (jsonObject instanceof List) {// 集合
				objectHashMap.put("list", jsonObject);
				outObject.print(getResult(msg, objectHashMap));
			} else if (jsonObject instanceof String) {// 字符串
				objectHashMap.put("name", jsonObject);
				outObject.print(getResult(msg, objectHashMap));
			} else {// HashMap
				outObject.print(getResult(msg, jsonObject));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			msg = "Fail";
			outObject.print(getResult(msg, jsonObject));
		}

	}

	public void write(HttpServletResponse response, String msg) {
		PrintWriter outObject = null;
		try {
			response.setCharacterEncoding(ContentType);
			response.setCharacterEncoding(CharacterEncoding);
			outObject = response.getWriter();
			outObject.print(getResult(msg));
		} catch (Exception e) {
			e.printStackTrace();
			outObject.print(msg);
		}
	}

	/**
	 * 对结果进行封装
	 * 
	 * @param alias
	 * @param resObject
	 * @return
	 */
	private String getResult(String alias, Object resObject) {
		Map<String, Object> mapResult = new HashMap<String, Object>();
		// JsonConfig config = new JsonConfig();
		// config.registerJsonValueProcessor(TimeStamp.class,new
		// DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		mapResult.put("code", CodeEnum.valueOf(alias).getIndex());
		mapResult.put("message", CodeEnum.valueOf(alias).getName());
		mapResult.put("data", resObject);
		System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(mapResult));
		return com.alibaba.fastjson.JSONObject.toJSONString(mapResult);
	}

	private String getResult(String alias) {
		Map<String, Object> mapResult = new HashMap<String, Object>();
		mapResult.put("code", "0000");
		mapResult.put("message", alias);
		logger.info(com.alibaba.fastjson.JSONObject.toJSONString(mapResult));
		return com.alibaba.fastjson.JSONObject.toJSONString(mapResult);
	}

	/**
	 * SSO单点数据同步接口调用--lwc
	 */
	public Map<String, Object> ssoData(JSONObject ssoparams, Map<String, Object> resHashMap) {
		Properties property = ClassLoaderUtil.getProperties("app_system.properties");
		System.out.println(ssoparams.toJSONString());
		final String ssoURL = property.getProperty("HzcfConstant.ssoUrl");
		System.out.println("访问的URL地址=====>" + ssoURL);
		String responseResult = SendDataToUrl.getDataToUrl(ssoURL, ssoparams.toJSONString(), "POST");
		System.out.println(responseResult);
		if (null != responseResult && responseResult != "") {
			JSONObject json = JSONArray.parseObject(responseResult);
			Map map = JSONArray.toJavaObject(json, Map.class);
			if (!"0000".equals(map.get("Code"))) {
				Map object = (Map) map.get("Body");
				// 是否绑定银行卡
				resHashMap.put("HasBindBankCard", String.valueOf(object.get("HasBindBankCard")));
				// 是否开通独立账户
				resHashMap.put("HasOpenTheSeparateAccount", String.valueOf(object.get("HasOpenTheSeparateAccount")));
				// E账户
				resHashMap.put("ACNO", object.get("ACNO"));
				// 身份证
				resHashMap.put("IdCardNo", object.get("IdCardNo"));
				// 真实姓名
				Object object2 = object.get("realName");
				if (object2 != null && !"".equals(object2)) {
					resHashMap.put("RealName", object.get("realName"));
				}
				// 是否风险测评
				resHashMap.put("isRisk", object.get("isRisk"));
				// 风险测评文案
				resHashMap.put("riskMessge", object.get("riskMessge"));
				// 2.0用户投资状态 1 未投资 0投资过
				if ("1".equals(resHashMap.get("isNewHand"))) {
					resHashMap.put("isNewHand", object.get("FristInvest"));
				}
			} else {
				logger.info("同步查询2.0信息业务处理异常！");
				resHashMap.put("Message", map.get("Message"));
			}
		} else {
			logger.info("调用2.0同步数据查询处理过程中 Connection Timeout！OR Response Data is null or ''");
			resHashMap.put("Message", CodeEnum.Fail.getName());
		}
		return resHashMap;

	}

	// messageNotify 消息通知
	public void messageNotify(User user, String type) {
		try {
			// 做个消息通知
			JSONObject ssoparams = couponNotify(user, type);
			logger.info("调用发送优惠券接口请求参数：{}", ssoparams);
			Properties property = ClassLoaderUtil.getProperties("app_system.properties");
			String ssoURL = property.getProperty("HzdjrCouponNotifyUrl");
			ssoURL = ssoURL + "couponInsert";
			logger.info("\n访问相关活动成功通知调用远程发送优惠卷接口的URL地址=====>" + ssoURL);
			String responseResult = SendDataToUrl.getDataToUrl(ssoURL, ssoparams.toJSONString(), "POST");
			logger.info("\n访问相关活动成功通知调用远程发送优惠卷接口响应数据=====>" + responseResult);

		} catch (Exception e) {
			e.getStackTrace();
			logger.info("\n相关活动成功通知调用远程接口异常：" + e.getMessage());
		}
	}

	private JSONObject couponNotify(User user, String type) {
		JSONObject ssoparams = new JSONObject();
		// 手机号
		ssoparams.put("mobile", user.getMobile());
		// 活动类型 1 注册 2 开户 3 首次投资 4 运营活动 5 1%增益券活动
		ssoparams.put("type", type);
		//活动-邀请码 inviteKey
		if ("1".equals(type) && null != user.getRefferee()) {
			// 视为1%增益券活动发券通知 5
			StringBuffer buffer = new StringBuffer();
			buffer.append("5");
			buffer.append("-");
			buffer.append(user.getRefferee());
			ssoparams.put("inviteKey", buffer.toString());
		}
		return ssoparams;
	}

	// 是不是新手或者首投 1 新手或者首投 0 否
	public String isFirstInvest(User user) {
		try {
			// 做个消息通知
			JSONObject ssoparams = new JSONObject();
			// 手机号
			ssoparams.put("mobile", user.getMobile());

			Properties property = ClassLoaderUtil.getProperties("app_system.properties");

			System.out.println(ssoparams.toJSONString());
			String ssoURL = property.getProperty("HzdjrCouponNotifyUrl");
			ssoURL = ssoURL + "isFirstInvest";
			logger.info("\n访问判断是否新手或者是首投调用远程接口的URL地址=====>" + ssoURL);
			String responseResult = SendDataToUrl.getDataToUrl(ssoURL, ssoparams.toJSONString(), "POST");
			logger.info("\n访问判断是否新手或者是首投调用远程接口响应数据=====>" + responseResult);
			net.sf.json.JSONObject fromObject = net.sf.json.JSONObject.fromObject(responseResult);
			return fromObject.getString("code");
		} catch (Exception e) {
			e.getStackTrace();
			logger.info("\n相关活动成功通知调用远程接口异常：" + e.getMessage());
		}
		return "0";
	}
	
	/**
	 * @description : coupon used information (优惠券的使用信息)
	 * @author Administrator
	 * @param Map
	 * @return Map
	 */
	public void informationCouponUsed(Map<String,Object> map ){
		//只有当投资使用优惠券的情况下才会触发远程调用查询相关优惠券的信息
		if(!"0".equals(map.get("couponId"))){
			//组装请求参数
			JSONObject conponParam = new JSONObject();
			conponParam.put("couponId", String.valueOf(map.get("couponId")));
			Properties property = ClassLoaderUtil.getProperties("app_system.properties");
			logger.info("远程调用优惠券使用相关信息的接口的request paramer"+conponParam.toJSONString());
			String ssoURL = property.getProperty("HzdjrCouponNotifyUrl");
			ssoURL = ssoURL + "getCouponInfo";
			logger.info("\n访问出借使用相关优惠券的信息远程接口的URL地址=====>" + ssoURL);
			String responseResult="";
			try {
				responseResult = SendDataToUrl.getDataToUrl(ssoURL, conponParam.toJSONString(), "POST");
				logger.info("\n访问出借使用相关优惠券的信息远程接口响应数据=====>" + responseResult);
				if(!"".equals(responseResult)){
					JSONObject responseObject = JSONObject.parseObject(responseResult);
					String statusValue = getStatusValue(responseObject.getString("type"));
					BigDecimal bigDecimal = new BigDecimal(String.valueOf(map.get("recievedInterest")));
					bigDecimal=bigDecimal.add(responseObject.getBigDecimal("amount"));
						if("".equals(responseObject.get("rate"))||IsObjectNullUtils.is(responseObject.get("rate"))){
							//返现券处理
							responseObject.remove("rate");
							BigDecimal intValue = responseObject.getBigDecimal("amount");
							responseObject.put("couponAmount", intValue.intValue()+"元");
							map.put("typeValue", responseObject.get("couponAmount")+statusValue);
						}else{
							//增益券处理
							responseObject.put("couponAmount", responseObject.get("amount")+"元");
							map.put("typeValue", responseObject.get("rate")+statusValue);
							responseObject.remove("rate");
						}
						map.putAll(responseObject);
						//回款金额字段处理（产品需求变更修改）
						if(map.containsKey("backpaymentAmount")){
							BigDecimal backpaymentAmount = new BigDecimal(String.valueOf(map.get("backpaymentAmount")));
							backpaymentAmount=backpaymentAmount.add(responseObject.getBigDecimal("amount"));
							map.put("backpaymentAmount", formatBigdecimal(backpaymentAmount));
						}
						map.put("recievedInterest", formatBigdecimal(bigDecimal));
						map.put("recievedDesc", "此收益包含："+statusValue+responseObject.get("couponAmount"));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.info("\n访问出借使用相关优惠券的信息远程接口处理过程中出了异常=====>" + e.getMessage());
				e.printStackTrace();
			}
			
			
		}
		
	}
	
	/**
	 * 金额保留两位小数
	 * @param s
	 * @return
	 */
	public String formatBigdecimal(BigDecimal s){
		String res = "0.00";
		if(null==s){
		}else{
			res = s.setScale(2,  BigDecimal.ROUND_HALF_UP).toString();
		}
		return res;
	}
	
	/**
	 * @description:状态数值转换
	 * @param status 数据库标识
	 * @return
	 */
	public String getStatusValue(String status){
		StatusValue statusValue = StatusValue.findStatusValue(status);
		if(statusValue==null){
			return "";
		}
		return statusValue.getValue();
	}
	

	/**
	 * 分享文案加密
	 * 
	 * @throws Exception
	 */
	public String ShareInformationEncryption(String str) throws Exception {

		return str2HexStr(DESecbpkcs7.getEncrypt(InvitationCode_Key, str));
	}

	/**
	 * 分享文案解密
	 * 
	 * @throws Exception
	 */
	public String ShareInformationDcryption(String str) throws Exception {
		String information = hexStr2Str(str);
		information = DESecbpkcs7.getDecrypt(InvitationCode_Key, information);
		return information;
	}

	/**
	 *      * 字符串 转换 十六进制  * @param String str Byte字符串(Byte之间无分隔符 如:[616C6B])  
	 *  * @return String 对应的字符串    
	 */
	public static String str2HexStr(String str) {
		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString().trim();
	}

	/**
	 *      * 十六进制转换字符串    * @param String str Byte字符串(Byte之间无分隔符 如:[616C6B])  
	 *  * @return String 对应的字符串    
	 */
	public static String hexStr2Str(String hexStr) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;
		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}
	
	public static String doHttpsGetJson(String Url)
    {
        String message = "";
        try
        {
            System.out.println("doHttpsGetJson");//TODO:dd
            URL urlGet = new URL(Url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection(); 
            http.setRequestMethod("GET");                
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  
            http.setDoOutput(true);  
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");        
            http.connect();
            InputStream is =http.getInputStream();
            int size =is.available();
            byte[] jsonBytes =new byte[size];
            is.read(jsonBytes);
            message=new String(jsonBytes,"UTF-8");
        } 
        catch (MalformedURLException e)
        {
              e.printStackTrace();
          }
        catch (IOException e)
          {
              e.printStackTrace();
          } 
        return message;
    }
}