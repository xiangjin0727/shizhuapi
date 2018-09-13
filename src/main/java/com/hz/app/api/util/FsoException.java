package com.hz.app.api.util;

public class FsoException extends Exception {

	private static final long serialVersionUID = -4074308932356115244L;
	
	protected String name;
	protected Object data;

	public FsoException(String name, Object data) {
		super(name);

		this.name = name;
		this.data = data;
	}

	public FsoException(String name) {
		this(name, null);
	}

	public String getName() {
		return name;
	}

	public Object getData() {
		return data;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(name);

		if (data != null) {
			buf.append(": ").append(data);
		}

		return buf.toString();
	}
}