package com.hz.util.common.httpClient;

import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;


public class HttpClientUtil {
	private static Logger logger = Logger.getLogger(HttpClientUtil.class);
	/**
	 * post方式 王浩 @2015-5-14 功能:超时和编码 timeout:超时(毫秒) encoding:编码
	 */
	@SuppressWarnings("deprecation")
	public static String postWay(String url, Map<String, String> map) {
		String result = "";
		PostMethod postMethod = null;
		try {
			// 读取内容
			HttpClient httpClient = new HttpClient();
			// 设置超时
//			httpClient.setConnectionTimeout(timeout);
//			if (encoding != null && !"".equals(encoding)) {
//				// 设置编码
//				httpClient.getParams().setParameter(
//						HttpMethodParams.HTTP_CONTENT_CHARSET, encoding);
//			}
			postMethod = new PostMethod(url);
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());
			// 参数
			if (map != null && map.size() != 0) {
				int i = map.size();
				NameValuePair[] params = new NameValuePair[i];
				int k = 0;
				for (String key : map.keySet()) {
					params[k] = new NameValuePair(key, map.get(key));
					k++;
				}
				postMethod.setRequestBody(params);
			}
			httpClient.executeMethod(postMethod);
			// 第一种方式
			result = postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error("httpclient请求异常", e);
		} finally {
			postMethod.releaseConnection();
		}
		return result;
	}
 
}
