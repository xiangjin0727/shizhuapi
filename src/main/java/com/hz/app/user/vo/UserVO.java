package com.hz.app.user.vo;

import java.io.Serializable;

public class UserVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String user_id;
	private String user_nickname; //用户昵称
	private String user_img; //用户头像
	private String user_login_name; //登录名
	private String user_login_pwd; //登录密码
	private String user_mob; //手机号
	private String user_email; //邮箱
	private String user_real_name; //真是姓名
	private String user_industry; //公司行业
	private String user_company_name; //公司名称
	private String user_company_address; //公司地址
	private String user_education; //学历
	private String user_id_card; //身份证号
	private String user_core; //用户积分
	private String user_schoole; //就读学校
	private String user_admission_time; //入学时间
	private String user_graduation_time; //毕业时间
	private String user_education_type; //用户教育类型
	private String user_status; //0启用1禁用
	private String is_authentication;//是否实名
	private String is_check;// 是否入住；
	private String check_time; // 入住时长；
	private String user_emergency_contact_name;
	private String user_emergency_contact_mobile;
	private String user_emergency_contact_type;
	private String sex;
	private Integer user_xinyong_score;
	private Double user_yajin_amount;//看房押金 
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_nickname() {
		return user_nickname;
	}
	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}
	public String getUser_img() {
		return user_img;
	}
	public void setUser_img(String user_img) {
		this.user_img = user_img;
	}
	public String getUser_login_name() {
		return user_login_name;
	}
	public void setUser_login_name(String user_login_name) {
		this.user_login_name = user_login_name;
	}
	public String getUser_login_pwd() {
		return user_login_pwd;
	}
	public void setUser_login_pwd(String user_login_pwd) {
		this.user_login_pwd = user_login_pwd;
	}
	public String getUser_mob() {
		return user_mob;
	}
	public void setUser_mob(String user_mob) {
		this.user_mob = user_mob;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_real_name() {
		return user_real_name;
	}
	public void setUser_real_name(String user_real_name) {
		this.user_real_name = user_real_name;
	}
	public String getUser_industry() {
		return user_industry;
	}
	public void setUser_industry(String user_industry) {
		this.user_industry = user_industry;
	}
	public String getUser_company_name() {
		return user_company_name;
	}
	public void setUser_company_name(String user_company_name) {
		this.user_company_name = user_company_name;
	}
	public String getUser_company_address() {
		return user_company_address;
	}
	public void setUser_company_address(String user_company_address) {
		this.user_company_address = user_company_address;
	}
	public String getUser_education() {
		return user_education;
	}
	public void setUser_education(String user_education) {
		this.user_education = user_education;
	}
	public String getUser_id_card() {
		return user_id_card;
	}
	public void setUser_id_card(String user_id_card) {
		this.user_id_card = user_id_card;
	}
	public String getUser_core() {
		return user_core;
	}
	public void setUser_core(String user_core) {
		this.user_core = user_core;
	}
	public String getUser_schoole() {
		return user_schoole;
	}
	public void setUser_schoole(String user_schoole) {
		this.user_schoole = user_schoole;
	}
	public String getUser_admission_time() {
		return user_admission_time;
	}
	public void setUser_admission_timev(String user_admission_time) {
		this.user_admission_time = user_admission_time;
	}
	public String getUser_graduation_time() {
		return user_graduation_time;
	}
	public void setUser_graduation_time(String user_graduation_time) {
		this.user_graduation_time = user_graduation_time;
	}
	public String getUser_education_type() {
		return user_education_type;
	}
	public void setUser_education_type(String user_education_type) {
		this.user_education_type = user_education_type;
	}
	public String getUser_status() {
		return user_status;
	}
	public void setUser_status(String user_status) {
		this.user_status = user_status;
	}
	public String getIs_authentication() {
		return is_authentication;
	}
	public void setIs_authentication(String is_authentication) {
		this.is_authentication = is_authentication;
	}
	public String getIs_check() {
		return is_check;
	}
	public void setIs_check(String is_check) {
		this.is_check = is_check;
	}
	public String getCheck_time() {
		return check_time;
	}
	public void setCheck_time(String check_time) {
		this.check_time = check_time;
	}
	public String getUser_emergency_contact_name() {
		return user_emergency_contact_name;
	}
	public void setUser_emergency_contact_name(String user_emergency_contact_name) {
		this.user_emergency_contact_name = user_emergency_contact_name;
	}
	public String getUser_emergency_contact_mobile() {
		return user_emergency_contact_mobile;
	}
	public void setUser_emergency_contact_mobile(String user_emergency_contact_mobile) {
		this.user_emergency_contact_mobile = user_emergency_contact_mobile;
	}
	public String getUser_emergency_contact_type() {
		return user_emergency_contact_type;
	}
	public void setUser_emergency_contact_type(String user_emergency_contact_type) {
		this.user_emergency_contact_type = user_emergency_contact_type;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setUser_admission_time(String user_admission_time) {
		this.user_admission_time = user_admission_time;
	}
	public Integer getUser_xinyong_score() {
		return user_xinyong_score;
	}
	public void setUser_xinyong_score(Integer user_xinyong_score) {
		this.user_xinyong_score = user_xinyong_score;
	}
	public Double getUser_yajin_amount() {
		return user_yajin_amount;
	}
	public void setUser_yajin_amount(Double user_yajin_amount) {
		this.user_yajin_amount = user_yajin_amount;
	}
	
	
	
}
