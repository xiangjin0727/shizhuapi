package com.hz.app.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.google.common.collect.Lists;
import com.hz.app.api.util.ConfigUtil;
import com.hz.app.api.util.ExceptionUtil;
import com.hz.app.api.util.JsonUtil;
import com.hz.core.util.ClassLoaderUtil;
import com.hz.core.util.UUIDUtil;
import com.hzwealth.app.dispatch.core.util.AESUtil;
import com.hzwealth.app.dispatch.core.util.Md5Util;

public class HttpProxyClientPost {
	private final Log log = LogFactory.getLog(this.getClass());
	private static RequestConfig requestConfig;
	private static Properties prop;

	static {
		prop = ClassLoaderUtil.getProperties("app_system.properties");
		requestConfig = RequestConfig.custom().setRedirectsEnabled(false)
				.setConnectTimeout(5000).setConnectionRequestTimeout(1000)
				.setSocketTimeout(480000).build();
	}
	public HttpProxyClientPost() {
	}

	public String openAccToCrm(Map<String,Object> params)
			throws Exception {
		String url =prop.getProperty("biz.http.server.openAccToCrm");
		String paramName = prop.getProperty("biz.http.server.openAccToCrm.paramName");
		String key = prop.getProperty("biz.http.server.key");
		String userName=String.valueOf(params.get("userName"));
		userName = new String(userName.getBytes(),"utf-8");
		String mobile=String.valueOf(params.get("mobile"));
		String single = Md5Util.getMD5String(StringUtils.join(new String[]{params.get("certificateNo").toString(), mobile}, ","), key);
		params.put("signature", single);
		String str = JsonUtil.jsonExcDeepEncode(params);
		System.out.println("加密前开始调用接口,str=" + str);
		str = AESUtil.enCrypt(str, key);
		System.out.println("加密后开始调用接口,str=" + str);
		String debug_type = prop.getProperty("debug_type");
		String json=null;
		//if("true".equals(debug_type))
		//json="{\"systemSourceId\":\"MGMT\",\"retInfo\":\"处理成功\",\"retCode\":\"0000\",\"sendBillList\":[{\"retInfo\":\"【ESB消息】信息发布失败:发送短信失败：; nested exception is: java.net.UnknownHostException: sdk229ws.eucp.b2m.cn\",\"retCode\":\"3000\",\"addr\":\"13263397546\"}],\"bizId\":\"34535354345345\"}";
		//else
		json = postRequest(url, paramName, str);
		System.out.println("接口调用完毕");
		System.out.println("openAccToCrm return message : " + json);
		return json;
	}

	public String openAccToPay(Map<String,Object> params)
			throws Exception {
		String url =prop.getProperty("biz.http.server.openAccToPay");
		String paramName = prop.getProperty("biz.http.server.openAccToPay.paramName");
		String key = prop.getProperty("biz.http.server.key");
		String single = Md5Util.getMD5String(StringUtils.join(new String[]{params.get("customerId") + "", params.get("bankAccount") + ""}, ","), key);
		params.put("signature", single);
		String str = JsonUtil.jsonExcDeepEncode(params);
		System.out.println("加密前开始调用接口,str=" + str);
		str = AESUtil.enCrypt(str, key);
		System.out.println("加密后开始调用接口,str=" + str);
		String debug_type = prop.getProperty("debug_type");
		String json=null;
		//if("true".equals(debug_type))
		//json="{\"systemSourceId\":\"MGMT\",\"retInfo\":\"处理成功\",\"retCode\":\"0000\",\"sendBillList\":[{\"retInfo\":\"【ESB消息】信息发布失败:发送短信失败：; nested exception is: java.net.UnknownHostException: sdk229ws.eucp.b2m.cn\",\"retCode\":\"3000\",\"addr\":\"13263397546\"}],\"bizId\":\"34535354345345\"}";
		//else
		json = postRequest(url, paramName, str);
		System.out.println("接口调用完毕");
		System.out.println("openAccToPay return message : " + json);
		return json;
	}

	public String investHandBill(Map<String,Object> params)
			throws Exception {
		String url =prop.getProperty("biz.http.server.investHandBill");
		String paramName = prop.getProperty("biz.http.server.investHandBill.paramName");
		String key = prop.getProperty("biz.http.server.key");
		String single = Md5Util.getMD5String(StringUtils.join(new String[]{params.get("systemSourceId") + "", params.get("systemSourceId") + ""}, ","), key);
		params.put("signature", single);
		String str = JsonUtil.jsonExcDeepEncode(params);
		System.out.println("加密前开始调用接口,str=" + str);
		str = AESUtil.enCrypt(str, key);
		System.out.println("加密后开始调用接口,str=" + str);
		String debug_type = prop.getProperty("debug_type");
		String json=null;
		//if("true".equals(debug_type))
		//json="{\"systemSourceId\":\"MGMT\",\"retInfo\":\"处理成功\",\"retCode\":\"0000\",\"sendBillList\":[{\"retInfo\":\"【ESB消息】信息发布失败:发送短信失败：; nested exception is: java.net.UnknownHostException: sdk229ws.eucp.b2m.cn\",\"retCode\":\"3000\",\"addr\":\"13263397546\"}],\"bizId\":\"34535354345345\"}";
		//else
		json = postRequest(url, paramName, str);
		System.out.println("接口调用完毕");
		System.out.println("investHandBill return message : " + json);
		return json;
	}

	public String renewalBankCard(Map<String,Object> params)
			throws Exception {
		String url =prop.getProperty("biz.http.server.renewalBankCard");
		String paramName = prop.getProperty("biz.http.server.renewalBankCard.paramName");
		String key = prop.getProperty("biz.http.server.key");
		String single = Md5Util.getMD5String(StringUtils.join(new String[]{params.get("systemSourceId") + "", params.get("bizId") + ""}, ","), key);
		params.put("signature", single);
		String str = JsonUtil.jsonExcDeepEncode(params);
		System.out.println("加密前开始调用接口,str=" + str);
		str = AESUtil.enCrypt(str, key);
		System.out.println("加密后开始调用接口,str=" + str);
		String debug_type = prop.getProperty("debug_type");
		String json=null;
		//if("true".equals(debug_type))
		//json="{\"systemSourceId\":\"MGMT\",\"retInfo\":\"处理成功\",\"retCode\":\"0000\",\"sendBillList\":[{\"retInfo\":\"【ESB消息】信息发布失败:发送短信失败：; nested exception is: java.net.UnknownHostException: sdk229ws.eucp.b2m.cn\",\"retCode\":\"3000\",\"addr\":\"13263397546\"}],\"bizId\":\"34535354345345\"}";
		//else
		json = postRequest(url, paramName, str);
		System.out.println("接口调用完毕");
		System.out.println("renewalBankCard return message : " + json);
		return json;
	}
	
	public String queryCustomerInfo(Map<String,Object> params)
			throws Exception {
		String url =prop.getProperty("biz.http.server.queryCustomerInfo");
		String paramName = prop.getProperty("biz.http.server.queryCustomerInfo.paramName");
		String key = prop.getProperty("biz.http.server.key");
		String single = Md5Util.getMD5String(StringUtils.join(new String[]{params.get("mobile") + "", params.get("systemSourceId") + ""}, ","), key);
		params.put("accountState", prop.getProperty("ChnId"));
		params.put("signature", single);
		String str = JsonUtil.jsonExcDeepEncode(params);
		System.out.println("加密前开始调用接口,str=" + str);
		str = AESUtil.enCrypt(str, key);
		System.out.println("加密后开始调用接口,str=" + str);
		String debug_type = prop.getProperty("debug_type");
		String json=null;
		//if("true".equals(debug_type))
		//json="{\"systemSourceId\":\"MGMT\",\"retInfo\":\"处理成功\",\"retCode\":\"0000\",\"sendBillList\":[{\"retInfo\":\"【ESB消息】信息发布失败:发送短信失败：; nested exception is: java.net.UnknownHostException: sdk229ws.eucp.b2m.cn\",\"retCode\":\"3000\",\"addr\":\"13263397546\"}],\"bizId\":\"34535354345345\"}";
		//else
		json = postRequest(url, paramName, str);
		System.out.println("接口调用完毕");
		System.out.println("queryCustomerInfo return message : " + json);
		return json;
	}

	public String webLogin(Map<String,Object> params)
			throws Exception {
		String url =prop.getProperty("biz.http.server.webLogin");
		String paramName = prop.getProperty("biz.http.server.webLogin.paramName");
		String key = prop.getProperty("biz.http.server.key");
		String single = Md5Util.getMD5String(StringUtils.join(new String[]{params.get("systemSourceId") + "", params.get("operatorId") + ""}, ","), key);
		params.put("signature", single);
		String str = JsonUtil.jsonExcDeepEncode(params);
		System.out.println("加密前开始调用接口,str=" + str);
		str = AESUtil.enCrypt(str, key);
		System.out.println("加密后开始调用接口,str=" + str);
		String debug_type = prop.getProperty("debug_type");
		String json=null;
		//if("true".equals(debug_type))
			//json="{\"systemSourceId\":\"MGMT\",\"retInfo\":\"处理成功\",\"retCode\":\"0000\",\"sendBillList\":[{\"retInfo\":\"【ESB消息】信息发布失败:发送短信失败：; nested exception is: java.net.UnknownHostException: sdk229ws.eucp.b2m.cn\",\"retCode\":\"3000\",\"addr\":\"13263397546\"}],\"bizId\":\"34535354345345\"}";
		//else
			json = postRequest(url, paramName, str);
		System.out.println("接口调用完毕");
		System.out.println("webLogin return message : " + json);
		return json;
	}
	
	public String queryBalance(Map<String,Object> params) throws Exception {
		String url =prop.getProperty("biz.http.server.queryBalance");
		String paramName = prop.getProperty("biz.http.server.queryBalance.paramName");
		String key = prop.getProperty("biz.http.server.key");
		String single = Md5Util.getMD5String(StringUtils.join(new String[]{params.get("bizId") + "", params.get("custNo") + ""}, ","), key);
		params.put("signature", single);
		String str = JsonUtil.jsonExcDeepEncode(params);
		System.out.println("加密前开始调用接口,str=" + str);
		str = AESUtil.enCrypt(str, key);
		System.out.println("加密后开始调用接口,str=" + str);
		String debug_type = prop.getProperty("debug_type");
		String json=null;
	/*	if("true".equals(debug_type))
			json="{\"systemSourceId\":\"MGMT\",\"retInfo\":\"处理成功\",\"retCode\":\"0000\",\"sendBillList\":[{\"retInfo\":\"【ESB消息】信息发布失败:发送短信失败：; nested exception is: \n\tjava.net.UnknownHostException: sdk229ws.eucp.b2m.cn\",\"retCode\":\"3000\",\"addr\":\"13263397546\"}],\"bizId\":\"34535354345345\"}";
		else*/
			json = postRequest(url, paramName, str);
		System.out.println("接口调用完毕");
		System.out.println("queryBalance return message : " + json);
		return json;
	}

	public String singleRtTrade(Map<String,Object> params) throws Exception {
		String url =prop.getProperty("biz.http.server.singleRtTrade");
		String paramName = prop.getProperty("biz.http.server.singleRtTrade.paramName");
		String key = prop.getProperty("biz.http.server.key");
		String single = Md5Util.getMD5String(StringUtils.join(new String[]{params.get("bizId") + "", params.get("systemSourceId") + ""}, ","), key);
		params.put("signature", single);
		String str = JsonUtil.jsonExcDeepEncode(params);
		System.out.println("加密前开始调用接口,str=" + str);
		str = AESUtil.enCrypt(str, key);
		System.out.println("加密后开始调用接口,str=" + str);
		String debug_type = prop.getProperty("debug_type");
		String json=null;
	/*	if("true".equals(debug_type))
			json="{\"systemSourceId\":\"MGMT\",\"retInfo\":\"处理成功\",\"retCode\":\"0000\",\"sendBillList\":[{\"retInfo\":\"【ESB消息】信息发布失败:发送短信失败：; nested exception is: \n\tjava.net.UnknownHostException: sdk229ws.eucp.b2m.cn\",\"retCode\":\"3000\",\"addr\":\"13263397546\"}],\"bizId\":\"34535354345345\"}";
		else*/
		json = postRequest(url, paramName, str);
		System.out.println("接口调用完毕");
		System.out.println("singleRtTrade return message : " + json);
		return json;
	}

	public String sendBill(Map<String,Object> params) throws Exception {
		String url =prop.getProperty("biz.http.server.sendBill");
		String paramName = prop.getProperty("biz.http.server.sendBill.paramName");
		String key = prop.getProperty("biz.http.server.key");
		String single = Md5Util.getMD5String(StringUtils.join(new String[]{params.get("systemSourceId") + "", params.get("bizId") + ""}, ","), key);
		params.put("signature", single);
		String str = JsonUtil.jsonExcDeepEncode(params);
		System.out.println("加密前开始调用接口,str=" + str);
		str = AESUtil.enCrypt(str, key);
		System.out.println("加密后开始调用接口,str=" + str);
		String debug_type = prop.getProperty("debug_type");
		String json=null;
//	   if("true".equals(debug_type))
//		   json="{\"systemSourceId\":\"MGMT\",\"retInfo\":\"处理成功\",\"retCode\":\"0000\",\"sendBillList\":[{\"retInfo\":\"【ESB消息】信息发布失败:发送短信失败：; nested exception is: java.net.UnknownHostException: sdk229ws.eucp.b2m.cn\",\"retCode\":\"3000\",\"addr\":\"13263397546\"}],\"bizId\":\"34535354345345\"}";
//		else
		   json = postRequest(url, paramName, str);
		System.out.println("接口调用完毕");
		System.out.println("sendBill return message : " + json);
		return json;
	}

	public String batchTrade(Map<String,Object> params)
			throws Exception {
		String url =prop.getProperty("biz.http.server.batchTrade");
		String paramName = prop.getProperty("biz.http.server.batchTrade.paramName");
		String key = prop.getProperty("biz.http.server.key");
		String single = Md5Util.getMD5String(StringUtils.join(new String[]{params.get("systemSourceId") + "", params.get("operatorId") + ""}, ","), key);
		params.put("signature", single);
		String str = JsonUtil.jsonExcDeepEncode(params);
		System.out.println("加密前开始调用接口,str=" + str);
		str = AESUtil.enCrypt(str, key);
		System.out.println("加密后开始调用接口,str=" + str);
		String debug_type = prop.getProperty("debug_type");
		String json=null;
		//if("true".equals(debug_type))
		//json="{\"systemSourceId\":\"MGMT\",\"retInfo\":\"处理成功\",\"retCode\":\"0000\",\"sendBillList\":[{\"retInfo\":\"【ESB消息】信息发布失败:发送短信失败：; nested exception is: java.net.UnknownHostException: sdk229ws.eucp.b2m.cn\",\"retCode\":\"3000\",\"addr\":\"13263397546\"}],\"bizId\":\"34535354345345\"}";
		//else
		json = postRequest(url, paramName, str);
		System.out.println("接口调用完毕");
		System.out.println("batchTrade return message : " + json);
		return json;
	}

	public String editBankCard(Map<String,Object> params)
			throws Exception {
		String url =prop.getProperty("biz.http.server.editBankCard");
		String paramName = prop.getProperty("biz.http.server.editBankCard.paramName");
		String key = prop.getProperty("biz.http.server.key");
		String single = Md5Util.getMD5String(StringUtils.join(new String[]{params.get("payNo") + "", params.get("bizId") + ""}, ","), key);
		params.put("signature", single);
		String str = JsonUtil.jsonExcDeepEncode(params);
		System.out.println("加密前开始调用接口,str=" + str);
		str = AESUtil.enCrypt(str, key);
		System.out.println("加密后开始调用接口,str=" + str);
		String debug_type = prop.getProperty("debug_type");
		String json=null;
		//if("true".equals(debug_type))
		//json="{\"systemSourceId\":\"MGMT\",\"retInfo\":\"处理成功\",\"retCode\":\"0000\",\"sendBillList\":[{\"retInfo\":\"【ESB消息】信息发布失败:发送短信失败：; nested exception is: java.net.UnknownHostException: sdk229ws.eucp.b2m.cn\",\"retCode\":\"3000\",\"addr\":\"13263397546\"}],\"bizId\":\"34535354345345\"}";
		//else
		json = postRequest(url, paramName, str);
		System.out.println("接口调用完毕");
		System.out.println("editBankCard return message : " + json);
		return json;
	}

	public String appRechargeCash(Map<String,Object> params)
			throws Exception {
		String url =prop.getProperty("biz.http.server.appRechargeCash");
		String paramName = prop.getProperty("biz.http.server.appRechargeCash.paramName");
		String key = prop.getProperty("biz.http.server.key");
		String single = Md5Util.getMD5String(StringUtils.join(new String[]{params.get("systemSourceId") + "", params.get("operatorId") + ""}, ","), key);
		params.put("signature", single);
		String str = JsonUtil.jsonExcDeepEncode(params);
		System.out.println("加密前开始调用接口,str=" + str);
		str = AESUtil.enCrypt(str, key);
		System.out.println("加密后开始调用接口,str=" + str);
		String debug_type = prop.getProperty("debug_type");
		String json=null;
		//if("true".equals(debug_type))
		//json="{\"systemSourceId\":\"MGMT\",\"retInfo\":\"处理成功\",\"retCode\":\"0000\",\"sendBillList\":[{\"retInfo\":\"【ESB消息】信息发布失败:发送短信失败：; nested exception is: java.net.UnknownHostException: sdk229ws.eucp.b2m.cn\",\"retCode\":\"3000\",\"addr\":\"13263397546\"}],\"bizId\":\"34535354345345\"}";
		//else
		json = postRequest(url, paramName, str);
		System.out.println("接口调用完毕");
		System.out.println("appRechargeCash return message : " + json);
		return json;
	}


	private String postRequest(String url, String paramName, String paramValue) {
		StringBuffer bodyStr = new StringBuffer();
		Long startTime = System.currentTimeMillis();
		HttpClient client = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		try {
			String protocol = null;
			String domain = null;
			String port = null;
			String method = null;
			
			Pattern urlPattern = Pattern.compile("(?<protocol>[a-zA-Z]+)://(?<domain>([\\w-]+\\.)+[\\w-]+)(:(?<port>\\d+))?(?<method>[/\\.\\w-]*)");
			Matcher matcher = urlPattern.matcher(url);
			
			if (matcher.matches()) {
				protocol = matcher.group("protocol");
				domain = matcher.group("domain");
				port = matcher.group("port");
				method = matcher.group("method");
			}

			HttpHost host = new HttpHost(domain, Integer.valueOf(null == port ? "80" : port), protocol);

			// 执行请求
			bodyStr.append((doSpecialRequest(client, host, method, domain, paramName,
					paramValue, 1)));
		} catch (ArrayIndexOutOfBoundsException ex) {
			log.debug("001-远程地址配置不正确:" + url);
			log.debug(ExceptionUtil.exception2String(ex));
			bodyStr.append(ConfigUtil.get("biz.http.server.errorCode.001"));
		} catch (Exception ex) {
			ex.printStackTrace();
			log.debug("002-获取远程页面时出错:" + ex.getMessage());
			bodyStr.append(ConfigUtil.get("biz.http.server.errorCode.002"));
		} finally {
			this.saveInvokingLog(url, startTime, System.currentTimeMillis(), bodyStr.toString());
		}
//		return formatHttpPostRsltJson(bodyStr.toString());
		
		return bodyStr.toString();
	}
	
	/**
	 * HttpClient返回Json结果格式化
	 * @param RsltJson Json结果
	 * @return Json结果串
	 * @author lch 2015-08-22
	 */
	private String formatHttpPostRsltJson(String rsltJson) {
		Map<String, Object> rsltJsonMap = null;
		
		log.debug(String.format("HttpProxyClientPost.postRequest Original rstValue：%1$s", rsltJson));
		
		if (StringUtils.isEmpty(rsltJson)) {
			rsltJsonMap = new HashMap<String, Object>();
			rsltJsonMap.put("retCode", "9000");
			rsltJsonMap.put("retInfo", "连接超时!");
			
			rsltJson = JsonUtil.jsonExcDeepEncode(rsltJsonMap);
		} else if (rsltJson.trim().startsWith("{")) {
			// 非Json字符串
			rsltJsonMap = new HashMap<String, Object>();
			rsltJsonMap.put("retCode", "9001");
			rsltJsonMap.put("retInfo", rsltJson);
			
			rsltJson = JsonUtil.jsonExcDeepEncode(rsltJsonMap);
		} else {
			// 接口异常Json字符串
			rsltJsonMap = JsonUtil.convJsonToMap(rsltJson, true);
			
			if (!rsltJsonMap.containsKey("retCode")) {
				rsltJsonMap.put("retCode", "4000");
				rsltJsonMap.put("retInfo", rsltJsonMap.get("msg"));
				
				rsltJson = JsonUtil.jsonExcDeepEncode(rsltJsonMap);
			}
		}
		
		return rsltJson;
	}

	
	private String doSpecialRequest(HttpClient client,HttpHost host, String url,
			String domain, String paramName, String paramValue, int count) throws IOException {
        StringBuffer bodyStr = new StringBuffer();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.getParams().setParameter("http.protocol.cookie-policy",
                CookiePolicy.BROWSER_COMPATIBILITY);
        List<NameValuePair> list = Lists.newArrayList();
        list.add(new BasicNameValuePair(paramName, paramValue));
		httpPost.setEntity(new UrlEncodedFormEntity(list));
        int retryCount = Integer.parseInt(prop.getProperty("biz.http.server.retryCount"));
        try {
            if (count > retryCount) {// 循环后跳出
                log.error("远程方法调用被重定向" + count + "次  " + " 调用方法:" + url);
                return " 访问异常 请联系管理员";
            }
            HttpResponse response = client.execute(host, httpPost);
            int retCode1 = response.getStatusLine().getStatusCode();
            log.debug("retCode1=" + retCode1);

            if (200 == retCode1) {// 已完成登录
                load(bodyStr, response.getEntity().getContent());// 加载页面
            } else {// 可能未完成登录
                log.debug("try again ... ");
                Header header = httpPost.getHeaders("location")[0];
                if (header != null) {// 递归请求重定向后的URL
                    return this.doSpecialRequest(client, host, header.getValue(),
                            domain, paramName, paramValue, count + 1);
                }
                httpPost.releaseConnection();

                httpPost = new HttpPost(url);
                response = client.execute(host, httpPost);
                int retCode2 = response.getStatusLine().getStatusCode();
                log.debug("retCode2=" + retCode2);

                load(bodyStr, response.getEntity().getContent());// 加载页面
            }
        } catch (Exception e) {
            log.error(ExceptionUtil.exceptionStack2String(e));
            log.debug(ExceptionUtil.exceptionStack2String(e));
        } finally {
            httpPost.releaseConnection();
        }
        return bodyStr.toString();
	}

    private void load(StringBuffer bodyStr, InputStream in)
            throws IOException, UnsupportedEncodingException {
        try {
            String encoding = prop.getProperty("biz.http.server.encode");
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    in, encoding));
            String line = null;
            while ((line = reader.readLine()) != null) {
                bodyStr.append(line).append("\n");
            }
        } finally {
            in.close();
        }
    }
	
	/**
	 * 记录接口调用时间
	 * @param method 接口方法URL
	 * @param startTime 开始调用时间
	 * @param stopTime 结束调用时间
	 * @param rsltJson 接口返回值
	 */
	private void saveInvokingLog(String method, Long startTime, Long stopTime, String rsltJson) {
		try {
			if (prop.getProperty("intf_timinglog") == "false") {
				return;
			}
			
			Map<String, Object> loggingParams = new HashMap<String, Object>();
			loggingParams.put("id", UUIDUtil.genUUIDString());
			loggingParams.put("invokingIntf", method.substring(method.lastIndexOf("/")+1, method.lastIndexOf(".do")));
			loggingParams.put("invokingTime", new Date(startTime));
			loggingParams.put("invokingSource", "app");
			loggingParams.put("execTime", stopTime - startTime);
			
		} catch (Exception ex) {
			
		}
	}
}
