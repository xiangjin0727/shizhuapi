package com.hz.app.index.service;

import java.util.HashMap;
import java.util.Map;

public interface PublishService {

	public  int insertZj(Map<String, Object> map);
	
	public  int insertZc(Map<String, Object> map);

	public  int insertOther(Map<String, Object> map);
	
	public  int uploadCompanyInfo(Map<String, Object> map);
	
	public  int uploadComplain(Map<String, Object> map);
	
	public  HashMap<String,Object> getCompanyInfoByUserId(Map<String, Object> map);
	
	public  HashMap<String,Object> getCompanyInfoById(Map<String, Object> map);
	
}
