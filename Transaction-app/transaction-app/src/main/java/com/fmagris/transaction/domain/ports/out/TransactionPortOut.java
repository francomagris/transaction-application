package com.fmagris.transaction.domain.ports.out;

import com.fmagris.transaction.domain.Account;
import com.fmagris.transaction.domain.Transaction;

public interface TransactionPortOut {
	public Transaction saveTransaction (Transaction transaction);
	public void deleteTransaction (Transaction transaction);
}
