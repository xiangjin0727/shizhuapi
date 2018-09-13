package com.hz.app.api.util.security;

import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 文本过滤器
 */
public class TextFilter {
	private final static Map<Integer, TextFilteStratege> DEFAULT_STRATEGE;
	private final static String FILTE_CHAR = "*";
	private final static int MAX_LEN = 12;// 策略定义最大长度

	static {
		DEFAULT_STRATEGE = new ConcurrentHashMap<Integer, TextFilteStratege>();
		updateStratege();
	}

	/**
	 * 按标准策略执行过滤
	 */
	public static String doFilte(String text) {
		if (null == text || text.isEmpty()) {
			return text;
		}
		text = text.trim();
		int len = text.length();
		TextFilteStratege tfs = DEFAULT_STRATEGE.get(len);
		if (null == tfs) {
			TextFilteStratege maxTfs = DEFAULT_STRATEGE.get(MAX_LEN);
			tfs = new TextFilteStratege(len, maxTfs.getStart(), maxTfs.getEnd());
			DEFAULT_STRATEGE.put(len, tfs);
		}

		// log.debug("tfs="+tfs);
		String repStr = StringUtils.repeat(FILTE_CHAR,
				tfs.getRealEnd() - tfs.getStart());
		return new StringBuffer(text).replace(tfs.getStart(), tfs.getRealEnd(),
				repStr).toString();
	}

	/**
	 * 按指定的策略过滤
	 */
	public static String doFilte(String text, int start, int end) {
		if (null == text || text.isEmpty()) {
			return text;
		}
		text = text.trim();
		int len = text.length();
		TextFilteStratege tfs = new TextFilteStratege(len, start, end);
		String repStr = StringUtils.repeat(FILTE_CHAR,
				tfs.getRealEnd() - tfs.getStart());

		return new StringBuffer(text).replace(tfs.getStart(), tfs.getRealEnd(),
				repStr).toString();
	}

	/**
	 * 更新过滤策略
	 */
	public static synchronized void updateStratege() {
		DEFAULT_STRATEGE.put(1, new TextFilteStratege(1, 0, 1));// 全过滤
		DEFAULT_STRATEGE.put(2, new TextFilteStratege(2, 0, -1));// 留末1
		DEFAULT_STRATEGE.put(3, new TextFilteStratege(3, 0, -2));// 留末2
		DEFAULT_STRATEGE.put(4, new TextFilteStratege(4, 1, -1));// 留首1末1
		DEFAULT_STRATEGE.put(5, new TextFilteStratege(5, 1, -2));// 留首1末2
		DEFAULT_STRATEGE.put(6, new TextFilteStratege(6, 2, -2));// 留首2末2
		DEFAULT_STRATEGE.put(7, new TextFilteStratege(7, 2, -3));// 留首2末3
		DEFAULT_STRATEGE.put(8, new TextFilteStratege(8, 3, -3));// 留首3末3
		DEFAULT_STRATEGE.put(9, new TextFilteStratege(9, 3, -3));// 留首3末3
		DEFAULT_STRATEGE.put(10, new TextFilteStratege(10, 3, -4));// 留首3末4
		DEFAULT_STRATEGE.put(11, new TextFilteStratege(11, 3, -4));// 留首3末4
		DEFAULT_STRATEGE.put(MAX_LEN, new TextFilteStratege(12, 4, -4));// 留首4末4

	}
}
