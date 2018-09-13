package com.hz.core.util;

import java.io.Serializable;

public class CommonResultVo implements Serializable {
	private static final long serialVersionUID = -7710890007062678194L;

	protected boolean success;
	protected String message;
	private Object dataOrMessage;
	
	public CommonResultVo() {
		super();
	}

	public CommonResultVo(boolean success, String message) {
		this();
		this.success = success;
		this.message = message;
	}
	
	public CommonResultVo(boolean success, String message,Object dataOrMessage) {
		this();
		this.success = success;
		this.message = message;
		this.dataOrMessage = dataOrMessage;
	}
	

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public void setDataOrMessage(Object dataOrMessage) {
		this.dataOrMessage = dataOrMessage;
	}


	public Object getDataOrMessage() {
		return dataOrMessage;
	}	
}
