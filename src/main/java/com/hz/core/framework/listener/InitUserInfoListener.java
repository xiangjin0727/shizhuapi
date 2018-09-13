package com.hz.core.framework.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 初始化及销毁用户的配置信息
 */
public class InitUserInfoListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent evt) {
		
			return;
	}

	public void sessionDestroyed(HttpSessionEvent evt) {

	}

}
