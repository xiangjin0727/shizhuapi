package com.hz.app.sms;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hz.app.api.util.JsonUtil;
import com.hz.app.servlet.BaseController;
import com.hz.core.framework.redis.RedisCacheDao;

//TODO    6.1 发送短信 S0001

@Controller
@RequestMapping("sms")
public class SMSController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(SMSController.class);
	
	@RequestMapping(value = "doSms",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map doSms(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------发送短信 S0001  入参：-----{}",parametersMap.toString());
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			String mobile = String.valueOf(data.get("user_mob"));
			//TODO 发送短信
			
			logger.debug("data:{}",data.toString());
			Random random = new Random();
			int viCode = random.nextInt(999999);
			if(viCode<100000){
				viCode +=100000;
			}
			
			
			
			
			
			// 短信验证码 5分钟有效时间
			RedisCacheDao.set(REDIS_VI_K+mobile, viCode+"", 5*60);
			
			
			Map<String, String> map = new HashMap<String, String>();
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "验证码发送成功");
			map.put("verification_code", viCode+"");
			resultMap.put("data", map);
		} catch (Exception e) {

			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "验证码发送失败");
		}
		return resultMap; 
	}
	
	public static void main(String[] args) {
		Map<String, Object> m = new HashMap<>();
		m.put("fid", "S0001");
		Map<String, String> mp = new HashMap<>();
		mp.put("user_mob", "15010735572");
		mp.put("sms_type", "1");
		m.put("data", mp);
		
		System.out.println(JsonUtil.writeJSON(m));
		
	}
}
