package com.hz.app.error.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hz.app.error.service.ErrorService;
import com.hz.core.util.StringUtil;


/**
 * 异常消息推送
 * @ClassName ErrorController
 * @Description TODO(异常消息推送)
 * @author ysj
 * @Date 2017年9月9日 下午2:24:20
 * @version 1.0.0
 */
@Controller
@RequestMapping("forError")
public class ErrorController {
	
	/**
	 * 刷新错误码
	 * @Description (TODO 刷新错误码)
	 * @return
	 */
	@RequestMapping("flush")
	public Map<String,Object> flush(String key,String errorData){
		Map<String,Object> resMap = new HashMap<String,Object>();
		if(StringUtil.requals("8eb29f3e5abe415ebd09cce8584141e0", key)){
			JSONObject obj = JSONObject.parseObject(errorData);
			if(ErrorService.FLUSH_DATA(obj)){
				resMap.put("success", true);
			}else{
				resMap.put("success", false);
				resMap.put("msg", "put msg error!");
			}
		}else{
			resMap.put("success", false);
			resMap.put("msg", "key is error!");
		}
		return resMap;
	}
	
}
