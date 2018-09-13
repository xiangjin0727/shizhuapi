package com.hz.app.index.vo;

import java.io.Serializable;

public class ItemProject implements Serializable{
	
	private String name;
	private String projectName;
	private String moneyLow;
	private String moneyHigh;
	private String type;
	private String scale;
	private String validity;
	private String remark;
	private String perTime;
	private String perMoney;
	private String monthMoney;
	private String dateStart;
	private String userId;
	private String perMoneyHigh;
	public String getPerMoneyHigh() {
		return perMoneyHigh;
	}
	public void setPerMoneyHigh(String perMoneyHigh) {
		this.perMoneyHigh = perMoneyHigh;
	}
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
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	public String getPerTime() {
		return perTime;
	}
	public void setPerTime(String perTime) {
		this.perTime = perTime;
	}
	public String getPerMoney() {
		return perMoney;
	}
	public void setPerMoney(String perMoney) {
		this.perMoney = perMoney;
	}
	public String getMonthMoney() {
		return monthMoney;
	}
	public void setMonthMoney(String monthMoney) {
		this.monthMoney = monthMoney;
	}
	public String getDateStart() {
		return dateStart;
	}
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}
	@Override
	public String toString() {
		return "ItemProject [name=" + name + ", projectName=" + projectName + ", moneyLow=" + moneyLow + ", moneyHigh="
				+ moneyHigh + ", type=" + type + ", scale=" + scale + ", validity=" + validity + ", remark=" + remark
				+ ", perTime=" + perTime + ", perMoney=" + perMoney + ", monthMoney=" + monthMoney + ", dateStart="
				+ dateStart + "]";
	}
	
	
	
	

}
