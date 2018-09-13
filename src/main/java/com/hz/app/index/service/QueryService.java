package com.hz.app.index.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface QueryService {

	public  HashMap<String,Object> getZjMap(Map<String, Object> map);
	
	public  HashMap<String,Object> getZcMap(Map<String, Object> map);
	
	public  HashMap<String,Object> getOtherMap(Map<String, Object> map);
	
	public  List<HashMap<String,Object>> getTjList(Map<String, Object> map);
	
	public  List<HashMap<String,Object>> getZjListByKey(Map<String, Object> map);
	
	public  List<HashMap<String,Object>> getZcListByKey(Map<String, Object> map);
	
	public  List<HashMap<String,Object>> getOtherListByKey(Map<String, Object> map);
	
    public  List<HashMap<String,Object>> getZjList(Map<String, Object> map);
	
	public  List<HashMap<String,Object>> getZcList(Map<String, Object> map);
	
	public  List<HashMap<String,Object>> getOtherList(Map<String, Object> map);
	
	public  List<HashMap<String,Object>> getKeyWordsList(Map<String, Object> map);
	
	public  int insertKeyWords(Map<String, Object> map);
	
	public  int countKeyWords(Map<String, Object> map);
	
}
