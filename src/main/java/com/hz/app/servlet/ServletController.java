package com.hz.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hz.app.api.NoTransServer;
import com.hz.app.api.ServletEnum;
import com.hz.app.api.util.JsonUtil;
import com.hz.app.user.vo.UserVO;
import com.hz.core.framework.redis.RedisCacheDao;
import com.hz.util.common.ResMsgUtils;
import com.hz.util.common.ServletReqUtils;
/**
 * mobileAPP 入口
 * @author XJin
 *
 */

@Controller
@RequestMapping("mobile")
public class ServletController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ServletController.class);
	
	
	private static final String CONTENT_TYPE="text/json;charset=UTF-8";
	@RequestMapping(value = "doServlet", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	public void doServlet(HttpServletRequest request,HttpServletResponse reponse) throws IOException{
		reponse.setContentType(CONTENT_TYPE);	
		PrintWriter out = reponse.getWriter();
		String reqMsg = "";
		String serviceId = "";
		String version = "";
		try {
			reqMsg = ServletReqUtils.getRequestStr(request);
			System.err.println("接收到的值："+reqMsg);
			Map<String, Object> paraMap = JsonUtil.convJsonToMapO(reqMsg);
			serviceId = String.valueOf(paraMap.get("fid"));
			version = String.valueOf(paraMap.get("version"));
			Map<String, String> data = (Map<String, String>) paraMap.get("data");
			String userId = String.valueOf(data.get("user_id"));
			int type = ServletEnum.valueOf((String)serviceId).getType();
			if(type==1){
				String s = RedisCacheDao.getObjByKey(userId)+"";
				System.out.println("======================"+s);
				UserVO v =  (UserVO) RedisCacheDao.getObjByKey(userId);
				if(v==null){
					out.print(ResMsgUtils.resErrorMsg(serviceId,"用户未登录"));
					return;
				}
			}
			String resultMsg = NoTransServer.reqHttpData(serviceId, reqMsg, request, version);			
			out.print(resultMsg);
		} catch (Exception e) {
			logger.error("APP Server 异常：{}",e);
			out.print(ResMsgUtils.resErrorMsg(serviceId,"网络超时，请稍后再试"));
		}
		
		
	}
}
