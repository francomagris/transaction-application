package com.fmagris.transaction.domain.ports.in;

import com.fmagris.transaction.domain.Account;
import com.fmagris.transaction.domain.exceptions.AccountNotFoundException;

public interface AccountPortIn {
	public Account getAcountByCbu(Long cbu) throws AccountNotFoundException;
}
