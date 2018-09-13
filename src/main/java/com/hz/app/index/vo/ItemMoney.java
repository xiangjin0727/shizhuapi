package com.hz.app.index.vo;

import java.io.Serializable;

public class ItemMoney implements Serializable{
	
	private String name;
	private String favorite;
	private String moneyLow;
	private String moneyHigh;
	private String type;
	private String scale;
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
	public String getFavorite() {
		return favorite;
	}
	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}
	public String getMoneyLow() {
		return moneyLow;
	}
	public void setMoneyLow(String moneyLow) {
		this.moneyLow = moneyLow;
	}
	public String getMoneyHigh() {
		return moneyHigh;
	}
	public void setMoneyHigh(String moneyHigh) {
		this.moneyHigh = moneyHigh;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
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
		return "ItemMoney [name=" + name + ", favorite=" + favorite + ", moneyLow=" + moneyLow + ", moneyHigh="
				+ moneyHigh + ", type=" + type + ", scale=" + scale + ", validity=" + validity + ", remark=" + remark
				+ "]";
	}
	
	
	

}
