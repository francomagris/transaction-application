package com.fmagris.transaction.app.infrastructure.adapters.out.persistence.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.AccountEntity;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.repositories.AccountRepository;


@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountRepositoryTest {

	@Autowired
	AccountRepository repo;
	
	
	@Test
	@Order(1)
	void getAccountByCbu_shouldReturnAccount() {
		Optional<AccountEntity> acc = repo.findFirstByCbu(1112587458950000L);
		assertTrue(acc.isPresent());
		assertEquals(acc.orElseThrow().getClientId(), "Franco-9f920f9c-ac23");
	}
	
	@Test
	@Order(2)
	void updateAccount_shouldReturnUpdatedAccount() {
		Optional<AccountEntity> acc = repo.findFirstByCbu(1112587458950000L);
		assertTrue(acc.isPresent());
		assertTrue(acc.get().getBalance() != 200.0);
		
		acc.get().setBalance(200.0);
		
		AccountEntity accSavedValue =  repo.save(acc.get());
		assertEquals(accSavedValue.getBalance(), 200.0);	
	}

}
