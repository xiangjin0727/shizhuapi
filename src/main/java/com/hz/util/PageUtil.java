package com.hz.util;

import java.util.Map;

import com.hz.constant.PageConstant;

/**
 * 提取分页信息
 * @Author: evan(nieyanhui)
 * @Create Date: 2015年8月10日下午5:02:41
 * @Version: V1.00 （版本号）
 */
public class PageUtil {
	public static Map<String,Object> getPageInfo(Map<String,Object> parametersMap){
		int pageNo=PageConstant.PAGE_NO;
		if(!IsObjectNullUtils.is(parametersMap.get("PageIndex"))){
			pageNo=Integer.valueOf(parametersMap.get("PageIndex").toString());
		}
		parametersMap.put("PageIndex", pageNo);
		
		int pageSize=PageConstant.PAGE_SIZE;
		if(!IsObjectNullUtils.is(parametersMap.get("PageSize"))){
			pageSize=Integer.valueOf(parametersMap.get("PageSize").toString());
		}
		parametersMap.put("PageSize", pageSize);
		return parametersMap;
	}
}
