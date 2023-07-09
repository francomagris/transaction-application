package com.fmagris.transaction.domain.ports.out;

import java.util.Date;
import java.util.List;

import com.fmagris.transaction.domain.Account;
import com.fmagris.transaction.domain.Transaction;
import com.fmagris.transaction.domain.exceptions.AccountNotFoundException;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.AccountType;

public interface AccountPortOut {
	public Account getAcountByCbu(Long cbu) throws AccountNotFoundException;
	public Account updateAccount(Account account);

}
