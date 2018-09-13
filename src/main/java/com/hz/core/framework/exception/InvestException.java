package com.hz.core.framework.exception;

public class InvestException extends RuntimeException {
	private static final long serialVersionUID = 4015661005860144464L;
	
	public InvestException() {
		super();
	}
	
	public InvestException(Throwable cause) {
		super(cause);
	}
	
	public InvestException(String message) {
		super(message);
	}
	
	public InvestException(String message, Throwable cause) {
		super(message, cause);
	}
}
