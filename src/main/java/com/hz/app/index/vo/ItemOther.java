package com.hz.app.index.vo;

import java.io.Serializable;

public class ItemOther implements Serializable{
	
	private String name;
	private String type;
	private String validity;
	private String remark;
	private String userId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "ItemOther [name=" + name + ", type=" + type + ", validity=" + validity + ", remark=" + remark + "]";
	}

	

}
