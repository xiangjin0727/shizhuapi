package com.hz.app.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.SysexMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hz.app.servlet.BaseController;
import com.hz.app.user.dao.UserDao;
import com.hz.app.user.service.UserService;
import com.hz.app.user.vo.UserVO;
import com.hz.core.framework.redis.RedisCacheDao;
import com.hz.core.util.StringUtil;
import com.hz.util.IdcardInfoExtractor;
import com.hz.util.ImgBase64Util;
/**
 *  TODO 6.2 注册 S0002
 *  TODO 6.3忘记密码 S0003
 *  TODO 6.4修改密码 S0004
 *  TODO 6.5实名认证 S0005
 *  TODO 6.6登录 S0006
 *  TODO 6.7头像更换 S0007
 *  TODO 6.29退出登录 S0029
 *  TODO 6.30修改昵称 S0030
 *  TODO 6.32更换绑定手机号 S0032
 *  TODO 6.48 修改用户信息 S0048
 *  获取信用纬度分值（信用总分=各维度信用分之和）S0049
 * @author XJin
 *
 */

@Controller
@RequestMapping("user")
public class UserController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	@Autowired
	UserDao userDao;
	
	/**
	 * 注册 S0002
	 * @return
	 */
	@RequestMapping(value = "register",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> searchUser(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------注册 S0002  入参：-----{}",parametersMap.toString());
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			String mobile = String.valueOf(data.get("user_mob"));
			String vi_code = String.valueOf(data.get("verification_code"));
			String verification_code = (String) RedisCacheDao.read(REDIS_VI_K+mobile);
			
			UserVO voE = userService.searchUserByLoginName(mobile);
			
			if(voE!=null){
				resultMap.put("result_code", "0002");
				resultMap.put("result_message", "该用户已存在！");
				return resultMap;
			}
			
			if(!vi_code.equals(verification_code)){
				resultMap.put("result_code", "0001");
				resultMap.put("result_message", "验证码失效或错误！");
				return resultMap;
			}
			// 默认昵称
			Random random = new Random();
			int viCode = random.nextInt(999999);
			if(viCode<100000){
				viCode +=100000;
			}
			data.put("userNickname", "SZ"+viCode);
			userService.registerUser(data);		
			UserVO vo = userService.searchUserByLoginName(mobile);
			Map<String, String> cM = userDao.searchUserCheck(vo);
			if(cM!=null&&!StringUtil.isEmpty(String.valueOf(cM.get("check_time"))) )
				vo.setCheck_time(String.valueOf(cM.get("check_time")));
			else
				vo.setCheck_time("0");
			
			//建立用户信用分记录
			userDao.createUserScoreInfo(data);
			
			
			//第一次实名认证成功 增加信用分
			List<Map<String, String>> mapL = userDao.getCreditDimension(data);
			for(Map<String, String> map:mapL){
					map.put("user_score_info_credit2", (Integer.valueOf(String.valueOf(map.get("user_score_info_credit2")))+5)+"");
					userDao.updateUserXinyonginfo(map);		
					map.put("xinyong", "5");
					userDao.updateUserXinyong(map);
			}
			
			
			RedisCacheDao.set(vo.getUser_id(), vo, 60*24*365*60);
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "注册成功");
			resultMap.put("data", vo);
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "注册失败");
		}
		return resultMap;
	}
	/**
	 * 忘记密码 S0003
	 * @return
	 */
	@RequestMapping(value = "forgetPassword",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> forgetPassword(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------忘记密码 S0003  入参：-----{}",parametersMap.toString());
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			String mobile = String.valueOf(data.get("user_mob"));
			String vi_code = String.valueOf(data.get("verification_code"));
			String verification_code = (String) RedisCacheDao.read(REDIS_VI_K+mobile);
			
	/*		if(!vi_code.equals(verification_code)){
				resultMap.put("result_code", "0001");
				resultMap.put("result_message", "验证码错误！");
				return resultMap;
			}*/
			
			userDao.updatePassword(data);
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "忘记密码成功");
			Map<String, String> da = new HashMap<>();
			da.put("result_code", "0000");
			resultMap.put("data", da);
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "忘记密码失败");
		}
		return resultMap;
	}
	/**
	 * 修改密码 S0004
	 * @return
	 */
	@RequestMapping(value = "updatePassword",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> updatePassword(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------修改密码 S0004  入参：-----{}",parametersMap.toString());
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			String mobile = String.valueOf(data.get("user_mob"));
			String old_password = String.valueOf(data.get("old_password"));
			UserVO vo = userDao.searchUserById(data);
			data.put("user_mob", vo.getUser_mob());
			if(vo!=null && vo.getUser_login_pwd().equals(old_password)){				
				userDao.updatePassword(data);
				resultMap.put("result_code", "0000");
				resultMap.put("result_message", "修改密码成功");
				Map<String, String> da = new HashMap<>();
				da.put("result_code", "0000");
				resultMap.put("data", da);			
			}else{
				resultMap.put("result_code", "0002");
				resultMap.put("result_message", "密码错误！");
			}
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "修改密码失败");
		}
		return resultMap;
	}
	/**
	 * 实名认证 S0005
	 * @return
	 */
	@RequestMapping(value = "authentication",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> authentication(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------实名认证 S0005  入参：-----{}",parametersMap.toString());
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			String user_id = String.valueOf(data.get("user_id"));
			String user_real_name = String.valueOf(data.get("user_real_name"));
			String user_id_card = String.valueOf(data.get("user_id_card"));
			// TODO  第三方实名接口
			IdcardInfoExtractor idcardInfo=new IdcardInfoExtractor(user_id_card);
			
			if(!idcardInfo.anomalyDetection(user_id_card)){
				resultMap.put("result_code", "0003");
				resultMap.put("result_message", "实名认证失败-身份证异常");
				return resultMap;
			}
			
			data.put("user_sex", idcardInfo.getSex()+"");
			//是否是第一次市民认证
			boolean isOne = false;
			UserVO u = userDao.searchUserById(data);
			if(StringUtil.isEmpty(u.getUser_id_card())){				
				isOne = true;
			}
			
			userDao.updateUserIdCardById(data);
			
			
			//第一次实名认证成功 增加信用分
			List<Map<String, String>> mapL = userDao.getCreditDimension(data);
			for(Map<String, String> map:mapL){
				if(isOne){
					map.put("user_score_info_credit1", (Integer.valueOf(String.valueOf(map.get("user_score_info_credit1")))+5)+"");
					userDao.updateUserXinyonginfo(map);		
					map.put("xinyong", "5");
					userDao.updateUserXinyong(map);
				}
			}
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "实名认证成功");
			Map<String, String> da = new HashMap<>();
			da.put("result_code", "0000");
			resultMap.put("data", da);		
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "实名认证失败");
		}
		return resultMap;
	}

	/**
	 * 登录 S0006
	 * @return
	 */
	@RequestMapping(value = "login",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------登录 S0006  入参：-----{}",parametersMap.toString());
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			String mobile = String.valueOf(data.get("user_mob"));
			String user_login_pwd = String.valueOf(data.get("user_login_pwd"));
			UserVO vo = userService.searchUserByLoginName(mobile);
			if(vo!=null && vo.getUser_login_pwd().equals(user_login_pwd)){	
			
				RedisCacheDao.set(vo.getUser_id(), vo, 60*24*365*60);
				Map<String, String> cM = userDao.searchUserCheck(vo);
				if(cM!=null&&!StringUtil.isEmpty(String.valueOf(cM.get("check_time"))) )
					vo.setCheck_time(String.valueOf(cM.get("check_time")));
				else
					vo.setCheck_time("0");
				

				if(StringUtil.isEmpty(vo.getSex())){
					vo.setSex("0");
				}
				resultMap.put("result_code", "0000");
				resultMap.put("result_message", "登录成功");
				resultMap.put("data", vo);		
			}else{
				resultMap.put("result_code", "0001");
				resultMap.put("result_message", "登录失败");			
			}
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "登录失败");
		}
		return resultMap;
	}
	
	
	/**
	 * 更换头像 S0007
	 * @return
	 */
	@RequestMapping(value = "changeTheAvatar",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> changeTheAvatar(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------更换头像 S0007 入参：-----{}",parametersMap.toString());
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			String imgBase = String.valueOf(data.get("img_source"));

			if(StringUtil.isEmpty(imgBase)){
				resultMap.put("result_code", "0001");
				resultMap.put("result_message", "头像数据异常");
				return resultMap;
			}
			String path = request.getSession().getServletContext().getRealPath("/");
			System.err.println(path);
			String imgName = System.currentTimeMillis()+"_"+String.valueOf(data.get("user_id"));
			path = path+"image/"+imgName+".png";
			boolean isture = ImgBase64Util.GenerateImage(imgBase, path);
			if(!isture){
				resultMap.put("result_code", "0002");
				resultMap.put("result_message", "头像数据保存异常");
				return resultMap;
			}
			Map<String, String> map = new HashMap<>();
			map.put("user_id", String.valueOf(data.get("user_id")));
			String url = "http://47.104.229.248:8080/szhz/image/"+imgName+".png";
			map.put("user_img", url);
			userDao.udateUserImg(map);
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "头像数据成功");
			resultMap.put("data", map);
			return resultMap;
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "更换头像失败");
		}
		return resultMap;
	}

	
	/**
	 * 退出登录 S0029
	 * @return
	 */
	@RequestMapping(value = "signOut",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> signOut(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------退出登录 S0029 入参：-----{}",parametersMap.toString());
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			String userId = String.valueOf(data.get("user_id"));
			RedisCacheDao.delete(userId);
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "退出登录成功");
			Map<String, String> da = new HashMap<>();
			da.put("result_code", "0000");
			resultMap.put("data", da);	
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "退出登录失败");
		}
		return resultMap;
	}
	
	/**
	 * 修改昵称 S00030
	 * @return
	 */
	@RequestMapping(value = "updateNickname",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> updateNickname(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------修改昵称 S00030 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			userDao.updateNickname(data);
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "修改成功");
			Map<String, String> da = new HashMap<>();
			da.put("result_code", "0000");
			resultMap.put("data", da);	
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "退修改昵称失败");
		}
		return resultMap;
	}
	/**
	 * 更换绑定手机号 S0032
	 * @return
	 */
	@RequestMapping(value = "updateMobile",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> updateMobile(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------更换绑定手机号 S00032 入参：-----{}",parametersMap.toString());
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			String old_mobile = String.valueOf(data.get("old_mobile"));
			String verification_code = String.valueOf(data.get("verification_code"));
			String userId = String.valueOf(data.get("user_id"));
			UserVO v =  (UserVO) RedisCacheDao.getObjByKey(userId);
			if(StringUtil.isEmpty(v.getUser_id_card())||StringUtil.isEmpty(data.get("user_id_card"))){
				resultMap.put("result_code", "0001");
				resultMap.put("result_message", "身份证号错误！");
				return resultMap;
			}
			if(!String.valueOf(data.get("user_id_card")).equals(v.getUser_id_card())){
				resultMap.put("result_code", "0001");
				resultMap.put("result_message", "身份证号错误！");
				return resultMap;
			}
			String v_co = (String)RedisCacheDao.read(REDIS_VI_K+old_mobile);
		    if(!verification_code.equals(v_co)){
				resultMap.put("result_code", "0001");
				resultMap.put("result_message", "验证码失效或错误！");
				return resultMap;
		    }
		 	
		    
			userDao.updateMobile(data);		
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "修改成功");
			Map<String, String> da = new HashMap<>();
			da.put("result_code", "0000");
			resultMap.put("data", da);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "更换绑定手机号失败");
		}
		return resultMap;
	}
	
	
	/**
	 * 修改用户信息 S0048
	 * @return
	 */
	@RequestMapping(value = "updateUser",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> updateUser(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------修改用户信息 S0048 入参：-----{}",parametersMap.toString());
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			
			//是否是第一次修改信息
			boolean isOne = false;
			UserVO u = userDao.searchUserById(data);

			int success = userDao.updateUser(data);	
			if(success>0){
				if(StringUtil.isEmpty(u.getUser_schoole())&&StringUtil.isEmpty(u.getUser_admission_time())
						&&StringUtil.isEmpty(u.getUser_graduation_time())&&StringUtil.isEmpty(u.getUser_education_type())&&StringUtil.isEmpty(u.getUser_education())	){				
						if(!StringUtil.isEmpty(data.get("user_education"))){
							isOne = true;
						}
						//第一次实名认证成功 增加信用分
						List<Map<String, String>> mapL = userDao.getCreditDimension(data);
						for(Map<String, String> map:mapL){
							if(isOne){
								map.put("user_score_info_credit1", (Integer.valueOf(String.valueOf(map.get("user_score_info_credit1")))+5)+"");
								userDao.updateUserXinyonginfo(map);		
								map.put("xinyong", "5");
								userDao.updateUserXinyong(map);
							}
						}
						isOne =false;
				}
				
				if(StringUtil.isEmpty(u.getUser_company_address())&&StringUtil.isEmpty(u.getUser_company_name())
						&&StringUtil.isEmpty(u.getUser_industry())){				
						if(!StringUtil.isEmpty(data.get("user_company_name"))){
							isOne = true;
						}
						//第一次实名认证成功 增加信用分
						List<Map<String, String>> mapL = userDao.getCreditDimension(data);
						for(Map<String, String> map:mapL){
							if(isOne){
								map.put("user_score_info_credit1", (Integer.valueOf(String.valueOf(map.get("user_score_info_credit1")))+5)+"");
								userDao.updateUserXinyonginfo(map);		
								map.put("xinyong", "5");
								userDao.updateUserXinyong(map);
							}
						}
				}
				

			
			}
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "修改成功");
			Map<String, String> da = new HashMap<>();
			da.put("result_code", "0000");
			resultMap.put("data", da);
			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "修改用户信息失败");
		}
		return resultMap;
	}

	
	/**
	 * 获取信用纬度分值（信用总分=各维度信用分之和）S0049
	 * @return
	 */
	@RequestMapping(value = "getCreditDimension",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> getCreditDimension(HttpServletRequest request, HttpServletResponse reponse){
		Map<String, Object> resultMap = new HashMap<>();
		try{
			Map<String, Object> parametersMap = getParameters(request);
			logger.debug("------获取信用纬度分值（信用总分=各维度信用分之和）S0049 入参：-----{}",parametersMap.toString());
			Map<String, String> data = (Map<String, String>) parametersMap.get("data");
			List<Map<String, String>> ml = userDao.getCreditDimension(data);
			
			resultMap.put("result_code", "0000");
			resultMap.put("result_message", "获取信用纬度分值（信用总分=各维度信用分之和）成功");
			resultMap.put("data", ml);			
		} catch (Exception e) {
			logger.error("系统异常:{}",e);
			resultMap.put("result_code", "0002");
			resultMap.put("result_message", "获取信用纬度分值（信用总分=各维度信用分之和）失败");
		}
		return resultMap;
	}
	
	
}
