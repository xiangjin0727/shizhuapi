package com.hz.app.user.service;

import java.util.Map;

import com.hz.app.user.vo.UserVO;


public interface UserService {
	public UserVO searchUserByLoginName(String loginName);
	/**
	 * 新用户注册
	 * @param map
	 * @return
	 */
	public int registerUser(Map<String, String> map);
}
