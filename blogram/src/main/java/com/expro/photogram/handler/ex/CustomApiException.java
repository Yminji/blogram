package com.expro.photogram.handler.ex;

public class CustomApiException extends RuntimeException{
	private static final long serialVersionID = 1L;
	
	public CustomApiException(String message) {
		super(message);
	}
}
