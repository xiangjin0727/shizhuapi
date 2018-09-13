package com.hz.core.framework.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;


public class InitSpringAndConfigUtilListener implements ServletContextListener {
	private Logger log = Logger.getLogger(getClass());

	public void contextDestroyed(ServletContextEvent evt) {
		log.info("开始关闭InitSpringAndConfigUtilListener...");
		//ConfigUtil.getConfig().clear();
		log.info("完成关闭InitSpringAndConfigUtilListener...");
	}

	public void contextInitialized(ServletContextEvent evt) {

		log.info("开始初始化Spring Util...");
		ServletContext ctx = evt.getServletContext();
		//SpringUtil.init(ctx);
		String webRootAbsPath = evt.getServletContext().getRealPath("/");
		log.info("web root abs path=" + webRootAbsPath);
		//SpringUtil.webRootAbsPath = webRootAbsPath;
		log.info("Spring Util初始化完成.");

	}

}
