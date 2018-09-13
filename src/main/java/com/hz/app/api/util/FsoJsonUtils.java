package com.hz.app.api.util;

import flexjson.JSONDeserializer;

public class FsoJsonUtils {

	public static <T> T josn2Object(String json, Class<T> clazz) {
		JSONDeserializer<T> deserializer = new JSONDeserializer<T>();
		T rsp = deserializer.use(null, clazz).deserialize(json);
		return rsp;
	}

	/**
	 * only handling one same class attribute
	 * other use should retry the logic.
	 * @param json
	 * @param clazz
	 * @param removeClassAttr
	 * @return
	 */
	public static <T> T josn2Object(String json, Class<T> clazz,
			boolean removeClassAttr) {
		if (removeClassAttr) {
			json = removeClassAttr(json);
		}
		return josn2Object(json, clazz);
	}

	private static String removeClassAttr(String json) {

		int i = json.indexOf("\"class\":");
		if (i != -1) {
			int j = json.indexOf(",", i + 1);
			char c = json.charAt(j);

			String subString = "";

			if ((c + "").equals(",")) {
				subString = json.substring(i, j + 1);
			} else if ((c + "").equals("}")) {
				subString = json.substring(i - 1, j);
			}
			return json.replaceAll(subString, "");
		}
		return json;
	}
}
