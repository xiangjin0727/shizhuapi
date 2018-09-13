package com.hz.app.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hz.app.user.dao.UserDao;
import com.hz.app.user.vo.UserVO;

public class Task {

	private static Logger logger = LoggerFactory.getLogger(Task.class);
	
	@Autowired
	UserDao userdao;
	public void doXinyon(){
		//查询出先还在租房的用户
		List<Map<String, String>> map = userdao.searchUserOrderNow();
		//查询出逾期3天者
		List<Map<String, String>> map_ = userdao.searchOrderBill();
		for(Map<String, String> m:map){
			
			m.put("xinyong", "0");
			m.put("time", "1");
			userdao.updateUserXinyongAndOrderTime(m);
			UserVO v= new UserVO();
			v.setUser_id(String.valueOf(m.get("user_id")));
			Map<String, String> m_time = userdao.searchUserCheck(v);
			int tim = Integer.valueOf(String.valueOf(m_time.get("check_time")));
			if(tim>0&&tim/365>=1&&tim%365==0){
				if(tim/365==1){
					m.put("xinyong", "5");
				}else if(tim/365==2){
					m.put("xinyong", "10");
				}else if(tim/365==3){
					m.put("xinyong", "20");
				}else if(tim/365>3){
					m.put("xinyong", "20");
				}
				
			}
			m.put("time", "0");
			userdao.updateUserXinyongAndOrderTime(m);
		}
		for(Map<String, String> m:map_){
			m.put("xinyong", "-2");
			m.put("time", "0");
			userdao.updateUserXinyongAndOrderTime(m);
		}
	}
	
	public static void main(String[] args) {
		int tim = 365*2;
		Map<String, String> m = new HashMap<String, String>();
		
		if(tim>0&&tim/365>=1&&tim%365==0){
			if(tim/365==1){
				m.put("xinyong", "5");
			}else if(tim/365==2){
				m.put("xinyong", "10");
			}else if(tim/365==3){
				m.put("xinyong", "20");
			}else if(tim/365>3){
				m.put("xinyong", "20");
			}
			
		}
		System.err.println(m.get("xinyong"));
	}
}
