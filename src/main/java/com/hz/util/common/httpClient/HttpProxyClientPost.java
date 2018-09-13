package com.hz.util.common.httpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONObject;
import com.hz.app.api.util.ConfigUtil;
import com.hz.app.api.util.ExceptionUtil;
import com.hz.app.api.util.JsonUtil;
import com.hzwealth.app.dispatch.core.util.AESUtil;
import com.hzwealth.app.dispatch.core.util.Md5Util;

public class HttpProxyClientPost {

	private final Log log = LogFactory.getLog(this.getClass());

	InputStream ins = HttpProxyClientPost.class.getClassLoader().getResourceAsStream("intf.properties");
	Properties prop = new Properties();

	public HttpProxyClientPost() {
	}

	public Map<String, Object> post(Map<String,Object> map) throws Exception {
		prop.load(ins);
		String code =(String) map.get("code");
		String url =prop.getProperty("biz.http.server.test")+""+code;
		@SuppressWarnings("unchecked")
		Map<String,Object> data=(Map<String, Object>) map.get("data");
		return postRequest(url,code,data);
	}
	
	public JSONObject postRequest(String url, String code, Map<String,Object> params) throws Exception {
		prop.load(ins);
		String paramName = "params";
		String key = prop.getProperty("biz.http.server.key");
		String single = Md5Util.getMD5String(StringUtils.join(new String[]{code, "2.0PC"}, ","), key);
		params.put("signature", single);

		String str = JsonUtil.jsonExcDeepEncode(params);
		log.info("加密前开始调用接口,str=" + str);
		str = AESUtil.enCrypt(str, key);
		log.info("加密后开始调用接口,str=" + str);
		String retJsonStr=null;
		
		retJsonStr = postRequest(url, paramName, str);
		log.info("接口调用完毕");
		log.info("返回参数 : " + retJsonStr);

		Map<String, Object> retParamsMap = JsonUtil.convJsonToMap(retJsonStr, true);
		
		String plainText =AESUtil.deCrypt(retParamsMap.get("retParams").toString(), key);
		log.info("返回参数解密：" + plainText);
		
		JSONObject jsonObject=JSONObject.parseObject(plainText);
		//Map<String, Object> retMap = JsonUtil.convJsonToMap(plainText);
		
		return jsonObject;
	}

	private String postRequest(String url, String paramName, String paramValue) {
		StringBuffer bodyStr = new StringBuffer();
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
			
			HttpClient client = new HttpClient();
			client.getHostConfiguration().setHost(domain, Integer.valueOf(null==port?"80":port), protocol);
			
			// HttpClient 超时设置
			client.getHttpConnectionManager().getParams().setConnectionTimeout(35000);
			client.getHttpConnectionManager().getParams().setSoTimeout(480000);

			// 执行请求
			bodyStr.append((doSpecialRequest(client, method, domain, paramName,
					paramValue, 1)));
		} catch (ArrayIndexOutOfBoundsException ex) {
			log.debug("001-远程地址配置不正确:" + url);
			log.debug(ExceptionUtil.exception2String(ex));
			bodyStr.append(ConfigUtil.get("biz.http.server.errorCode.001"));
		} catch (Exception ex) {
			ex.printStackTrace();
			log.debug("002-获取远程页面时出错:" + ex.getMessage());
			bodyStr.append(ConfigUtil.get("biz.http.server.errorCode.002"));
		}
		//return formatHttpPostRsltJson(bodyStr.toString());
		
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
		} else if (!rsltJson.trim().startsWith("{")) {
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
	
	private String doSpecialRequest(HttpClient client, String url,
			String domain, String paramName, String paramValue, int count) throws IOException {
		StringBuffer bodyStr = new StringBuffer();
		PostMethod postRequest = new PostMethod(url);
		postRequest.getParams().setParameter("http.protocol.cookie-policy",
				CookiePolicy.BROWSER_COMPATIBILITY);
		// 关闭httpclient自动重定向动能
		postRequest.setFollowRedirects(false);
		postRequest.setParameter(paramName, paramValue);
		prop.load(ins);
		int retryCount = Integer.parseInt(prop.getProperty("biz.http.server.retryCount"));
		try {
			if (count > retryCount) {// 循环后跳出
				log.error("远程方法调用被重定向" + count + "次  " + " 调用方法:" + url);
				return " 访问异常 请联系管理员";
			}
			int retCode1 = client.executeMethod(postRequest);
			log.debug("retCode1=" + retCode1);

			if (200 == retCode1) {// 已完成登录
				load(bodyStr, postRequest);// 加载页面
			} else {// 可能未完成登录
				log.debug("try again ... ");
				Header header = postRequest.getResponseHeader("location");
				if (header != null) {// 递归请求重定向后的URL
					return this.doSpecialRequest(client, header.getValue(),
							domain, paramName, paramValue, count + 1);
				}
				postRequest.releaseConnection();

				postRequest = new PostMethod(url);
				postRequest.setFollowRedirects(false);
				int retCode2 = client.executeMethod(postRequest);
				log.debug("retCode2=" + retCode2);

				load(bodyStr, postRequest);// 加载页面
			}
		} catch (Exception e) {
			log.error(ExceptionUtil.exceptionStack2String(e));
			log.debug(ExceptionUtil.exceptionStack2String(e));
		} finally {
			postRequest.releaseConnection();
		}
		return bodyStr.toString();
	}

	private void load(StringBuffer bodyStr, PostMethod postRequest)
			throws IOException, UnsupportedEncodingException {
		InputStream in = null;
		try {
			prop.load(ins);

			String encoding = prop.getProperty("biz.http.server.encode");
			in = postRequest.getResponseBodyAsStream();
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
}
