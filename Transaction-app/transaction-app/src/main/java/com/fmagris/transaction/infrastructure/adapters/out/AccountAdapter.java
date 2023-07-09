package com.fmagris.transaction.infrastructure.adapters.out;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.fmagris.transaction.domain.Account;
import com.fmagris.transaction.domain.exceptions.AccountNotFoundException;
import com.fmagris.transaction.domain.ports.out.AccountPortOut;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.AccountEntity;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.repositories.AccountRepository;

import jakarta.transaction.Transactional;

@Component
@Primary
public class AccountAdapter implements AccountPortOut {

	@Autowired
	private AccountRepository accountRepo;

	@Override
	public Account getAcountByCbu(Long cbu) throws AccountNotFoundException {
		AccountEntity account = accountRepo.findFirstByCbu(cbu).orElseThrow(() -> new AccountNotFoundException());
		return AccountEntity.mapToDTO(account);
	}

	@Override
	@Transactional
	public Account updateAccount(Account account) {
		AccountEntity a = accountRepo.save(new AccountEntity(account));
		System.out.println("Account CBU: "+ a.getCbu() + " - New balance: " + a.getBalance());
		return AccountEntity.mapToDTO(a);
	}

}
