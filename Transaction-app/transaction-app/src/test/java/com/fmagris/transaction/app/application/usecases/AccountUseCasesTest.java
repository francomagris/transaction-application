package com.fmagris.transaction.app.application.usecases;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fmagris.transaction.application.usecases.AccountUseCases;
import com.fmagris.transaction.domain.Account;
import com.fmagris.transaction.domain.Movements;
import com.fmagris.transaction.domain.exceptions.AccountNotFoundException;
import com.fmagris.transaction.domain.ports.out.AccountPortOut;
import com.fmagris.transaction.domain.ports.out.MovementsPortOut;
import com.fmagris.transaction.infrastructure.adapters.out.persistence.entities.AccountType;

@SpringBootTest
class AccountUseCasesTest {
	
	@MockBean
	private AccountPortOut accountPort;
	
	@MockBean
	private MovementsPortOut movementsPort;
	
	@Autowired
	private AccountUseCases accountUseCases;
	
	private Account temporalAccount;
	private List<Movements> movements;
	private Movements movement;
	
	
	@BeforeEach
	public void setUp() {
		temporalAccount = Account.builder()
				.cbu(12345L)
				.balance(500.0)
				.clientId("fmagris-45s55d2258f1g4g85h25")
				.accountType(new AccountType()).build();
		
		movements = new ArrayList<>();
		
		movement = Movements.builder()
				.id(1L)
				.account(temporalAccount)
				.amount(200.0)
				.date(new Date())
				.build();
		
		movements.add(Movements.builder()
				.id(1L)
				.account(temporalAccount)
				.amount(200.0)
				.date(new Date())
				.build());
		
		movements.add(Movements.builder()
				.id(2L)
				.account(temporalAccount)
				.amount(500.0)
				.date(new Date())
				.build());
		
	}
	
	@Test
	public void getAccountByCbu_shouldReturnTemporalAccount() {
		try{
			when(accountPort.getAcountByCbu(12345L)).thenReturn(temporalAccount);
			Account result = accountUseCases.getAcountByCbu(12345L);
			
			assertEquals(temporalAccount, result);
			verify(accountPort,times(1)).getAcountByCbu(12345L);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getAccountByCbu_shouldReturnAccountNotFoundException() throws AccountNotFoundException {
		when(accountPort.getAcountByCbu(12345L)).thenThrow(AccountNotFoundException.class);
		
		assertThrows(AccountNotFoundException.class, () ->{
			accountUseCases.getAcountByCbu(12345L);
		});
	}
	
	@Test
	public void getMovements_shouldReturnTemporalMovements() throws Exception {
		Calendar c = Calendar.getInstance();
		Date dateTo = c.getTime();
		
		Date dateFrom = c.getTime();
		c.add(Calendar.DATE, -15);
				
		when(movementsPort.getAccountMovements(12345L, dateFrom, dateTo)).thenReturn(movements);
		
		List<Movements> result = accountUseCases.getMovements(12345L, dateFrom, dateTo);
		
		assertTrue(!result.isEmpty());
		assertEquals(movements, result);
		verify(movementsPort, times(1)).getAccountMovements(12345L, dateFrom, dateTo);
			
	}
}


