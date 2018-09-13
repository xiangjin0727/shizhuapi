package com.hz.core.framework.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class CommonExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest req,
			HttpServletResponse resp, Object handler, Exception ex) {
		ex.printStackTrace();
		Map<String,String> model = new HashMap<String,String>();
		if(ex instanceof TimeOutException){
			model.put("message", "session_timeout_status");
		}else if(ex instanceof NoAccessException){
			model.put("message", "你没有操作该页面的权限！");
		}
//		else if(ex instanceof MaxUploadSizeExceededException){
//			model.put("message", JSONObject.toJSONString(new CommonResultVo(false,"请上传小于10M的附件！","TOO_MAX_ATTACHMENT_FILE","")));
//		}else{
//			model.put("message", JSONObject.toJSONString(new CommonResultVo(false,"处理操作失败，出现异常,请检查网络！")));
//		}
		return new ModelAndView("common/exception",model);
	}
}
