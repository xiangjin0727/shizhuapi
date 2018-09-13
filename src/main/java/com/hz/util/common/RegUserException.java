package com.hz.util.common;

public class RegUserException extends RuntimeException {
	private static final long serialVersionUID = 4015661005860144464L;
	
	public RegUserException() {
		super();
	}
	
	public RegUserException(Throwable cause) {
		super(cause);
	}
	
	public RegUserException(String message) {
		super(message);
	}
	
	public RegUserException(String message, Throwable cause) {
		super(message, cause);
	}
}
