package com.hz.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 将Map中的value转为String
 * @Author: evan(nieyanhui)
 * @Create Date: 2015年8月7日下午6:00:34
 * @Version: V1.00 （版本号）
 */
public class MapToStringUtil {
	/**
	 * 集合中的map值转成字符串
	 * @throws ParseException 
	 * @Create Date: 2015年8月7日下午6:25:19
	 * @Version: V1.00 （版本号）
	 * @Parameters: MapToStringUtil
	 * @Return: List<Map<String,Object>>
	 */
	public static List<Map<String,Object>> mapListToString(List<Map<String,Object>> list){
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			if(IsObjectNullUtils.is(list)){
				return null;
			}
			Iterator<Map<String, Object>> iterator = list.iterator();
			while(iterator.hasNext()){
				Map<String, Object> map = iterator.next();
				Set<String> keySet = map.keySet();
				Iterator<String> iterator2 = keySet.iterator();
				while(iterator2.hasNext()){
					String key = iterator2.next();
					if(key.equals("Date")){
						Date date = format.parse(String.valueOf(map.get(key)));
						String date2 = format.format(date);
						map.put(key,date2);
					}else if(key.equals("DateTime")){
						Date date = format1.parse(String.valueOf(map.get(key)));
						String date2 = format1.format(date);
						map.put(key,date2);
					}else{
						map.put(key, String.valueOf(map.get(key)));
					}
				}
			}
			return list;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * map中的值转为字符串
	 * @throws ParseException 
	 * @Create Date: 2015年8月7日下午6:25:49
	 * @Version: V1.00 （版本号）
	 * @Parameters: MapToStringUtil
	 * @Return: Map<String,Object>
	 */
	public static Map<String,Object> mapToString(Map<String,Object> map){
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(IsObjectNullUtils.is(map)){
				return null;
			}
			Set<String> keySet = map.keySet();
			Iterator<String> iterator2 = keySet.iterator();
			while(iterator2.hasNext()){
				String key = iterator2.next();
				if(key.equals("Date")){
					Date date2 = format.parse(String.valueOf(map.get(key)));
					String date = format.format(date2);
					map.put(key,date);
				}else{
					map.put(key, String.valueOf(map.get(key)));
				}
			}
			return map;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
}
