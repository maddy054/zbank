package com.zbank.exceptions;

import com.zbank.enums.ErrorCode;

public class BankingException extends Exception{
	
	private ErrorCode erroCode;

	private static final long serialVersionUID = 1L;
	
	public BankingException(String message,Throwable throwable) {
		super(message,throwable);
	}
	public BankingException(String message) {
		super(message);
	}
	
	public BankingException(ErrorCode errorCode) {
		this.erroCode =errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return  erroCode;
	}
}
