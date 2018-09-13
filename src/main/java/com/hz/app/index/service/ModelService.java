package com.hz.app.index.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hz.app.index.vo.User;

public interface ModelService {

	//登录获取用户对象
	public  User loginByUser(Map<String, Object> map);
	
	public  User wchatUser(Map<String, Object> map);
	
	public  String getOpenId(Map<String, Object> map);
	
	public  String getMobile(Map<String, Object> map);
	//注册
	public  int register(Map<String, Object> map);
	//重置密码
	public  int resetPassword(Map<String, Object> map);
	//更新密码
	public  int updatePassword(Map<String, Object> map);
	//设置隐私策略
	public  int setYscl(Map<String, Object> map);
	//关注
	public  int focus(Map<String, Object> map);
	//存在关注
	public  boolean existFocus(Map<String, String> map);
	//获取隐私策略
	public  HashMap<String,Object> getYscl(Map<String, Object> map);
	//行业报告详情
	public  HashMap<String,Object> hyreportInfo(Map<String, Object> map);
	//微信详情
	public  HashMap<String,Object> getChatInfo(Map<String, Object> map);
	//用户信息
	public  HashMap<String,Object> getUserInfo(Map<String, Object> map);
	//更新用户信息
	public  int updateUserInfo(Map<String, Object> map);
	
	public  int updateUserInfoNoDay(Map<String, Object> map);
	//更新用户信息
	public  int getLogById(Map<String, Object> map);
	
	public  int getLogByIdNoDay(Map<String, Object> map);
	//判断
	public  int queryUserInfo(Map<String, Object> map);
	
	public  int queryUserInfoNoDay(Map<String, Object> map);
	//用户信息
	public  HashMap<String,Object> userInfo(Map<String, Object> map);
	//发布者信息
	public  HashMap<String,Object> userInfoOther(Map<String, Object> map);
	//资金资产详情
	public  HashMap<String,Object> zjzcInfo(Map<String, Object> map);
	//消息详情
	public  HashMap<String,Object> messageInfo(Map<String, Object> map);
	//圈子列表
	public  List<HashMap<String,Object>> qList(Map<String, Object> map);
	//关注列表
	public  List<HashMap<String,Object>> focusList(Map<String, Object> map);
	//消息列表
	public  List<HashMap<String,Object>> messageList(Map<String, Object> map);
	//banner列表
	public  List<HashMap<String,Object>> bannerList(Map<String, Object> map);
	//行业报告列表
	public  List<HashMap<String,Object>> hyreportList(Map<String, Object> map);
	//行业资讯列表
	public  List<HashMap<String,Object>> hyzxList(Map<String, Object> map);
	
	public  int hyreportCount(Map<String, Object> map);
	//资金资产List
	public  List<HashMap<String,Object>> zjzcList(Map<String, Object> map);
	//总数
	public int qListCount(Map<String, Object> map);
	//支付回调入库
	public int payResDb(Map<String, String> map);
	
}
