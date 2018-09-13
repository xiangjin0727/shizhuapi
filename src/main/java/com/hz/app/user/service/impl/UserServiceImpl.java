package com.hz.app.user.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hz.app.user.dao.UserDao;
import com.hz.app.user.service.UserService;
import com.hz.app.user.vo.UserVO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	@Override
	public UserVO searchUserByLoginName(String loginName) {
		UserVO userVo = userDao.searchUserByLoginName(loginName);
		return userVo;
	}
	@Override
	public int registerUser(Map<String, String> map) {
		int resultI = userDao.registerUser(map);
		return 0;
	}

}
