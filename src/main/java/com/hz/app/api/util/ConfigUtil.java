package com.hz.app.api.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigUtil {

	private static Map<String, Object> config = new ConcurrentHashMap<String, Object>(); // ?

	public static void put(String key, Object value) {
		config.put(key, value);
	}

	public static Object get(String key) {
		return config.get(key);
	}

	public static Map<String, Object> getConfig() {
		return config;
	}


}
