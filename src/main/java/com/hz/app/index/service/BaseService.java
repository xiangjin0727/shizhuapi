package com.hz.app.index.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hz.util.common.DbUtils;
import com.hz.util.common.JDBCUtil;

public class BaseService {

	public static JDBCUtil jdbc = new JDBCUtil();
	
	public  static HashMap<String,Object>  getMapForDb(String sql){
		HashMap<String,Object> objectHashMap = new HashMap<String,Object>();
		try {
			String coulmnName  = sql.substring(sql.indexOf("select")+7,sql.indexOf("from"));
			String[][]  resDb = DbUtils.getResultSetArray(jdbc, sql);
			if(resDb!=null && resDb.length >0){
				for(int i = 0 ; i < coulmnName.split(",").length ; i++){
					objectHashMap.put(coulmnName.split(",")[i].trim(), resDb[0][i]);
			    }
			}
			return  objectHashMap;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return  objectHashMap;
		}
		
	}
	
	public static List<HashMap<String,Object>>  getListForDb(String sql) {
		List<HashMap<String,Object>> objectList = new ArrayList<HashMap<String,Object>>();
		try {
			String coulmnName  = sql.substring(sql.indexOf("select")+7,sql.indexOf("from"));
			//System.out.println("执行的SQL：======》"+coulmnName);
			String[][]  resDb = DbUtils.getResultSetArray(jdbc, sql);
			if(resDb!=null && resDb.length >0){
				for(int k = 0 ; k < resDb.length ; k ++){
					HashMap<String,Object> objectHashMap = new HashMap<String,Object>();
					for(int i = 0 ; i < coulmnName.split(",").length ; i++){
						//System.out.println("组装SQL键：======》"+coulmnName.split(",")[i].trim());
						objectHashMap.put(coulmnName.split(",")[i].trim(), resDb[k][i]);
					}
					objectList.add(objectHashMap);
				}
			}
			//System.out.println("组装SQL：======》"+objectList);
			return  objectList;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return  objectList;
		}
	}
	
}
