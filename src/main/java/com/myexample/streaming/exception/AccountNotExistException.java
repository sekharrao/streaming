package com.myexample.streaming.exception;

public class AccountNotExistException extends Exception {

	private static final long serialVersionUID = -5544172069421781849L;

	public AccountNotExistException(String msg) {
		super(msg);
	}
	
}
