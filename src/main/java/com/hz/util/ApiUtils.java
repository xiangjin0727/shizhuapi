package com.hz.util;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import com.hz.constant.CodeEnum;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 统一处理返回结果
 * @Author: evan(nieyanhui)
 * @Create Date: 2015年8月5日上午10:39:01
 * @Version: V1.00 （版本号）
 */
public class ApiUtils {
	/**
     * 获取返回结果
     * @param codeEnum
	 * @param financeProductMap 
     * @return
     */
    public static String getResult(CodeEnum codeEnum, Map<String, Object> financeProductMap) {
    	JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Timestamp.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
    	Map<String, Object> mapResult = new HashMap<String,Object>();
        mapResult.put("Code", codeEnum.getIndex());
        mapResult.put("Message", codeEnum.getName());
        mapResult.put("Body", financeProductMap);
        String strResult = JSONObject.fromObject(mapResult,config).toString();
        return strResult;
    }
    
    /**
     * @param pageZise  每页显示条数
     * @param pageConut 共多少条
     * @param page      传入的页码
     * @return
     */
    public static boolean checkPage(int pageZise, int pageConut, int page) {
        if (pageConut > 0) {
            int i = getPageCount(pageConut, pageZise);
            if (page <= i)
                return true;
        }
        return false;
    }
    
    public static int getPageCount(int intCount, int intSize) {
        int intPageCount = intCount / intSize;
        return intPageCount + (intCount % intSize == 0 ? 0 : 1);
    }
    
    public static boolean checkPwd(String pwd){
    	if(pwd.matches("[0-9A-Za-z\\,\\.\\;\\:\"\\'\\!\\@\\~\\#\\$\\%\\^\\&\\*\\(\\[\\\\/\\?\\>\\<\\+\\-\\_\\)]*")){
  		  return true;
  		}
    	return false;
    }
    
    public static void main(String[] args) {
    	System.out.println(checkPwd("`````````"));
	}
}
