package com.fmagris.transaction.app.infrastructure.adapters.out.persistence.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fmagris.transaction.domain.Transaction;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.AccountEntity;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.AccountType;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.MovementsEntity;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.TransactionEntity;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.repositories.MovementsRepository;

@DataJpaTest
class MovementsRepositoryTest {

	@Autowired
	MovementsRepository repo;

	@Test
	void findMovementsByPeriod_shouldReturnMovements() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		List<MovementsEntity> listall = repo.findAll();
		listall.size();

		List<MovementsEntity> list = repo.findMovementsByPeriod(1112587458950000L, sdf.parse("20230501"),
				sdf.parse("20230505"));
		
		assertFalse(list.isEmpty());
		assertTrue(list.size() == 1);
		
		list = repo.findMovementsByPeriod(1112587458955555L, sdf.parse("20230501"),
				sdf.parse("202305615"));
		
		assertFalse(list.isEmpty());
		assertTrue(list.size() == 2);
	}

}
