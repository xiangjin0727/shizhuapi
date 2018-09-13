package com.hz.app.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hz.app.user.vo.UserVO;

public interface UserDao {

	public UserVO searchUserByLoginName(@Param("loginName") String loginName);
	
	/**
	 * 新用户注册
	 * @param map
	 * @return
	 */
	public int registerUser(Map<String, String> map);
	/**
	 * 查询用户 是否入住 和 入住时长
	 * @param vo
	 * @return
	 */
	public Map<String, String> searchUserCheck(UserVO vo);
	/**
	 * 修改用户密码
	 * @param map
	 * @return
	 */
	public int updatePassword(Map<String, String> map);
	/**
	 * 更新用户实名认证信息
	 * @param map
	 * @return
	 */
	public int updateUserIdCardById(Map<String, String> map);
	/**
	 * 根据ID查询用户
	 * @param map
	 * @return
	 */
	public UserVO searchUserById(Map<String, String> map);
	/**
	 * 修改昵称
	 * @param map
	 * @return
	 */
	public int updateNickname(Map<String, String> map);
	/**
	 * 修改手机号
	 * @param map
	 * @return
	 */
	public int updateMobile(Map<String, String> map);
	
	
	/**
	 * 修改用户信息
	 * @param map
	 * @return
	 */
	public int updateUser(Map<String, String> map);
	/**
	 * 获取信用纬度分值（信用总分=各维度信用分之和）S0049
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getCreditDimension(Map<String, String> map);
	/**
	 * 修改用户头像
	 * @param map
	 * @return
	 */
	public int udateUserImg(Map<String, String> map);
	
	/**
	 * 跟新用户信用分
	 * @param map
	 * @return
	 */
	public int updateUserXinyong(Map<String, String> map);
	/**
	 * 跟新用户信用分记录
	 * @param map
	 * @return
	 */
	public int updateUserXinyonginfo(Map<String, String> map);
	
	/**
	 * 查询用户当前时候还有正在进行的租房订单
	 * @param map
	 * @return
	 */
	public  List<Map<String, String>> searchUserOrderNow();
	/**
	 * 跟新用户信用分 和租住时长
	 * @param map
	 * @return
	 */
	public int updateUserXinyongAndOrderTime(Map<String, String> map);
	/**
	 * 查询逾期账单
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> searchOrderBill();
	/**
	 * 增加用户信用记录
	 * @param map
	 * @return
	 */
	public int createUserScoreInfo(Map<String, String> map);
}
