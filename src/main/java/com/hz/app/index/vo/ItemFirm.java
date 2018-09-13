package com.hz.app.index.vo;

import java.io.Serializable;

public class ItemFirm implements Serializable{
	
	private String firmFull;
	private String firm;
	private String area;
	private String address;
	private String name;
	private String phone;
	private String wechat;
	private String userId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirmFull() {
		return firmFull;
	}
	public void setFirmFull(String firmFull) {
		this.firmFull = firmFull;
	}
	public String getFirm() {
		return firm;
	}
	public void setFirm(String firm) {
		this.firm = firm;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	@Override
	public String toString() {
		return "ItemFirm [firmFull=" + firmFull + ", firm=" + firm + ", area=" + area + ", address=" + address
				+ ", name=" + name + ", phone=" + phone + ", wechat=" + wechat + "]";
	}
	
    	
	
	

}
