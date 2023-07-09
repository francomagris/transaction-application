package com.fmagris.transaction.domain.exceptions;

public class AccountNotFoundException extends Exception{
	
	public AccountNotFoundException() {
		super("Account not found with the given data");
		
	}
	
	public AccountNotFoundException(String msj) {
		super(msj);
		
	}
}
