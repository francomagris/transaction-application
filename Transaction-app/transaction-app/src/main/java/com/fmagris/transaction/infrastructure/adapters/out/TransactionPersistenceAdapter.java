package com.fmagris.transaction.infrastructure.adapters.out;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.fmagris.transaction.domain.Account;
import com.fmagris.transaction.domain.Transaction;
import com.fmagris.transaction.domain.ports.out.TransactionPortOut;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.AccountEntity;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.TransactionEntity;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.repositories.AccountRepository;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.repositories.TransactionRepository;

import jakarta.transaction.Transactional;

@Component
public class TransactionPersistenceAdapter implements TransactionPortOut {
	
	@Autowired
	private TransactionRepository transactionRepo;

	@Override
	@Transactional
	public Transaction saveTransaction(Transaction transaction) {
		return transactionRepo.save(new TransactionEntity(transaction)).entityToDto();
	}

	@Override
	public void deleteTransaction(Transaction transaction) {
		transactionRepo.delete(new TransactionEntity(transaction));
	}
	

}
