package com.hz.app.index.vo;

import java.io.Serializable;

public class AllInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String  numInfo;
	private String  numReport;
	private String  numPublish;
	private String  numGroup;
	public String getNumInfo() {
		return numInfo;
	}
	public void setNumInfo(String numInfo) {
		this.numInfo = numInfo;
	}
	public String getNumReport() {
		return numReport;
	}
	public void setNumReport(String numReport) {
		this.numReport = numReport;
	}
	public String getNumPublish() {
		return numPublish;
	}
	public void setNumPublish(String numPublish) {
		this.numPublish = numPublish;
	}
	public String getNumGroup() {
		return numGroup;
	}
	public void setNumGroup(String numGroup) {
		this.numGroup = numGroup;
	}
}
