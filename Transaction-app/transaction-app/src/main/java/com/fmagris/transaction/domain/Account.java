package com.fmagris.transaction.domain;

import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.AccountType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {
	private Long cbu;
	private Double balance;
	private String clientId;
    private AccountType accountType;
    
    
    public void debit(Double amount) {
    	this.setBalance(this.balance - amount);
    }
	
    
    public void credit(Double amount) {
    	this.setBalance(this.balance + amount);
    }
}
