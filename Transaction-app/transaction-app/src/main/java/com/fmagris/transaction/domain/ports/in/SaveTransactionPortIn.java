package com.fmagris.transaction.domain.ports.in;

import com.fmagris.transaction.domain.Transaction;

public interface SaveTransactionPortIn {
	public void saveTransaction (Transaction transaction) throws Exception;
}
