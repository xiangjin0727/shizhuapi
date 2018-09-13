package com.hz.app.index.vo;

import java.io.Serializable;
import java.util.Date;


public class User implements Serializable{//登录用户实体例
	  private static final long serialVersionUID = 171368901592546079L;
	  private String id;
	  private String depId;//部门ID
	  private String userName;//用户名称
	  private String realName;//真实姓名
	  private String sex;//性别（1男2女）
	  private Date birthday ;//出生日期
	  private Date regDate; //注册时间
	  private String mobile;//手机号
	  private String email;// 邮箱
	  private Integer loginCount;//登录次数
	  private String refferee;//推荐人(也是userid)
	  private String lastIp;//最后登录IP
	  private String lastDate;//最后登录时间
	  private String headImg;//头像
	  private Integer lockStatus;//锁定状态(1锁定，2未锁定)
	  private Date lockTime;//锁定时间
	  private Integer cashStatus;//提现状态
	  private Integer loginErrorCount;//错误登录次数
	  private String password ;//密码 MD5加密
	  private Integer isFuYou;//是否开通富友
	  private Integer isLoginLimit;//是否限制登录 0不限制1限制
	  private Integer userType;//注册来源（pc|webApp|appleApp|androidApp）
	  private String regSource;//用户类型1借款2投资
	  private String token;
	  private String staffId;
	  private String keyCode;
	  private String userToken;
	  
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	public String getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(String keyCode) {
		this.keyCode = keyCode;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	//认证表
	  private String refferType;//推荐类型  0为互联网用户1内部员工
	 private String customerId;
	 
	 
	public User(String id) {
		this.id = id;
	}
	
	
	public User(String id, String depId, String userName, String realName, String sex, Date birthday, Date regDate,
			String mobile, String email, Integer loginCount, String refferee, String lastIp, String lastDate,
			String headImg, Integer lockStatus, Date lockTime, Integer cashStatus, Integer loginErrorCount,
			String password, Integer isFuYou, Integer isLoginLimit, Integer userType, String regSource, String token,
			String refferType, String customerId) {
		super();
		this.id = id;
		this.depId = depId;
		this.userName = userName;
		this.realName = realName;
		this.sex = sex;
		this.birthday = birthday;
		this.regDate = regDate;
		this.mobile = mobile;
		this.email = email;
		this.loginCount = loginCount;
		this.refferee = refferee;
		this.lastIp = lastIp;
		this.lastDate = lastDate;
		this.headImg = headImg;
		this.lockStatus = lockStatus;
		this.lockTime = lockTime;
		this.cashStatus = cashStatus;
		this.loginErrorCount = loginErrorCount;
		this.password = password;
		this.isFuYou = isFuYou;
		this.isLoginLimit = isLoginLimit;
		this.userType = userType;
		this.regSource = regSource;
		this.token = token;
		this.refferType = refferType;
		this.customerId = customerId;
	}
	
	public User() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDepId() {
		return depId;
	}
	public void setDepId(String depId) {
		this.depId = depId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getLoginCount() {
		return loginCount;
	}
	public void setLoginCoun(Integer loginCount) {
		this.loginCount = loginCount;
	}
	
	public String getLastIp() {
		return lastIp;
	}
	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}
	public String getLastDate() {
		return lastDate;
	}
	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public Integer getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}
	public Date getLockTime() {
		return lockTime;
	}
	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}
	public Integer getCashStatus() {
		return cashStatus;
	}
	public void setCashStatus(Integer cashStatus) {
		this.cashStatus = cashStatus;
	}
	public Integer getLoginErrorCount() {
		return loginErrorCount;
	}
	public void setLoginErrorCount(Integer loginErrorCount) {
		this.loginErrorCount = loginErrorCount;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getIsFuYou() {
		return isFuYou;
	}
	public void setIsFuYou(Integer isFuYou) {
		this.isFuYou = isFuYou;
	}
	public Integer getIsLoginLimit() {
		return isLoginLimit;
	}
	public void setIsLoginLimit(Integer isLoginLimit) {
		this.isLoginLimit = isLoginLimit;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getRefferee() {
		return refferee;
	}
	public void setRefferee(String refferee) {
		this.refferee = refferee;
	}
	public String getRegSource() {
		return regSource;
	}
	public void setRegSource(String regSource) {
		this.regSource = regSource;
	}
	public String getRefferType() {
		return refferType;
	}
	public void setRefferType(String refferType) {
		this.refferType = refferType;
	}
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", depId=" + depId + ", userName=" + userName + ", realName=" + realName + ", sex="
				+ sex + ", birthday=" + birthday + ", regDate=" + regDate + ", mobile=" + mobile + ", email=" + email
				+ ", loginCount=" + loginCount + ", refferee=" + refferee + ", lastIp=" + lastIp + ", lastDate="
				+ lastDate + ", headImg=" + headImg + ", lockStatus=" + lockStatus + ", lockTime=" + lockTime
				+ ", cashStatus=" + cashStatus + ", loginErrorCount=" + loginErrorCount + ", password=" + password
				+ ", isFuYou=" + isFuYou + ", isLoginLimit=" + isLoginLimit + ", userType=" + userType + ", regSource="
				+ regSource + ", token=" + token + ", refferType=" + refferType + ", customerId=" + customerId + "]";
	}
	
	
}
