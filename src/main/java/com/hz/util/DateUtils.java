package com.hz.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 格式化日期
	 */
	public static String formatDate(Date date, String pattern) {
		if(null == date)
			return null;
		SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);
		return dateFormator.format(date);
	}
	
	/**
	 * 计算出date day天之前或之后的日期
	 * @param date 日期
	 * @param date 天数，正数为向后几天，负数为向前几天
	 * @return 返回Date日期类型
	 */
	public static Date getDateBeforeOrAfterDays(Date date, int days) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + days);
		return now.getTime();
	}
}
