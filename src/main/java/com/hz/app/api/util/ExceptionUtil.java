package com.hz.app.api.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常消息工具类，功能： 将异常对象转换为：字符串
 */

public class ExceptionUtil {

	private ExceptionUtil() {
	}

	/**
	 * 将异常堆栈消息转换成字符串
	 */
	public static String exception2String(Throwable t) {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		t.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return sw.toString();
	}

	/**
	 * 将异常堆栈消息转换成字符串
	 */
	public static String exceptionStack2String(Throwable t) {
		String error = exception2String(t);

		return error;
	}

	/**
	 * 将异常堆栈消息转换成字符串
	 */
	public static String exceptionChainToString(Throwable t) {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		while (t != null) {
			t.printStackTrace(pw);
			t = t.getCause();
		}
		pw.flush();
		sw.flush();
		return sw.toString();
	}
}