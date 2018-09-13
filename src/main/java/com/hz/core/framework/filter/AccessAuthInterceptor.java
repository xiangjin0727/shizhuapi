package com.hz.core.framework.filter;

import java.net.URLEncoder;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hz.app.index.vo.User;
import com.hz.core.framework.redis.RedisCacheDao;
import com.hz.core.util.PropertiesUtil;
import com.hz.core.util.StringUtil;

/**
 * 用户登录权限验证
 * @author lch 2015-07-18
 * @version 1.1
 * 
 * 遗留问题
 * 1. 更改拦截路径后，缓存刷新问题
 *
 */
public class AccessAuthInterceptor extends HandlerInterceptorAdapter {
	private static Logger logger = LoggerFactory.getLogger(AccessAuthInterceptor.class);
	
	static{
		RedisCacheDao.delete("authLoginUrlsCache");
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestUrl = request.getServletPath();
		StringBuffer requestFullUrl = request.getRequestURL();
		User usr = (User)request.getSession().getAttribute("userInfo");
		
		// 用户已登录放行
		if (null != usr) {
			return true;
		}
		
		// 不需要拦截路径直接放行
		if (ReqUrlUnTestLoginAuth(requestUrl)) {
			return true;
		}
		
		if (!StringUtil.isEmpty(request.getQueryString())) {
			requestFullUrl.append("?").append(request.getQueryString());
		}

		response.sendRedirect(String.format("%1$s%2$s?pg=%3$s", request.getContextPath(), 
				"/user/login", URLEncoder.encode(requestFullUrl.toString(), "utf-8")));
		
		return false; 
	}
	
	/**
	 * 验证请求路径是否不需要登录直接访问
	 * @param requestUrl 请求路径
	 * @return true--可直接访问; false--登录访问
	 */
	private Boolean ReqUrlUnTestLoginAuth(String requestUrl) {
		String unAuthLoginUrls = LoadUnAuthLoginUrlList();
		
		if (StringUtil.isEmpty(unAuthLoginUrls)) {
			logger.warn("无需登录验证地址列表加载失败,可能是加载异常或管理员未配置!");
			
			return true;
		}
		
		for (String authUrl : unAuthLoginUrls.split(",")) {
			if (StringUtil.isEmpty(authUrl.trim())) {
				continue;
			}
			
			if (Pattern.compile(authUrl.trim()).matcher(requestUrl).find()) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 加载无需登录验证路径列表
	 */
	private String LoadUnAuthLoginUrlList() {
		String unAuthLoginUrlsCache = (String)RedisCacheDao.read("unAuthLoginUrlsCache");
		
		if (StringUtil.isEmpty(unAuthLoginUrlsCache)) {
			PropertiesUtil propUtil = new PropertiesUtil("authurls.properties");
			
			unAuthLoginUrlsCache = propUtil.getKeyValue("unauthurls");

			RedisCacheDao.set("unAuthLoginUrlsCache", unAuthLoginUrlsCache, 60*10);
		}
		
		return unAuthLoginUrlsCache;
	}
}