package com.hz.app.api.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hz.app.api.util.security.JsonTransformer;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FlexJson封装工具类
 */
public class JsonUtil {
	private static ObjectMapper mapper = new ObjectMapper();

	@SuppressWarnings("unchecked")
	public static Map<String, Object> convJsonToMap(String json,
			boolean removeClassAttr) {
		if (removeClassAttr) {
			json = removeClassAttr(json);
		}

		JSONDeserializer<List<Map<String, Object>>> deserializer = new JSONDeserializer<List<Map<String, Object>>>();
		Map<String, Object> map = (Map<String, Object>) deserializer.use(null,
				HashMap.class).deserialize(json);

		return map;
	}

	public static <T> T josn2Object(String json, Class<T> clazz) {
		JSONDeserializer<T> deserializer = new JSONDeserializer<T>();
		T rsp = deserializer.use(null, clazz).deserialize(json);
		return rsp;
	}

	/**
	 * only handling one same class attribute other use should retry the logic.
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

	/**
	 * 将对象转换成json字符串 指定需要排除的属性
	 */
	public static String jsonExcEncode(Object obj, String... excPropNames) {
		JSONSerializer serializer = new JSONSerializer();
		batchExc(serializer, excPropNames);
		return serializer./* exclude("*.class"). */serialize(obj);
	}

	/**
	 * 将对象转换成json字符串
	 */
	public static String jsonExcEncode(Object obj, String[] excPropNames,
			String[] secPropNames) {
		JSONSerializer serializer = new JSONSerializer();
		batchExc(serializer, excPropNames);
		batchSec(serializer, secPropNames);
		return serializer./* exclude("*.class"). */serialize(obj);
	}

	/**
	 * 将对象转换成json字符串 指定需要排除的属性 深层序列化
	 */
	public static String jsonExcDeepEncode(Object obj, String... excPropNames) {
		JSONSerializer serializer = new JSONSerializer();
		batchExc(serializer, excPropNames);
		return serializer./* exclude("*.class"). */deepSerialize(obj);
	}

	/**
	 * 将对象转换成json字符串
	 */
	public static String jsonExcDeepEncode(Object obj, String[] excPropNames,
			String[] secPropNames) {
		JSONSerializer serializer = new JSONSerializer();
		batchExc(serializer, excPropNames);
		batchSec(serializer, secPropNames);
		return serializer./* exclude("*.class"). */deepSerialize(obj);
	}

	/**
	 * 将对象转换成json字符串 指定需要序列化的属性
	 */
	public static String jsonIncEncode(Object obj, String... incPropNames) {
		JSONSerializer serializer = new JSONSerializer();
		batchInc(serializer, incPropNames);
		serializer.exclude("*");
		return serializer.serialize(obj);
	}

	/**
	 * 将对象转换成json字符串
	 */
	public static String jsonIncEncode(Object obj, String[] incPropNames,
			String[] secPropNames) {
		JSONSerializer serializer = new JSONSerializer();
		batchInc(serializer, incPropNames);
		batchSec(serializer, secPropNames);
		serializer.exclude("*");
		return serializer.serialize(obj);
	}

	/**
	 * 将对象转换成json字符串 指定需要序列化的属性 深层序列化
	 */
	public static String jsonIncDeepEncode(Object obj, String... incPropNames) {
		JSONSerializer serializer = new JSONSerializer();
		batchInc(serializer, incPropNames);
		serializer.exclude("*");
		return serializer.deepSerialize(obj);
	}

	/**
	 * 将对象转换成json字符串(深层)
	 */
	public static String jsonIncDeepEncode(Object obj, String[] incPropNames,
			String[] secPropNames) {
		JSONSerializer serializer = new JSONSerializer();
		batchInc(serializer, incPropNames);
		batchSec(serializer, secPropNames);
		serializer.exclude("*");
		return serializer.deepSerialize(obj);
	}

	/**
	 * 将Json字符串转换为mapList
	 */
	public static List<Map<String, Object>> convJsonToMapList(String json) {
		JSONDeserializer<List<Map<String, Object>>> deserializer = new JSONDeserializer<List<Map<String, Object>>>();
		List<Map<String, Object>> mapList = deserializer.use(null,
				ArrayList.class).deserialize(json);

		return mapList;
	}

	/**
	 * 将Json字符串转换为map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> convJsonToMap(String json) {
		JSONDeserializer<List<Map<String, String>>> deserializer = new JSONDeserializer<List<Map<String, String>>>();
		Map<String, String> map = (Map<String, String>) deserializer.use(null,
				HashMap.class).deserialize(json);

		return map;
	}
	/**
	 * 将Json字符串转换为map object
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> convJsonToMapO(String json) {
		JSONDeserializer<List<Map<String, Object>>> deserializer = new JSONDeserializer<List<Map<String, Object>>>();
		Map<String, Object> map = (Map<String, Object>) deserializer.use(null,
				HashMap.class).deserialize(json);

		return map;
	}
	/**
	 * Description: 批量排除属性
	 */
	private static void batchExc(JSONSerializer serializer,
			String... excPropNames) {
		if (null != excPropNames && excPropNames.length > 0) {
			for (String propName : excPropNames) {
				if (null != propName && propName.trim().length() > 0)
					serializer.exclude("*." + propName);
			}
		}
	}

	/**
	 * 批量包含属性
	 */
	private static void batchInc(JSONSerializer serializer,
			String... incPropNames) {
		if (null != incPropNames && incPropNames.length > 0) {
			for (String propName : incPropNames) {
				if (null != propName && propName.trim().length() > 0)
					serializer.include("*." + propName);
			}
		}
	}

	/**
	 * 批量设置要安全过滤的属性
	 */
	private static void batchSec(JSONSerializer serializer,
			String... secPropNames) {
		if (null != secPropNames && secPropNames.length > 0) {
			serializer.transform(new JsonTransformer(), secPropNames);
		}
	}
	
	public static String writeJSON(Object o){
		String resultJson ="";
		try {
			resultJson = mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {			
			e.printStackTrace();
		}
		return resultJson;
	}
	
}
